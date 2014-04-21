package com.vogon101.game.lib.vogongame.platform;

import static org.lwjgl.opengl.GL11.*;

/**
 * The basic platform class.<br/>
 * To add new types extend this, also override the empty
 * logic method. The player logic must be overridden for
 * it to react differently
 * 
 * @author Freddie Poser
 *
 */
public class Platform {

	protected double x, y, width, height;
	protected double r = 0, g = 1,  b = 0.1;
	
	/**
	 * <b>Constructor</b>
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Platform (double x, double y,double  width, double height){
		this.x=x;
		this.y=y;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Main render method, override to add custom rendering
	 */
	public void draw () {
		glPushMatrix();
		
		glTranslated(x, y, 0);
		
		{
			/*
			 * For a quad the coords are:
			 * vertex 1 = 0, 0
			 * vertex 2 = width, 0
			 * vertex 3 = width, height
			 * vertex 4 = 0, height
			 */
			glBegin(GL_QUADS);
			glColor3d(r, g, b);
			glVertex2d(0, 0);
			
			glVertex2d(width, 0);
			
			glVertex2d(width, height);
			
			glVertex2d(0, height);
			
			glEnd();
		}
		
		glPopMatrix();
	}
	
	public void setColor(Double r, Double g, Double b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public double getTopEdge() {
		return y+height;
	}
	
	public double getLeftEdge() {
		return x;
	}
	
	public double getRightEdge() {
		return x+width;
	}
	
	public double getBottomEdge(){
		return y;
	}
	
	public void logic() {
		
	}
	
	
}
