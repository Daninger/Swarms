package com.citrotec.swarms.gui;

import com.citrotec.swarms.core.BoidManager;
import com.citrotec.swarms.core.Swarm;
import com.citrotec.swarms.graphic.Boid;
import com.citrotec.swarms.graphic.Player;
import com.citrotec.swarms.util.Vector;

import processing.core.PApplet;
import processing.core.PVector;

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
			if (boid instanceof Player) {
				renderPlayer(boid);
			} else {
				renderBoid(boid);
			}
		}
	}

	protected void renderBoid(Boid boid) {
		float r = boid.getRadius();
		fill(boid.getColor().getRGB());
		ellipse(boid.getPosition().getX(), boid.getPosition().getY(), 2 * r,
				2 * r);
	}

	protected void renderPlayer(Boid player) {
		float r = player.getRadius();
		fill(player.getColor().getRGB());
		PVector vel = new PVector(player.getVelocity().getX(), player
				.getVelocity().getY());
		vel.normalize();
		float theta = vel.heading() + PI / 2;
		stroke(255);
		pushMatrix();
		translate(player.getPosition().x, player.getPosition().y);
		rotate(theta);
		beginShape(TRIANGLES);
		vertex(0, -r * 2);
		vertex(-r, r * 2);
		vertex(r, r * 2);
		endShape();
		popMatrix();
	}

	public void mouseDragged() {
		Vector steer = new Vector(mouseX, mouseY).sub(player.getPosition());
		steer.normalize();
		player.addAcceleration(steer);
	}

	public static void main(String args[]) {
		PApplet.main(new String[] { SwarmsGame.class.getName() });
	}
}
