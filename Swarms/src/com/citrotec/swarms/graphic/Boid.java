package com.citrotec.swarms.graphic;

import java.awt.Color;

import com.citrotec.swarms.core.AbstractBoid;

public class Boid extends AbstractBoid {

	Color color;

	public Boid(float x, float y) {
		super(x, y);
		color = Color.WHITE;
	}

	public Color getColor() {
		return color;
	}
}
