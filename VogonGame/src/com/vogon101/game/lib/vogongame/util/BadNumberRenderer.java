package com.vogon101.game.lib.vogongame.util;

import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;

import com.vogon101.game.lib.vogongame.VogonGameException;

public class BadNumberRenderer {

	private static Texture one, two, three, four, five, six, seven, eight, nine, zero;
	private static final int WIDTH = 16, HEIGHT = 16;
	
	public static void init() throws VogonGameException {
		one  = VogonTextureLoader.loadTexture("res/font/1.png");
		two  = VogonTextureLoader.loadTexture("res/font/2.png");
		three  = VogonTextureLoader.loadTexture("res/font/3.png");
		four  = VogonTextureLoader.loadTexture("res/font/4.png");
		five  = VogonTextureLoader.loadTexture("res/font/5.png");
		six  = VogonTextureLoader.loadTexture("res/font/6.png");
		seven  = VogonTextureLoader.loadTexture("res/font/7.png");
		eight  = VogonTextureLoader.loadTexture("res/font/8.png");
		nine  = VogonTextureLoader.loadTexture("res/font/9.png");
		zero  = VogonTextureLoader.loadTexture("res/font/2.png");
		
	}
	
	public static void renderNumber(String num, double x, double y) throws VogonGameException {
		glPushMatrix();
		glTranslated(x, y, 0);
		for (int i  = 0; i < num.length(); i++) {
			if (num.charAt(i)  == '0') {
				Texture texture = zero;				
				glBegin(GL_QUADS);
				texture.bind();
				{
					glTexCoord2d(1, 1);
					glVertex2d(0, 0);
					glTexCoord2d(0, 1);
					glVertex2d(WIDTH, 0);
					glTexCoord2d(0, 0);
					glVertex2d(WIDTH, HEIGHT);
					glTexCoord2d(1, 0);
					glVertex2d(0, HEIGHT);
				}
				texture.release();
				glEnd();
			}
			else if (num.charAt(i)  == '1') {
				
			}
			else if (num.charAt(i)  == '2') {
							
						}
			else if (num.charAt(i)  == '3') {
				
			}
			else if (num.charAt(i)  == '4') {
				
			}
			else if (num.charAt(i)  == '5') {
				
			}
			else if (num.charAt(i)  == '6') {
							
						}
			else if (num.charAt(i)  == '7') {
				
			}
			else if (num.charAt(i)  == '8') {
				
			}
			else if (num.charAt(i)  == '9') {
				
			}
			else {
				throw new VogonGameException("The string was not madeup of only numbers");
			}
			
		}
	}
	
}
