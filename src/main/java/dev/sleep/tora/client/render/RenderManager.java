package dev.sleep.tora.client.render;

import static org.lwjgl.glfw.GLFW.glfwGetCurrentContext;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import dev.sleep.example.client.gui.GuiTest;
import imgui.ImGui;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiWindowFlags;

public class RenderManager {
	
	public WindowManager windowManager;
	
	//Remove this later (Just for test)
	public GuiTest guiTest;
	
	public RenderManager(WindowManager windowManager) {
		this.windowManager = windowManager;
		guiTest = new GuiTest("Test", 0, 0, 1920, 1080, ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoResize);
	}

	public void clearColor() {
		glClearColor(0.824f, 0.412f, 0.118f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT);
	}

	public void update() {
		clearColor();
		renderActiveGui();
		refresh();
	}
	
	public void refresh() {
		glfwSwapBuffers(windowManager.windowPointer);
		glfwPollEvents();
	}

	public void renderActiveGui() {
		windowManager.imGuiGlfw.newFrame();
		ImGui.newFrame();

		//Render gui
		guiTest.renderGui();
		
		ImGui.render();
		windowManager.imGuiGl3.renderDrawData(ImGui.getDrawData());
		
		if(ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
			final long windowPointer = glfwGetCurrentContext();
			ImGui.updatePlatformWindows();
			ImGui.renderPlatformWindowsDefault();
			glfwMakeContextCurrent(windowPointer);
		}
	}

}
