package CLIENT;

public class TicTacToeClient {

    

    private static TicTacToeFrame frame;
    private static Play client;

    private static int PORT = 8902;

    public static void main(String[] args) throws Exception {
        while (true) {
            String serverAddress = (args.length == 0) ? "localhost" : args[1];
            
            frame = new Frame();
            client = new Client(serverAddress, PORT);
            client.play();
            
            if (!frame.wantsToPlayAgain()) {
                break;
            }
        }
    }

	public static void setFrameIcon(char mark) {
		frame.setFrameIcon(mark);
	}

	public static void writeToClient(String move) {
		client.write(move);
	}

	public static void validMove() {
		frame.valideMove();
	}

	public static void opponentMoved(int loc) {
		frame.opponentMoved(loc);
	}

	public static void setMessage(String message) {
		frame.setMessage(message);
	}
}