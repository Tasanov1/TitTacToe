package SERVER;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player extends PlayerManager{

	char mark;
    Player opponent;
    Socket socket;
    BufferedReader input;
    PrintWriter output;
	
    public Player(Socket socket, char mark) {
        this.socket = socket;
        this.mark = mark;
        try {
            input = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println("WELCOME " + mark);
	        output.println("MESSAGE Waiting for opponent to connect");
	    } catch (IOException e) {
	        System.out.println("Player died: " + e);
	    }
	}

	@Override
	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}

	@Override
	public void otherPlayerMoved(int location) {
		output.println("OPPONENT_MOVED " + location);
        output.println(
            TicTacToeServer.gameHasWinner() ? "DEFEAT" : TicTacToeServer.gameBoardFilledUp() ? "TIE" : "");
	}
	
	@Override
	public void run() {
		try {
            // The thread is only started after everyone connects.
            output.println("MESSAGE All players connected");

            // Tell the first player that it is her turn.
            if (mark == 'X') {
                output.println("MESSAGE Your move");
            }

            // Repeatedly get commands from the client and process them.
            while (true) {
                String command = input.readLine();
                if (command.startsWith("MOVE")) {
                    int location = Integer.parseInt(command.substring(5));
                    if (TicTacToeServer.legalGameMove(location, this)) {
                        output.println("VALID_MOVE");
                        output.println(TicTacToeServer.gameHasWinner() ? "VICTORY"
                                     : TicTacToeServer.gameBoardFilledUp() ? "TIE"
                                     : "");
                    } else {
                        output.println("MESSAGE Invalid move");
                    }
                } else if (command.startsWith("QUIT")) {
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("Player died: " + e);
        } finally {
            try {
            	socket.close();
            } catch (IOException e) {}
        }
	}

}
