package com.citrotec.swarms.core;

import java.util.ArrayList;
import java.util.List;

public class Swarm<T extends AbstractBoid> {

	List<T> boidSwarm;

	public Swarm() {
		boidSwarm = new ArrayList<T>();
	}

	public void addBoid(T boid) {
		boidSwarm.add(boid);
	}

	public List<T> getSwarm() {
		return boidSwarm;
	}
}
