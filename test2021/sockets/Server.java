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
            TicTacToe game = new TicTacToe();
            char winner;
            // repeat until there is a winner or a draw
            while ((winner = game.getWinner()) == TicTacToe.EMPTY) {
                writer.println(game);
                writer.printf("Player '%s', your move!%n", game.getNextPlayer());
                writer.println("INPUT!!");

                String input = reader.readLine();

                try {
                    // the input should look like "put x on 1 1"
                    String[] parts = input.split(" ");
                    // - length must be 5
                    if (parts.length != 5) throw new IllegalArgumentException();
                    // - [0] must be "put"
                    if (!parts[0].equals("put")) throw new IllegalArgumentException();
                    // - [1] must be one character
                    if (parts[1].length() != 1) throw new IllegalArgumentException();
                    // - [2] must be "on"
                    if (!parts[2].equals("on")) throw new IllegalArgumentException();

                    char player = parts[1].charAt(0);

                    // - [1] must be X or O
                    if (player != TicTacToe.X && player != TicTacToe.O) throw new IllegalArgumentException();

                    // - [3] must be an integer
                    int x = Integer.parseInt(parts[3]);
                    // - [4] must be an integer
                    int y = Integer.parseInt(parts[4]);

                    // extra try/catch, because when the error comes from here we want a different message
                    try {
                        game.makeMove(player, x, y);
                    } catch (IllegalArgumentException ex) {
                        writer.println("Illegal move: " + ex.getMessage());
                    }
                } catch (IllegalArgumentException ex) {
                    writer.println("Invalid input");
                }
            }

            writer.println(game);
            if (winner == TicTacToe.DRAW) {
                writer.println("A draw, as expected...");
            } else {
                writer.printf("Player '%s' won! '%s', shame!", winner, TicTacToe.getOtherPlayer(winner));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
