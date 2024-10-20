package main;

import IHM.IHMConsole;
import go.Factory;
import players.ConsolePlayer;
import players.RandomPlayer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class App {
    public static void main(String[] args) {
        Factory.addProto("console", new ConsolePlayer());
        Factory.addProto("random", new RandomPlayer());
        IHMConsole.session(Factory.create("console"), Factory.create("console"));
    }
}
