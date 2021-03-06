import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Hailey
 * @version 1.0
 * @created 13-Feb-2019 4:08:00 PM
 * This creates the start menu, options menu, and game menu for the game
 */
public class MainMenu extends Application {

	/**
	 *  creates an object of score
	 */
	Score scoreScore = new Score();

	/**
	 *  player1 and player 2 create rectangles for the players paddles
	 *  lowerBorder and lowerColor creates rectangles so we can change the colors of the game
	 */
	Rectangle player1,player2,lowerBorder,lowerColor;
	
	/**
	 *  sets the normal paddle height
	 */
	int paddleHeight = 80;
	
	/**
	 *  creates the ball
	 */
	Circle ball;
	
	/**
	 *  creates the text for player 1's score
	 */
	Text score1;
	
	/**
	 *  creates the text for player 2's score
	 */
	Text score2;
	
	/**
	 *  sets the width and height for the screen and is used to set the layout for the ball and paddles
	 */
	int WIDTH = 1000, HEIGHT = 400;
	
	/**
	 *  sets the starting play style as 0 until the player chooses their own style
	 */
	int playStyle = 0;
	
	/**
	 *  creates the variables and sets the initial speeds
	 */
	int speedX = 0, speedY = 0, dx = speedX, dy = speedY, scorePlayer1 = 0, scorePlayer2 = 0, winner = 0;
	
	/**
	 *  creates gameover which is false until one player wins
	 */
	boolean gameOver = false;
	
	/**
	 *  creates and sets initial difficulty value
	 */
	int playDifficulty = 0;
	
	/**\
	 *  creates the int for current time in the game
	 */
	int currentGameTime;
	
	/**
	 *  creates int for the end game time
	 */
	int finalGameTime;
	
	/**
	 *  creates int to tell us which player won
	 */
	int winnerPlayer;
	
	/**
	 *  creates int to store losing player's score
	 */
	int loserScore;
	//

	/**
	 * create the stage for the main menu
	 */
	Stage stage = new Stage();

	/**
	 * creates the stage for the game over window
	 */
	Stage gameOverStage = new Stage();
	
	/**
	 * creates the pane for the game over pane
	 */
	Pane gameOverPane = new Pane();
	
	/**
	 *  creates the scene for the game over window and sets its parameters
	 */
	Scene gameOverScene = new Scene(gameOverPane, 1000, 600);

	/**
	 * create pane for the main menu
	 */
	Pane mainPane = new Pane();
	
	/**
	 * creates the scene for the main menu and sets its parameters
	 */
	Scene mainScene = new Scene(mainPane, 1000, 600);

	/**
	 * create Text for the game name
	 */
	private Text text = new Text();	
	/**
	 * create button to start game
	 */
	private Button startButton = new Button("Start Game");
	/**
	 * create button for the help menu
	 */
	private Button helpButton = new Button("Instructions");

