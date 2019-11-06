package mypackage;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.Iterator;

public class CarRentalList extends HttpServlet {

  int cont = 0;

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();
    String nombre = req.getParameter("userid");
    String passwd = req.getParameter("password");
    if(!inputValid(nombre,passwd)){
      out.println("<html><h1>User i/o password buit</h1></html>");
      return;
    }
    cont ++;
    out.println("<html><big>Hola "+ nombre + "</big><br>"+
                cont + " Accesos desde su carga.<br></html>");

    String output = "<html> ";
    try{
      JSONParser parser = new JSONParser();

      JSONObject jObject = (JSONObject) parser.parse(new FileReader("test.json"));
      JSONArray jArray = (JSONArray) jObject.get("rentals");
      
      for(int i = 0; i < jArray.size(); ++i){
        Car carRental = new Car((JSONObject) jArray.get(i));
        output += carRental.printeableInfo();
        output += "<br>";
      }
    }catch(Exception e){
      out.println("Error llegint el JSON " + e);
    }
    output += " </html>";
    out.println(output);
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res)
                    throws ServletException, IOException {
    doGet(req, res);
  }

  private boolean inputValid(String nom, String passwd){
    //check passwd es valid
    return nom != null && passwd != null && !nom.isEmpty() && !passwd.isEmpty();

  }
}
