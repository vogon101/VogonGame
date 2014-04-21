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
	protected Game game;
	protected double WIDTH, HEIGHT;
	
	public Level (Game game_) {
		game = game_;
	}
	
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
	public void addMob(double x, double y, int xSpeed, int ySpeed, int xSave, int ySave) throws VogonGameException {
		mobs.add(new Mob(this, x, y, xSpeed, ySpeed, xSave, ySave));
	}
	
	public ArrayList<Mob> getMobs() {
		return mobs;
	}
	
	public ArrayList<Platform> getPlatforms() {
		return platforms;
	}
	
	public void setDimens (double WIDTH_, double HEIGHT_) {
		
		WIDTH =  WIDTH_*2;
		HEIGHT = HEIGHT_*2;
		
	}
	
}
