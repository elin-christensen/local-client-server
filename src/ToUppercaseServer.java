import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ToUppercaseServer extends Thread {

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(6010)) {
            System.out.println("Server started, waiting for client...");

            try (Socket socket = serverSocket.accept();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            
                handleClientInput(reader, writer);

            }

        } catch (IOException e) {
            System.out.println("[Server] I/O error: " + e.getMessage());
        }
    }

    private void handleClientInput(BufferedReader reader, BufferedWriter writer) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().equals("<end>")) {
                System.out.println("Connection closed");
                break;
            }

            System.out.println("Received: " + line);
            writer.write(line.toUpperCase());
            writer.newLine(); 
            writer.flush();
        }
    }
}
