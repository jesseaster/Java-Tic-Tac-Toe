import javax.swing.JFrame;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import java.awt.GridLayout;
import javax.swing.JRadioButton;

public class TicTacToe implements ActionListener{

	JFrame  window = new JFrame("Tic Tac Toe");
	JButton clearButton = new JButton("CLEAR"); 
	JButton[][] spaceButtons = new JButton[3][3];
	JLabel player1Label = new JLabel("Player 1 Score", SwingConstants.RIGHT);
	JLabel player2Label = new JLabel("Player 2 Score", SwingConstants.RIGHT);
	JTextField player1Field = new JTextField(4);
	JTextField player2Field = new JTextField(4);
	JTextField whoWonField = new JTextField(8);
	JPanel spacePanel = new JPanel();
	JPanel panel = new JPanel();
	JPanel scorePanel = new JPanel();
	JRadioButton humanVsHumanMode = new JRadioButton("human vs. human");
	JRadioButton humanVsZombieMode = new JRadioButton("human vs. dumb computer");
	JRadioButton humanVsAIMode = new JRadioButton("human vs. intelligent computer");
	ButtonGroup buttonGroup = new ButtonGroup();

	private char[][] board; 
	private char currentPlayerMark;
	private int player1Score = 0;
	private int player2Score = 0;
	private Random generator = new Random(); 
	public TicTacToe() {
		spacePanel.setLayout(new GridLayout(3, 3));
		for(int i = 0; i < spaceButtons.length; i++){
			for(int j = 0; j < spaceButtons[i].length; j++){
				spaceButtons[i][j] = new JButton("");
				spacePanel.add(spaceButtons[i][j]);
			}
		}
		spacePanel.setPreferredSize(new Dimension(400, 400));
		panel.setLayout(new GridLayout(0,1));
		panel.add(clearButton);
		panel.add(humanVsHumanMode);
		panel.add(humanVsZombieMode);
		panel.add(humanVsAIMode);
		buttonGroup.add(humanVsHumanMode);
		buttonGroup.add(humanVsZombieMode);
		buttonGroup.add(humanVsAIMode);
		scorePanel.add(player1Label);
		scorePanel.add(player1Field);
		scorePanel.add(player2Label);
		scorePanel.add(player2Field);
		scorePanel.add(whoWonField);
		player1Field.setEditable(false);
		player2Field.setEditable(false);
		whoWonField.setEditable(false);
		window.getContentPane().add(panel,"North");
		window.getContentPane().add(scorePanel,"Center");
		window.getContentPane().add(spacePanel,"South");
		window.setSize(400, 600);
		window.setLocation(300,200);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		humanVsHumanMode.setSelected(true);
		humanVsZombieMode.setSelected(false);
		humanVsAIMode.setSelected(false);
		for (int i = 0; i < spaceButtons.length; i++) {
			for (int j = 0; j < spaceButtons[i].length; j++){
				spaceButtons[i][j].addActionListener(this);
			}
		}
		clearButton.addActionListener(this);
		humanVsZombieMode.addActionListener(this);
		humanVsHumanMode.addActionListener(this);
		humanVsAIMode.addActionListener(this);
		board = new char[3][3];
		currentPlayerMark = 'x';
		initializeBoard();
	}

