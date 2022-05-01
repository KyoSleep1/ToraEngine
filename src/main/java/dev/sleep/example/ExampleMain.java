package dev.sleep.example;

import dev.sleep.tora.IGameInstance;
import dev.sleep.tora.ToraMain;

public class ExampleMain implements IGameInstance {
	
	public static ExampleMain mainInstance;
	public static ToraMain toraInstance;
		
	public ExampleMain() {
		toraInstance = new ToraMain(this);
		toraInstance.initialize();
	}
	
	@Override
	public void create() {
	}
	
	public static void main(String[] args) {
		mainInstance = new ExampleMain();
	}
}
