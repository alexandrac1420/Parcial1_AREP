package edu.escuelaing.arep;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ReflexCalculator {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(36000);
        System.out.println("Escuchando por el puerto 36000");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            String command = null;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.startsWith("GET /compreflex?comando=")) {
                    command = inputLine.split("=")[1].split(" ")[0];
                    break;
                }
                if (!in.ready()) {
                    break;
                }
            }

            if (command != null) {
                String operation = extractOperation(command);
                List<Double> params = extractParameters(command);

                String result = getOperation(operation, params);
                out.println("HTTP/1.1 200 OK\r\n" + "Content-Type: application/json\r\n" + "\r\n" + "{\"resultado\": " + result + "}");
            }
            out.close();
            in.close();
            clientSocket.close();
        }
    }

    public static String extractOperation(String input) {
        return input.substring(0, input.indexOf('('));
    }

    public static List<Double> extractParameters(String input) {
        List<Double> params = new ArrayList<>();
        String paramString = input.substring(input.indexOf('(') + 1, input.indexOf(')'));
        if (!paramString.isEmpty()) {
            String[] paramArray = paramString.split(",");
            for (String param : paramArray) {
                params.add(Double.parseDouble(param.trim())); 
            }
        }
        return params;
    }

    public static String getOperation(String path, List<Double> params) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if ("pi".equals(path)) {
            return String.valueOf(Math.PI);
        } else if ("bbl".equals(path)) {
            return bubbleSort(params).toString();
        } else {
            Class<?> c = Math.class;
            if (params.size() == 1) {
                Method method = c.getMethod(path, double.class);
                Double result = (Double) method.invoke(null, params.get(0));
                return result.toString();
            } else if (params.size() == 2) {
                Method method = c.getMethod(path, double.class, double.class);
                Double result = (Double) method.invoke(null, params.get(0), params.get(1));
                return result.toString();
            } else {
                throw new IllegalArgumentException("Número incorrecto de parámetros");
            }
        }
    }

    public static List<Double> bubbleSort(List<Double> numbers) {
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = 0; j < numbers.size() - 1 - i; j++) {
                if (numbers.get(j) > numbers.get(j + 1)) {
                    Double temp = numbers.get(j);
                    numbers.set(j, numbers.get(j + 1));
                    numbers.set(j + 1, temp);
                }
            }
        }
        return numbers;
    }
}