	public void actionPerformed(ActionEvent ae) {

		if(ae.getSource()== clearButton){
			initializeBoard();
			printBoard();
			return;
		}


		if(humanVsHumanMode.isSelected() == true)
		{
			System.out.println("human vs. human");
			for (int i = 0; i < spaceButtons.length; i++) {
				for (int j = 0; j < spaceButtons[i].length; j++){
					if(ae.getSource()==spaceButtons[i][j] && spaceButtons[i][j].getText() == ""){
						humanTurn(i, j);
					}
				}
			}
		}

		if(humanVsZombieMode.isSelected() == true){
			System.out.println("human vs. zombie");
			if(currentPlayerMark == 'x'){
				for (int i = 0; i < spaceButtons.length; i++) {
					for (int j = 0; j < spaceButtons[i].length; j++){
						if(ae.getSource()==spaceButtons[i][j] && spaceButtons[i][j].getText() == ""){
							humanTurn(i, j);
							zombieComputer();
						}
					}
				}
			}
			else{
				zombieComputer();
			}
		}

		if(humanVsAIMode.isSelected() == true){
			System.out.println("human vs. AI");
			if(currentPlayerMark == 'x'){
				for (int i = 0; i < spaceButtons.length; i++) {
					for (int j = 0; j < spaceButtons[i].length; j++){
						if(ae.getSource()==spaceButtons[i][j] && spaceButtons[i][j].getText() == ""){
							humanTurn(i, j);
							AIComputer();
						}
					}
				}
			}
			else{
				AIComputer();
			}
		}
	}
	public void humanTurn(int i, int j){
		spaceButtons[i][j].setText(currentPlayerMark + "");
		board[i][j]=currentPlayerMark;
		printBoard();
		System.out.println(checkForWin());
		updateGame();
		changePlayer();
	}

	public void zombieComputer(){
		boolean done = false;
		while (done == false){
			int i = generator.nextInt(3);
			int j = generator.nextInt(3);
			if (spaceButtons[i][j].getText() == ""){
				spaceButtons[i][j].setText(currentPlayerMark + "");
				board[i][j]=currentPlayerMark;
				printBoard();
				System.out.println(checkForWin());
				updateGame();
				changePlayer();
				done = true;
			}
		}
	}

	public void AIComputer(){
		if(checkFor2() == true){
			printBoard();
			System.out.println(checkForWin());
			updateGame();
			changePlayer();
			System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
		}
		else{
			zombieComputer();
		}
	}

	// Set/Reset the board back to all empty values.
	public void initializeBoard() {

		// Loop through rows
		for (int i = 0; i < 3; i++) {

			// Loop through columns
			for (int j = 0; j < 3; j++) {
				board[i][j] = '-';
				spaceButtons[i][j].setText("");
			}
		}
	}


	// Print the current board (may be replaced by GUI implementation later)
	public void printBoard() {
		System.out.println("-------------");

		for (int i = 0; i < 3; i++) {
			System.out.print("| ");
			for (int j = 0; j < 3; j++) {
				System.out.print(board[i][j] + " | ");
			}
			System.out.println();
			System.out.println("-------------");
		}
	}

	public void updateGame() {
		if(checkForWin()){
			updateScore();
			initializeBoard();
		}
		if (isBoardFull()){
			initializeBoard();
			whoWonField.setText("Tie");
		}
	}


	public void updateScore() {
		if(currentPlayerMark == 'x'){
			player1Score += 1;
			player1Field.setText(player1Score + "");
			whoWonField.setText("player1 Won");
		}
		else {
			player2Score += 1;
			player2Field.setText(player2Score + "");
			whoWonField.setText("player2 Won");
		}
	}

