package com.citrotec.swarms.core;

import java.util.Random;

import com.citrotec.swarms.util.Vector;

public class AbstractBoid {

	private Vector position;
	private Vector velocity;
	private Vector acceleration;

	protected float radius = 5;
	private float maxspeed = 1;
	private float mass = 2;

	public AbstractBoid(float x, float y) {
		position = new Vector(x, y);
		acceleration = new Vector(0, 0);
		velocity = new Vector(new Random().nextFloat(),
				new Random().nextFloat());
		velocity.normalize();
	}

	public Vector getPosition() {
		return position;
	}

	public Vector getVelocity() {
		return velocity;
	}
	
	public Vector getAccerleration() {
		return acceleration;
	}
	
	public float getRadius() {
		return radius;
	}

	public void addAcceleration(Vector vector) {
		acceleration.add(vector);
	}

	public void addVeclocity(Vector vel) {
		velocity.add(vel);
	}


	public void updatePosition() {
		acceleration.div(mass);
		velocity.add(acceleration);
		velocity.limit(maxspeed);
		position.add(velocity);
		acceleration.mult(0);
	}

}