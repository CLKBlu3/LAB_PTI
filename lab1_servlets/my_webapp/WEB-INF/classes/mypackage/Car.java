package mypackage;
import org.json.simple.JSONObject;

public class Car{
    private String model;
    private String subModel;
    private int diesLloguer;
    private int numVehicles;
    private double descompte;

    public Car(String model,String submodel,String dieslloguer,String numVehicles,String descompte) throws Exception {
        this.model = model;
        this.subModel = submodel;
        this.diesLloguer = Integer.parseInt(dieslloguer);
        this.numVehicles = Integer.parseInt(numVehicles);
        this.descompte = Double.parseDouble(descompte);
        checkValid();
    }

    public Car(JSONObject aux){
        this.model = String.valueOf(aux.get("model_vehicle"));
        this.subModel = String.valueOf(aux.get("sub_model"));
        this.numVehicles = Integer.parseInt(String.valueOf(aux.get("num_vehicles")));
        this.diesLloguer = Integer.parseInt(String.valueOf(aux.get("dies_lloguer")));
        this.descompte = Double.parseDouble(String.valueOf(aux.get("descompte")));
    }
    private void checkValid() throws Exception {
        String motius = "";
        if(!checkModelValid()) motius += "Model no es valid | ";
        if(!checkSubModelValid()) motius += "Combustible no es valid | ";
        if(!checkDiesLloguer()) motius += "La reserva ha de ser per 1 o mes dies | ";
        if(!checkNumVehicles()) motius += "Has de reservar 1 o mes vehicles | ";
        if(!checkDescompte()) motius += "Descompte no pot ser negatiu ni superior a 100| ";
        if(!motius.equals("")) throw new Exception(motius);
    }

    private boolean checkDescompte() {
        return (descompte >= 0.0 && descompte <= 100.0);
    }

    private boolean checkNumVehicles() {
        return numVehicles > 0;
    }

    private boolean checkDiesLloguer(){
        return diesLloguer > 0;
    }

    private boolean checkModelValid() {
        return "54".equals(model) || "71".equals(model) || "82".equals(model) || "139".equals(model);
    }

    private boolean checkSubModelValid() {
        return "Gasolina".equals(subModel) || "Diesel".equals(subModel);
    }

    public String printeableInfo(){
        return "<p><b>model_vehicle: </b>"+ model + "</p>" +
                "<p><b>sub_model_vehicle: </b>"+ subModel +"</p>"+
                "<p><b>dies_lloguer: </b>"+ diesLloguer +"</p>"+
                "<p><b>num_vehicles: </b>"+ numVehicles +"</p>"+
                "<p><b>descompte: </b>"+ descompte +"</p>";
    }
    public JSONObject toJSON() {
        JSONObject vehicle = new JSONObject();
        vehicle.put("model_vehicle", model);
        vehicle.put("sub_model", subModel);
        vehicle.put("num_vehicles", numVehicles);
        vehicle.put("dies_lloguer", diesLloguer);
        vehicle.put("descompte", descompte);
        return vehicle;
    }
}