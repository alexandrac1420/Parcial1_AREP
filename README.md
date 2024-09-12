# Parcial

### Instalación

1. Clona el repositorio y navega al directorio del proyecto:
    ```sh
    git clone https://github.com/alexandrac1420/Parcial1_AREP.git
    cd Parcial1_AREP
    ```

2. Compila el proyecto:
    ```sh
    mvn package
    ```

    Deberías ver una salida similar a esta:
    ```sh
    [INFO] --- jar:3.3.0:jar (default-jar) @ servidor_web_concurrente ---
    [INFO] Building jar: C:\Users\alexandra.cortes.LABINFO\Desktop\Parcial1_AREP\target\parcial-1.0-SNAPSHOT.jar
    [INFO] BUILD SUCCESS
    ```

3. Ejecuta la aplicación:
    ```sh
    java -cp target/parcial-1.0-SNAPSHOT.jar edu.escuelaing.arep.ReflexCalculator 
    ```

    Al ejecutar la aplicación, deberías ver el siguiente mensaje:

    ```
    Escuchando por el puerto 36000
    ```

    Luego ejecuta el servicio de fachada en otra terminal o máquina virtual:

     ```sh
    java -cp target/parcial-1.0-SNAPSHOT.jar edu.escuelaing.arep.HttpServerFachada 
    ```

    Al ejecutar la aplicación, deberías ver el siguiente mensaje:

    ```
    Escuchando por el puerto 35000
    ```

    Ahora puedes acceder a la página `http://localhost:35000/`

    ## Ejemplo de uso

### Interfaz Web

1. Accede a la interfaz web en tu navegador a través de [http://localhost:35000/](http://localhost:35000/).
2. Ingresa una operación en el formato `comando(param1,param2)`, como `pow(2,3)` o `pi()`.
3. Presiona el botón "Calcular" y verás el resultado en pantalla.
![image](https://github.com/user-attachments/assets/d6e35e2b-c31d-4255-814e-8679741e6ce2)
![image](https://github.com/user-attachments/assets/10118a98-0f55-40fd-ae6e-11282ff6f9d1)
![image](https://github.com/user-attachments/assets/b5721e1b-79dd-44b9-bf02-d55463114354)


