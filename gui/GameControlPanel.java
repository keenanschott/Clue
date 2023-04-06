import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
    /**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		
	}
	
    private void createLayout(JFrame currentFrame) {
        currentFrame.setLayout(new GridLayout(0, 1));
        JPanel gameControlPanel = new JPanel();
        gameControlPanel.setLayout(new GridLayout(2, 0));
        JPanel topPanel = new JPanel();

        //
        JPanel topOne = new JPanel();
        topOne.setLayout(new GridLayout(2, 0));
        JLabel topOneLabel = new JLabel("Whose turn?");
        JTextField topOneText = new JTextField("Captain Haddock");
        topOneText.setBackground(Color.ORANGE);
        topOneLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topOne.add(topOneLabel, BorderLayout.NORTH);
        topOne.add(topOneText, BorderLayout.NORTH);
        //
        JPanel topTwo = new JPanel();
        JLabel topTwoLabel = new JLabel("Roll:");
        JTextField topTwoText = new JTextField("5");
        topTwoText.setEditable(false);
        topTwo.add(topTwoLabel);
        topTwo.add(topTwoText);
        //
        JButton topThree = new JButton();
        topThree.setText("Make Accustation");
        //
        JButton topFour = new JButton();
        topFour.setText("NEXT!");
        //

        topPanel.setLayout(new GridLayout(1, 4));
        topPanel.add(topOne, BorderLayout.WEST);
        topPanel.add(topTwo, BorderLayout.AFTER_LAST_LINE);
        topPanel.add(topThree, BorderLayout.AFTER_LAST_LINE);
        topPanel.add(topFour, BorderLayout.EAST);
        


        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(0, 2));

        JPanel bottomOne = new JPanel();
        bottomOne.setLayout(new GridLayout(1, 0));
        JTextField bottomOneText = new JTextField("I have no guess!");
        bottomOneText.setEditable(false);

        Border tempBorder = new LineBorder(Color.BLACK);
        TitledBorder bottomOneBorder = new TitledBorder(tempBorder, "Guess");
        bottomOneBorder.setTitleJustification(TitledBorder.LEFT);
        bottomOneBorder.setTitlePosition(TitledBorder.TOP);

        bottomOne.setBorder(bottomOneBorder);
        bottomOne.add(bottomOneText, BorderLayout.CENTER);

        JPanel bottomTwo = new JPanel();
        bottomTwo.setLayout(new GridLayout(1, 0));
        JTextField bottomTwoText = new JTextField("So you have nothing?");
        bottomTwoText.setEditable(false);
        tempBorder = new LineBorder(Color.BLACK);
        TitledBorder bottomTwoBorder = new TitledBorder(tempBorder, "Guess Result");
        bottomTwoBorder.setTitleJustification(TitledBorder.LEFT);
        bottomTwoBorder.setTitlePosition(TitledBorder.TOP);
        bottomTwo.setBorder(bottomTwoBorder);
        bottomTwo.add(bottomTwoText, BorderLayout.CENTER);




        bottomPanel.add(bottomOne);
        bottomPanel.add(bottomTwo);



        gameControlPanel.add(topPanel, BorderLayout.NORTH);
        gameControlPanel.add(bottomPanel, BorderLayout.SOUTH);
        currentFrame.add(gameControlPanel, BorderLayout.CENTER);

    }

	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
        panel.createLayout(frame);
		frame.setVisible(true); // make it visible
	}
}
