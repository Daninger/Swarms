package com.citrotec.swarms.gui;

import com.citrotec.swarms.core.BoidManager;
import com.citrotec.swarms.core.Swarm;
import com.citrotec.swarms.graphic.Boid;
import com.citrotec.swarms.graphic.Player;
import com.citrotec.swarms.util.Vector;

import processing.core.PApplet;

public class SwarmsGame extends PApplet {

	private static final long serialVersionUID = 1L;

	Swarm<Boid> swarm;
	BoidManager boidManager;
	Player player;

	int width = 800;
	int height = 600;
	int numberBoids = 100;

	public void setup() {
		size(width, height);

		swarm = new Swarm<Boid>();
		player = new Player(200, 200);
		swarm.addBoid(player);
		initSwarm();

		boidManager = new BoidManager(swarm, width, height);
	}

	private void initSwarm() {
		for (int i = 0; i < numberBoids; i++) {
			swarm.addBoid(new Boid(random(width), random(height)));
		}
	}

	public void draw() {
		background(0);
		boidManager.updateSwarm();
		if (boidManager.getGameOver()) {
			noLoop();
			text("Congratulations!", width / 2, height / 2);
			return;
		}
		boidManager.update(player);
		render();
	}

	protected void render() {
		for (Boid boid : swarm.getSwarm()) {
			renderBoid(boid);
		}
	}

	protected void renderBoid(Boid boid) {
		float r = boid.getRadius();
		fill(boid.getColor().getRGB());
		ellipse(boid.getPosition().getX(), boid.getPosition().getY(), r, r);
	}

	public void keyPressed() {
		switch (keyCode) {
		case 37:
			player.addAcceleration(new Vector(1, 0));
			break;
		case 38:
			player.addAcceleration(new Vector(0, 1));
			break;
		case 39:
			player.addAcceleration(new Vector(1, 0));
			break;
		case 40:
			player.addAcceleration(new Vector(0, 1));
			break;
		default:
			player.addAcceleration(new Vector(0, 0));
			break;
		}
	}

	public void mousePressed() {
		Vector steer = new Vector(mouseX, mouseY).sub(player.getPosition());
		player.addAcceleration(steer);
	}

	public static void main(String args[]) {
		PApplet.main(new String[] { SwarmsGame.class.getName() });
	}
}
