package mypackage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class CarRentalNew extends HttpServlet {

  int cont = 0;

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
    res.setContentType("text/html; charset=UTF-8");
    res.setCharacterEncoding("UTF-8");
    PrintWriter out = res.getWriter();
    String model = req.getParameter("model_vehicle");
    String submodel = req.getParameter("sub_model_vehicle");
    String dieslloguer = req.getParameter("dies_lloguer");
    String numVehicles = req.getParameter("num_vehicles");
    String descompte = req.getParameter("descompte");
    Car rentedCar = null;
    try {
      rentedCar = new Car(model, submodel, dieslloguer, numVehicles, descompte);
    }catch(Exception e){
      out.println("Error al formulari, motius: " + e.getMessage());
      return;
    }

    cont++;
    out.println("<html>"+rentedCar.printeableInfo()+"</html>");
    new JsonWriter().addRental(rentedCar);
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
    doGet(req, res);
  }
}
