
//java API imports
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

//own imports
import key1p12.tetris.game.*;
import key1p12.tetris.bot.*;
import key1p12.tetris.gui.*;

//class initializing and controling the application
public class Tetris 
{
	public final static Dimension mMAIN_FRAME_DIM = new Dimension (400, 700);
	public final static String mCAPTION = new String ("Tetris!");
	public final static String iconImgPath = "res/image/appIcon.png";
	public final static String hsFilePath = "res/hscores.txt";
	
	public final static int mBOARD_COLS = 5, mBOARD_ROWS = 15, mHSLIST_ENTRIES = 10;
	
	public static void main (String args[])
	{
		Tetris app = null;
		try
		{
			app = new Tetris();
		}
		catch (IOException ioe)
		{
			System.err.println ("could not read file, application terminates now");
		}
		
		app.showMainMenu();
	}
	
	public class PauseButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			Tetris.this.pauseGame();
		}
	}
	
	public class ResumeButtonListener implements ActionListener
	{
		public void actionPerformed (ActionEvent e)
		{
			Tetris.this.resumeGame();
		}
	}
	
	public class LaunchGameSetupListener implements ActionListener
	{
		public void actionPerformed (ActionEvent e)
		{
			mGui.showDialog (TetrisGui.ScreenType.SETUP);
		}
	}
	
	public class LaunchHSListener implements ActionListener
	{
		public void actionPerformed (ActionEvent e)
		{
			mGui.showDialog (TetrisGui.ScreenType.HIGHSCORE);
		}
	}
	
	public class QuitGameListener implements ActionListener
	{
		public void actionPerformed (ActionEvent e)
		{
			mGui.setVisible(false);
			mGui.dispose();
		}
	}
	
	public class GameSetupListener extends TetrisGui.GameSetupListener
	{
		public void actionPerformed (ActionEvent e) 
		{
			//create player
/*			Player player = null;
			if (super.getPlayerType() == TetrisGui.PlayerType.HUMAN)
				player = new HumanPlayer(super.getPlayerName());*/
			/*else
				player = <bot constructors>*/
			//create & load high score
/*			HScore scores = null;
			try
			{
				File hsFile = new File (hsFilePath);
				if (!hsFile.exists())
					HScore.generateHighScoreFile(hsFile, mHSLIST_ENTRIES);
				scores = new HScore(hsFile, player.getName(), new ExponentialScore(2, 1, 1));
			}
			//if generating the high score file does not work
			catch (IOException e1) 
			{
				
			}
			mGui.setUpHighScorePanel(scoreList);
			//create board
			Board gameBoard = new Board (super.getInputWidth(), super.getInputHeight());
			//create pentominoes
			ArrayList <Pentomino> pentsToUse = Pentomino.createsPentList();
			//create game
			mGame = new Game(gameBoard, pentsToUse, player, scores);
			
			//mGui.showPanel (TetrisGui.ScreenType.GAME);
			//mGame.play();*/
			mGui.setVisible (false);
			mGui.dispose();
		}
	}
	
	
	
	//public section
	/**
	 * Sets up gui (except for high score panel)
	 * @throws IOException
	 */
	public Tetris () throws IOException
	{
		//construct gui
		BufferedImage iconImg = ImageIO.read(new File (iconImgPath));
		mGui = new TetrisGui (mMAIN_FRAME_DIM, mCAPTION, iconImg);
		mGui.setUpGamePanel (mGame, new PauseButtonListener());
		mGui.setUpMainMenuPanel(new LaunchGameSetupListener(), new LaunchHSListener(), new QuitGameListener());
		mGui.setUpPauseMenuPanel(new ResumeButtonListener(), new QuitGameListener());
		mGui.setUpGameSetupPanel(new GameSetupListener());
		
		//lauch main menu
		mGui.showPanel (TetrisGui.ScreenType.START);
		mGui.setVisible (true);
		
		/*control flow:
		*launch main menu
		*selection
		*	play game: launch setup menu => (via listener) play game
		*	display high scores: launch highscore frame, return: (via listener) launch main menu
		*	quit: exit game
		*/
		
	}
	
	//protected section
	
	
	//private section
	
	private void showMainMenu()
	{
		mGui.loadPanel (TetrisGui.ScreenType.START);
		if (!mGui.isVisible())
			mGui.setVisible (true);
	}
	
	private void playGame()
	{
		while (!mGame.isGameOver() && !mGame.isGamePaused())
		{
			mGame.play();
		}
	}
	
	private void pauseGame()
	{
		mGame.pause();
		//show pause screen
	}
	
	private void resumeGame()
	{
		playGame();
	}
	
	private Game mGame;
	//private HScore mHighestScores;
	private TetrisGui mGui;
}
