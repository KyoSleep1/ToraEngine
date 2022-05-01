package dev.sleep.tora;

import dev.sleep.tora.client.render.RenderManager;
import dev.sleep.tora.client.render.WindowManager;
import dev.sleep.tora.thread.RenderThread;

public class ToraMain {
	
	public static ToraMain engineInstance;
	
	public IGameInstance gameInstance;
	
	public WindowManager windowManager;
	public RenderManager renderManager;
	
	public RenderThread renderThread;
	
	public ToraMain(IGameInstance gameInstance) {
		engineInstance = this;
		
		this.gameInstance = gameInstance;
		
		windowManager = new WindowManager();
		renderManager = new RenderManager(windowManager);
		
		renderThread = new RenderThread("render-thread");
	}
	
	public void initialize() {
		gameInstance.create();
		renderThread.run();
	}
}