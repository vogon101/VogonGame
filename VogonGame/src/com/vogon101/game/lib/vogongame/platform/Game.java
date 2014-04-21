package com.vogon101.game.lib.vogongame.platform;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.vogon101.game.lib.vogongame.VogonGameException;

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
	protected int WIDTH, HEIGHT;
	protected String title = "Vogongame";
	
	public Game() {
	}
	
	/**
	 * Called when the window is closed by the user
	 */
	public void onClose() {
		System.out.println("Game closed without errors");
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
	

	private void mainloop() {
		
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
		
		mainloop();
		
		
		
	}
	
	public Level getLevel() {
		return level;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	private void logic() {
		for (Mob mob : level.getMobs()) {
			mob.logic();
		}
		
		for (Platform plat : level.getPlatforms()) {
			plat.logic();
		}
		player.logic();
		addLogic();
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
		for (Mob mob : level.getMobs()) {
			mob.draw();
		}
		
		for (Platform plat : level.getPlatforms()) {
			plat.draw();
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
		glOrtho(0, 1280, 0, 720, -1, 1);

		// Modify modelview matrix
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

	}
	
	/**
	 * Override this method to render additional things,
	 * this is called when {@link render()} runs
	 * 
	 * Override this if you need to add additional objects to render that are not
	 * contained in the {@link Player.draw()}or the rendering of all the
	 * mobs/platforms in the level
	 */
	public void addRender() {
		
	}
	

	private void initGl() throws LWJGLException{
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
}
