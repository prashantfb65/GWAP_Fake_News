/**
Created By:               P R A S H A N T   G A R  G  |  STUDENT ID : 16201447
Created Date:             20-April-2017
Copyright:                University College Dublin
Subject:				  COLLECTIVE INTELLIGENCE (GWAP Application)
Description:              This is the controller file for Startup Screen Window. 
						  All the actions taken from Game Startup Window are 
						  handled here.
Version:                  00.00.00.01

Modification history:
----------------------------------------------------------------------------------------------------------------------------
Modified By         Modified Date (dd/mm/yyyy)                Version               Description
---------------------------------------------------------------------------------------------------------------------------
 **/

package com.ci.fakenews.controller;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Inclusion of Header Files
 */
import com.ci.fakenews.bean.UserGameInfo;
import com.ci.fakenews.sdo.OperationFile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


/*
 * Class StartupScreenController
 */
public class StartupScreenController implements Initializable {

	/*
	 * FXML Labels
	 */
	
    @FXML
    private Label name;

    @FXML
    private Label rank;

    @FXML
    private Label energyLabel;

    @FXML
    private Label coins;

    @FXML
    private Label rankOne;

    @FXML
    private Label rankTwo;

    @FXML
    private Label rankThree;

    @FXML
    private Label rankFour;
    
    
    /*
	 * The static variable mainplayGameScene is declared to keep 
	 * the reference of the Startup Page 
	 */	
    public static Scene mainplayGameScene;
    
    
    UserGameInfo userInfo = null;	// Will hold the Logged in User Details
    OperationFile operation = new OperationFile();	//Will be used to perform database operations
    
    /*
     * Creating Audio Media for the Startup Page
     */
    
    URL resource = getClass().getResource("/music/startup.mp3");
    MediaPlayer media = new MediaPlayer(new Media(resource.toString()));

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    
    /**
	 * Method Name: [login]
	 * This method is called when the user is validated after clicking the
	 * login button. 
	 * @param Username of the User Logged in 
	 * 
	 */
    public void initData(String userName) {
    	
    	//As soon as the user Logs in ..The music method is called to start the media for the window
        music();

        //The details of the Logged in User is fetched and stored in UserGameInfo bean
        if (userName != null && !"".equalsIgnoreCase(userName)) {
            try {
                userInfo = operation.fetchUserGameDetails(userName); //Method Hit
            } catch (SQLException ex) {
                Logger.getLogger(StartupScreenController.class.getName()).log(Level.SEVERE, "Error while fetching User Info in Startup Screen");
            }
        }
        
        //Once the user details are fetched the labels are are populated with those details
        if (userInfo != null) {
            name.setText(userInfo.getName().toUpperCase());		//Name Label Populated
            coins.setText(String.valueOf(userInfo.getCoins()));	//Coins Label Populated
            int energy = userInfo.getEnergy();
            if (energy > 100) {
                energy = 100;
            } else if (energy <= 0) {
                energy = 0;
            }
            energyLabel.setText(String.valueOf(energy) + "%");	//Energy Label Populated 
        }
        
      /*
       * Once the user details are fetched the list of all the users is 
       * fetched in list of UserGameInfo Bean and populated in the Leaderboard 
       * list
      */
        try {
        	
            List<UserGameInfo> userList = operation.fetchUserDetails();	//Method Hit

            if (userList != null) {
                int length = userList.size();

                /*
                 * Ranks Label Setting start
                 */
                if (length >= 4) {
                    rankOne.setText(userList.get(0).getName().toUpperCase());
                    rankTwo.setText(userList.get(1).getName().toUpperCase());
                    rankThree.setText(userList.get(2).getName().toUpperCase());
                    rankFour.setText(userList.get(3).getName().toUpperCase());
                } else if (length == 1) {
                    rankOne.setText(userList.get(0).getName().toUpperCase());
                } else if (length == 2) {
                    rankOne.setText(userList.get(0).getName().toUpperCase());
                    rankTwo.setText(userList.get(1).getName().toUpperCase());
                } else if (length == 3) {
                    rankOne.setText(userList.get(0).getName().toUpperCase());
                    rankTwo.setText(userList.get(1).getName().toUpperCase());
                    rankThree.setText(userList.get(2).getName().toUpperCase());
                }
                /*
                 * end
                 */

                /*
                 * Rank Evaluation for the User
                 */
                int rankEval = 0;
                for (UserGameInfo order : userList) {
                    rankEval++;
                    if (userName.equalsIgnoreCase(order.getUsername())) {
                        break;
                    }
                }
                
                rank.setText("#" + String.valueOf(rankEval));	//Populating User Rank

            }

        } catch (SQLException ex) {
            Logger.getLogger(StartupScreenController.class.getName()).log(Level.SEVERE, "Error while Retrieving user details");
        }

    }
    /*
     * End of Method - initData
     */

