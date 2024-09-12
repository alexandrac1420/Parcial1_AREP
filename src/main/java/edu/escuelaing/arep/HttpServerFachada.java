package edu.escuelaing.arep;

import java.net.*;
import java.io.*;

public class HttpServerFachada {
   public static void main(String[] args) throws IOException {
      ServerSocket serverSocket = new ServerSocket(35000);
      System.out.println("Escuchando por el puerto 35000");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            String command = null;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Recibi: " + inputLine);
                if (inputLine.startsWith("GET /computar?comando=")) {
                    command = inputLine.split("=")[1].split(" ")[0];
                    break;
                }
                if (!in.ready()) {
                    break;
                }
            }

            if (command != null) {
                URL reflexCalculatorUrl = new URL("http://localhost:36000/compreflex?comando=" + command);
                HttpURLConnection conn = (HttpURLConnection) reflexCalculatorUrl.openConnection();
                conn.setRequestMethod("GET");
                BufferedReader calcIn = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String response = calcIn.readLine();
                calcIn.close();

                out.println("HTTP/1.1 200 OK\r\n" + "Content-Type: application/json\r\n" + "\r\n" + response);
            } else {
                String clientHtml = getClientHtml();
                out.println("HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n" + "\r\n" + clientHtml);
            }

            out.close();
            in.close();
            clientSocket.close();
        }
    }

    public static String getClientHtml() {
        return "<!DOCTYPE html>\n"
            + "<html>\n"
            + "<head>\n"
            + "<meta charset=\"UTF-8\">\n"
            + "<title>Calculadora Reflexiva</title>\n"
            + "</head>\n"
            + "<body>\n"
            + "<h1>Calculadora Reflexiva</h1>\n"
            + "<form>\n"
            + "<label for=\"comando\">Comando con parametros:</label><br>\n"
            + "<input type=\"text\" id=\"comando\" name=\"comando\" placeholder=\"pow(2,3)\"><br><br>\n"
            + "<input type=\"button\" value=\"Calcular\" onclick=\"loadGetMsg()\">\n"
            + "</form>\n"
            + "<div id=\"getrespmsg\"></div>\n"
            + "<script>\n"
            + "function loadGetMsg() {\n"
            + "let comando = document.getElementById(\"comando\").value;\n"
            + "const xhttp = new XMLHttpRequest();\n"
            + "xhttp.onload = function() {\n"
            + "document.getElementById(\"getrespmsg\").innerHTML = this.responseText;\n"
            + "}\n"
            + "xhttp.open(\"GET\", \"/computar?comando=\" + comando);\n"
            + "xhttp.send();\n"
            + "}\n"
            + "</script>\n"
            + "</body>\n"
            + "</html>";
    }
}