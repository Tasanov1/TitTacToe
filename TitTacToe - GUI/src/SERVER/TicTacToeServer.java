package SERVER;
import java.net.ServerSocket;

public class TicTacToeServer {

	private static Game game = new Game();
	
    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(8902);
        System.out.println("Tic Tac Toe Server is Running");
        try {
            while (true) {
                Player playerX = new Player(listener.accept(), 'X');
                Player playerO = new Player(listener.accept(), 'O');
                playerX.setOpponent(playerO);
                playerO.setOpponent(playerX);
                game.setCurrentPlayer(playerX);
                playerX.start();
                playerO.start();
            }
        } finally {
            listener.close();
        }
    }

	public static boolean gameHasWinner() {
		return game.hasWinner();
	}

	public static boolean gameBoardFilledUp() {
		return game.boardFilledUp();
	}

	public static boolean legalGameMove(int location, Player player) {
		return game.legalMove(location, player);
	}
}
