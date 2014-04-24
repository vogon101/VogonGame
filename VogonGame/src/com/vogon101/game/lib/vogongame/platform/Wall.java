package com.vogon101.game.lib.vogongame.platform;

import static org.lwjgl.opengl.GL11.*;

public class Wall{

	protected Level level;
	protected int x,y,width,height;
	
	public Wall (Level level_, int x_, int y_, int width_, int height_) {
		level = level_;
		x = x_;
		y = y_;
		width = width_;
		height = height_;
	}
	
	public void draw () {
		glPushMatrix();
		glTranslated(x, y, 0);
		glBegin(GL_QUADS);
		{
			glColor3d(0.7, 0.7, 0.7);
			glVertex2d(0, 0);
			glVertex2d(width, 0);
			glVertex2d(width, height);
			glVertex2d(0, height);
		}
		glEnd();
		glPopMatrix();
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
}
	
