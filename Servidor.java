import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        try {
            // Se inicializa el socket en el puerto 5000 como indica el ejemplo [cite: 3]
            ServerSocket servidor = new ServerSocket(5000);
            System.out.println("Servidor multicliente esperando conexiones...");

            // Bucle infinito: permite que el servidor no se cierre tras el primer cliente [cite: 4]
            while (true) {
                // El servidor se queda "escuchando" hasta que alguien se conecta [cite: 10]
                Socket socket = servidor.accept();
                System.out.println("Cliente conectado: " + socket.getInetAddress());

                // LA CLAVE: Creamos un hilo (Thread) nuevo para cada cliente.
                // Esto permite que el servidor atienda a varios a la vez sin bloquearse.
                new Thread(new AtencionCliente(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/**
 * Esta clase se encarga de la lógica individual de cada cliente conectado.
 * Mantiene la estructura de recibir mensaje y enviar respuesta del ejemplo[cite: 5, 6].
 */
class AtencionCliente implements Runnable {
    private Socket socket;

    public AtencionCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // Entrada de datos: Leemos lo que el cliente nos envía
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String mensaje = entrada.readLine();
            System.out.println("Mensaje recibido: " + mensaje);

            // Salida de datos: Respondemos al cliente
            // Creamos un flujo de salida para enviar datos al cliente a través del socket
            // El parámetro 'true' activa el "auto-flush" (vaciado automático)
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            salida.println("Hola desde el servidor multicliente");

            // Cerramos la conexión con ESTE cliente específico
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
