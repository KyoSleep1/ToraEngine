package dev.sleep.example.client.gui;

import dev.sleep.tora.client.gui.Gui;
import imgui.ImGui;

public class GuiTest extends Gui {
	
	@Override
	public void renderGui() {
		ImGui.begin("Test Gui");
		
		if(ImGui.button("A little button")) {
			System.out.println("You just clicked the button");
		}
		
		ImGui.end();
	}

}
