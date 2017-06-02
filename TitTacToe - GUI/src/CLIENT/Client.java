package CLIENT;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Play {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
	
    public Client(String serverAddress, int port) throws UnknownHostException, IOException{
    	socket = new Socket(serverAddress, port);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }
    
	@Override
	public void play() throws Exception {
		String response;
        try {
            response = in.readLine();
            if (response.startsWith("WELCOME")) {
                char mark = response.charAt(8);
                TicTacToeClient.setFrameIcon(mark);
            }
            while (true) {
                response = in.readLine();
                if (response.startsWith("VALID_MOVE")) {
                	TicTacToeClient.validMove();
                } else if (response.startsWith("OPPONENT_MOVED")) {
                    int loc = Integer.parseInt(response.substring(15));
                    TicTacToeClient.opponentMoved(loc);
                } else if (response.startsWith("VICTORY")) {
                    TicTacToeClient.setMessage("You win");
                    break;
                } else if (response.startsWith("DEFEAT")) {
                	TicTacToeClient.setMessage("You lose");
                    break;
                } else if (response.startsWith("TIE")) {
                	TicTacToeClient.setMessage("You tied");
                    break;
                } else if (response.startsWith("MESSAGE")) {
                	TicTacToeClient.setMessage(response.substring(8));
                }
            }
            out.println("QUIT");
        }
        finally {
            socket.close();
        }
	}

	public void write(String move) {
		out.println(move);
	}

}
