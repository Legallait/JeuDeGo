package go;

import utils.Coord;

public interface Player {
    String[] getMove(GoGame go);

    Player cloner();
}
