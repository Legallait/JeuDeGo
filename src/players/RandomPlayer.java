package players;

import go.GoGame;
import go.Player;
import utils.Coord;

import java.util.HashSet;
import java.util.Set;

public class RandomPlayer implements Player {
    @Override
    public String[] getMove(GoGame go) {
        String color = go.getTurn() % 2 == 0 ? "black" : "white";
        if (go.isBoardFull())
            return new String[]{"pass", color};

        Character oppositeColor = color.equals("white") ? 'X' : 'O';
        int n = go.getNbFreeSpaces();
        int cpt = 0;
        Set<Coord> visited = new HashSet<>();
        boolean suicide = true;
        int size = go.getBoard().length, x, y;

        do {
            Coord xy;
            do {
                x = (int) (Math.random() * size);
                y = (int) (Math.random() * size);
                xy = new Coord(x, y);
            } while (!go.isMoveLegal(x, y) || visited.contains(xy));
            go.playTemp(x, y, color);
            suicide = go.suicides(x, y, oppositeColor);
            if (suicide) {
                visited.add(xy);
                ++cpt;
            }
            go.cancelPlayTemp(x, y);
        } while (suicide && cpt < n);
        if (cpt >= n)
            return new String[]{"pass", color};

        String coord = (char) (y + 'A') + "" + (size - x);
        return new String[]{"play", color, coord};
    }

    @Override
    public Player cloner() {
        return new RandomPlayer();
    }
}
