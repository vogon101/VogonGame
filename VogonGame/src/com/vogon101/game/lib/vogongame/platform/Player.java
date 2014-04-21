package com.vogon101.game.lib.vogongame.platform;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import com.vogon101.game.lib.vogongame.VogonGameException;
import com.vogon101.game.lib.vogongame.util.VogonTextureLoader;


/**
 * 
 * <b>The main player class for the platform game.</b><br/>
 * Will default to square, one colour quad to represent the player on the screen.
 * This will be a 64x64 red quad. To overide this either extend the class or
 * use the {@link setTexture(String path)}, {@link setColour(double r, double g, double b)}
 * and {@link setSize(int w, int h)}
 * <br/>
 * <b>Variables to use for control</b> (All protected) <br/>
 * <b>x</b> - The x postion of the player <br/>
 * <b>y</b> - The y postion of the player <br/>
 * <b>xSpeed</b> - The x speed of the player<br/>
 * <b>ySpeed</b> - The y speed of the player<br/>
 * <b>newFloor</b> - True if the player is not on the base floor<br/>
 * <b>jump</b. - True if the player is mid jump;
 * @author Freddie Poser
 *
 */
public class Player {

	protected Game game;
	protected Texture texture = null;
	protected Level level;
	protected int height  = 64, width = 64, xSpeed = 0, ySpeed = 0, jumptimer = 0, deaths, mobkills, score;
	protected double x, y, r = 1, g = 0, b = 0, floor = 0, baseFloor = 0;
	protected boolean newFloor = false, jump  = false;
	
	
	/**
	 * <b>Constructor</b><br/>
	 * If you use this constructor you MUST set the x and y position BEFORE drawing {@link setPos(double x, double y)}<br/>
	 * @param game_ This is the main game object (ie: something that extends {@link Game})
	 * @param level_ The starting current level
	 */
	public Player ( Game game_, Level level_ ) {
		game = game_;
	}
	
	/**
	 * <b>Constructor</b><br/>
	 * Starting point of player contained in x and y <br/>
	 * @param game_ This is the main game object (ie: something that extends {@link Game})
	 * @param level The starting level
	 * @param x The starting x position
	 * @param y The starting y position
	 */
	public Player (Game game_, Level level_, double x, double y) {
		game = game_;
		this.x = x;
		this.y = y;
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
	
	public void setLevel (Level level) {
		this.level = level;
	}
	
	/**
	 * <b>The player's render method</b><br/>
	 * Override to add custom rendering otherwise it will default to a
	 * square (size set by user ({@link setSize(int w, int h)}) or default 64x64
	 * and colour red or set by user ({@link setColour (double r, double g, double b)})
	 * unless texture is set {@link setTexture (Texture texture)}
	 */
	public void draw() {
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
				glColor3d(1, 0, 0);
				glVertex2d(0, 0);
				glVertex2d(width, 0);
				glVertex2d(width, height);
				glVertex2d(0, height);
			}
			glEnd();
		}

		
		
		glPopMatrix();
	}
	
	public void logic() {
		x += xSpeed;
		y += ySpeed;
		control();
		checkPlatforms();
		gravity();
		if(!newFloor)
			floor = baseFloor;
	}
	
	public void checkPlatforms() {
		for (Platform plat: level.getPlatforms()) {
			if (isOnPlatform(plat)) {
				floor  = plat.getTopEdge();
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
	
	public void control() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W) && y <= floor){
			jump = true;
			jumptimer = 0;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S) && y >= floor || Keyboard.isKeyDown(Keyboard.KEY_S) && y >= floor+1){
			jump = false;
			jumptimer = 0;
			ySpeed = -3;
		}
		
		
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			xSpeed = 2;
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			xSpeed = -2;
		}
		else {
			xSpeed = 0;
		}
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
	 * Set the x and y coordiantes of the player. 
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
	 * Check if the player is on a particular platform
	 * @param plat
	 * @return true if player is on platform
	 */
	public boolean isOnPlatform(Platform plat) {
		if (x+width/2 > plat.getLeftEdge() && x+width/2 < plat.getRightEdge()){
			//if above top of platform
			if (y > plat.getTopEdge()-4 && y < plat.getTopEdge()+4) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Set the "ground level" for the player, the gravity will bring him
	 * down to here if he is not on a platform
	 * @param floor
	 */
	public void setBaseFloor(double floor) {
		baseFloor = floor;
	}
}
