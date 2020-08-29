/**
 * @author Eli Estridge; eestridge@middlebury.edu
 * A visualization of problem 73, "Rooster Chase," in Levitin & Levitin's Algorithmic Puzzles.
 */

package farmerrooster;
public class Main{
	/* Initializes game board using the parameters Gamespace(boardsize, isRoostersTurn)
	 * set isRoostersTurn false to make the farmer go first.  Set the board size to 8 for the board size of the
	 * original problem (it shouldn't matter since it is a problem of parity). */
	public static void main(String[] args) {
		Gamespace game = new Gamespace(Integer.parseInt(args[0]), Boolean.parseBoolean(args[1]));
		game.drawGame();
		while (game.isCaught() == false) {

			//Adjust for better experience.  sleep(n) = n milliseconds.
			try { Thread.sleep(500);}
			catch (InterruptedException e) { Thread.currentThread().interrupt();e.printStackTrace();}

			game.move();
			game.drawGame();

		}
		System.out.println("CAUGHT!!!");
	}

}
