package edu.escuelaing.arep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ReflexCalculator {
        public ReflexCalculator(){

        }

        public static String getOperation(String path) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
            Class <?> c = Math.class;
            Method method = null;

            method = c.getMethod(path);
            Object response = method.invoke(method).toString();

            return (String) response;

        }
}

