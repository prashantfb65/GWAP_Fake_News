/**
Created By:               P R A S H A N T   G A R  G  |  STUDENT ID : 16201447
Created Date:             21-April-2017
Copyright:                University College Dublin
Subject:				  COLLECTIVE INTELLIGENCE (GWAP Application)
Description:              This is the controller file for Main Game Play Window. 
						  All the actions taken from Game Window are handled here.
Version:                  00.00.00.01

Modification history:
----------------------------------------------------------------------------------------------------------------------------
Modified By         Modified Date (dd/mm/yyyy)                Version               Description
---------------------------------------------------------------------------------------------------------------------------
 **/
package com.ci.fakenews.controller;

import java.io.IOException;
/*
 * Inclusion of Header Files
 */
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ci.fakenews.bean.KeyValueDouble;
import com.ci.fakenews.bean.NewsDetails;
import com.ci.fakenews.bean.UserGameInfo;
import com.ci.fakenews.comparor.CompareSort;
import com.ci.fakenews.constant.Constant;
import com.ci.fakenews.sdo.OperationFile;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/*
 * Class PlayGameController
 */
public class PlayGameController implements Initializable {

	/*
	 * FXML Labels
	 */

	 @FXML
	 private Label headline;
	 
	 @FXML
	 private Label levelInfo;

	 @FXML
	 private Label scoreInfo;

	 @FXML
	 private Label coinsInfo;
	
	 @FXML
	 private Label coinsUpdate;

	 @FXML
	 private Label energyUpdate;

	 @FXML
	 private Label progressUpdate;

	 @FXML
	 private Label scoreUpdate;	 
	    
	 @FXML
	 private Label energyInfo;

	 @FXML
	 private Label messageInfo;

	 @FXML
	 private Label labelProg;
	 
	 @FXML
	 private Label timer;
	 
	 
	 /*
	  * FXML Radio Buttons
	  */
	 @FXML
	 private RadioButton optionOne;

	 @FXML
	 private RadioButton optionTwo;

	 @FXML
	 private RadioButton optionThree;

	 @FXML
	 private RadioButton optionForth;

	 @FXML
	 private ToggleGroup options;
	 
	 
	 /*
	  * FXML Image View
	  */
	 @FXML
	 private ImageView newsImage;
	 
	
	 /*
	  * Class Variables
	  */
	 private int indexedNews;	//Index of the News currently accessing
	 
	 List<Integer> newsAttempted = new ArrayList<>(); //List of News already attempted by the User
	 
	 OperationFile operation = new OperationFile(); //Object creation for OperationFile to perform DB operations

	 private final Integer startTime = 240;	//If No timer is specified assign 240 to the timer
	 	
	 private Integer seconds = startTime;	//Assign this final startTime to seconds

	 List<NewsDetails> newsDetails = null;	//Holds the Information about all the news
	 
	 UserGameInfo gameInfoDetails = null;	//Holds the Information about all the User
    
	 String correctAnswer = "";	//Holds the Correct Answer to the Question
	 
	 String userResponse = "";	//Holds the User Response for the Question

	 int continousSkip = 0;		//Holds the count of Continuous Skips by the User
	 
	 int continousCorrect = 0;	//Holds the count of Continuous correct answers by the User
	 
	 int continousWrong = 0;	//Holds the count of Continuous wrong answers by the User
	 
	 int progressCount = 0;		//Holds the Current Level Progress status
	 
	/*
	* The static variable mainStartupScene is declared to keep 
	* the reference of the Startup Page 
	*/	
	 public static Scene mainStartupScene;
	 
	 
	 /*
	 * Creating Audio Media for the PlayGame Page
	 */
	 
	 URL resource = getClass().getResource("/music/game.mp3");
	 MediaPlayer media = new MediaPlayer(new Media(resource.toString()));
	 String status = "Active";
	 