    /**
     * Method - PlayGame
     * This method is called when the user click on the PLAY GAME Label
     * If the energy is more than zero the user is allowed to proceed with Game Play
     * Otherwise the player is notified to wait till the energy reloads
     */
    public void playGame() {
    	
    	
        int energy = userInfo.getEnergy(); //Fetching the User's Energy

        /*
         * If the energy is less than zero the user is notified to wait
         */
        if (energy <= 0) {
            Alert alert = new Alert(AlertType.INFORMATION, "Insufficient Energy! Wait till it gets Refilled");
            alert.showAndWait();
        }
        /*
         * If the sufficient Energy is there, the music media for Game Startup Screen is ended first
         */
        else {
            endMusic();	//Media stopped
            
            try {
            	//Reading and Loading the PlayGame FXML file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ci/fakenews/ui/PlayGame.fxml"));

                //Creating a new Stage
                Stage stage = new Stage();
                
                //Creating a Scene using the StartupScreen FXML
                Scene scene = new Scene((Pane) loader.load());
                scene.setFill(null);
                
                /*
                 * Assigning the Scene Reference to the variable mainStartupScene
                 * This reference is used to close the PlayGame Window from other
                 * windows
                 */
                mainplayGameScene = scene;
                
                
                /*
                 * Setting the scene for the stage and 
                 * disabling its decorations e.g. close,
                 * minimize, maximize button
                 */
                stage.setScene(scene);
                stage.initStyle(StageStyle.TRANSPARENT);

                /*
                 * Calling the Method initData of the PlayGame Controller
                 */
                PlayGameController controller = loader.<PlayGameController>getController();
                controller.initData(userInfo.getUsername());
                
                /*
                 * Enabling the display of the stage
                 */
                stage.show();
                
                /*
                 * Hiding the Startup Window
                 */
                LoginController.mainRegisterScene.getWindow().hide();
            } catch (IOException ex) {
                Logger.getLogger(StartupScreenController.class.getName()).log(Level.SEVERE, "Problem while Loading PlayGame Window");
            }
        }
    }
    /*
     * End of Method - playGame
     */
    
    /**
     * Method Name - useCoins
     * This method will enable the user to Use the coins earned
     * by him while playing the game
     * @param event
     */

    public void useCoins(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION, "Functionality Coming Soon.....");
        alert.showAndWait();
    }
    /*
     * End of Method - useCoins
     */
    
    /**
     * Method Name - fetchInstructions
     * This method will enable the user to see the instructions for the
     * game
     * 
     * @param event
     */
    
    public void fetchInstructions(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION, "News Buster\n\n"
        		+ "\nThe user will be shown the News Headline along with the News Image\n"
        		+ "There will be four options given to the user and user is supposed to classify the news in those categories\n"
        		+ "The user has the choice to skip the question by pressing the Skip button\n"
        		+ "The user must complete every Level/Round before the Energy runs out\n"
        		+ "The Energy is reloaded in every Level\n"
        		+ "Every correct answer leads to increase in Score, Ranking and Level progress\n"
        		+ "Every Pass and Incorrect answer leads to penalty in Energy loss\n"
        		+ "Continuous Correct answer leads to gaining Coins,  Energy\n"
        		+ "Continuous Skips and Incorrect answer lead to significant loss in Energy"
        		+ "If the energy becomes zero, the player will have to wait till it gets refilled"
        		+ "More accuracy will also allow user to attempt Bonus question after Each Round \n\n ");
        alert.showAndWait();
    }
    /*
     * End of Method - fetchInstructions
     */

    /**
     * Method Name - music
     * This method will play the media on the Window in a loop
     */
    public void music() {
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
        media.stop();
    }
    /*
     * End of Method - endMusic
     */
    
    /**
     * Method Name - closeWindow
     * This method is called when the user clicks on Close Icon
     * The method closes the Startup Screen Window and ends the Music Media as 
     * well
     */
    public void closeWindow() {
    	media.stop();
    	try{
    		if(LoginController.mainRegisterScene!=null){
        		LoginController.mainRegisterScene.getWindow().hide();
        	}
        	if(PlayGameController.mainStartupScene!=null){
        		PlayGameController.mainStartupScene.getWindow().hide();
        	}
    	}catch(Exception e){
    		Logger.getLogger(StartupScreenController.class.getName()).log(Level.SEVERE, "Problem while Closing PlayGame Window");
    	}
    }
    /*
     * End of Method - closeWindow
     */

}
/*
 * End of File
 * 
*/