	/**
	 * creates and contains the features and functionality of the main menu, options menu, and help menu
	 */
	public MainMenu() {
		
		mainPane.setStyle("-fx-background: #16A500");
		
		/**
		 * set details of newGameButton
		 */
		startButton.setLayoutX(400);
		startButton.setLayoutY(200);
		startButton.setFont(new Font(24));

		/**
		 * set details of helpButton
		 */
		helpButton.setLayoutX(400);
		helpButton.setLayoutY(300);
		helpButton.setFont( new Font(24));

		/** 
		 * set main menu title text
		 */
		text.setText("Deep Learning Pong");
		text.setLayoutX(125);
		text.setLayoutY(100);
		text.setFill(Color.BLACK);
		text.setFont(Font.font ("Gill Sans Ultra Bold", 60) );

		/**
		 * add newGameButton, helpButton, scoreButton, and text to the pane
		 */
		mainPane.getChildren().addAll(startButton, helpButton, text);

		/**
		 * create and format title text for the options menu
		 */
		Text title = new Text("Options");

		title.setLayoutX(325);
		title.setLayoutY(150);
		title.setFill(Color.BLACK);
		title.setFont(Font.font ("Gill Sans Ultra Bold", 60) );
		/**
		 * create back button
		 */
		Button backButton = new Button();

		/**
		 * format back button
		 */
		backButton.setText("Back");
		backButton.setFont(new Font(24) );
		backButton.setLayoutX(320);
		backButton.setLayoutY(450);

		/**
		 * create start button
		 */
		Button startGameButton = new Button();

		/**
		 * format start button
		 */
		startGameButton.setText("Start Game");
		startGameButton.setLayoutX(520);
		startGameButton.setLayoutY(450);
		startGameButton.setFont(new Font(24));

		/**
		 * create and format combo box/drop down box to choose game type
		 */
		final ComboBox<String> pickPlayers = new ComboBox<String>();
		pickPlayers.getItems().addAll(
				"Player VS Player",
				"Player VS Traditional AI",
				"Player VS Deep Learning AI"
				);
		pickPlayers.setLayoutX(220);
		pickPlayers.setLayoutY(300);
		pickPlayers.setPromptText("Choose Game Type");

		/**
		 * create and format combo box/drop down box to choose difficulty
		 */
		final ComboBox<String> pickDifficulty = new ComboBox<String>();
		pickDifficulty.getItems().addAll(
				"Easy",
				"Medium",
				"Hard"
				);
		pickDifficulty.setLayoutX(520);
		pickDifficulty.setLayoutY(300);
		pickDifficulty.setPromptText("Choose Difficulty");

		/**
		 * text box to inform player that only hard difficulty stores score
		 */
		Text notifyPlayer = new Text();
		notifyPlayer.setText("Only 'Hard' difficulty will store your score!");
		notifyPlayer.setLayoutX(690);
		notifyPlayer.setLayoutY(315);
		notifyPlayer.setFont(new Font(14));

		/**
		 * set action for newGameButton
		 */
		startButton.setOnAction(e-> {

			mainPane.getChildren().removeAll(startButton, helpButton, text);

			mainPane.getChildren().addAll(pickPlayers, pickDifficulty, title, backButton, startGameButton, notifyPlayer);
		});

		/**
		 * set action for helpButton
		 */
		helpButton.setOnAction(e-> {
			new HelpPage();
		});

		/**
		 * set action of backbutton to go back to the main menu
		 */
		backButton.setOnAction(e-> {

			mainPane.getChildren().removeAll(pickPlayers, pickDifficulty, title, backButton, startGameButton, notifyPlayer);
			mainPane.getChildren().addAll(startButton, helpButton, text);
		});


		/**
		 * set action of startButton to display gameScene
		 */
		startGameButton.setOnAction(e->{

			if ( pickPlayers.getValue() == null || pickDifficulty.getValue() == null) {
				AlertBox.display("Error", "You must set your options before the game begins.");
			} else {
				if (pickPlayers.getValue() == "Player VS Player") {
					playStyle = 1;
				} else if (pickPlayers.getValue() == "Player VS Traditional AI") {
					playStyle = 2;
				} else if (pickPlayers.getValue() == "Player VS Deep Learning AI") {
					playStyle = 3;
				}

				if (pickDifficulty.getValue() == "Easy") {
					playDifficulty = 1;
					speedX = 10;
					speedY = 10;
					dx = speedX;
					dy = speedY;
				} else if (pickDifficulty.getValue() == "Medium") {
					playDifficulty = 2;
					speedX = 15;
					speedY = 15;
					dx = speedX;
					dy = speedY;
				} else if (pickDifficulty.getValue() == "Hard") {
					playDifficulty = 3;
					speedX = 20;
					speedY = 20;
					dx = speedX;
					dy = speedY;
				}

				mainPane.getChildren().removeAll(pickPlayers, pickDifficulty, title, startGameButton, backButton, notifyPlayer);

				if (playStyle == 1) { //starts full controls for pvp
					mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
						public void handle(KeyEvent ke) {
							if (ke.getCode() == KeyCode.W && player1.getLayoutY() > 1) {
								player1.setLayoutY(player1.getLayoutY() - 15);
							}
							if (ke.getCode() == KeyCode.S && player1.getLayoutY() < 320) {
								player1.setLayoutY(player1.getLayoutY() + 15);
							}
							if (ke.getCode() == KeyCode.UP && player2.getLayoutY() > 1) {
								player2.setLayoutY(player2.getLayoutY() - 15);
							}
							if (ke.getCode() == KeyCode.DOWN  && player2.getLayoutY() < 320) {
								player2.setLayoutY(player2.getLayoutY() + 15);
							}
						}
					});
				}
				else { //starts only w/s controls for pve
					mainScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
						public void handle(KeyEvent ke) {
							if (ke.getCode() == KeyCode.W && player1.getLayoutY() > 1) {
								player1.setLayoutY(player1.getLayoutY() - 15);
							}
							if (ke.getCode() == KeyCode.S && player1.getLayoutY() < 320) {
								player1.setLayoutY(player1.getLayoutY() + 15);
							}
						}
					});
				}
				
				
				mainPane.setMaxSize(WIDTH, HEIGHT);
				mainPane.setStyle("-fx-background-color: #86DB64");


