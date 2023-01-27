package dev.sleep.example;

import dev.sleep.example.client.screen.ScreenTest;
import dev.sleep.tora.IGameInstance;
import dev.sleep.tora.ToraMain;

public class ExampleMain implements IGameInstance {

    public static void main(String[] args) {
        ToraMain.initialize(new ExampleMain());

        testScreen();
    }

    public static void testScreen() {
        for(int i = 0; i < 10; i++){
            ScreenTest screenTest = new ScreenTest();
            ToraMain.displayScreen(screenTest);
        }
    }
}
