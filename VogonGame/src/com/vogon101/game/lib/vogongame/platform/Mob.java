package com.vogon101.game.lib.vogongame.platform;

import static org.lwjgl.opengl.GL11.*;

import java.util.Random;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import com.vogon101.game.lib.vogongame.VogonGameException;
import com.vogon101.game.lib.vogongame.util.VogonTextureLoader;
/**
 * Contact with any mob will kill the player unless he jumps on its head or
 * the player's logic is overridden.
 * @author Freddie Poser
 *
 */
public class Mob {

	protected Level level;
	protected Texture texture = null;
	protected int height  = 64, width = 64, xSpeed = 0, ySpeed = 0, xSave = xSpeed, ySave = ySpeed, jumptimer, dieTimer, respawnTimer;
	protected double x, y, r = 0, g = 0, b = 1, floor = 0, basefloor = 0;
	protected boolean jump = false, alive = true, there = true, dieing = false, respawning = false, wallLeft = false, wallRight = false;
	
	/**
	 * <b>Constructor</b><br/>
	 * If you use this constructor you MUST set the x and y position BEFORE drawing {@link setPos(double x, double y)}<br/>
	 * @param level_ This is the level that contains this mob (ie: something that extends {@link Level})
	 */
	public Mob ( Level level_ ) {
		level = level_;
	}
	
	
	/**
	 * <b>Constructor</b><br/>
	 * Starting point of player contained in x and y <br/>
	 * @param level_ This is the level that contains this mob (ie: something that extends {@link Level})
	 * @param x The starting x position
	 * @param y The starting y position
	 */
	public Mob ( Level level_, double x, double y) {
		level = level_; 
		this.x = x;
		this.y = y;
	}
	
	/**
	 * <b>Constructor</b><br/>
	 * Starting point of player contained in x and y <br/>
	 * @param level_ This is the level that contains this mob (ie: something that extends {@link Level})
	 * @param x The starting x position
	 * @param y The starting y position
	 * @param xSpeed The starting x direction/speed of the mob
	 * @param ySpeed The starting y direction/speed of the mob
	 */
	public Mob (Level level_, double x, double y, int xSpeed, int ySpeed) {
		level = level_; 
		this.x = x;
		this.y = y;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}
	
	/**
	 * <b>Constructor</b><br/>
	 * Starting point of player contained in x and y <br/>
	 * @param level_ This is the level that contains this mob (ie: something that extends {@link Level})
	 * @param x The starting x position
	 * @param y The starting y position
	 * @param xSpeed The starting x direction/speed of the mob
	 * @param ySpeed The starting y direction/speed of the mob
	 * @param xSave The default speed of the mob on the x axis
	 * @param ySave The default speed of the mob on the y axis
	 */
	public Mob (Level level_, double x, double y, int xSpeed, int ySpeed, int xSave, int ySave)  {
		level = level_; 
		this.x = x;
		this.y = y;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		if (xSave < 0 || ySave < 0) {
			System.out.println("One or more of the save vars was negative, making both +tive");
			 if (xSave < 0) {
				 xSave = xSave*-1;
			 }
			 if (ySave < 0) {
				 ySave = ySave*-1;
			 }
			 
		}
		this.xSave = xSave;
		this.ySave = ySave;
	}

	/**
	 * The main logic method of the mob
	 * override to do fancier stuff
	 */
	public void logic() {
		if (!alive) {
			if (dieing) {
				dieTimer++;
				if (dieTimer > 0 && dieTimer < 150) {
					y++;
				}
				else if (dieTimer > 150 && dieTimer < 550) {
					y--;
				}
				
				if (dieTimer == 350) {
					dieing = false;
					dieTimer = 0;
					there = false;
				}
			}
		}
		else if (there) {
			x += xSpeed;
			y += ySpeed;
			gravity();
			checkWalls();
			if (x>level.WIDTH-width) {
				xSpeed = -xSave;
			}
			else if (x<0) {
				xSpeed = xSave;
			}
			else if (wallLeft) {
				xSpeed = -xSave;
				wallLeft = false;
			}
			else if (wallRight) {
				xSpeed = xSave;
				wallRight = false;
			}
		}
	}
	
	
	/**
	 * Basic gravity, override for fancier stuff
	 */
	public void gravity() {
		if (!jump) {
			if (y > floor) {
				ySpeed = -1;
			}
			else {
				ySpeed = 0;
			}
		}
		else {
			jumptimer++;
			if (jumptimer < 50) {
				ySpeed = 2;
			}
			else if (jumptimer <70) {
				ySpeed = 1;
			}
			else {
				jump = false;
			}
		}
		
		if (!isOnFloor() && !jump) {
			y--;
		}
	}
	/**
	 * Returns true if the player is on his current floor
	 * @return
	 */
	public boolean isOnFloor() {
		if (y > floor - 2 && y < floor +1) {
			return true;
		}
		return false;
	}
	
