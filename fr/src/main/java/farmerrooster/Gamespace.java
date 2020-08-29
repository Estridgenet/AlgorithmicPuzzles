package farmerrooster;
import java.util.Random;

public class Gamespace {
	int size;
	char[] board;
	Player farmer, rooster; // for farmer pos and rooster pos
	boolean turn;
	private Random rand;

	/* Gamespace Constructor: boolean whoGoesFirst false = Farmer, True = Rooster */
	Gamespace(int arraySize, boolean whoGoesFirst) {
		size = arraySize;
		turn = whoGoesFirst;
		farmer = new Player(size * (size - 1));
		rooster = new Player(size - 1);
		rand = new Random();
		createBoard();

	}

	/* Creates game board of size size using special board-drawing ASCII chars */
	private void createBoard() {
		char[] specialChars = { '┌', '┐', '└', '┘', '├', '┤', '┬', '┴', '┼' };
		board = new char[size * size];

		// T and +
		for (int i = 0; i < board.length; i++) {
			// top T squares:
			if (i < size) {
				board[i] = specialChars[6];
			}
			// left T squares
			else if (i % size == 0) {
				board[i] = specialChars[4];
			}
			// right T squares
			else if ((i % size) == (size - 1)) {
				board[i] = specialChars[5];
			}
			// bottom T squares
			else if (i >= size * (size - 1)) {
				board[i] = specialChars[7];
			} else {
				board[i] = specialChars[8];
			}
		}

		// Corners
		board[0] = specialChars[0];
		board[size - 1] = specialChars[1];
		board[size * (size - 1)] = specialChars[2];
		board[size * size - 1] = specialChars[3];
	}

	/* Draws the game Board with 'F' and 'R' overlayed via conditional check  */
	public void drawGame() {

		for (int i = 0; i < board.length; i++) {
			if (i % size == 0) {
				System.out.println();
			}
			if (i == rooster.pos && i != farmer.pos) { // if rooster.pos == farmer.pos, don't show 'R' to signal farmer caught rooster.
				System.out.print('R' + " ");
			} else if (i == farmer.pos) {
				System.out.print('F' + " ");
			} else {
				System.out.print(board[i] + " ");
			}
		}
		System.out.println();

	}

	/*
	 * gives valid movement for farmer. illegal moves: can't move from an index%size
	 * == 0 to index%size == -1 square.
	 */
	public int[] validMoves(Player player) {
		// move generation:
		int[] validMovesList = { -1, -1, -1, -1 }; // -1 for illegal move
		// up
		if (player.pos >= size) {
			validMovesList[0] = player.pos - size;
		}

		// down
		if (player.pos < size * (size - 1)) {
			validMovesList[1] = player.pos + size;
		}

		// left
		if (player.pos % size != 0) {
			validMovesList[2] = player.pos - 1;
		}

		// right
		if (player.pos % size != size - 1) {
			validMovesList[3] = player.pos + 1;

		}
		return validMovesList;
	}

	/*
	 * best movement chooser; examines column distance by integer division of
	 * potential position and opponent position. examines row distance by the modulo
	 * of potential move - modulo of opponent position. If there are two equally
	 * good moves, it randomly chooses one.
	 */
	public void move() {
		int moves[];
		int rowDistance, columnDistance, playerDistance;

		// Farmer
		if (turn == false) {
			moves = validMoves(farmer);

			int bestMove = moves[0];
			int bestDistance = size * size; // we want to minimize distance so we set the distance to the highest
											// possible value.
			for (int i = 0; i < moves.length; i++) {
				if (moves[i] != -1) {
					columnDistance = (Math.abs(rooster.pos - moves[i]) / size);
					rowDistance = Math.abs((rooster.pos % size) - (moves[i] % size));
					playerDistance = rowDistance + columnDistance;
					if (playerDistance < bestDistance) {
						bestMove = moves[i];
						bestDistance = playerDistance;
					} else if (playerDistance == bestDistance) {
						if (rand.nextInt(2) == 1) {
							bestMove = moves[i];
							bestDistance = playerDistance;
						}
					}
				}
			}

			farmer.pos = bestMove;
			turn = true;

			// Rooster
		} else {
			moves = validMoves(rooster);

			int bestMove = moves[0];
			int bestDistance = 0; // we want to maximize distance so we set it to 0.
			for (int i = 0; i < moves.length; i++) {
				if (moves[i] != -1) {
					columnDistance = (Math.abs(farmer.pos - moves[i]) / size);
					rowDistance = Math.abs((farmer.pos % size) - (moves[i] % size));
					playerDistance = rowDistance + columnDistance;
					if (playerDistance > bestDistance) {
						bestMove = moves[i];
						bestDistance = playerDistance;
					} else if (playerDistance == bestDistance) {
						if (rand.nextInt(2) == 1) {
							bestMove = moves[i];
							bestDistance = playerDistance;
						}
					}
				}
			}

			rooster.pos = bestMove;
			turn = false;
		}

	}

	public boolean isCaught() {
		if (farmer.pos == rooster.pos) {
			return true;
		}
		return false;
	}

	/* Useful print functions for debugging */
	public void test() {
		int[] moves;
		System.out.println("current pos farmer: " + farmer.pos);
		System.out.print("valid Moves Farmer: ");
		moves = validMoves(farmer);
		for (int i = 0; i < moves.length; i++) {
			System.out.print(moves[i] + " ");
		}
		System.out.println();
		System.out.println("current pos rooster: " + rooster.pos);
		System.out.print("valid Moves Rooster: ");
		moves = validMoves(rooster);
		for (int i = 0; i < moves.length; i++) {
			System.out.print(moves[i] + " ");
		}
		System.out.println();

	}

}
