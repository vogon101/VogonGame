package com.vogon101.game.lib.vogongame.test;

import org.lwjgl.LWJGLException;

import com.vogon101.game.lib.vogongame.VogonGameException;
import com.vogon101.game.lib.vogongame.platform.Game;
import com.vogon101.game.lib.vogongame.platform.Level;
import com.vogon101.game.lib.vogongame.util.BadNumberRenderer;

/**
 * Just a test
 * @author Freddie Poser
 *
 */
public class TestGame extends Game{

	public TestGame(int width, int height) {
		
		TestPlayer tp = new TestPlayer(this, null, 100, 100);
		tp.setPos(100, 100);
		tp.setBaseFloor(0);
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
	public static void main (String a[]) throws VogonGameException, InterruptedException {
		
		game = new TestGame(1280,720);
		game.getLevel().gen(1);
		Thread.sleep(500);
		game.start();
		
		
	}
	
	@Override
	public void addLogic() {
	}
	

	
	
}
