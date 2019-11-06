package main

import(
    "fmt"
    "log"
    "net/http"
    "github.com/gorilla/mux"
    "encoding/json"
    "io"
    "io/ioutil"
    "os"
    "encoding/csv"
    "strconv"
    "bufio"
)

type Car struct {
    CarMaker string
    CarModel string
    Ndays int
    Nunits int
}

func main() {

router := mux.NewRouter().StrictSlash(true)
router.HandleFunc("/", Index)
router.HandleFunc("/newRental", endpointAddRental)
router.HandleFunc("/viewRentals", endpointViewRentals)

log.Fatal(http.ListenAndServe(":8080", router))
}

func Index(w http.ResponseWriter, r *http.Request) {
    fmt.Fprintln(w, "Service OK")
}

func endpointAddRental(w http.ResponseWriter, r *http.Request) {
    var newCar Car
    body, err := ioutil.ReadAll(io.LimitReader(r.Body, 1048576))
    if err != nil {
        panic(err)
    }
    if err := r.Body.Close(); err != nil {
        panic(err)
    }
    if err := json.Unmarshal(body, &newCar); err != nil {
        w.Header().Set("Content-Type", "application/json; charset=UTF-8")
        w.WriteHeader(422) // unprocessable entity
        if err := json.NewEncoder(w).Encode(err); err != nil {
            panic(err)
        }
    } else {
        fmt.Fprintln(w, "Successfully added a new rental, price: ", calcularPrice(newCar))
		fmt.Println(newCar)
		writeToFile(w, newCar)
    }
}

func endpointViewRentals(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json; charset=UTF-8")
    w.WriteHeader(422) // unprocessable entity
	readFromFile(w)
}

func calcularPrice(newCar Car) (int){
	return int(30*newCar.Ndays*newCar.Nunits)
}

func writeToFile(w http.ResponseWriter, newCar Car) {
    file, err := os.OpenFile("rentals.csv", os.O_APPEND|os.O_WRONLY|os.O_CREATE, 0600)
    if err!=nil {
        json.NewEncoder(w).Encode(err)
        return
    }
    writer := csv.NewWriter(file)
    var data1 = []string{newCar.CarMaker, newCar.CarModel, strconv.Itoa(newCar.Ndays), strconv.Itoa(newCar.Nunits)}
    writer.Write(data1)
    writer.Flush()
    file.Close()
}
func readFromFile(w http.ResponseWriter) {
	var cars []Car
	file, err := os.Open("rentals.csv")
		if err!=nil {
		json.NewEncoder(w).Encode(err)
		return
		}
		reader := csv.NewReader(bufio.NewReader(file))
		for {
			record, err := reader.Read()
			if err == io.EOF {
					break
				}
				days, err := strconv.Atoi(record[2])
				units, err := strconv.Atoi(record[3])
				cars = append(cars, 
				Car{CarMaker: record[0], CarModel: record[1], Ndays:days, Nunits:units})
		}
		
		if err := json.NewEncoder(w).Encode(cars); err != nil {
            panic(err)
        }
		
}

