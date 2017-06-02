package CLIENT;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Frame extends TicTacToeFrame{

	private JFrame frame = new JFrame("Tic Tac Toe");
    private JLabel messageLabel = new JLabel("");
    private ImageIcon icon;
    private ImageIcon opponentIcon;
    
    private Square[] board = new Square[9];
    private Square currentSquare;
	
    public Frame(){
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(240, 240);
        frame.setVisible(true);
        frame.setResizable(false);
    	
    	messageLabel.setBackground(Color.lightGray);
        frame.getContentPane().add(messageLabel, "South");
        
        JPanel boardPanel = new JPanel();
        boardPanel.setBackground(Color.black);
        boardPanel.setLayout(new GridLayout(3, 3, 2, 2));
        for (int i = 0; i < board.length; i++) {
            final int j = i;
            board[i] = new Square();
            board[i].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    currentSquare = board[j];
                    TicTacToeClient.writeToClient("MOVE " + j);
                }
            });
            boardPanel.add(board[i]);
        }
        frame.getContentPane().add(boardPanel, "Center");
    }
    
	@Override
	public boolean wantsToPlayAgain() {
		int response = JOptionPane.showConfirmDialog(frame,
	            "Want to play again?",
	            "Tic Tac Toe",
	            JOptionPane.YES_NO_OPTION);
	        frame.dispose();
	        return response == JOptionPane.YES_OPTION;
	}

	@Override
	public void setFrameIcon(char mark) {
		String xPath = Frame.class.getResource("x.png").getPath();
		String oPath = Frame.class.getResource("o.png").getPath();
		icon = new ImageIcon(mark == 'X' ? xPath : oPath);
        opponentIcon  = new ImageIcon(mark == 'X' ? oPath : xPath);
        frame.setTitle("Tic Tac Toe - Player " + mark);
	}

	@Override
	public void valideMove() {
		messageLabel.setText("Valid move, please wait");
        currentSquare.setIcon(icon);
        currentSquare.repaint();
	}

	@Override
	public void opponentMoved(int loc) {
		currentSquare = board[loc];
		currentSquare.setIcon(opponentIcon);
		currentSquare.repaint();
        messageLabel.setText("Opponent moved, your turn");
	}

	@Override
	public void setMessage(String message) {
		messageLabel.setText(message);
	}

}
