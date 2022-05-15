package dev.sleep.example.client.gui;

import dev.sleep.tora.client.gui.Gui;
import imgui.ImGui;

public class GuiTest extends Gui {
	
	public GuiTest(String title, int positionX, int positionY, int width, int height, int flags) {
		super(title, positionX, positionY, width, height, flags);
	}

	@Override
	public void renderGui() {
		beginRender();
		
		if(ImGui.button("A little button")) {
			System.out.println("You just clicked the button");
		}
		
		endRender();		
	}
}
