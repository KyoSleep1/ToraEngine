package dev.sleep.tora;

import dev.sleep.tora.client.manager.RenderManager;
import dev.sleep.tora.client.manager.WindowManager;
import dev.sleep.tora.thread.manager.ThreadManager;

public class ToraMain {

    public static IGameInstance gameInstance;

    public static WindowManager windowManager;
    public static RenderManager renderManager;
    public static ThreadManager threadManager;

    public static void initialize(IGameInstance currentGameInstance) {
        gameInstance = currentGameInstance;

        windowManager = new WindowManager();
        renderManager = new RenderManager();
        threadManager = new ThreadManager();

        threadManager.initializeThreads();
    }

    public static void displayScreen(Screen screen) {
        renderManager.addScreenToRenderList(screen);
    }
}