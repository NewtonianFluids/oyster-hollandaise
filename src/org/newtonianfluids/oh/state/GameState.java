package org.newtonianfluids.oh.state;

///////////////////////////////////////////////
//
// File:			GameState.java
// Date Created:	12/01/2018
// Last Modified:	12/01/2018
// Author:			Simon Ou
//
///////////////////////////////////////////////

/**
 * GameState interface, contains all methods mandatory in a gamestate
 * 
 * @author simon
 *
 */
public interface GameState {
	/**
	 * Called upon first time game state initialization
	 */
	public void init();

	/**
	 * Update game information
	 */
	public void update();

	/**
	 * Draw game to screen
	 */
	public void draw();

	/**
	 * Destroys game state
	 */
	public void release();

	/**
	 * Processes user keyboard input
	 */
	public void keyboardEvent();

	/**
	 * Processes user mouse input
	 */
	public void mouseEvent();
}
