package com.vogon101.game.lib.vogongame.test;

import org.lwjgl.LWJGLException;

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
	
	@Override
	public void start() throws VogonGameException {
		try {
			initGl();
		} catch (LWJGLException e1) {
			e1.printStackTrace();
			throw new VogonGameException("ERROR IN GL INIT! MESSAGE - " + e1.getMessage());
		}
		try {
			player.setTexture("res/char.png");
		} catch (VogonGameException e) {
			e.printStackTrace();
		}
		mainloop();
	}

	private static TestGame game;
	public static void main (String a[]) throws VogonGameException {
		
		game = new TestGame(1280,720);
		game.getLevel().addMob(200,200,1,0,1,0);
		game.getLevel().addPlatform(300, 50, 100, 16);
		game.getLevel().addBlock(450, 50, 25, 25);
		game.start();
		
		
	}
	
	@Override
	public void addLogic() {
	}
	

	
	
}
