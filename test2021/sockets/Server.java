package test2021.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (
            ServerSocket server = new ServerSocket(5000);
        ) {
            while (true) {
                Socket socket = server.accept();
                new Thread(new ClientHandler(socket)).start();
                // oder:
                // new Thread(() -> handleClient(socket)).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void handleClient(Socket socket) {
        try (
            // socket;
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
        ) {
            System.out.println("todo");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
