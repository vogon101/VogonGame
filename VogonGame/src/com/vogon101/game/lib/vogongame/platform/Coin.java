package com.vogon101.game.lib.vogongame.platform;
import static org.lwjgl.opengl.GL11.*;

/**
 * Coin class for scoring
 * @author Freddie
 *
 */
public class Coin {
	
	protected double x, y, xSpeed, ySpeed, floor;
	protected int timer;
	protected boolean falling = true, there = true; 
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
		floor = game.floor;
	}
	
	/**
	 * Renders gold cube
	 */
	public void draw() {
		if (there) {
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
		else if (timer > 100 ) {
			Platform p = checkPlatforms();
			if (p != null) {
				 floor = p.getTopEdge();
				 
			}
			else {
				x += xSpeed;
				floor = game.floor;
				falling = false;
			}
		}
		
		xSpeed = xSpeed/1.01;
		if (y > floor)
			y--;
	}
	
	
	/**
	 * Return the platform the coin is on OR null
	 * if not on one
	 * @return
	 */
	public Platform checkPlatforms() {
		for (Platform plat : game.getLevel().getPlatforms()) {
			if (isOnPlatform (plat)) {
				return plat;
				
			}
		}
		return null;
	}
	
	/**
	 * Check if the coin is on a particular platform
	 * 
	 * @param plat
	 * @return true if coin is on platform
	 */
	public boolean isOnPlatform(Platform plat) {
		if (x + 10 / 1.5 > plat.getLeftEdge()
				&& x + 10 / 3 < plat.getRightEdge()) {
			// if above top of platform
			if (y > plat.getTopEdge() - 4 && y < plat.getTopEdge() + 4) {
				return true;
			}
		}
		return false;
	}
		
	
	
}
