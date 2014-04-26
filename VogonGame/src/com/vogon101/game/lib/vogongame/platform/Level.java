package com.vogon101.game.lib.vogongame.platform;

import java.util.ArrayList;

import com.vogon101.game.lib.vogongame.VogonGameException;

/**
 * The level class has the list of mobs and platforms
 * that are rendered to the screen. Also use the 
 * double WIDTH and HEIGHT found inside this class
 * not the ones in the main {@link Game} class
 * @author Freddie Poser
 *
 */
public class Level {

	protected ArrayList<Mob> mobs  = new ArrayList<Mob>();
	protected ArrayList<Platform> platforms = new ArrayList<Platform>();
	protected ArrayList<Coin> coins = new ArrayList<Coin>();
	protected ArrayList<Wall> walls = new ArrayList<Wall>();
	protected Game game;
	protected double WIDTH, HEIGHT;
	
	/**
	 * <bConstrutor<b>
	 * @param game_ Reference game object
	 */
	public Level (Game game_) {
		game = game_;
	}
	
	/**
	 * <bConstrutor<b>
	 * @param game_ Reference game object
	 * @param WIDTH_ Width of the screen
	 * @param HEIGHT_ Height of the screen
	 */
	public Level (Game game_, double WIDTH_, double HEIGHT_) {
		game = game_;
		WIDTH =  WIDTH_;
		HEIGHT = HEIGHT_;
	}
	
	/**
	 * Add a new mob off screen, must have x and y set before being drawn
	 */
	public void addMob() {
		mobs.add(new Mob(this));
	}
	
	/**
	 * Add a new mob at coordinates
	 * @param x
	 * @param y
	 */
	public void addMob(double x, double y) {
		mobs.add(new Mob(this, x, y));
	}
	
	/**
	 * Add a new mob at coordinates with speed and direction
	 * @param x
	 * @param y
	 * @param xSpeed
	 * @param ySpeed
	 */
	public void addMob(double x, double y, int xSpeed, int ySpeed) {
		mobs.add(new Mob(this, x, y, xSpeed, ySpeed));
	}
	
	/**
	 * Add a new mob at coordinates with speed and direction
	 * and also default speed for reference
	 * @param x
	 * @param y
	 * @param xSpeed
	 * @param ySpeed
	 * @param xSave
	 * @param ySave
	 * @throws VogonGameException
	 */
	public void addMob(double x, double y, int xSpeed, int ySpeed, int xSave, int ySave){
		mobs.add(new Mob(this, x, y, xSpeed, ySpeed, xSave, ySave));
	}
	
	/**
	 * Return the list of {@link Mob}s in the current {@link Level}
	 * @return
	 */
	public ArrayList<Mob> getMobs() {
		return mobs;
	}
	
	
	/**
	 * Return the list of {@link Platform}s in the current {@link Level}
	 * @return
	 */
	public ArrayList<Platform> getPlatforms() {
		return platforms;
	}
	
	/**
	 * Set the dimensions of the level (the screen dimensions)
	 * @param WIDTH_
	 * @param HEIGHT_
	 */
	public void setDimens (double WIDTH_, double HEIGHT_) {
		
		WIDTH =  WIDTH_;
		HEIGHT = HEIGHT_;
		
	}
	
	/**
	 * Override to add level generation
	 */
	public void genLevel() {
		
	}
	
	/**
	 * Add a new platform to the level
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void addPlatform (double x, double y, double width, double height) {
		platforms.add(new Platform(x, y, width, height, this));
	}
	
	/**
	 * Returns the {@link ArrayList} of coins
	 * @return
	 */
	public ArrayList<Coin> getCoins () {
		return coins;
	}
	
	/**
	 * Add a new block to the level
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void addBlock (double x, double y, double width, double height) {
		platforms.add(new Block(x, y, width, height, this));
	}
	
	/**
	 * Return the reference object
	 * @return
	 */
	public Game getGame () {
		return game;
	}
	
	public void addGoalPlatform(double x, double y, double width, double height) {
		platforms.add(new GoalPlatform(x, y, width, height, this));
	}

	public void gen(int levelnum){
		addMob(200,200,1,0,1,0);
		addPlatform(300, 50, 100, 16);
		addBlock(450, 75, 25, 25);
		addPlatform(570, 50, 100, 16);
		addWall(700, 0, 32, 500);
	}
	
	public void addWall(int x, int y, int width , int height) {
		walls.add(new Wall(this, x, y, width, height));
	}
	
	public ArrayList<Wall> getWalls () {
		return walls;
	}
	
}