	 /*
	  * Creating Counter Media for the PlayGame Page 
	  * It is played with the remaining time becomes less than 10 seconds
	  */
	 URL resource2 = getClass().getResource("/music/countdown.mp3");
	 MediaPlayer media2 = new MediaPlayer(new Media(resource2.toString()));
	 String status2 = "Active";

    
	 String statusWindow = "Active";	//Holds the status of the Play Game Window i.e whether it is active or not

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    
    /**
	 * Method Name: [login]
	 * This method is called when the user clicks on PLAY GAME button
	 * on the Game Startup Screen Window 
	 * @param Username of the User Logged in 
	 * 
	 */
    public void initData(String username) {
    	
    	//As soon as the user Logs in ..The music method is called to start the media for the window
        music();
        
        //The details of the News Articles is fetched and list of NewsDetails beans
        try {
            newsDetails = operation.fetchNews();
        } catch (SQLException ex) {
            Logger.getLogger(PlayGameController.class.getName()).log(Level.SEVERE, "Error while fetching the News");
        }
        
        //The details of the Logged in User is fetched and stored in UserGameInfo bean
        try {
            gameInfoDetails = operation.fetchUserGameDetails(username);
        } catch (SQLException ex) {
            Logger.getLogger(PlayGameController.class.getName()).log(Level.SEVERE, "Error while fetching the Game Details for the User");
        }

        //Progress Level of the Level Set to 0%
        labelProg.setText("0%");

	
        int timerTime = 240;	//Level 1 Timer

        
        /*
         * Different Timers assigned Depending Upon the Level in which the User is 
         */
        switch (gameInfoDetails.getLevel()) {
            case 2:
                timerTime = Constant.startTimeLevel2;
                break;
            case 3:
                timerTime = Constant.startTimeLevel3;
                break;
            case 4:
                timerTime = Constant.startTimeLevel4;
                break;
            case 5:
                timerTime = Constant.startTimeLevel5;
                break;
            case 6:
                timerTime = Constant.startTimeLevel6;
                break;
            case 7:
                timerTime = Constant.startTimeLevel7;
                break;
            case 8:
                timerTime = Constant.startTimeLevel8;
                break;
            case 9:
                timerTime = Constant.startTimeLevel9;
                break;
            case 10:
                timerTime = 0;
                break;
        }
        
        /*
         * After assigning the Timer ..changeNews method is called
         */
        changeNews();
        
        /*
         * As soon as the first news is invoked the timer initializes using the 
         * timer method
         */
        timer(timerTime);
        //timer();
    }
    /*
     * End of Method - initData
     */

    
    /**
     * Method Name- changeNews
     * This method is called to load the new News Article as a Question to the 
     * Player
     */
    public void changeNews() {
    	
    	/*
    	 * Calculate the Reputation of the User with every Question 
    	 */
        reputation();
        
        
        /*try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(PlayGameController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        
        /*
         * Finding an Index Randomly over the set of all news to pop as question
         */
        indexedNews = randomIndex();
        
        /*
         * Blanking the Options and News Headline
         */
        optionOne.setText("");
        optionTwo.setText("");
        optionThree.setText("");
        optionForth.setText("");
        headline.setText(" ");

        /*
         * Clearing the Level Info, Score Info and Coins Info
         */
        levelInfo.setText("");
        scoreInfo.setText("");
        coinsInfo.setText("");

        /*
         * Extracting the Headline and Image Source for the News Article selected as 
         * Question and Displaying them as Question
         */
        try {
            String imageSource = newsDetails.get(indexedNews).getImageURL().toString(); //Image Source
            Image img = new Image(imageSource);	//Creating Image
            newsImage.setImage(img);	//Setting the=at Image to ImageView
            correctAnswer = newsDetails.get(indexedNews).getType(); //Getting Correct Answer
        } catch (Exception exp) {
        	/*
        	 * In case exception load ..Not Available Pic
        	 */
            Image img = new Image("file:images/notAvail.png"); 
            newsImage.setImage(img);
        }
        headline.setText(newsDetails.get(indexedNews).getHeadline());	//Set the Head line
        
        /*
         * Set the options start
         */
        List<String> type = new ArrayList<>();
        type.add("Biased News");
        type.add("Satirical News");
        type.add("Conspiracy News");
        type.add("Genuine News");

        Collections.shuffle(type);

        optionOne.setText(type.get(0));
        optionTwo.setText(type.get(1));
        optionThree.setText(type.get(2));
        optionForth.setText(type.get(3));
        /* End*/
        
        /*
         * Setting other details such as score, level, coins and energy
         */
        try {
            int score = gameInfoDetails.getScore();
            scoreInfo.setText(String.valueOf(score));

            int level = gameInfoDetails.getLevel();
            String levelInfoDetails = String.valueOf(level);
            levelInfo.setText(levelInfoDetails);

            int coins = gameInfoDetails.getCoins();
            coinsInfo.setText(String.valueOf(coins));

            int energy = gameInfoDetails.getEnergy();
            energyInfo.setText(String.valueOf(energy) + "%");

        } catch (Exception e) {
        	Logger.getLogger(PlayGameController.class.getName()).log(Level.SEVERE, "Error in changeNews method ",e);
        }
    }

    
    /**
     * Method Name - reputation
     * This method is used to compute the reputation of the player
     * on the basis of correct answers answered by the player
     * 
     */
    public double reputation() {
    	
        int totalCorrect = gameInfoDetails.getCorrect();	// Holds the correct answers answered by the user
        
        int totalWrong = gameInfoDetails.getWrong();		// Holds the wrong answers answered by the user
        
        int totalPass = gameInfoDetails.getPass();			// Holds the questions passed by the user
        
        double totalAttemted = totalCorrect + totalWrong + totalPass;	// Holds the total number of questions attempted by the user
        
        double repo = 0;	// Initializes Repo to zero
        
        /*
         * If the user hasn't attempted any question return 0
         */
        if (totalAttemted <= 0) {
            return repo;
        }
        
        /*
         * If the user has attempted questions 65% correctly 
         * and attempts are more than 5. Compute repo, assign it to user 
         */
        double correctPercentage = (totalCorrect / totalAttemted) * 100;
        if (correctPercentage > 65 && totalAttemted > 5) {
            repo = correctPercentage;
        }
        gameInfoDetails.setReputation(repo);
        return correctPercentage;
    }
    /*
     * End of Method - reputation
     * 
     */
    
