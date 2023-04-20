package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import gui.ClueGame;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Board
 * The game board initialization; reads in the game setup and layout, populates
 * the game board, creates adjacency lists, etc. This class also implements the
 * movement algorithm.
 * DATE: 4/18/2023
 * 
 * @author Keenan Schott
 * @author Finn Burns
 */
public class Board extends JPanel {
	private BoardCell[][] grid; // grid of cells
	private int numRows;
	private int numColumns;
	private String layoutConfigFile; // layout file
	private String setupConfigFile; // setup file
	private Map<Character, Room> roomMap; // maps characters to rooms
	private static Board theInstance = new Board(); // Singleton Pattern
	private Set<BoardCell> targets; // all valid cells to move to
	private Set<BoardCell> visited; // visited cells
	private Solution theAnswer; // the solution
	private ArrayList<Player> players; // all players
	private ArrayList<Card> deck; // all cards
	// game flow logic variables
	private int playerTurn = 0; // current player turn
	private Player currentPlayer; // the current player
	private Random random = new Random();
	private int currentRoll = 0; // the roll
	private boolean finished = false; // turn conclusion flag
	private static int offsetX = 0;
	private static int offsetY = 0;
	private static int cellWidth;
	private static int cellHeight;

	/**
	 * Board()
	 * Constructor (see Singleton Pattern).
	 */
	private Board() {
		super();
		addMouseListener(new CellListener());
	}

	/**
	 * getInstance()
	 * Constructor supplement (see Singleton Pattern).
	 */
	public static Board getInstance() {
		return theInstance;
	}

