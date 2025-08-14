import java.io.*;
import java.net.Socket;

public class ToUppercaseClient extends Thread {

    @Override
    public void run() {
        try (Socket socket = new Socket("127.0.0.1", 6010);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to server");

            handleUserInput(reader, writer, userInput);

        } catch (IOException e) {
            System.out.println("[Client] I/O error: " + e.getMessage());
        }
    }

    private void handleUserInput(BufferedReader reader, BufferedWriter writer, BufferedReader userInput) throws IOException {
        String line;
        while (true) {
            System.out.print("Enter line: ");
            line = userInput.readLine();

            // send to server
            writer.write(line);
            writer.newLine();
            writer.flush();

            if (line.trim().equals("<end>")) {
                System.out.println("Logging off...");
                break;
            }

            // read response
            String response = reader.readLine();
            System.out.println("Response: " + response);
        }
    }
}
