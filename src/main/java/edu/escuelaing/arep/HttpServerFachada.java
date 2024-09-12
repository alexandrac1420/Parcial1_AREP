package edu.escuelaing.arep;

import java.net.*;
import java.io.*;

public class HttpServerFachada {
   public static void main(String[] args) throws IOException {
      ServerSocket serverSocket = null;
      try {
         serverSocket = new ServerSocket(35000);
      } catch (IOException e) {
         System.err.println("Could not listen on port: 35000.");
         System.exit(1);
      }
      Socket clientSocket = null;

      try {
         System.out.println("Listo para recibir ...");
         clientSocket = serverSocket.accept();
      } catch (IOException e) {
         System.err.println("Accept failed.");
         System.exit(1);
      }
      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      String inputLine, outputLine;
      while ((inputLine = in.readLine()) != null) {
         System.out.println("Recibí: " + inputLine);
         if (!in.ready()) {
            break;
         }
      }
      outputLine = "HTTP/1.1 200 OK\r\n"
            + "Content-Type: text/html\r\n"
            + "\r\n"
            + "<!DOCTYPE html>\n"
            + "<html>\n"
            + "<head>\n"
            + "<meta charset=\"UTF-8\">\n"
            + "<title>calculadora</title>\n"
            + "</head>\n"
            + "<body>\n"
            + "<h1>calculadora</h1>\n"
            + "<form action=\"/calculadora\">\n"
            + "<label for=\"name\">Name:</label><br>\n"
            + "<input type=\"text\" id=\"comando\" name=\"comando\" value=\"pow\"><br><br>\n"
            + "<input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n"
            + "</form>\n"
            + "<div id=\"getrespmsg\"></div>\n"
            + "<script>\n"
            + "function loadGetMsg() {\n"
            + "let nameVar = document.getElementById(\"comando\").value;\n"
            + "const xhttp = new XMLHttpRequest();\n"
            + "xhttp.onload = function() {\n"
            + "document.getElementById(\"getrespmsg\").innerHTML =\n"
            + "this.responseText;\n"
            + "}\n"
            + "xhttp.open(\"GET\", \"/computar?comando=\"+nameVar);\n"
            + "xhttp.send();\n"
            + "</script>\n"
            + "</body>\n"
            + "</html>\n";
      out.println(outputLine);
      out.close();
      in.close();
      clientSocket.close();
      serverSocket.close();
   }


}