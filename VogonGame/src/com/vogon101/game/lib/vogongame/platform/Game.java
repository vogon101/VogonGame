package com.vogon101.game.lib.vogongame.platform;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glVertex2d;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import com.vogon101.game.lib.vogongame.VogonGameException;
import com.vogon101.game.lib.vogongame.util.BadTextRender;

/**
 * <b>The main game class</b>
 * This class should be init'd before using, THIS IS NOT A STATIC CLASS
 * To use this class:<br/>
 * <b>1:</b> Create a class that extends this class<br/>
 * <b>2:</b> Override the logic method and maybe the render method<br/>
 * Remember to set the level and player as well as the screen size
 * @author Freddie Poser
 *
 */
public class Game {

	protected Level level = null;
	protected Player player = null;
	protected int WIDTH, HEIGHT, floor, levelnum = 1;
	protected String title = "Vogongame";
	
	public Game() {
	}
	
	/**
	 * Called when the window is closed by the user
	 */
	public void onClose() {
	}
	
	/**
	 * Set the player to render/use
	 * @param player  - The player object
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	/**
	 * Set the level, should be called each time the level changes
	 * @param lvl  -The level object
	 */
	public void setLevel(Level lvl) {
		level = lvl;
	}
	
	/**
	 * Set the screen size, CALL ONLY ONCE
	 * @param w - width
	 * @param h - height
	 */
	public void setDimen (int w, int h) {
		WIDTH = w;
		HEIGHT = h;
	}
	

	protected void mainloop() {
		
		while (!Display.isCloseRequested()) {
			logic();
			render();
			Display.update();
		}
		
		onClose();
		Display.destroy();
		System.exit(0);
		
	}
	
	/**
	 * Start the game mainloop
	 * @throws VogonGameException
	 */
	public void start() throws VogonGameException {
		if (level == null) {
			throw new VogonGameException("The starting level is null");
		}
		else if (player == null) {
			throw new VogonGameException("The player is null");
		}
		try {
			initGl();
		} catch (LWJGLException e) {
			throw new VogonGameException("The window could not be created; ERROR: " + e.getMessage());
		}
		
		BadTextRender.initText();
		
		mainloop();
		
	}
	
	/**
	 * Returns the level object for reference and other such
	 * @return
	 */
	public Level getLevel() {
		return level;
	}
	
	/**
	 * Returns the player object for reference and other such
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}
	
	/** 
	 * Loops all the logics
	 */
	private void logic() {
		for (Mob mob : level.getMobs()) {
			mob.logic();
		}
		
		for (Platform plat : level.getPlatforms()) {
			plat.logic();
		}
		for (Coin coin : level.getCoins()) {
			coin.logic();
		}
		player.logic();
		addLogic();
		playMusic();
	}
	/**
	 * Override this method to update additional things,
	 * this is called when {@link logic()} runs
	 * 
	 * override this if you need to add additional objects to update that are not
	 * contained in the {@link Player.logic()}or the updating of all the
	 * mobs/platforms in the level
	 */
	public void addLogic() {
		
	}
	
	private void render() {
		setCamera();
		drawBG();
		for (Mob mob : level.getMobs()) {
			mob.draw();
		}
		
		for (Platform plat : level.getPlatforms()) {
			plat.draw();
		}
		for (Coin coin : level.getCoins()) {
			coin.draw();
		}
		//TODO: FIX BAD CODE 
		for (Wall wall : level.walls) {
			wall.draw();
		}
		player.draw();
		
		addRender();
		Display.sync(120);
	}
	
	/**
	 * DO NOT OVERRIDE
	 * 
	 */
	private void setCamera() {
		glClearColor(1f, 1f, 1f, 1.0f);
		// Clear
		glClear(GL_COLOR_BUFFER_BIT);
		// Modify projection matrix - 2d projection
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, 0, HEIGHT, -1, 1);

		// Modify modelview matrix
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		GL11.glViewport(0,0,WIDTH,HEIGHT);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
 
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, WIDTH, 0, HEIGHT, -1, 1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

	}
	
	/**
	 * Override this method to render additional things,
	 * this is called when {@link render()} runs
	 * 
	 * Override this if you need to add additional objects to render that are not
	 * contained in the {@link Player.draw()}or the rendering of all the
	 * mobs/platforms/coins in the level
	 */
	public void addRender() {
		
	}
	
	/**
	 * DO NOT OVERRIDE
	 * @throws LWJGLException
	 */
	protected void initGl() throws LWJGLException{
		Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
		Display.create();
		Display.setTitle(title);
	}
	
	public int getWidth(){
		return WIDTH;
	}
	
	public int getHeight(){
		return HEIGHT;
	}
	
	/**
	 * Render the Background
	 */
	public void drawBG() {
		
		/*
		 * For a quad the coords are:
		 * vertex 1 = 0, 0
		 * vertex 2 = width, 0
		 * vertex 3 = width, height
		 * vertex 4 = 0, height
		 */
		
		glBegin(GL_QUADS);
		{
			glColor3d(0.4, 0.4, 1);
			glVertex2d(0, 0);
			glVertex2d(WIDTH, 0);
			glColor3d(0.1, 0, 1);
			glVertex2d(WIDTH, HEIGHT);
			glVertex2d(0, HEIGHT);
		}
		glEnd();
	}
	
	public void levelWin (){
		System.out.println("WIN");
		player.reset();
		levelnum++;
		level.gen(levelnum);
	}
	
	public void playMusic() {
		
	}
}