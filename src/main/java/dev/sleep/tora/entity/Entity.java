package dev.sleep.tora.entity;

import dev.sleep.tora.util.math.Vector3f;

public class Entity {
	
	Vector3f position, rotation;
	
	public Entity(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
	}

}
