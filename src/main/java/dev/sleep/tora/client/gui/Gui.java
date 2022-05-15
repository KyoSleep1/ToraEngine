package dev.sleep.tora.client.gui;

import imgui.ImGui;

public abstract class Gui {
	
	String title;
	int positionX, positionY, width, height, flags;
	
	public Gui(String title, int positionX, int positionY, int width, int height, int flags) {
		setTitle(title);
		setPosition(positionX, positionY);
		setSize(width, height);
		setFlags(flags);
	}
	
	public abstract void renderGui();
	
	public void beginRender() {
		ImGui.setNextWindowPos(positionX, positionY);
		ImGui.setWindowSize(width, height);
		ImGui.begin(title, flags);
	}
	
	public void endRender() {
		ImGui.end();
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setPosition(int positionX, int positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}
	
	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}
	
	public void setFlags(int flags) {
		this.flags = flags;
	}

}
