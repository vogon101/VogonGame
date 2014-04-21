package com.vogon101.game.lib.vogongame.test;

import com.vogon101.game.lib.vogongame.VogonGameException;
import com.vogon101.game.lib.vogongame.platform.Game;
import com.vogon101.game.lib.vogongame.platform.Level;

/**
 * Just a test
 * @author Freddie Poser
 *
 */
public class TestGame extends Game{

	public TestGame(int width, int height) {
		
		TestPlayer tp = new TestPlayer(this, null);
		tp.setPos(100, 100);
		setPlayer(tp);
		setDimen(width, height);
		setLevel(new Level(this, width, height));
		tp.setLevel(getLevel());
		
	}

	private static TestGame game;
	public static void main (String a[]) throws VogonGameException {
		
		game = new TestGame(1280,720);
		game.getLevel().addMob(200,200,1,0,1,0);
		game.getLevel().addPlatform(300, 50, 100, 16);
		game.start();
		
		
	}
	
	@Override
	public void addLogic() {
	}
	

	
	
}
