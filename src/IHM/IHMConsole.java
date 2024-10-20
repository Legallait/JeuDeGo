package IHM;

import go.Factory;
import go.GoGame;
import go.Player;
import utils.Coord;

import java.awt.*;
import java.io.Console;
import java.util.*;


public class IHMConsole {
    private static final Scanner sc = new Scanner(System.in);
    private static GoGame go;
    private static HashMap<Color, Player> players;
    private static Player currentPlayer;
    private static final int NO_ID = -1;

    public static void session(Player black, Player white) {
        String command = "start";
        go = new GoGame();
        players = new HashMap<>();
        players.put(Color.BLACK, black);
        players.put(Color.WHITE, white);
        currentPlayer = players.get(Color.BLACK);

        while (!go.isOver()) {
            if (!(currentPlayer.getClass().equals(Factory.create("console").getClass()))) {
                String[] args = currentPlayer.getMove(go);

                switch (args[0]) {
                    case "play":
                        play(NO_ID, args);
                        break;
                    case "pass":
                        System.out.println("RandomPlayer passes");
                        pass(NO_ID, args);
                        break;
                }
                continue;
            }

            command = sc.nextLine().trim();
            String[] args = command.split(" ");

            command = args[0];
            int id = NO_ID;
            if (isNumeric(command)) {
                id = Integer.parseInt(command);
                command = args[1];
                args = Arrays.copyOfRange(args, 1, args.length);
            }

            switch (command) {
                case "boardsize":
                    boardsize(id, args);
                    break;
                case "showboard":
                    showboard(id);
                    break;
                case "clear_board":
                    clearBoard(id);
                    break;
                case "play":
                    play(id, args);
                    break;
                case "pass":
                    pass(id, args);
                    break;
                case "liberties":
                    getLiberties(id, args);
                    break;
                case "player":
                    player(id, args);
                    break;
                case "quit":
                    quit(id);
                    break;
                default:
                    displayErrorMessage(id, "unknown command");
                    break;
            }
        }
    }

    private static void play(int id, String[] args) {
        if (args.length != 3) {
            displayErrorMessage(id, "syntax error");
            return;
        }
        String[] result = go.playMove(args[1], args[2]);
        if (result[0].equals("error")) {
            displayErrorMessage(id, result[1]);
        } else {
            displaySuccessMessage(id, result[1]);
            switchCurrent();
        }
    }

    private static void pass(int id, String[] args) {
        if (args.length != 2) {
            displayErrorMessage(id, "syntax error");
            return;
        }
        String result = go.pass(args[1]);
        if (result.equals("error")) {
            displayErrorMessage(id, "wrong color");
        } else {
            displaySuccessMessage(id, "");
            switchCurrent();
        }
    }

    private static void switchCurrent() {
        currentPlayer = currentPlayer.equals(players.get(Color.WHITE)) ? players.get(Color.BLACK) : players.get(Color.WHITE);
    }

    private static void quit(int id) {
        go.end();
        displaySuccessMessage(id, "");
    }

    private static void player(int id, String[] args) {
        if (args.length != 3) {
            displayErrorMessage(id, "syntax error");
            return;
        }
        if (!Factory.getTypes().contains(args[2])) {
            displayErrorMessage(id, "unknown player type");
            return;
        }
        if (!args[1].equals("black") && !args[1].equals("white")) {
            displayErrorMessage(id, "wrong color");
            return;
        }
        Color color = args[1].equals("black") ? Color.BLACK : Color.WHITE;
        Player player = args[2].equals("console") ? Factory.create("console") : Factory.create("random");
        players.put(color, player);
        switchCurrent();
        switchCurrent();
        displaySuccessMessage(id, "");
    }

    private static void boardsize(int id, String[] args) {
        if (args.length == 1) {
            displayErrorMessage(id, "unacceptable size");
            return;
        }

        if (!isNumeric(args[1])) {
            displayErrorMessage(id, "not an Integer");
            return;
        }
        boolean tailleCorrecte = go.boardsize(Integer.parseInt(args[1]));
        if (!tailleCorrecte) {
            displayErrorMessage(id, "unacceptable size");
            return;
        }

        //go.resetCapturedStones();

        displaySuccessMessage(id, "");
    }

    private static void showboard(int id) {
        displaySuccessMessage(id, go.toString());
    }

    private static void clearBoard(int id) {
        go.boardsize(go.getBoard().length);
        displaySuccessMessage(id, "");
    }

    private static void getLiberties(int id, String[] args) {
        int n = go.getNbLiberties(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        if (n == -1) {
            displayErrorMessage(id, "coordinates out of range");
            return;
        }
        if (n == -2) {
            displayErrorMessage(id, "no stone at these coordinates");
            return;
        }
        displaySuccessMessage(id, n + "");
    }

    private static void displayErrorMessage(int id, String errorMessage) {
        StringBuilder sb = new StringBuilder();
        sb.append("?");
        if (id != NO_ID)
            sb.append(id);
        sb.append(" ").append(errorMessage);
        System.out.println(sb.toString());
    }

    private static void displaySuccessMessage(int id, String successMessages) {
        StringBuilder sb = new StringBuilder();
        sb.append("=");
        if (id != NO_ID)
            sb.append(id);
        sb.append(" ").append(successMessages);
        System.out.println(sb.toString());
    }

    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
