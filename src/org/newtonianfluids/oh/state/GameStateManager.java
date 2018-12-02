package org.newtonianfluids.oh.state;

///////////////////////////////////////////////
//
// File:			GameStateManager.java
// Date Created:	12/01/2018
// Last Modified:	12/01/2018
// Author:			Simon Ou
//
///////////////////////////////////////////////

public final class GameStateManager {
	private static GameState[] GAME_STATES;
	
	public static final int LOAD_SCREEN = 0;
	public static final int MAIN_MENU = 1;
	public static final int IN_GAME = 2;

	private static int currentState;

	public static void createStates() {
		createStates(3);
	}
	
	public static void createStates(int states) {
		GAME_STATES = new GameState[states];
	}
	
	/**
	 * Initializes all game states
	 */
	public static void initStates() {
		for (GameState state : GAME_STATES) {
			if (state != null)
				state.init();
		}
	}

	/**
	 * Changes current game state
	 * 
	 * @param 	newState new game state to begin
	 */
	public static void setState(int newState) {
		currentState = newState;
	}

	/**
	 * Updates game information
	 */
	public static void update() {
		GAME_STATES[currentState].update();
	}
	
	/**
	 * Draws game to screen
	 */
	public static void draw() {
		GAME_STATES[currentState].draw();
	}
	
	/**
	 * Detects for user keyboard input
	 */
	public static void keyboardEvent() {
		GAME_STATES[currentState].keyboardEvent();
	}

	/**
	 * Detects for user mouse input
	 */
	public static void mouseEvent() {
		GAME_STATES[currentState].mouseEvent();
	}

	/**
	 * Destroys all game states
	 */
	public static void releaseAll() {
		for (GameState state : GAME_STATES) {
			if (state != null)
				state.release();
		}
	}
}
