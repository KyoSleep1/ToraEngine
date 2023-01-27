package dev.sleep.tora.client.manager;

import static org.lwjgl.glfw.GLFW.glfwGetCurrentContext;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import dev.sleep.tora.ToraMain;
import imgui.ImGui;
import imgui.flag.ImGuiConfigFlags;

import java.util.ArrayList;
import java.util.List;

public class RenderManager {

    private List<Screen> screenToRenderList;

    /**
     * START OF BASIC RENDERING
     **/
    public RenderManager() {
        screenToRenderList = new ArrayList<>();
    }

    public void clearColor() {
        glClearColor(0.824f, 0.412f, 0.118f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public void update() {
        clearColor();
        renderActiveScreen();
        refresh();
    }

    public void refresh() {
        glfwSwapBuffers(ToraMain.windowManager.windowPointer);
        glfwPollEvents();
    }

    /**
     * START OF SCREEN RENDERING
     **/
    public void addScreenToRenderList(Screen screen) {
        screen.init();

        if (screenToRenderList != null) {
            screenToRenderList.add(screen);
            return;
        }

        screenToRenderList = new ArrayList<>();
        screenToRenderList.add(screen);
    }

    public void renderActiveScreen() {
        for (Screen activeScreen : screenToRenderList) {
            ToraMain.windowManager.imGuiGlfw.newFrame();
            ImGui.newFrame();

            activeScreen.renderScreen();

            ImGui.render();

            ToraMain.windowManager.imGuiGl3.renderDrawData(ImGui.getDrawData());
        }


        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            final long windowPointer = glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            glfwMakeContextCurrent(windowPointer);
        }
    }
}