    /**
     * Method Name - randomIndex 
     * Choose random number between the range in correspondence to the
     * news items size and return it
     * 
     * @return
     */
    public int randomIndex() {
        int randomNum = ThreadLocalRandom.current().nextInt(0, newsDetails.size() - 1);
        while (newsAttempted.contains(randomNum)) {
            randomNum = ThreadLocalRandom.current().nextInt(0, newsDetails.size() - 1);
        }
        newsAttempted.add(randomNum);
        return randomNum;
    }
    /*
     * End of Method - randomIndex
     */
    
    
    /**
     * Method Name - timer
     * The method invokes the Timer based of timeseconds passed as argument to it
     * @param timeSeconds
     */
    public void timer(int timeSeconds) {
        
    	seconds = timeSeconds;
        
    	Timeline time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                seconds = seconds - 1;
                timer.setText(seconds.toString());
                
                /*
                 * Starts Counter Media when time becomes less than 10 econds
                 */
                if (seconds <= 10 && seconds >= 0 && statusWindow.equals("Active")) {
                    media.stop();
                    startCountDown();
                }
                
                /*
                 * Ends the Game 
                 */
                if (seconds <= 0 && statusWindow.equals("Active")) {
                    try {
                        stopCountDown();
                        time.stop();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Ran out of Time: Game Over");
                        alert.show();
                        endGame();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PlayGameController.class.getName()).log(Level.SEVERE, "Problem while saving the game in Timer function");
                    }
                }
            }
        });

        time.getKeyFrames().add(frame);
        time.playFromStart();
    }
    /*
     * End of Method - timer
     */
    
    /**
     * Method Name - endGame
     * This method saves the game details and ends the Game
     * After that it invokes the startup screen
     * @param event
     * @throws InterruptedException
     */
    public void endGame() throws InterruptedException {
        try {
        	statusWindow ="Inactive";
            operation.updateUserGameDetails(gameInfoDetails);
            endMusic();
            
        } catch (SQLException ex) {
            Logger.getLogger(PlayGameController.class.getName()).log(Level.SEVERE, "Error while saving the Game");
        }
        
        try {
        	
        	//Reading and Loading the StartupScreen FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ci/fakenews/ui/StartupScreen.fxml"));
            
            //Creating a new Stage
            Stage stage = new Stage();
            
            //Creating a Scene using the StartupScreen FXML
            Scene scene = new Scene((Pane) loader.load());
            scene.setFill(null);
            
            
            /*
             * Assigning the Scene Reference to the variable mainStartupScene
             * This reference is used to close the StartupScreen Window from further 
             * windows
             */
            mainStartupScene = scene;
            
            /*
             * Setting the scene for the stage and 
             * disabling its decorations e.g. close,
             * minimize, maximize button
             */
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            
            /*
             * Calling the Method initData of the StartupScreen Controller
             */
            StartupScreenController controller = loader.<StartupScreenController>getController();
            controller.initData(gameInfoDetails.getUsername());
            
            /*
             * Enabling the display of the stage
             */
            stage.show();
            
            StartupScreenController.mainplayGameScene.getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(PlayGameController.class.getName()).log(Level.SEVERE, "Problem while Loading the Startup FXML");
        }
    }
    /*
     * End of Method endGame
     * 
     */
    
    /*
     * Method Name - closeWindow
     * This method is called when the player click the close
     * It terminates the game
     */
    public void closeWindow() {
        statusWindow = "Inactive";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to end the Current Game ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            try {
				endGame();
			} catch (InterruptedException e) {
				Logger.getLogger(PlayGameController.class.getName()).log(Level.SEVERE, "Problem while Terminating the Game");
			}
        }
    }
    /*
     * end of method- closeWindow
     */
    
    /**
     * Method Name - music
     * This method will play the media on the Window in a loop
     */
    public void music() {
    	status = "Active";
        media.setOnEndOfMedia(new Runnable() {
            public void run() {
                media.seek(Duration.ZERO);
            }
        });
        media.play();
    }
    /*
     * End of Method - music 
     */

    /**
     * Method Name - endMusic
     * This method will end the music media on the Window
     */
    public void endMusic() {
    	status = "Inactive";
        status2 = "Inactive";
        media.stop();
        media2.stop();
    }
    /*
     * End of Method - endMusic
     */
    

    /**
     * Method Name - startStopMusic
     * This is method toggles the sound play and disable
     */
    public void startStopMusic() {
        if ("Active".equalsIgnoreCase(status)) {
            endMusic();
        } else if ("Inactive".equalsIgnoreCase(status)) {
            music();
        }
    }
    /*
     * End of Method- startStopMusic
     */

    /**
     * Method Name - startCountDown
     * This method loads the countdown music media to the window
     * 
     */
    public void startCountDown() {
        status2 = "Active";
        media2.setOnEndOfMedia(new Runnable() {
            public void run() {
                media2.seek(Duration.ZERO);
            }
        });
        media2.play();
    }
    /*
     * end of method
     */
    
    /**
     * Method Name - stopCountDown
     * This method un loads the countdown music media from the window
     */
    public void stopCountDown() {
        status2 = "Inactive";
        media2.stop();
    }
    /*
     * End of Method - stopCountDown
     */
    
    
    /**
     * Method Name - submitAnswer
     * This method is called when the answer is submitted from PlayGame Window
     * 
     * @param event
     */
    public void submitAnswer(ActionEvent event) {

    	/*
    	 * Retrieving the selected Option
    	 */
    	String selectedAnswer = "";
    	if (optionOne.isSelected()) {
            selectedAnswer = optionOne.getText();
        }
        if (optionTwo.isSelected()) {
            selectedAnswer = optionTwo.getText();
        }
        if (optionThree.isSelected()) {
            selectedAnswer = optionThree.getText();
        }
        if (optionForth.isSelected()) {
            selectedAnswer = optionForth.getText();
        }
    	
        try{
        	/*
        	 * If no option is selected
        	 */
        	if (selectedAnswer == null || "".equalsIgnoreCase(selectedAnswer)) {
                String message = "SELECT OPTION";
                messageInfo.setText(message);
                
                scoreUpdate.setText("");
                energyUpdate.setText("");
                coinsUpdate.setText("");
                progressUpdate.setText("");
            }
        	/*
        	 * If some option is selected
        	 */
        	else{
            	
            	messageInfo.setText("");
                scoreUpdate.setText("");
                energyUpdate.setText("");
                coinsUpdate.setText("");
                progressUpdate.setText("");

                continousSkip = 0;
                
                userResponse = "";
                int previousScore = gameInfoDetails.getScore();
                int level = gameInfoDetails.getLevel();
                int previousEnergy = gameInfoDetails.getEnergy();
                int previousCoins = gameInfoDetails.getCoins();
            	
                if (selectedAnswer.equals("Genuine News")) {
                    userResponse = "truth";
                } else if (selectedAnswer.equals("Satirical News")) {
                    userResponse = "satire";
                } else if (selectedAnswer.equals("Conspiracy News")) {
                    userResponse = "conspiracy";
                } else if (selectedAnswer.equals("Biased News")) {
                    userResponse = "bias";
                }
                
                /*
                 * If the selected answer is correct
                 */
                if (userResponse.equalsIgnoreCase(correctAnswer) || " ".equalsIgnoreCase(correctAnswer) 
                		|| "".equalsIgnoreCase(correctAnswer)) {
                	
                    int correct = gameInfoDetails.getCorrect();
                    gameInfoDetails.setCorrect(correct + 1);

                    int newScore = previousScore;
                    int newEnergy = previousEnergy;
                    int newCoins = previousCoins;

                    int instantCoins = 0;
                    int instantEnergy = 0;
                    int instantScore = 0;

                    continousCorrect++;
                    continousWrong = 0;
                    progressCount++;

                    if (continousCorrect <= 3) {
                        instantScore = Constant.fivePointer;
                        newScore = previousScore + instantScore;
                    } else if (continousCorrect > 3 && continousCorrect <= 10) {
                        instantCoins = Constant.coin1 * level;
                        instantEnergy = Constant.tenPointer;
                        instantScore = Constant.fiftPointer * level;

                        newEnergy = previousEnergy + instantEnergy;
                        newCoins = previousCoins + instantCoins;
                        newScore = previousScore + instantScore;
                    }
                    
                    //code for setting score, coins and energy

                    if (instantCoins > 0) {
                        coinsUpdate.setText("+ " + String.valueOf(instantCoins));
                    }
                    if (instantEnergy > 0) {
                        energyUpdate.setText("+ " + String.valueOf(instantEnergy));
                    }
                    if (instantScore > 0) {
                        scoreUpdate.setText("+ " + String.valueOf(instantScore));
                    }
                    messageInfo.setText("CORRECT " + continousCorrect);

                    gameInfoDetails.setCoins(newCoins);
                    gameInfoDetails.setEnergy(newEnergy);
                    gameInfoDetails.setScore(newScore);

                    progress(progressCount);

                }
                
                /*
                 *	If the selected answer is incorrect 
                 */
                
                else if (!userResponse.equals(correctAnswer)) {
                	
                    int wrong = gameInfoDetails.getWrong();
                    
                    gameInfoDetails.setWrong(wrong + 1);	//Increment wrong by 1
                    	
                    continousCorrect = 0;	
                    
                    continousWrong++;
                    
                    int newEnergy = previousEnergy;

                    int instantEnergy = 0;

                    if (continousWrong <= 1) {
                        instantEnergy = Constant.fivePointer;
                        newEnergy = previousEnergy - instantEnergy;
                    } else if (continousWrong > 1 && continousWrong <= 5) {
                        instantEnergy = Constant.tenPointer;
                        newEnergy = previousEnergy - instantEnergy;
                    } else if (continousWrong > 5 && continousWrong <= 10) {
                        instantEnergy = Constant.twentyPointer;
                        newEnergy = previousEnergy - instantEnergy;
                    }
                    gameInfoDetails.setEnergy(newEnergy);
                    
                    messageInfo.setText("INCORRECT " + continousWrong);
                    if (instantEnergy > 0) {
                        energyUpdate.setText("- " + String.valueOf(instantEnergy));
                    }
                    if (newEnergy <= 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Ran out of Energy! Game Over ...");
                        alert.showAndWait();
                        Thread.sleep(200);
                        endGame();
                    }
                    setLabels();
                }
                computation();
                changeNews();
            }
        	
        }catch(Exception e){
        	Logger.getLogger(PlayGameController.class.getName()).log(Level.SEVERE, "Error while submitting the answer" + e);
        	e.printStackTrace();
        }
    }
    /*
     * End of Method - submitAnswer
     */
        
    
    /**
     * Method Name - skipAnswer
     * This method is called when the user skips the question or presses Pass button
     * 
     * @param event
     */
    public void skip(ActionEvent event) {

        
        scoreUpdate.setText("");
        energyUpdate.setText("");
        coinsUpdate.setText("");
        progressUpdate.setText("");

        int skips = gameInfoDetails.getPass() + 1;
        
        gameInfoDetails.setPass(skips);
        
        int energy = gameInfoDetails.getEnergy();
        
        continousWrong = 0;
        continousCorrect = 0;
        continousSkip++;

        int newEnergy = 100;
        
        int instantEnergy = 0;

        if (continousSkip <= 2) {
            instantEnergy = Constant.twoPointer;
            newEnergy = energy - instantEnergy;
            gameInfoDetails.setEnergy(newEnergy);
        } else if (continousSkip > 2 && continousSkip <= 6) {
            instantEnergy = Constant.fivePointer;
            newEnergy = energy - instantEnergy;
            gameInfoDetails.setEnergy(newEnergy);
        } else if (continousSkip > 6 && continousSkip <= 10) {
            instantEnergy = Constant.tenPointer;
            newEnergy = energy - instantEnergy;
            gameInfoDetails.setEnergy(newEnergy);
        } 
        
        messageInfo.setText("PASSED " + continousSkip);
        
        gameInfoDetails.setEnergy(newEnergy);
        
        energyUpdate.setText("- " + String.valueOf(instantEnergy));

        double repoPassMtd = reputation();
        
        /*
         * If the Reputed User Skips the Question increment the passcount for the news
         */
        if (repoPassMtd > 65) {
            NewsDetails newsItem = newsDetails.get(indexedNews);
            
            
            double passCount = newsItem.getPassCount()+1;
            
            newsItem.setPassCount((int)passCount);
            
            double totalAppearance = passCount + newsItem.getBiasCount() + newsItem.getConspiracyCount()
                    + newsItem.getSatireCount() + newsItem.getTrueCount();
            double passPercent = (passCount / totalAppearance) / 100;
            if (passPercent > 50 && totalAppearance > 5) {
                newsItem.setStatus("I");
            }
            try {
               operation.updateNewsData(newsItem);
            } catch (SQLException ex) {
                Logger.getLogger(PlayGameController.class.getName()).log(Level.SEVERE, "Error while updating the news for pass");
            }
        }
        
        /*
         * If the energy runs out
         */
        if (newEnergy <= 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Ran out of Energy: Game Over");
            alert.showAndWait();
            try {
                endGame();
            } catch (InterruptedException ex) {
                Logger.getLogger(PlayGameController.class.getName()).log(Level.SEVERE, "Problem while changing the Game", ex);
            }
        } 
        changeNews(); //Change the New Article
    }
    /*
     * End of Method
     */

   

    
    /**
     * Method Name - Progress
     * This method is used to increment the progress of user in a level on every correct answer
     * @param count
     * @throws SQLException
     * @throws InterruptedException 
     */
    public void progress(int count) throws SQLException, InterruptedException {
        String progInLevel = count * 10 + "%";	// For every correct answer increment by one
        progressUpdate.setText("+ 10 %");
        labelProg.setText(progInLevel);
        setLabels();	// Set Label after the Changes
        
        /*
         * If the user completes the Level
         */
        if ((count * 10) == 100) {
            changeLevel();	// Call change Level Method
        }

    }
    /*
     * End of Method 
     */

    /**
     * Method Name: computation
     * This is method is quite important from GWAP perspective,
     * Not only does it categorize the new News Articles into the 
     * four categories such as Genuine, Satirical, Biased and Conspiracy
     * But also changes the category of pre-existing news using Human 
     * Intelligence
     */
    public void computation() {
    	
    	/*
    	 * Computing the reputation of the User
    	 */
        double reputation = gameInfoDetails.getReputation();
        
        /*
         * If the Reputation is more than 65%
         */
        if (reputation > 65) {
        	
        	//Fetch the News Item
            NewsDetails newsItem = newsDetails.get(indexedNews);

            /*
             * Fetching the different counts of categories for the news
             */
            int conspiracyCount = newsItem.getConspiracyCount();
            int biasCount = newsItem.getBiasCount();
            int genuineCount = newsItem.getTrueCount();
            int satireCount = newsItem.getSatireCount();

            /*
             * Adding them to a list
             */
            List<KeyValueDouble> keyVal = new ArrayList<>();
            keyVal.add(new KeyValueDouble("conspiracy", conspiracyCount));
            keyVal.add(new KeyValueDouble("bias", biasCount));
            keyVal.add(new KeyValueDouble("truth", genuineCount));
            keyVal.add(new KeyValueDouble("satire", satireCount));
            
            /*
             * Compute the dominace of the categories
             */
            for(KeyValueDouble categoryWithCount: keyVal)
            if (categoryWithCount.getKey().equals("conspiracy") && userResponse.equalsIgnoreCase("conspiracy")) {
                newsItem.setConspiracyCount(conspiracyCount++);
            }else if (categoryWithCount.equals("bias") && userResponse.equalsIgnoreCase("bias")) {
                newsItem.setConspiracyCount(biasCount++);
            } else if (categoryWithCount.equals("truth")&& userResponse.equalsIgnoreCase("truth")) {
                newsItem.setConspiracyCount(genuineCount++);
            } else if (categoryWithCount.equals("satire")&& userResponse.equalsIgnoreCase("satire")) {
                newsItem.setConspiracyCount(satireCount++);
            }

            double dominance = 0;
             
            double totalCatCount = conspiracyCount + biasCount + genuineCount + satireCount;
            
            List<KeyValueDouble> keyVal2 = new ArrayList<>();
            keyVal2.add(new KeyValueDouble("conspiracy", conspiracyCount));
            keyVal2.add(new KeyValueDouble("bias", biasCount));
            keyVal2.add(new KeyValueDouble("truth", genuineCount));
            keyVal2.add(new KeyValueDouble("satire", satireCount));
            
            Collections.sort(keyVal2, new CompareSort().reversed());
            double dominatingNewsSect =  keyVal2.get(0).getValue();
            String category = keyVal.get(0).getKey();
            
            dominance = (dominatingNewsSect/totalCatCount)*100;
            
            /*
             * Reassign if the dominance goes above 70%
             */
            if(dominance>70){
            	newsItem.setType(category);
            }
            /*
             * Update the News Item and then select it again to newDetails
             */
            try {
                operation.updateNewsData(newsItem);
            } catch (SQLException ex) {
                Logger.getLogger(PlayGameController.class.getName()).log(Level.SEVERE, "Error while updating the news computations");
            }

            try {
                newsDetails = operation.fetchNews();
            } catch (SQLException ex) {
                Logger.getLogger(PlayGameController.class.getName()).log(Level.SEVERE, "Error while fetching the news again for computations");
            }
        }
    }
    /*
     * End of Method
     */
    

   
    /**
     * Method Name - Change Level
     * This method is called whenever there is a change
     * of level
     * Bonus Question pops up if the Players Reputation is more than 65%
     * @throws SQLException
     * @throws InterruptedException 
     */
    public void changeLevel() throws SQLException, InterruptedException {

        double reputation = reputation();
        String bonusAnswer = "";

        /*
         * Resetting Continuous Correct, Pass, Incorrect
         */
        continousCorrect = 0;
        continousSkip = 0;
        continousWrong = 0;
        progressCount = 0;
        
        int level = gameInfoDetails.getLevel();
        
        /**
         * If the User's reputation is more than 65
         */
        if (reputation > 65 && level!=9) {
        	
            List<String> sources = operation.getSources(); // Getting News Sources
            
            Collections.shuffle(sources);	// Sorting them randomly
            
            if (sources.size() > 0) {
                
            	String sourceName = sources.get(0);
                String question = "Does '" + sourceName + "' give Fake News ?";	//Framing the Question

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, question + " (For 25 points) ", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                
                /*
                 * Taking the Response from confirm box
                 */
                if (alert.getResult() == ButtonType.NO) {
                    bonusAnswer = "No";
                    
                } else if (alert.getResult() == ButtonType.YES) {
                    bonusAnswer = "Yes";
                    
                } else {
                    bonusAnswer = "";
                }
                if (!"".equalsIgnoreCase(bonusAnswer)) {
                    operation.insertBonusAnswer(bonusAnswer, sourceName);	//Inserting the answer
                }
            }
            
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Logger.getLogger(PlayGameController.class.getName()).log(Level.SEVERE, "Error while fetching the news again for computations");
			}
        }
        
        if(level!=9){
        	Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Level " +  gameInfoDetails.getLevel() + " completed ... Do you want to continue ?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.NO) {
            	changeStatusIntrmdt("no");
            } else if (alert.getResult() == ButtonType.YES) {
            	changeStatusIntrmdt("yes");
            }
        }if(level==9){
        	Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Game Over .. Do you want to play Level 9 again ?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.NO) {
            	changeStatusIntrmdt("no");
            } else if (alert.getResult() == ButtonType.YES) {
            	changeStatusIntrmdt("yes");
            }
        }
        
    }

    public void changeStatusIntrmdt(String proceed) throws InterruptedException {
    	
    	int level = gameInfoDetails.getLevel();
        int nextLevel = level + 1;
        gameInfoDetails.setLevel(nextLevel);
        levelInfo.setText(String.valueOf(nextLevel));

        progressUpdate.setText("New");
        labelProg.setText("0%");

        energyUpdate.setText("New");
        energyInfo.setText("100%");
        
        messageInfo.setText("NEXT LEVEL");

        int coins = 0;
        int timerTime = 240;

        switch (nextLevel) {
            case 2:
                coins = Constant.level2Coins;
                timerTime = Constant.startTimeLevel2;
                break;
            case 3:
                coins = Constant.level3Coins;
                timerTime = Constant.startTimeLevel3;
                break;
            case 4:
                coins = Constant.level4Coins;
                timerTime = Constant.startTimeLevel4;
                break;
            case 5:
                coins = Constant.level5Coins;
                timerTime = Constant.startTimeLevel5;
                break;
            case 6:
                coins = Constant.level6Coins;
                timerTime = Constant.startTimeLevel6;
                break;
            case 7:
                coins = Constant.level7Coins;
                timerTime = Constant.startTimeLevel7;
                break;
            case 8:
                coins = Constant.level8Coins;
                timerTime = Constant.startTimeLevel8;
                break;
            case 9:
                coins = Constant.level9Coins;
                timerTime = Constant.startTimeLevel9;
                break;
            case 10:
                coins = Constant.finalBounty;
                timerTime = Constant.startTimeLevel9;
                break;
        }
        
        int previousCoins = gameInfoDetails.getCoins();

        coinsUpdate.setText("+ " + String.valueOf(coins));
        int updatedCoins = coins + previousCoins;
        gameInfoDetails.setCoins(updatedCoins);
        coinsInfo.setText(String.valueOf(updatedCoins));
        gameInfoDetails.setEnergy(100);
        
    	if("no".equalsIgnoreCase(proceed)){
    		endGame();
    	}else if("yes".equalsIgnoreCase(proceed)){
    		timer(timerTime);
    	}
    	if(nextLevel==10){
    		gameInfoDetails.setLevel(9);
    	}
        
    }

    
    /**
     * Method Name : setLabels
     * This is method is used to set the Labels for the Different attributes of the Game
     * 
     */
    public void setLabels() {

        int score = gameInfoDetails.getScore();
        int energy = gameInfoDetails.getEnergy();
        int coins = gameInfoDetails.getCoins();
        int level = gameInfoDetails.getLevel();

        coinsInfo.setText(String.valueOf(coins));

        if (energy <= 100) {
            energyInfo.setText(String.valueOf(energy) + "%");
        } else if (energy > 100) {
            gameInfoDetails.setEnergy(100);
            energyInfo.setText(String.valueOf(100) + "%");
        }

        scoreInfo.setText(String.valueOf(score));
        levelInfo.setText(String.valueOf(level));
        labelProg.setText(String.valueOf(progressCount * 10) + "%");

        options.selectToggle(null);
    }
    /*
     * End of Method
     */
    
    
}

/*
 * End Of file
 */
