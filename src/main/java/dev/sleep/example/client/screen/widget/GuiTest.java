package dev.sleep.example.client.screen.widget;

import dev.sleep.tora.client.screen.Gui;
import imgui.ImGui;

public class GuiTest extends Gui {

    public GuiTest(int positionX, int positionY) {
        super(positionX, positionY);
    }

    @Override
    public void render() {
        if(ImGui.button("test")) {
            System.out.println("Test");
        }
    }
}
