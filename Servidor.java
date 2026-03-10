package servidor;

import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) throws IOException {
        // Creamos el servidor en el puerto 5000
        ServerSocket servidor = new ServerSocket(5000);
        System.out.println("Servidor esperando conexiones...");

        // Bucle infinito para aceptar múltiples clientes
        while (true) {
            // Esperamos a que se conecte un cliente
            Socket socket = servidor.accept();
            System.out.println("Cliente conectado: " + socket.getInetAddress());

            // Creamos un hilo nuevo para atender a este cliente
            // Así el servidor queda libre para aceptar el siguiente
            new Thread(new Manejarcliente()).start();
        }
    }
}
