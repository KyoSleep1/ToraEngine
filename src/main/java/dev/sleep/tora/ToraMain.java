package dev.sleep.tora;

public class ToraMain {
	
	public IGameInstance gameInstance;
	
	public ToraMain(IGameInstance gameInstance) {
		this.gameInstance = gameInstance;
	}
	
	public void initialize() {
		gameInstance.create();
	}
}