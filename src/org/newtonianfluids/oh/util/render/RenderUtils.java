package org.newtonianfluids.oh.util.render;

///////////////////////////////////////////////
//
// File:			RenderHandler.java
// Date Created:	12/01/2018
// Last Modified:	12/01/2018
// Author:			Simon Ou
//
///////////////////////////////////////////////

import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.opengl.Texture;

import org.newtonianfluids.oh.Main;

/**
 * Utility class for rendering all objects to screen
 * 
 * @author simon
 *
 */
public final class RenderUtils {
	public static final int ROTATE_BY_CENTRE = 1;
	public static final int ROTATE_BY_TOP_LEFT = 2;

	private static double imgRotation = 0.0;
	private static int rotationMode = ROTATE_BY_CENTRE;

	private static float aOffset;
	private static float bOffset;
	private static float cOffset;
	private static float dOffset;
	private static float eOffset;
	private static float fOffset;
	private static float lastWidth;
	private static float lastHeight;

	/**
	 * Draws the given texture to screen
	 * 
	 * @param tex
	 *            Slick2D object instance of desired texture
	 * @param texLeft
	 *            left UV bound (0.0F)
	 * @param texRight
	 *            right UV bound (1.0F)
	 * @param texTop
	 *            top UV bound (0.0F)
	 * @param texBottom
	 *            bottom UV bound (1.0F)
	 * @param imgLeft
	 *            left screen bound (-1.0F)
	 * @param imgRight
	 *            right screen bound (1.0F)
	 * @param imgTop
	 *            top screen bound (1.0F)
	 * @param imgBottom
	 *            bottom screen bound (-1.0F)
	 */
	public static void draw(Texture tex, float texLeft, float texRight, float texTop, float texBottom, float imgLeft,
			float imgRight, float imgTop, float imgBottom) {
		tex.bind();
		glBegin(GL_TRIANGLES);

		if (imgRotation != 0.0) {
			if (imgRight - imgLeft != lastWidth || imgTop - imgBottom != lastHeight)
				calculateRotation(imgRight - imgLeft, imgTop - imgBottom);

			if (rotationMode == ROTATE_BY_CENTRE) {
				glTexCoord2f(texRight, texTop);
				glVertex2f(imgRight - aOffset, imgTop + bOffset / Main.HW_RATIO);
				glTexCoord2f(texLeft, texTop);
				glVertex2f(imgLeft - cOffset, imgTop - dOffset / Main.HW_RATIO);
				glTexCoord2f(texLeft, texBottom);
				glVertex2f(imgLeft + aOffset, imgBottom - bOffset / Main.HW_RATIO);
				glTexCoord2f(texLeft, texBottom);
				glVertex2f(imgLeft + aOffset, imgBottom - bOffset / Main.HW_RATIO);
				glTexCoord2f(texRight, texBottom);
				glVertex2f(imgRight + cOffset, imgBottom + dOffset / Main.HW_RATIO);
				glTexCoord2f(texRight, texTop);
				glVertex2f(imgRight - aOffset, imgTop + bOffset / Main.HW_RATIO);
			} else {
				glTexCoord2f(texRight, texTop);
				glVertex2f(imgRight + aOffset, imgTop - bOffset / Main.HW_RATIO);
				glTexCoord2f(texLeft, texTop);
				glVertex2f(imgLeft, imgTop);
				glTexCoord2f(texLeft, texBottom);
				glVertex2f(imgLeft + cOffset, imgBottom - dOffset / Main.HW_RATIO);
				glTexCoord2f(texLeft, texBottom);
				glVertex2f(imgLeft + cOffset, imgBottom - dOffset / Main.HW_RATIO);
				glTexCoord2f(texRight, texBottom);
				glVertex2f(imgRight + eOffset, imgBottom - fOffset / Main.HW_RATIO);
				glTexCoord2f(texRight, texTop);
				glVertex2f(imgRight + aOffset, imgTop - bOffset / Main.HW_RATIO);
			}
		} else {
			glTexCoord2f(texRight, texTop);
			glVertex2f(imgRight, imgTop);
			glTexCoord2f(texLeft, texTop);
			glVertex2f(imgLeft, imgTop);
			glTexCoord2f(texLeft, texBottom);
			glVertex2f(imgLeft, imgBottom);
			glTexCoord2f(texLeft, texBottom);
			glVertex2f(imgLeft, imgBottom);
			glTexCoord2f(texRight, texBottom);
			glVertex2f(imgRight, imgBottom);
			glTexCoord2f(texRight, texTop);
			glVertex2f(imgRight, imgTop);
		}

		glEnd();
	}

	/**
	 * Draws the entirety of given texture to screen
	 * 
	 * @param tex
	 *            Slick2D object instance of desired texture
	 * @param imgLeft
	 *            left screen bound (-1.0F)
	 * @param imgRight
	 *            right screen bound (1.0F)
	 * @param imgTop
	 *            top screen bound (1.0F)
	 * @param imgBottom
	 *            bottom screen bound (-1.0F)
	 */
	public static void draw(Texture tex, float imgLeft, float imgRight, float imgTop, float imgBottom) {
		draw(tex, 0.0F, 1.0F, 0.0F, 1.0F, imgLeft, imgRight, imgTop, imgBottom);
	}

	/**
	 * Sets angle of rotation for future renders, must be called every time the
	 * rotation angle needs to be changed
	 * 
	 * @param degrees 	measure of angle
	 */
	public static void setRotation(double degrees) {
		imgRotation = degrees % 360.0;
		lastWidth = -1;
	}

	/**
	 * Sets type of rotation
	 * 
	 * @param mode 	ROTATE_BY_CENTRE or ROTATE_BY_TOP_LEFT
	 */
	public static void setRotationMode(int mode) {
		rotationMode = mode;
		lastHeight = -1;
	}

	/**
	 * Calculates the vertex offsets based on rotation angle and screen coordinates
	 * 
	 * @param width		width of texture on screen
	 * @param height	width of texture on screen
	 */
	private static void calculateRotation(float width, float height) {
		lastWidth = width;
		lastHeight = height;

		height *= Main.HW_RATIO;

		if (rotationMode == ROTATE_BY_CENTRE) {
			double hyp = Math.sqrt(width * width + height * height) / 2;
			double theta = Math.asin(height / (2 * hyp)) + Math.toRadians(imgRotation);
			aOffset = (float) (width / 2 - hyp * Math.cos(theta));
			bOffset = (float) (hyp * Math.sin(theta) - height / 2);
			theta = (Math.PI / 2 - Math.asin(height / (2 * hyp)) + Math.toRadians(imgRotation));
			cOffset = (float) (hyp * Math.sin(theta) - width / 2);
			dOffset = (float) (height / 2 - hyp * Math.cos(theta));
		} else {
			double theta = Math.toRadians(imgRotation);
			aOffset = (float) (-1 * width * (1 - Math.cos(theta)));
			bOffset = (float) (-1 * width * Math.sin(theta));
			cOffset = (float) (height * Math.sin(theta));
			System.out.println(cOffset);
			dOffset = (float) (-1 * height * (1 - Math.cos(theta)));
			eOffset = (float) (cOffset + width * (Math.cos(theta) - 1));
			fOffset = (float) (bOffset + dOffset);
		}
	}
}
