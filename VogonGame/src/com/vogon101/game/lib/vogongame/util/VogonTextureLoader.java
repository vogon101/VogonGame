package com.vogon101.game.lib.vogongame.util;

import java.io.File;
import java.io.FileInputStream;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import com.vogon101.game.lib.vogongame.VogonGameException;

public class VogonTextureLoader {

	public static Texture loadTexture (String path) throws VogonGameException {
		Texture texture;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream(new File(path)));
		} catch (Exception e)  {
			throw new VogonGameException(("The texture could not be loaded - Exception trace:   \n" + e.getMessage()));
		}
		return texture;
	}
	
	public static Texture loadTexture (String path, String type) throws VogonGameException {
		Texture texture;
		try {
			texture = TextureLoader.getTexture(type, new FileInputStream(new File(path)));
		} catch (Exception e)  {
			throw new VogonGameException(("The texture could not be loaded - Exception trace:   \n" + e.getMessage()));
		}
		return texture;
	}
	
	
}
