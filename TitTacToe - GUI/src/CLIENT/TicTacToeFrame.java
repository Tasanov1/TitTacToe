package CLIENT;

public abstract class TicTacToeFrame implements WantsToPlayAgain {

	public abstract void setFrameIcon(char mark);

	public abstract void valideMove();

	public abstract void opponentMoved(int loc);

	public abstract void setMessage(String message);

}