	/**
	 * <b>The mob's render method</b><br/>
	 * Override to add custom rendering otherwise it will default to a
	 * square (size set by user ({@link setSize(int w, int h)}) or default 64x64
	 * and colour red or set by user ({@link setColour (double r, double g, double b)})
	 * unless texture is set {@link setTexture (Texture texture)}
	 */
	public void draw() {
		if (there) {
			glPushMatrix();
			glTranslated(x, y, 0);
			
			
			
			/*
			 * For a quad the coords are:
			 * vertex 1 = 0, 0
			 * vertex 2 = width, 0
			 * vertex 3 = width, height
			 * vertex 4 = 0, height
			 */
			
			
			if (texture != null) {
				glEnable(GL_TEXTURE_2D);
				glEnable(GL_BLEND); glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
				glBegin(GL_QUADS);
				texture.bind();
				{
					
					glTexCoord2f(0,0);
					glVertex2d(0, 0);
					glTexCoord2f(1,0);
					glVertex2d(width, 0);
					glTexCoord2f(1,1);
					glVertex2d(width, height);
					glTexCoord2f(1,0);
					glVertex2d(0, height);
				}
				texture.release();
				glEnd();
				glDisable(GL_TEXTURE_2D);
			}
			else {
				glBegin(GL_QUADS);
				{
					glColor3d(r, g, b);
					glVertex2d(0, 0);
					glVertex2d(width, 0);
					glVertex2d(width, height);
					glVertex2d(0, height);
				}
				glEnd();
			}
	
			
			
			glPopMatrix();
		}
	}
	
	/**
	 * Set the texture to a pre-loaded texture
	 * @param texture A pre loaded texture (Use {@link VogonTextureLoader})
	 */
	public void setTexture (Texture texture) {
		this.texture = texture;
	}
	
	/**
	 * Load a new texture to use for rendering from <b>PNG</b> image<br/>
	 * Uses {@link VogonTextureLoader} which extends {@link TextureLoader}
	 * @param path  - The path of the <b>PNG</b> image
	 * @throws VogonGameException  - Thrown when the image can't be loaded
	 */
	public void setTexture (String path) throws VogonGameException{
		texture =  VogonTextureLoader.loadTexture(path);
	}
	
	/**
	 * Load a new texture to use for rendering from <b>PNG</b> image<br/>
	 * Uses {@link VogonTextureLoader} which extends {@link TextureLoader}
	 * @param path  - The path of the <b>PNG</b> image
	 * @param type  - The file extention/type of image ie <b>PNG</b> or <b>JPG</b>
	 * @throws VogonGameException  - Thrown when the image can't be loaded
	 */
	public void setTexture (String path, String type) throws VogonGameException {
		texture =  VogonTextureLoader.loadTexture(path, type);
	}
	
	/**
	 * Set the size of the quad to be rendered
	 * @param w  - Width
	 * @param h  - Height
	 */
	public void setSize (int w, int h) {
		width = w;
		height = h;
	}
	
	/**
	 * Set a colour for use when rendering the default quad (@link GL11.glColor3d}
	 * @param r
	 * @param g
	 * @param b
	 */
	public void setColour (double r, double g, double b) {
		this.r = r;
		this.g = g;
		this.b = b;	
	}
	
	/**
	 * Set the x and y coordiantes of the mob. 
	 * Essential if you didn't use the constructor
	 * with this option
	 * @param x_
	 * @param y_
	 */
	public void setPos (double x_, double y_) {
		x = x_;
		y = y_;
	}
	
	/**
	 * Set the current speed and direction of the mob
	 * @param xSpeed_
	 * @param ySpeed_
	 */
	public void setSpeed(int xSpeed_, int ySpeed_) {
		xSpeed = xSpeed_;
		ySpeed = ySpeed_;
	}

	/**
	 * Set the default speed that (But not direction)
	 * the mob will use when dictated by the logic
	 * The numbers MUST NOT be negative
	 * @param xSave_
	 * @param ySave_
	 * @throws VogonGameException When a number is negative
	 */
	public void setBaseSpeed (int xSave_, int ySave_) throws VogonGameException {
		if (xSave_ < 0 || ySave_ < 0) {
			throw new VogonGameException("One of the variables were negative");
		}
		xSave = xSave_;
		ySave = ySave_;
		
	}
	
	public boolean isAlive () {
		return alive;
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
	
	/**
	 * Kill the mob, override to do better things
	 * Will spawn 0-40 coins
	 */
	public void kill() {
		alive = false;
		dieing = true;
		Random r = new Random();
		for (int i = 0; i < r.nextInt(40); i++) {
			int test  = r.nextInt(2);
			if (test == 1) {
				level.getCoins().add(new Coin(x+(width/2), y+(height/2), level.game, -r.nextDouble(), r.nextDouble()));
			}
			level.getCoins().add(new Coin(x+(width/2), y+(height/2), level.game, r.nextDouble(), r.nextDouble()));
		}
		
	}
	
	public void checkWalls() {
		for (Wall wall : level.getWalls())
			if (x + width > wall.getLeftEdge() - 5 && x + width < wall.getLeftEdge() + 5) {
				x-=3;
				wallLeft = true;
			}
			else if (x > wall.getRightEdge() - 5 && x <  + wall.getRightEdge() + 5) {
				x+=3;
				wallRight = true;
			}
	}
}
