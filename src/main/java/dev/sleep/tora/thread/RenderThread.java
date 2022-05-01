package dev.sleep.tora.thread;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import dev.sleep.tora.ToraMain;
import dev.sleep.tora.client.render.RenderManager;
import dev.sleep.tora.client.render.WindowManager;

public class RenderThread extends Thread {

	public RenderManager renderManager;
	public WindowManager windowManager;

	public RenderThread(String threadName) {
		setName(threadName);

		renderManager = ToraMain.engineInstance.renderManager;
		windowManager = ToraMain.engineInstance.windowManager;
	}

	@Override
	public void run() {
		windowManager.initialize();
		
		while (!glfwWindowShouldClose(windowManager.windowPointer)) {
			renderManager.update();
		}
		
		windowManager.destroy();
	}

}
