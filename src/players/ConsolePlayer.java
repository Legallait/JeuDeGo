package players;

import go.GoGame;
import go.Player;

public class ConsolePlayer implements Player {
    @Override
    public String[] getMove(GoGame go) {
        String color = go.getTurn() % 2 == 0 ? "black" : "white";
        return new String[]{"pass", color};
    }

    @Override
    public Player cloner() {
        return new ConsolePlayer();
    }
}
