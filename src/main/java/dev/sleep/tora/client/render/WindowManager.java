package dev.sleep.tora.client.render;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import imgui.ImGui;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class WindowManager {
	
	public final ImGuiImplGlfw imGuiGlfw;
	public final ImGuiImplGl3 imGuiGl3;
	
	public String glslVersion;
	public long windowPointer;
	
	public WindowManager() {
		imGuiGl3 = new ImGuiImplGl3();
		imGuiGlfw = new ImGuiImplGlfw();
	}
	
	public void initialize() {
		initWindow();
		initImGui();
		
		imGuiGlfw.init(windowPointer, true);
		imGuiGl3.init(glslVersion);
	}
	
	public void initWindow() {
		GLFWErrorCallback.createPrint(System.err).set();
		
		if(!glfwInit()) {
			System.out.println("Unable to initialize GLFW");
			System.exit(-1);
		}
		
		glslVersion = "#version 130";
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
		
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		windowPointer = glfwCreateWindow(1920, 1080, "Test", NULL, NULL);
		
		if(windowPointer == NULL) {
			System.out.println("Unable to create Window");
			System.exit(-1);
		}
		
		glfwMakeContextCurrent(windowPointer);
		glfwSwapInterval(1);
		glfwShowWindow(windowPointer);
		
		GL.createCapabilities();
	}
	
	public void initImGui() {
		ImGui.createContext();
	}
	
	public void destroy() {
		imGuiGl3.dispose();
		imGuiGlfw.dispose();
		
		ImGui.destroyContext();
		Callbacks.glfwFreeCallbacks(windowPointer);
		
		glfwDestroyWindow(windowPointer);
		glfwTerminate();
	}

}
