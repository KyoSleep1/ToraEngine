package dev.sleep.tora.thread.manager;

import dev.sleep.tora.thread.RenderThread;

public class ThreadManager {

    public static RenderThread renderThread;

    public void initializeThreads(){
        renderThread = new RenderThread("render-thread");
        renderThread.start();
    }

}
