package test2021.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try {
            TicTacToe game = new TicTacToe();
            char winner;
            // repeat until there is a winner or a draw
            while ((winner = game.getWinner()) == TicTacToe.EMPTY) {
                System.out.println(game);
                System.out.printf("Player '%s', your move!%n", game.getNextPlayer());

                String input = in.readLine();

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
                        System.out.println("Illegal move: " + ex.getMessage());
                    }
                } catch (IllegalArgumentException ex) {
                    System.out.println("Invalid input");
                }
            }

            System.out.println(game);
            if (winner == TicTacToe.DRAW) {
                System.out.println("A draw, as expected...");
            } else {
                System.out.printf("Player '%s' won! '%s', shame!", winner, TicTacToe.getOtherPlayer(winner));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
