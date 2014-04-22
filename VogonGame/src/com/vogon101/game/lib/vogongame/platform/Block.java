package com.vogon101.game.lib.vogongame.platform;

/**
 * Block class, platform that gives coins/items
 * @author Freddie Poser
 *
 */
public class Block extends Platform {

	protected boolean payed;

	public Block(double x, double y, double width, double height, Level level) {
		super(x, y, width, height, level);
		setColor(1.0, 0.5, 0.4);
	}

	public void land() {
		payed = true;
	}

}
