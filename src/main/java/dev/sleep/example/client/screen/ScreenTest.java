package dev.sleep.example.client.screen;

import dev.sleep.example.client.screen.widget.GuiTest;
import dev.sleep.tora.client.screen.Gui;

import java.util.Random;

public class ScreenTest extends Gui {

    @Override
    public void init() {
        Random r = new Random();
        addToComponentList(new GuiTest(r.nextInt(100), r.nextInt(100)));
    }

}