				/**
				 * creates and sets the rectangles color for the screen color
				 */
				lowerColor = new Rectangle(1000,200, Color.GREEN);
				lowerColor.setLayoutX(0);
				lowerColor.setLayoutY(405);

				/**
				 * creates and sets the rectangles color for the border
				 */
				lowerBorder = new Rectangle(1000,10, Color.BLACK);
				lowerBorder.setLayoutX(0);
				lowerBorder.setLayoutY(400);


				//change based on difficulty
				if (playDifficulty == 1) {
					paddleHeight = 80;

				} else if (playDifficulty == 2) {
					paddleHeight = 60;

				} else if (playDifficulty == 3) {
					paddleHeight = 40;
				}

				/**
				 *  creates and sets the paddle rectangles colors and sizes
				 */
				player1 = new Rectangle(10,paddleHeight, Color.BLACK);
				player2 = new Rectangle(10,paddleHeight, Color.BLACK);

				player1.setLayoutX(0);
				player1.setLayoutY(HEIGHT/2-40);

				player2.setLayoutX(WIDTH-10);
				player2.setLayoutY(HEIGHT/2-40);

				/**
				 *  creates the ball
				 */
				ball = new Circle(5);
				ball.setFill(Color.BLACK);
				ball.setLayoutX(WIDTH/2);
				ball.setLayoutY(HEIGHT/2);
				/**
				 *  creates the scores for both players
				 */
				score1 = new Text();
				score1.setLayoutX(100);
				score1.setLayoutY(500);
				score1.setFont(new Font(20));
				score1.setText("Player 1's Score: 0");
				score1.setWrappingWidth(158);

				score2 = new Text();
				score2.setLayoutX(742);
				score2.setLayoutY(500);
				score2.setFont(new Font(20));
				score2.setText("Player 2's Score: 0");
				score2.setWrappingWidth(158);
				/**
				 *  places the help button
				 */
				helpButton.setLayoutX(425);
				helpButton.setLayoutY(450);
				/**
				 *  adds all the things to the pane
				 */
				mainPane.getChildren().addAll(player1,player2,ball,lowerBorder,lowerColor,score1,score2,helpButton);

				displayGameTime();