	/**
	 * initialize()
	 * Set up the game board with new board cells. Then, create an adjacency list
	 * for every cell.
	 */
	public void initialize() {
		try {
			// try to load files
			loadSetupConfig(); 
			loadLayoutConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		// create adjacency lists for every cell
		createAdj(); 
	}

	/**
	 * setConfigFiles()
	 * Connect instance variables to requested input files.
	 * 
	 * @param layoutCSV The requested layout CSV file.
	 * @param setupTXT  The requested setup text file.
	 */
	public void setConfigFiles(String layoutCSV, String setupTXT) {
		layoutConfigFile = "data/" + layoutCSV; // our files are stored in a data folder
		setupConfigFile = "data/" + setupTXT;
	}

	/**
	 * loadSetupConfig()
	 * Load the setup file and check for validity.
	 * 
	 * @throws BadConfigFormatException
	 */
	public void loadSetupConfig() throws BadConfigFormatException {
		roomMap = new HashMap<Character, Room>();
		players = new ArrayList<Player>(); 
		deck = new ArrayList<Card>(); 
		File file = new File(setupConfigFile); 
		String currentLine;
		// array of words in current line
		String[] lineArray;
		try {
			Scanner scan = new Scanner(file); 
			while (scan.hasNext()) {
				// get entire line
				currentLine = scan.nextLine(); 
				lineArray = currentLine.split(", "); 
				// valid line for a room
				if (lineArray.length == 3 && lineArray[0].equals("Room")) {
					// do not insert center or label yet
					Room newRoom = new Room(lineArray[1], null, null);
					// put into map
					roomMap.put(lineArray[2].charAt(0), newRoom); 
					// add to deck as a room
					deck.add(new Card(lineArray[1], CardType.ROOM)); 
				// valid line for a space
				} else if (lineArray.length == 3 && lineArray[0].equals("Space")) { 
					// do not insert center or label yet
					Room newRoom = new Room(lineArray[1], null, null); 
					// put into map as a room
					roomMap.put(lineArray[2].charAt(0), newRoom); 
				// valid line for a player
				} else if (lineArray.length == 5 && lineArray[0].equals("Player")) { 
					if (lineArray[3].equals("Human")) { // human
						players.add(new HumanPlayer(lineArray[1], lineArray[2],
						// new human player
							Integer.parseInt(lineArray[4].split("-")[0]),
							Integer.parseInt(lineArray[4].split("-")[1])));
					} else { // computer
						// new computer player
						players.add(new ComputerPlayer(lineArray[1], lineArray[2],
							Integer.parseInt(lineArray[4].split("-")[0]),
							Integer.parseInt(lineArray[4].split("-")[1]))); 
					}
					// add to deck as a player
					deck.add(new Card(lineArray[1], CardType.PERSON));
					// valid line for a weapon
				} else if (lineArray.length == 2 && lineArray[0].equals("Weapon")) { 
					// add to deck as a weapon
					deck.add(new Card(lineArray[1], CardType.WEAPON));
				} else {
					if (!lineArray[0].substring(0, 2).equals("//")) { // if not a comment, close before exception
						scan.close();
						throw new BadConfigFormatException();
					}
				}
			}
			scan.close(); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * loadLayoutConfig()
	 * Load the layout file and check for validity.
	 * 
	 * @throws BadConfigFormatException
	 */
	public void loadLayoutConfig() throws BadConfigFormatException {
		ArrayList<String[]> allLinesLayout = new ArrayList<String[]>(); 
		File file = new File(layoutConfigFile); 
		String currentLine; 
		String[] lineArray; // array of words in current line
		try {
			Scanner sc = new Scanner(file); // open
			while (sc.hasNext()) {
				currentLine = sc.nextLine();
				lineArray = currentLine.split(",");
				allLinesLayout.add(lineArray);
			}
			// number of rows is equal to size of allLinesLayout
			numRows = allLinesLayout.size(); 
			// number of columns is equal to size of any entry in allLinesLayout
			numColumns = allLinesLayout.get(0).length;
			sc.close(); // close
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// fill the grid given allLinesLayout
		fillGrid(allLinesLayout);
	}

	/**
	 * fillGrid()
	 * Fill the grid given a two-dimensional array of Strings.
	 * 
	 * @param layout Contains all cells as strings.
	 * @throws BadConfigFormatException
	 */
	private void fillGrid(ArrayList<String[]> layout) throws BadConfigFormatException {
		grid = new BoardCell[numRows][numColumns];
		int rowCounter = 0; 
		int colCounter = 0; 
		for (String[] row : layout) {
			for (String cell : row) { // for a given cell String
				if (cell.length() < 1 || cell.length() > 2) {
					// invalid length of String; invalid cell
					throw new BadConfigFormatException("Layout file contains configuration errors!"); 
				} else {
					BoardCell newCell = new BoardCell(rowCounter, colCounter, cell.charAt(0)); 
					// a valid cell with regards to our mapv
					if (roomMap.containsKey(cell.charAt(0))) {
						// set cell booleans depending on the initial; see fillGridCellHelper
						fillGridCellHelper(newCell); 
						// if a cell with special behavior see fillGridRoomHelper
						if (cell.length() == 2) { 
							fillGridRoomHelper(cell, newCell); 
						}
					} else {
						// not in setup file
						throw new BadConfigFormatException("Layout file specifies a room that is not in the legend!");
					}
					// fill grid with newCell
					grid[rowCounter][colCounter] = newCell; 
					colCounter++;
				}
			}
			rowCounter++; 
			if (colCounter != numColumns) {
				// invalid row; did not have a matching column count
				throw new BadConfigFormatException("Layout file that does not have the same number of columns for each row!"); 
			}
			// reset column count to start from left for the next row
			colCounter = 0; 
		}
	}

	/**
	 * fillGridCellHelper()
	 * Set cell booleans depending on the initial.
	 * 
	 * @param cell The cell under examination.
	 */
	private void fillGridCellHelper(BoardCell cell) {
		if (cell.getInitial() != 'W' && cell.getInitial() != 'X') {
			// if not W or X, the cell is a room
			cell.setIsRoom(true); 
		} else if (cell.getInitial() == 'X') {
			// if X, set the cell to be occupied
			cell.setOccupied(true); 
		}
	}

	/**
	 * fillGridRoomHelper()
	 * Change aspects of the Room and BoardCell depending on the special characters
	 * observed.
	 * 
	 * @param cellString The string that represents the given cell.
	 * @param cell       The corresponding BoardCell.
	 */
	private void fillGridRoomHelper(String cellString, BoardCell cell) {
		 // temporary Room object to change the room within the map
		Room changeRoom = roomMap.get(cellString.charAt(0)); 
		if (cellString.charAt(1) == '#') { // label cell
			cell.setLabel(true);
			changeRoom.setLabelCell(cell);
		} else if (cellString.charAt(1) == '*') { // center cell
			cell.setCenter(true);
			changeRoom.setCenterCell(cell);
		} else if (cellString.charAt(0) == 'W') { // doorway cell
			cell.setDoorDirection(cellString.charAt(1));
		} else {
			// only remaining special cell option
			cell.setSecretPassage(cellString.charAt(1)); 
		}
	}

	/**
	 * paintComponent()
	 * Overidden method of paintComponent to draw entire board using Swing graphics
	 * library. Drawing of the board cells and rooms are done by BoardCell and Room,
	 * respectively.
	 * 
	 * @param g The Graphics object.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// initialize coordinates for drawing of board cells
		int xCoord = 0;
		int yCoord = 0;
		// dynamically resize window according to the size of the JPanel
		cellWidth = getWidth() / numColumns;
		cellHeight = getHeight() / numRows;
		if (cellWidth > cellHeight) {
			cellWidth = cellHeight;
			offsetX = (getWidth() - cellWidth * numColumns) / 2; 
		}  else {
			cellHeight = cellWidth;
			offsetY = (getHeight() - cellHeight * numRows) / 2; 
		}
		// set coordinates to be iteratedimage.png
		xCoord = -cellWidth; 
		yCoord = -cellHeight;
		g.setColor(Color.BLACK); // base color
		// draw rooms first, so borders of walkways don't get overwritten
		for (int i = 0; i < numRows; i++) { // iterate through rows of board grid
			yCoord += cellHeight; 
			// iterate then through columns of board grid
			for (int j = 0; j < numColumns; j++) { 
				xCoord += cellWidth; 
				if (grid[i][j].getIsRoom()) {
					BoardCell.setDimensions(xCoord + offsetX, yCoord + offsetY, cellWidth, cellHeight);
					grid[i][j].paintComponent(g); // board cells draw themselves
				}
			}
			xCoord = -cellWidth; // reset xCoord
		}
		// reset coordinates to be iterated
		xCoord = -cellWidth; 
		yCoord = -cellHeight;
		// same loop structure and draw methodology for non-room cells
		for (int i = 0; i < numRows; i++) {
			yCoord += cellHeight;
			for (int j = 0; j < numColumns; j++) {
				xCoord += cellWidth;
				if (!grid[i][j].getIsRoom()) {
					BoardCell.setDimensions(xCoord + offsetX, yCoord + offsetY, cellWidth, cellHeight);
					grid[i][j].paintComponent(g);
				}
			}
			xCoord = -cellWidth;
		}
		xCoord = -cellWidth; // reset coordinates to be iterated
		yCoord = -cellHeight;
		// handle names next, as we want to overlay these on the board
		for (int i = 0; i < numRows; i++) {
			yCoord += cellHeight;
			for (int j = 0; j < numColumns; j++) {
				xCoord += cellWidth;
				// if label cell, draw
				if (grid[i][j].isLabel()) { 
					// we'll get our label for grid[i][j]
					grid[i][j].drawLabel(g, xCoord + offsetX, yCoord + offsetY); 
				} else if (grid[i][j].getDoorDirection() != DoorDirection.NONE) { // if doorway
					grid[i][j].drawDoor(g, xCoord + offsetX, yCoord + offsetY, cellWidth, cellHeight);
				}
			}
			xCoord = -cellWidth;
		}
		// lastly, draw players using draw method for a player
		// offset is calculated using width of cell and initial starting row/col
		for (Player player : players) {
			player.draw(g, player.getColumn() * cellWidth + offsetX, player.getRow() * cellHeight + offsetY, cellWidth, cellHeight, players);
		}
	}

	/**
	 * createAdj()
	 * Create an adjacency list for every cell within the game board.
	 */
	private void createAdj() {
		for (int i = 0; i < numRows; i++) { // create an adjacency list for every cell
			for (int j = 0; j < numColumns; j++) {
				// checking if its a secret passage (\u0000 is null val)
				if (grid[i][j].getSecretPassage() != '\0') {
					// connect other room's center cell by adding it to the current room's center
					// cell's adjacency list
					roomMap.get(grid[i][j].getInitial()).getCenterCell()
							.addAdjacency(roomMap.get(grid[i][j].getSecretPassage()).getCenterCell());
				}
				// if the cell is not a room cell in any capacity and not an unused space -
				// create a generic adjacency list
				else if (!grid[i][j].getIsRoom() && grid[i][j].getInitial() != 'X') {
					if (grid[i][j].getDoorDirection() != DoorDirection.NONE) {
						// special rules apply to center cells in relation to doorways; see doorwayAdjacencies
						doorwayAdjacencies(i, j); 
					}
					// generic adjacency checking; see genericAdjacencies
					genericAdjacencies(i, j); 
				}
			}
		}
	}

	/**
	 * genericAdjacencies()
	 * The generic adjacency list creation conditions.
	 * 
	 * @param row The row to access on the game board.
	 * @param col The column to access on the game board.
	 */
	private void genericAdjacencies(int row, int col) {
		// if not at the top of the board AND the cell above is not occupied AND the
		// cell above is a walkway, then add to the current cell's adjacency list
		if (row != 0 && !grid[row - 1][col].getIsOccupied() && grid[row - 1][col].getInitial() == 'W') {
			grid[row][col].addAdjacency(grid[row - 1][col]);
		}
		// if not at the bottom of the board AND the cell below is not occupied AND the
		// cell below is a walkway, then add to the current cell's adjacency list
		if (row != numRows - 1 && !grid[row + 1][col].getIsOccupied() && grid[row + 1][col].getInitial() == 'W') {
			grid[row][col].addAdjacency(grid[row + 1][col]);
		}
		// if not at the left of the board AND the cell to the left is not occupied AND
		// the cell to the left is a walkway, then add to the current cell's adjacency
		// list
		if (col != 0 && !grid[row][col - 1].getIsOccupied() && grid[row][col - 1].getInitial() == 'W') {
			grid[row][col].addAdjacency(grid[row][col - 1]);
		}
		// if not at the right of the board AND the cell to the right is not occupied
		// AND the cell to the right is a walkway, then add to the current cell's
		// adjacency list
		if (col != numColumns - 1 && !grid[row][col + 1].getIsOccupied() && grid[row][col + 1].getInitial() == 'W') {
			grid[row][col].addAdjacency(grid[row][col + 1]);
		}
	}

	/**
	 * doorwayAdjacencies()
	 * Add the doorway to the room's center cell's adj. list and the room's center
	 * cell to the doorway's adj. list.
	 * 
	 * @param row The row to access on the game board.
	 * @param col The column to access on the game board.
	 */
	private void doorwayAdjacencies(int row, int col) {
		if (grid[row][col].getDoorDirection() == DoorDirection.UP) {
			// add the room's center cell to the doorway's adj. list.
			grid[row][col].addAdjacency(roomMap.get(grid[row - 1][col].getInitial()).getCenterCell()); 
			// add the doorway to the room's center cell's adj. list
			roomMap.get(grid[row - 1][col].getInitial()).getCenterCell().addAdjacency(grid[row][col]); 
		} else if (grid[row][col].getDoorDirection() == DoorDirection.DOWN) {
			// add the room's center cell to the doorway's adj. list.
			grid[row][col].addAdjacency(roomMap.get(grid[row + 1][col].getInitial()).getCenterCell()); 
			// add the doorway to the room's center cell's adj. list
			roomMap.get(grid[row + 1][col].getInitial()).getCenterCell().addAdjacency(grid[row][col]); 
		} else if (grid[row][col].getDoorDirection() == DoorDirection.LEFT) {
			 // add the room's center cell to the doorway's adj. list.
			grid[row][col].addAdjacency(roomMap.get(grid[row][col - 1].getInitial()).getCenterCell());
			// add the doorway to the room's center cell's adj. list
			roomMap.get(grid[row][col - 1].getInitial()).getCenterCell().addAdjacency(grid[row][col]); 
		} else if (grid[row][col].getDoorDirection() == DoorDirection.RIGHT) {
			// add the room's center cell to the doorway's adj. list.
			grid[row][col].addAdjacency(roomMap.get(grid[row][col + 1].getInitial()).getCenterCell());
			// add the doorway to the room's center cell's adj. list
			roomMap.get(grid[row][col + 1].getInitial()).getCenterCell().addAdjacency(grid[row][col]); 
		}
	}

	/**
	 * calcTargets()
	 * Calculate all valid targets to move to.
	 * 
	 * @param startCell  The starting cell to examine as it pertains to the
	 *                   pathLength.
	 * @param pathLength The roll/how many moves we have.
	 */
	public void calcTargets(BoardCell startCell, int pathLength) {
		// allocate space for our sets
		visited = new HashSet<BoardCell>(); 
		targets = new HashSet<BoardCell>();
		// can never move back to the start cell
		visited.add(startCell); 
		findAllTargets(startCell, pathLength); 
	}

	/**
	 * findAllTargets()
	 * Find all valid targets to move to.
	 * 
	 * @param startCell  The starting cell to examine as it pertains to the
	 *                   pathLength.
	 * @param pathLength The roll/how many moves we have.
	 */
	private void findAllTargets(BoardCell startCell, int pathLength) {
		// all adjacent cells to the start cell
		for (BoardCell adjCell : startCell.getAdjList()) { 
			/*
			 * What we need to check (all conditions below need to be met):
			 * - not in visited AND
			 * - is not occupied and is a walkway OR
			 * - is a room center
			 */
			if ((!visited.contains(adjCell) && !adjCell.getIsOccupied() && adjCell.getInitial() == 'W')
					|| (!visited.contains(adjCell) && adjCell.isRoomCenter())) { // see above
				visited.add(adjCell);
				// if no more moves or at a room center
				if (pathLength == 1 || adjCell.isRoomCenter()) { 
					targets.add(adjCell); 
					if (currentPlayer instanceof HumanPlayer) {
						// set drawing flag to true
						adjCell.setTarget(true); 
					}
				} else {
					// call recursively with one less move
					findAllTargets(adjCell, pathLength - 1); 
				}
				// remove from visited
				visited.remove(adjCell);
			}
		}
	}

	/**
	 * deal()
	 * Deal all of the cards.
	 */
	public void deal() {
		theAnswer = new Solution(); // allocate space for the solution
		ArrayList<Card> deckCopy = new ArrayList<Card>();
		deckCopy.addAll(deck); // copy the deck to remove from
		// iterate through players
		int player_counter = 0;
		// temporary card to look at
		Card temp;
		// RNG
		Random rand = new Random();
		int int_random;
		// solution booleans
		boolean solutionPlayer = false, solutionWeapon = false, solutionRoom = false;
		// look through deck
		while (deckCopy.size() != 0) {
			// get a random card from the copy and remove it
			int_random = rand.nextInt(deckCopy.size());
			temp = deckCopy.get(int_random);
			deckCopy.remove(temp);
			if (solutionPlayer == false && temp.getType() == CardType.PERSON) {
				solutionPlayer = true;
				 // set solution person to first random person
				theAnswer.setPerson(temp);
			} else if (solutionWeapon == false && temp.getType() == CardType.WEAPON) {
				solutionWeapon = true;
				// set solution weapon to first random weapon
				theAnswer.setWeapon(temp); 
			} else if (solutionRoom == false && temp.getType() == CardType.ROOM) {
				solutionRoom = true;
				// set solution room to first random room
				theAnswer.setRoom(temp); 
			} else {
				// iterate through the players and add the current card
				players.get(player_counter % players.size()).updateHand(temp); 
				players.get(player_counter % players.size()).updateSeen(temp);
				player_counter++; // next player
			}
		}
	}

	/**
	 * checkAccusation()
	 * Check a player's accusation.
	 * 
	 * @return A boolean corresponding to the accuracy of the accusation.
	 */
	public boolean checkAccusation(Solution accusation) {
		return theAnswer.equals(accusation);
	}

	/**
	 * handleSuggestion()
	 * Handle a player's suggestion.
	 * 
	 * @param origin     The player the suggestion originates from.
	 * @param suggestion The suggestion itself.
	 * @return The card that disproves the suggestion.
	 */
	public Card handleSuggestion(Player origin, Solution suggestion) {
		Card evidence;
		for (Player aPlayer : players) { 
			// the player of origin cannot be used here
			if (aPlayer != origin) { 
				evidence = aPlayer.disproveSuggestion(suggestion);
				if (evidence != null) {
					// return the card if a player can disprove it
					return evidence; 
				}
			}
		}
		return null;
	}

	/**
	 * handleSuggestionTestReturn()
	 * handleSuggestion()'s helper function to test the player that returns the
	 * card.
	 * 
	 * @param origin     The player the suggestion originates from.
	 * @param suggestion The suggestion itself.
	 * @return The player that disproved the suggestion.
	 */
	public Player handleSuggestionTestReturn(Player origin, Solution suggestion) {
		Card evidence;
		for (Player aPlayer : players) { 
			if (aPlayer != origin) { 
				evidence = aPlayer.disproveSuggestion(suggestion);
				if (evidence != null) {
					// return the player that disproved the suggestion
					return aPlayer;
				}
			}
		}
		return null;
	}

	/**
	 * initializeGame()
	 * Initialize game flow logic and listeners to enable that.
	 * 
	 * @param gameFrame The outer frame we need to reference.
	 */
	public void initializeGame(ClueGame gameFrame) {
		currentPlayer = players.get(0); // initialize current player to the human player
		gameFrame.getRightPanel().addHand(currentPlayer); // add hand to cards panel
		currentRoll = randomRoll();
		gameFrame.getBottomPanel().setTurn(currentPlayer, currentRoll);
		gameFrame.getBottomPanel().getTopFour().addActionListener(new ActionListener() { // add listener to "NEXT!" button
			@Override
			public void actionPerformed(ActionEvent e) {
				// if the current turn has concluded
				if (finished == true) { 
					playerTurn++;
					currentPlayer = players.get(playerTurn % players.size());
					currentRoll = randomRoll();
					gameFrame.getBottomPanel().setTurn(currentPlayer, currentRoll);
					runTurn(); // run the next turn
				} else { // prompt player to end their turn
					JLabel label = new JLabel("<html><center>You need to complete your turn!");
					label.setHorizontalAlignment(SwingConstants.CENTER);
					JOptionPane.showMessageDialog(theInstance, label, "Warning!", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		runTurn(); // run first turn manually
	}

	/**
	 * runTurn()
	 * Run a player's turn.
	 * 
	 * @param gameFrame The outer frame we need to reference.
	 */
	private void runTurn() {
		getCell(currentPlayer.getRow(), currentPlayer.getColumn()).setOccupied(false); // deoccupy current cell inhabited
		finished = false; // turn not over
		calcTargets(getCell(currentPlayer.getRow(), currentPlayer.getColumn()), currentRoll);
		if (targets.isEmpty()) { 
			finished = true; // skip turn
			// tell player their turn is being skipped
			if (currentPlayer instanceof HumanPlayer) { 
				JLabel label = new JLabel("<html><center>There are no valid tiles to move to; skipping turn!");
				label.setHorizontalAlignment(SwingConstants.CENTER);
				JOptionPane.showMessageDialog(Board.getInstance(), label, "Warning!", JOptionPane.WARNING_MESSAGE);
			}
		} else {
			repaint();
			if (currentPlayer instanceof ComputerPlayer) {
				move(((ComputerPlayer)currentPlayer).selectTarget(), currentPlayer); // automate computer movement
			}
		}
	}

	/**
	 * move()
	 * Simulate the player's movement; both computer and player.
	 * 
	 * @param targetCell The cell to move to.
	 */
	public void move(BoardCell targetCell, Player currentPlayer) {
		currentPlayer.setLocation(targetCell.getRow(), targetCell.getCol()); // set new location
		if (!getCell(currentPlayer.getRow(), currentPlayer.getColumn()).isRoomCenter()) {
			// if not a room center, set new location to be occupied
			getCell(currentPlayer.getRow(), currentPlayer.getColumn()).setOccupied(true); 
		}
		finished = true; // turn over
		if (currentPlayer instanceof HumanPlayer) removePaint();
		repaint();
	}


	/**
	 * removePaint()
	 * Remove the drawing flag off of all of the targets.
	 */
	public void removePaint() {
		for (BoardCell currentCell : targets) {
			currentCell.setTarget(false); // remove flag
		}
	}

	/**
	 * randomRoll()
	 * Return a random dice roll.
	 * 
	 * @return The random roll.
	 */
	public int randomRoll() {
		return random.nextInt(1, 7); // dice roll
	}

	// all getters and setters
	public Room getRoom(char roomType) {
		return roomMap.get(roomType); // return a room by character input
	}

	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial()); // return a room by cell input
	}

	public Room getRoom(Card roomCard) {
		return roomMap.get(roomCard.getName().charAt(0)); // return a room by card input
	}

	public int getNumRows() {
		return numRows; // return the number of rows
	}

	public int getNumColumns() {
		return numColumns; // return the number of columns
	}

	public Set<BoardCell> getAdjList(int row, int col) {
		return grid[row][col].getAdjList(); // call BoardCell's getAdjList()
	}

	public Set<BoardCell> getTargets() {
		return targets; // return target cells
	}

	public BoardCell getCell(int row, int col) {
		return grid[row][col]; // returns the cell at the given parameters
	}

	public Solution getTheAnswer() {
		return theAnswer; // return the solution
	}

	public ArrayList<Player> getPlayersList() {
		return players; // return the players list
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public Player getPlayer(String name) {
		for (Player player : players) {
			if (player.getName().equals(name)) {
				return player; // return a player from the players list if the name matches (used exclusively
								// in safe testing)
			}
		}
		return null;
	}

	public ArrayList<Card> getDeck() {
		return deck; // return the deck
	}

	/**
     * Cell Listener
     * This class implements the listener for the mouse when a BoardCell is pressed.
     * DATE: 4/18/2023
     * 
     * @author Keenan Schott
     * @author Finn Burns
     */
    private class CellListener implements MouseListener {
        /**
         * mouseClicked()
         * When the mouse is clicked, a BoardCell should behave according to this logic.
         * 
         * @param e The mouse event.
         */
        @Override
        public void mouseClicked(MouseEvent e) {
			int col = (e.getX() - offsetX) / cellWidth;  // calculate row and column with offset
			int row = (e.getY() - offsetY) / cellHeight;
			// if click is in range
			if (e.getX() > offsetX && e.getY() > offsetY && e.getX() < offsetX + (cellWidth * numColumns) && e.getY() < offsetY + (cellHeight * numRows)) {
				BoardCell pressedCell = getCell(row, col); // the pressed cell 
				if (pressedCell.isTarget() && theInstance.getCurrentPlayer() instanceof HumanPlayer) { // if a target cell
					theInstance.move(theInstance.getCell(row, col), theInstance.getCurrentPlayer()); // move the human player
					repaint();
					return;
				} else if (pressedCell.getIsRoom() && theInstance.getRoom(pressedCell.getInitial()).getCenterCell().isTarget() && theInstance.getCurrentPlayer() instanceof HumanPlayer) {
					// if a room cell with a center cell as a target
					theInstance.move(theInstance.getRoom(pressedCell.getInitial()).getCenterCell(), theInstance.getCurrentPlayer()); // move the human player
					repaint();
					return;
				} 
			} 
			JLabel label = new JLabel("<html><center>Invalid tile!"); // option pane to warn the player
			label.setHorizontalAlignment(SwingConstants.CENTER);
			JOptionPane.showMessageDialog(theInstance, label, "Warning!", JOptionPane.WARNING_MESSAGE);
        }

        @Override
        public void mousePressed(MouseEvent e) {
			// not needed 
        }

        @Override
        public void mouseReleased(MouseEvent e) {
			// not needed 
        }

        @Override
        public void mouseEntered(MouseEvent e) {
			// not needed 
        }

        @Override
        public void mouseExited(MouseEvent e) {
			// not needed 
        }
    }

	/**
	 * main()
	 * Main to test the board display.
	 * 
	 * @param args The list of arguments.
	 */
	public static void main(String[] args) {
		// Board is singleton, get the only instance
		Board board = Board.getInstance();
		// set the file names
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// initialize
		board.initialize();
		board.deal();
		// create test frame
		JFrame frame = new JFrame();
		frame.setSize(1500, 1000); // arbitrary frame size
		frame.setContentPane(board);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true);
	}
}