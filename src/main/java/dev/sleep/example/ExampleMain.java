package dev.sleep.example;

import dev.sleep.tora.IGameInstance;
import dev.sleep.tora.ToraMain;

public class ExampleMain implements IGameInstance {

    public static void main(String[] args) {
        ToraMain.initialize(new ExampleMain());
    }

}
