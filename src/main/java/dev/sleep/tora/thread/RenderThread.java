package dev.sleep.tora.thread;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import dev.sleep.tora.ToraMain;
import dev.sleep.tora.client.render.manager.RenderManager;
import dev.sleep.tora.client.render.manager.WindowManager;

public class RenderThread extends Thread {

    public RenderThread(String threadName) {
        setName(threadName);
    }

    @Override
    public void run() {
        RenderManager renderManager = ToraMain.renderManager;
        WindowManager windowManager = ToraMain.windowManager;

        windowManager.initialize();

        while (!glfwWindowShouldClose(windowManager.windowPointer)) {
            renderManager.update();
        }

        windowManager.destroy();
    }
}