	public boolean isBoardFull() {
		boolean isFull = true;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == '-') {
					isFull = false;
				}
			}
		}

		return isFull;
	}


	// Returns true if there is a win, false otherwise.
	// This calls our other win check functions to check the entire board.
	public boolean checkForWin() {
		return (checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin());
	}

	public boolean checkFor2() {
		if (checkRowsFor2()){
			return true;
		}
		else if (checkColumnsFor2()){
			return true;
		}
		else if (checkDiagonalsFor2()){
			return true;
		}
		else{
			return false;
		}
	}

	// Loop through rows and see if any are winners.
	private boolean checkRowsForWin() {
		for (int i = 0; i < 3; i++) {
			if (checkRowCol(board[i][0], board[i][1], board[i][2]) == true) {
				return true;
			}
		}
		return false;
	}

	private boolean checkRowsFor2() {
		for (int i = 0; i < 3; i++) {
			if (checkRowCol2(board[i][0], board[i][1], board[i][2]) == true) {
				for (int j = 0; j < 3; j++) {
					if (board[i][j] == '-'){
						board[i][j] = 'o';
						spaceButtons[i][j].setText(currentPlayerMark + "");
					}
				}
				return true;
			}
		}
		return false;
	}

	private boolean checkColumnsFor2() {
		for (int i = 0; i < 3; i++) {
			if (checkRowCol2(board[0][i], board[1][i], board[2][i]) == true) {
				for (int j = 0; j < 3; j++) {
					if (board[j][i] == '-'){
						board[j][i] = 'o';
						spaceButtons[j][i].setText(currentPlayerMark + "");
					}
				}
				return true;
			}
		}
		return false;
	}

	// Loop through columns and see if any are winners.
	private boolean checkColumnsForWin() {
		for (int i = 0; i < 3; i++) {
			if (checkRowCol(board[0][i], board[1][i], board[2][i]) == true) {
				return true;
			}
		}
		return false;
	}


	// Check the two diagonals to see if either is a win. Return true if either wins.
	private boolean checkDiagonalsForWin() {
		return ((checkRowCol(board[0][0], board[1][1], board[2][2]) == true) || (checkRowCol(board[0][2], board[1][1], board[2][0]) == true));
	}

	private boolean checkDiagonalsFor2() {
		if(checkRowCol2(board[0][0], board[1][1], board[2][2]) == true){
			if (board[0][0] == '-'){
				board[0][0] = 'o';
				spaceButtons[0][0].setText(currentPlayerMark + "");
			}
			if (board[1][1] == '-'){
				board[1][1] = 'o';
				spaceButtons[1][1].setText(currentPlayerMark + "");
			}
			if (board[2][2] == '-'){
				board[2][2] = 'o';
				spaceButtons[2][2].setText(currentPlayerMark + "");

			}
			return true;
		}
		else if(checkRowCol2(board[0][2], board[1][1], board[2][0]) == true){
			if (board[0][2] == '-'){
				board[0][2] = 'o';
				spaceButtons[0][2].setText(currentPlayerMark + "");
			}
			if (board[1][1] == '-'){
				board[1][1] = 'o';
				spaceButtons[1][1].setText(currentPlayerMark + "");
			}
			if (board[2][0] == '-'){
				board[2][0] = 'o';
				spaceButtons[2][0].setText(currentPlayerMark + "");
			}
			return true;

		}
		else{
			return false;
		}
	}

	// Check to see if all three values are the same (and not empty) indicating a win.
	private boolean checkRowCol(char c1, char c2, char c3) {
		return ((c1 != '-') && (c1 == c2) && (c2 == c3));
	}

	private boolean checkRowCol2(char c1, char c2, char c3) {
		boolean areThere2x = (((c1 == 'x') && (c2 == 'x') && (c3 == '-')) || ((c1 == 'x') && (c2 == '-') && (c3 == 'x')) || ((c1 == '-') && (c2 == 'x') && (c3 == 'x')));
		boolean areThere2o = (((c1 == 'o') && (c2 == 'o') && (c3 == '-')) || ((c1 == 'o') && (c2 == '-') && (c3 == 'o')) || ((c1 == '-') && (c2 == 'o') && (c3 == 'o')));
		return (areThere2x || areThere2o);
	}

	// Change player marks back and forth.
	public void changePlayer() {
		if (currentPlayerMark == 'x') {
			currentPlayerMark = 'o';
		}
		else {
			currentPlayerMark = 'x';
		}
	}


	// Places a mark at the cell specified by row and col with the mark of the current player.
	public boolean placeMark(int row, int col) {

		// Make sure that row and column are in bounds of the board.
		if ((row >= 0) && (row < 3)) {
			if ((col >= 0) && (col < 3)) {
				if (board[row][col] == '-') {
					board[row][col] = currentPlayerMark;
					return true;
				}
			}
		}

		return false;
	}

	public static void main(String[] args){
		// Create game and initialize it.
		// First player will be 'x'
		TicTacToe game = new TicTacToe();

		// Player 'x' places a mark in the top right corner row 0, column 2
		// These values are based on a zero index array, so you may need to simply take in a row 1 and subtract 1 from it if you want that.

		// Lets print the board
		game.printBoard();

		// Did we have a winner?
		if (game.checkForWin()) {
			System.out.println("We have a winner! Congrats!");
			System.exit(0);
		}
		else if (game.isBoardFull()) {
			System.out.println("Appears we have a draw!");
			System.exit(0);
		}
	}
}
