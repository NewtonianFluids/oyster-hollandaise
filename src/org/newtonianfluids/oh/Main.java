package org.newtonianfluids.oh;

///////////////////////////////////////////////
//
// File:			Main.java
// Date Created:	12/01/2018
// Last Modified:	12/01/2018
// Author:			Simon Ou
//
///////////////////////////////////////////////

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import org.newtonianfluids.oh.state.GameStateManager;

/**
 * Class containing main method, initializes display and game loop
 * 
 * @author Simon Ou
 * 
 */
public class Main {
	public static final String WINDOW_TITLE = "Oyster Hollandaise";
	public static final int TARGET_FPS = 60;

	/*
	 * Screen dimensions
	 */
	public static int DISPLAY_WIDTH;
	public static int DISPLAY_HEIGHT;
	public static float HW_RATIO;
	public static int STATES = 3;
	/**
	 * M A I N
	 * 
	 * M E T H O D
	 */
	public static void main(String... args) {
		try {
			/*
			 * Sets display properties and creates window
			 */
			DISPLAY_WIDTH = Display.getDesktopDisplayMode().getWidth();
			DISPLAY_HEIGHT = Display.getDesktopDisplayMode().getHeight();
			HW_RATIO = ((float) DISPLAY_HEIGHT) / ((float) DISPLAY_WIDTH);
			Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
			Display.setTitle(WINDOW_TITLE);
			Display.create();
		} catch (LWJGLException ex) {
			ex.printStackTrace();
			destroy(1);
		}

		/*
		 * Initializes LWJGL rendering modes
		 */
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(-1, 1, -1, 1, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		/*
		 * Initializes all game states, sets current game state to start of game
		 */
		GameStateManager.createStates(STATES);
		GameStateManager.initStates();
		GameStateManager.setState(GameStateManager.IN_GAME); // temporarily skips directly into game

		/*
		 * Game loop, continues as long as a destroy attempt isn't called
		 */
		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT);

			/*
			 * Detect user input
			 */
			GameStateManager.keyboardEvent();
			GameStateManager.mouseEvent();

			/*
			 * Update game info and draw to screen
			 */
			GameStateManager.update();
			GameStateManager.draw();

			Display.update();
			Display.sync(TARGET_FPS); // Wait until next frame
		}
		destroy(0); // called when game loop is exited
	}

	/**
	 * Terminates the program
	 * 
	 * @param exitStatus
	 *            program abortion type (0 for standard, 1 for error)
	 */
	public static void destroy(int exitStatus) {
		GameStateManager.releaseAll();
		Display.destroy();
		System.exit(exitStatus);
	}
}