				Timeline timelineGame = new Timeline();
				timelineGame.setCycleCount(Timeline.INDEFINITE);
				KeyFrame keyframeTargets = new KeyFrame(Duration.seconds(.08), action -> {
					//gameUpdate
					double x = ball.getLayoutX();
					double y = ball.getLayoutY();

					//traditional ai bot
					if (playStyle == 2) {
						player2.setLayoutY(MovePaddle(ball.getLayoutY(),player2.getLayoutY())+player2.getLayoutY());
					}

					//Move ball
					if (x <=10 && x > -10 && y > player1.getLayoutY() && y < player1.getLayoutY()+80) {
						dx = speedX;
					} else if (ball.getLayoutX() < -10) {
						player1.setLayoutY(HEIGHT/2-40);
						player2.setLayoutY(HEIGHT/2-40);
						ball.setLayoutX(WIDTH/2);
						ball.setLayoutY(HEIGHT/2);
						scorePlayer2++;

						//remove old score
						mainPane.getChildren().remove(score2);
						//convert score to string
						String scorePlayer2string = Integer.toString(scorePlayer2);
						//set text to score
						score2.setText("Player 2's Score: " + scorePlayer2string);
						//add score to pane
						mainPane.getChildren().add(score2);
					}

					//Move ball
					if(x >= WIDTH-10 && x < WIDTH+10 && y > player2.getLayoutY() && y< player2.getLayoutY()+80) {
						speedX++;
						dx = -speedX;
					} else if (ball.getLayoutX() > 1010) {
						player1.setLayoutY(HEIGHT/2-40);
						player2.setLayoutY(HEIGHT/2-40);
						ball.setLayoutX(WIDTH/2);
						ball.setLayoutY(HEIGHT/2);
						scorePlayer1++;

						//remove old score
						mainPane.getChildren().remove(score1);
						//convert score to string
						String scorePlayer1string = Integer.toString(scorePlayer1);
						//set text to score
						score1.setText("Player 1's Score: " + scorePlayer1string);
						//add score to pane
						mainPane.getChildren().add(score1);
					}

					if (y <= 0) {		// if the ball hits the 
						dy = speedY;
					}

					if (y >= HEIGHT-5) {
						dy = -speedY;
					}

					//end game and determine winner
					if (scorePlayer1 == 10 || scorePlayer2 == 10) {
						timelineGame.stop();
						createGameOverStage();
					}

					ball.setLayoutX(ball.getLayoutX()+dx);
					ball.setLayoutY(ball.getLayoutY()+dy);
					//
					//
				});
				timelineGame.getKeyFrames().add(keyframeTargets);
				timelineGame.play();
				//
			}});
	}
	/**
	 * sets playStyle
	 * @param x int playStyle
	 */

	public void setPlayStyle(int x) {
		playStyle = x;
	}
	/**
	 * returns playStyle
	 * @return playStyle
	 */
	public int getPlayStyle() {
		return playStyle;
	}

	//for ai bot
	public double MovePaddle(double ballY, double paddleY) {
		double movement = 0;
		if (ballY > paddleY+15) {
			movement = 10;
		}
		else if (ballY < paddleY+15) {
			movement = -10;
		}
		return movement;
	}
	/**
	 * sets the time
	 */
	public void displayGameTime() {
		//
		currentGameTime = 0;
		//create text box for the countdown timer
		Text time = new Text();
		time.setLayoutX(435);
		time.setLayoutY(550);
		time.setFont(new Font(20));
		mainPane.getChildren().add(time);
		//create timeline to keep time
		Timeline timelineTime = new Timeline();
		timelineTime.setCycleCount(Timeline.INDEFINITE);
		KeyFrame keyframeTime = new KeyFrame(Duration.seconds(1), action -> {
			if (gameOver == false) {
				//update with current time
				mainPane.getChildren().removeAll(lowerBorder, lowerColor, score1, score2, helpButton, time);
				currentGameTime++;
				time.setText("Game Time: " + currentGameTime);
				mainPane.getChildren().addAll(lowerBorder, lowerColor, score1, score2, helpButton, time);
				finalGameTime = currentGameTime;
			} else {
				mainPane.getChildren().remove(time);
				finalGameTime = currentGameTime;
				timelineTime.stop();
			}
		});
		timelineTime.getKeyFrames().add(keyframeTime);
		timelineTime.play();
		finalGameTime = currentGameTime;
	}
	/**
	 * get finalGameTime
	 * @return finalGameTime
	 */
	public int getFinalGameTime() {
		//get final game time
		return finalGameTime;
	}
	/**
	 * get final score for player 1
	 * @return scorePlayer1
	 */
	public int getFinalScore1() {
		return scorePlayer1;
	}
	/**
	 * get final score for player 2
	 * @return scorePlayer2
	 */
	public int getFinalScore2() {
		return scorePlayer2;
	}
	/**
	 * get winnerPlayer
	 * @return winnerPlayer
	 */
	public int getWinner() {
		return winnerPlayer;
	}
	/**
	 * get loser Score
	 * @return loserScore
	 */
	public int getLoserScore() {
		return loserScore;
	}
	/**
	 * displays the winner
	 */
	public void displayWinner() {
		//display winner
		gameOver = true;
		player1.setLayoutY(HEIGHT/2-40);
		player2.setLayoutY(HEIGHT/2-40);
		ball.setLayoutX(WIDTH/2);
		ball.setLayoutY(HEIGHT/2);
		speedX = 0;
		speedY = 0;
		mainPane.getChildren().removeAll(lowerBorder, lowerColor, ball, score1, score2, player1, player2, helpButton);
		Text win1 = new Text();
		win1.setLayoutX(400);
		win1.setLayoutY(150);
		win1.setFont(Font.font ("Gill Sans Ultra Bold", 28) );
		if (scorePlayer1 == 10) {
			winnerPlayer = 1;
			loserScore = scorePlayer2;
		} else {
			winnerPlayer = 2;
			loserScore = scorePlayer1;
		}
		win1.setText("Player " + winnerPlayer + " WINS!");
		gameOverPane.getChildren().add(win1);
	}
	/**
	 * displays the highscores
	 */
	public void displayHighscore() {
		//create timeline to let time catch up
		Timeline timelineTime = new Timeline();
		timelineTime.setCycleCount(5);
		KeyFrame keyframeTime = new KeyFrame(Duration.seconds(.5), action -> {
		});
		timelineTime.getKeyFrames().add(keyframeTime);
		timelineTime.play();

		//if hard difficulty was selected store score then sort
		if (playDifficulty == 3) {
			scoreScore.writeScore(this);
			scoreScore.sortHighscores(this);
			displayScoresText();
		} else {
			displayScoresText();
		}
	}

	//display scores
	public void displayScoresText() {
		//create text box to display high score
		//part1
		Text textHighscore1 = new Text();
		textHighscore1.setLayoutX(200);
		textHighscore1.setLayoutY(300);
		textHighscore1.setFont(new Font(30));
		textHighscore1.setText("Current Highest Score Stats: ");
		//part2
		Text textHighscore2 = new Text();
		textHighscore2.setLayoutX(200);
		textHighscore2.setLayoutY(350);
		textHighscore2.setFont(new Font(25));
		textHighscore2.setText("Winner: " + scoreScore.getMaxWinner());
		//part3
		Text textHighscore3 = new Text();
		textHighscore3.setLayoutX(200);
		textHighscore3.setLayoutY(400);
		textHighscore3.setFont(new Font(25));
		textHighscore3.setText("Loser's Score: " + scoreScore.getmaxScoreLoser());
		//part4
		Text textHighscore4 = new Text();
		textHighscore4.setLayoutX(200);
		textHighscore4.setLayoutY(450);
		textHighscore4.setFont(new Font(25));
		textHighscore4.setText("Game Time: " + scoreScore.getMaxGameTime());
		//part5
		Text textHighscore5 = new Text();
		textHighscore5.setLayoutX(200);
		textHighscore5.setLayoutY(500);
		textHighscore5.setFont(new Font(25));
		textHighscore5.setText("Play Style: " + scoreScore.getMaxPlayStyle());
		//add elements for display highest score
		gameOverPane.getChildren().addAll(textHighscore1, textHighscore2, textHighscore3, textHighscore4, textHighscore5);

		//display your game name, missed, and score
		//your info
		Text textYourInfo = new Text();
		textYourInfo.setLayoutX(600);
		textYourInfo.setLayoutY(300);
		textYourInfo.setFont(new Font(30));
		textYourInfo.setText("Your Game Stats: ");
		//display your name
		Text textYourName = new Text();
		textYourName.setLayoutX(600);
		textYourName.setLayoutY(350);
		textYourName.setFont(new Font(25));
		textYourName.setText("Winner: " + winnerPlayer);
		//display your game missed clicks
		Text textYourMissedClicks = new Text();
		textYourMissedClicks.setLayoutX(600);
		textYourMissedClicks.setLayoutY(400);
		textYourMissedClicks.setFont(new Font(25));
		textYourMissedClicks.setText("Loser's Score: " + loserScore);
		//display your score
		Text textYourScore = new Text();
		textYourScore.setLayoutX(600);
		textYourScore.setLayoutY(450);
		textYourScore.setFont(new Font(25));
		textYourScore.setText("Game Time:  " + finalGameTime);
		//display your play style
		Text textYourStyle = new Text();
		textYourStyle.setLayoutX(600);
		textYourStyle.setLayoutY(500);
		textYourStyle.setFont(new Font(25));
		textYourStyle.setText("Play Style: " + playStyle);
		//add the text boxes and logo to the pane
		gameOverPane.getChildren().addAll(textYourInfo, textYourName, textYourScore, textYourMissedClicks, textYourStyle);
	}
	/**
	 * creates the game over stage
	 */
	public void createGameOverStage() {
		stage.close();
		displayWinner();
		displayHighscore();
		gameOverPane.setStyle("-fx-background-color: white");
		gameOverStage.setTitle("Game Over");
		gameOverStage.setScene(gameOverScene);
		gameOverStage.show();
		
	}

	/**
	 * Start method for the program that titles and displays the stage
	 */
	public void start(Stage stage) {
		stage.setScene(mainScene);
		stage.setTitle("Deep Learning Pong");
		stage.setResizable(false);
		stage.sizeToScene();
		stage.show();
	}
	/**
	 * main it just works
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}