package test2021.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (
            Socket socket = new Socket("localhost", 5000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        ) {
            String input;
            while((input = reader.readLine()) != null) {
                if (input.equals("INPUT!!")) {
                    writer.println(in.readLine());
                } else {
                    System.out.println(input);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
