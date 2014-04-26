package com.vogon101.game.lib.vogongame.test;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.vogon101.game.lib.vogongame.VogonGameException;
import com.vogon101.game.lib.vogongame.platform.Game;
import com.vogon101.game.lib.vogongame.platform.Level;

/**
 * Just a test
 * @author Freddie Poser
 *
 */
public class TestGame extends Game{

	private Audio oggEffect;
	
	public TestGame(int width, int height) {
		
		TestPlayer tp = new TestPlayer(this, null, 100, 100);
		tp.setPos(100, 100);
		try {
			tp.jumpSound = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/jump.wav"));
			tp.coinSound = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/coin.wav"));
			tp.coinSound = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("res/attack.wav"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		tp.setBaseFloor(0);
		setPlayer(tp);
		setDimen(width, height);
		setLevel(new Level(this, width, height));
		tp.setLevel(getLevel());
		
	}
	
	@Override
	public void start() throws VogonGameException {
		try {
			oggEffect = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/music.ogg"));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		 
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
		oggEffect.playAsMusic(1.0f, 1.0f, false);
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
