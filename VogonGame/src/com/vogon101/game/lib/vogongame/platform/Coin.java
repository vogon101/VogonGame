package com.vogon101.game.lib.vogongame.platform;
import static org.lwjgl.opengl.GL11.*;

/**
 * Coin class for scoring
 * @author Freddie
 *
 */
public class Coin {
	
	protected double x, y, xSpeed, ySpeed;
	protected int timer;
	private Game game;
	
	/**
	 * <b>Constructor</b><br/>
	 * @param x
	 * @param y
	 * @param game_
	 * @param xSpeed_
	 * @param ySpeed_
	 */
	public Coin (double x, double y, Game game_, double xSpeed_, double ySpeed_) {
		this.x = x;
		this.y = y;
		game = game_;
		xSpeed = xSpeed_;
		ySpeed = ySpeed_;
	}
	
	/**
	 * Renders gold cube
	 */
	public void draw() {
		glPushMatrix();
		glTranslated(x, y, 0);
		glBegin(GL_QUADS);
		{
			glColor3d(1, 0.6, 0.3);
			glVertex2d(0, 0);
			glVertex2d(10, 0);
			glVertex2d(10, 10);
			glVertex2d(0, 10);
		}
		
		glEnd();
		glPopMatrix();
	}
	
	/**
	 * Basic logic, flies out then falls
	 */
	public void logic() {
		timer++;
		if (timer > 0 && timer < 100) {
			y += ySpeed;
			x += xSpeed;
		}
		if (timer > 100 ) {
			x += xSpeed;
			y--;
		}
	}
	
}
