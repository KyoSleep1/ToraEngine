package dev.sleep.tora;

import dev.sleep.tora.client.render.manager.RenderManager;
import dev.sleep.tora.client.render.manager.WindowManager;
import dev.sleep.tora.thread.manager.ThreadManager;

public class ToraMain {

    public static IGameInstance gameInstance;

    public static WindowManager windowManager;
    public static RenderManager renderManager;
    public static ThreadManager threadManager;

    public static void initialize(IGameInstance currentGameInstance) {
        gameInstance = currentGameInstance;

        windowManager = new WindowManager();
        renderManager = new RenderManager(windowManager);
        threadManager = new ThreadManager();

        threadManager.initializeThreads();
    }
}