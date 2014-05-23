package com.citrotec.swarms.util;

public class Vector{

	private final static float TOL = 1/Float.MAX_VALUE;
	
	public float x;
	public float y;	
	
	public Vector(float x, float y){
		this.x=x;
		this.y=y;
	}
	
	public float getX(){
		return x;
	}
	
	public void setX(float x){
		this.x = x;
	}
	
	public float getY(){
		return y;
	}
	
	public void setY(float y){
		this.y = y;
	}

	public float norm() {
		return (float) Math.hypot(x, y);
	}

	public Vector plus(Vector v) {
		return new Vector(getX()+ ((Vector) v).getX(), getY()+ ((Vector) v).getY());
	}

	public void add(Vector v) {
		this.x += ((Vector) v).getX();
		this.y += ((Vector) v).getY();
	}

	public Vector sub(Vector v) {
		return new Vector(x-v.x, y- v.y);		
	}

	public void minus(Vector v) {
		this.x -= v.x;
		this.y -= v.y;
	}
	
	public void mult(float lambda){
		this.x *= lambda;
		this.y *= lambda;
	}
	
	public Vector scale(float lambda){
		return new Vector(getX()*lambda, getY()*lambda);
	}
	
	
	@Override
	public String toString(){
		return "("+x+","+y+")";
	}

	public Vector getNormalizedRectangularVector() {
		float x = this.x;
		float y = -this.y;
		Vector rect = new Vector(x,y);
		rect.normalize();
		return rect;
	}

	public static float dist(Vector v, Vector w) {
		return  v.sub(w).norm();
	}
	
	public void normalize(){
		float norm = this.norm();
		if(norm != 0)
		{
			this.mult(1/norm);
		}
	}

	@Override
	public boolean equals(Object o){
		if(o instanceof Vector){
			return ((Vector) o).sub(this).norm() <= TOL;
		}
		return false;	
	}

	public void div(int count) {
		x = x/count;
		y = y/count;
	}

	public void div(float count) {
		x = x/count;
		y = y/count;	
	}
	
	public void limit(float maxforce) {
		if(this.norm() > maxforce){
			this.normalize();
			this.scale(maxforce);
		}
	}

}
