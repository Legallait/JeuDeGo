package go;

import java.util.HashMap;
import java.util.Set;

public class Factory {

    private static HashMap<String, Player> protos = new HashMap<>();

    public static void addProto(String type, Player p) {
        if (protos.containsKey(type))
            throw new IllegalArgumentException();
        if (p == null)
            throw new NullPointerException();
        protos.put(type, p);
    }

    public static Player create(String type) {
        if (!protos.containsKey(type))
            throw new IllegalArgumentException();
        return protos.get(type).cloner();
    }

    public static Set<String> getTypes() {
        return protos.keySet();
    }
}

