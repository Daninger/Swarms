package com.citrotec.swarms.core;

import com.citrotec.swarms.util.Vector;
import com.citrotec.swarms.graphic.Player;

public class BoidManager {

	int width;
	int height;

	private Swarm<? extends AbstractBoid> swarm;
	private Player Player;

	private static float maxforce = 0.03f;
	private static float maxspeed = 1;

	private static float ALI_FACTOR = 1.5f;
	private static float ALI_RADIUS = 50;

	private static float SEP_FACTOR = 1;
	private static float SEP_RADIUS = 25;

	private static float COH_FACTOR = 1;
	private static float COH_RADIUS = 50;

	private static float WINNING_RADIUS = 200;
	private boolean gameOver = false;

	public BoidManager(Swarm<? extends AbstractBoid> swarm, int width,
			int height) {
		this.swarm = swarm;
		Player = (Player) swarm.getSwarm().get(0);
		this.width = width;
		this.height = height;
	}

	public void updateSwarm() {
		gameOver = true;
		for (AbstractBoid AbstractBoid : swarm.getSwarm()) {
			if (!(AbstractBoid instanceof Player)) {
				gameOver = checkForGameOver(AbstractBoid);
				seperate(AbstractBoid);
				align(AbstractBoid);
				cohesion(AbstractBoid);
				AbstractBoid.updatePosition();
				borders(AbstractBoid);
			}
		}
	}

	private boolean checkForGameOver(AbstractBoid AbstractBoid) {
		if (!gameOver) {
			return false;
		} else {
			if (Vector.dist(Player.getPosition(), AbstractBoid.getPosition()) > WINNING_RADIUS) {
				return false;
			} else {
				return true;
			}
		}
	}

	public void update(AbstractBoid AbstractPlayer) {
		AbstractPlayer.updatePosition();
		borders(AbstractPlayer);
	}

	void borders(AbstractBoid AbstractBoid) {
		Vector loc = AbstractBoid.getPosition();
		float r = AbstractBoid.getRadius();
		if (loc.x < -r)
			loc.x = width + r;
		if (loc.y < -r)
			loc.y = height + r;
		if (loc.x > width + r)
			loc.x = -r;
		if (loc.y > height + r)
			loc.y = -r;
	}

	private void align(AbstractBoid AbstractBoid) {
		Vector sum = new Vector(0, 0);
		int count = 0;
		for (AbstractBoid other : swarm.getSwarm()) {
			if (inZone(AbstractBoid, other, ALI_RADIUS)) {
				sum.add(other.getVelocity());
				count++;
			}
		}
		Vector steer;
		if (count > 0) {
			sum.div(count);
			sum.normalize();
			sum.mult(maxspeed);
			steer = sum.sub(AbstractBoid.getVelocity());
			steer.limit(maxforce);
			steer.mult(ALI_FACTOR);
			AbstractBoid.addAcceleration(steer);
		}
	}

	private void seperate(AbstractBoid AbstractBoid) {
		Vector steer = new Vector(0, 0);
		int count = 0;
		for (AbstractBoid other : swarm.getSwarm()) {
			if (inZone(AbstractBoid, other, SEP_RADIUS)) {
				Vector diff = AbstractBoid.getPosition().sub(
						other.getPosition());
				diff.normalize();
				diff.div(Vector.dist(AbstractBoid.getPosition(),
						other.getPosition()));
				steer.add(diff);
				count++;
			}
		}
		if (count > 0) {
			steer.div((float) count);
		}
		if (steer.norm() > 0) {
			steer.normalize();
			steer.mult(maxspeed);
			steer.sub(AbstractBoid.getVelocity());
			steer.limit(maxforce);
		}
		steer.mult(SEP_FACTOR);
		AbstractBoid.addAcceleration(steer);
	}

	private void cohesion(AbstractBoid AbstractBoid) {
		Vector steer;
		Vector sum = new Vector(0, 0);
		int count = 0;
		for (AbstractBoid other : swarm.getSwarm()) {
			if (inZone(AbstractBoid, other, COH_RADIUS)) {
				sum.add(other.getPosition());
				count++;
			}
		}
		if (count > 0) {
			sum.div(count);
			steer = seek(AbstractBoid, sum);
			steer.mult(COH_FACTOR);
			AbstractBoid.addAcceleration(steer);
		}
	}

	private Vector seek(AbstractBoid AbstractBoid, Vector target) {
		Vector desired = target.sub(AbstractBoid.getPosition());
		desired.normalize();
		desired.mult(maxspeed);
		Vector steer = desired.sub(AbstractBoid.getVelocity());
		steer.limit(maxforce);
		return steer;
	}

	private boolean inZone(AbstractBoid AbstractBoid, AbstractBoid other,
			float radius) {
		float d = Vector.dist(AbstractBoid.getPosition(), other.getPosition());
		if ((d > 0) && (d < radius))
			return true;
		else
			return false;
	}

	public boolean getGameOver() {
		return gameOver;
	}
}
