/**
Created By:               P R A S H A N T   G A R  G  |  STUDENT ID : 16201447
Created Date:             20-April-2017
Copyright:                University College Dublin
Subject:				  COLLECTIVE INTELLIGENCE (GWAP Application)
Description:              This is the controller file for Login Window. All the actions taken from Login Window are 
						  handled here.
Version:                  00.00.00.01

Modification history:
----------------------------------------------------------------------------------------------------------------------------
Modified By         Modified Date (dd/mm/yyyy)                Version               Description
---------------------------------------------------------------------------------------------------------------------------
 **/
package com.ci.fakenews.controller;


/*
 * Inclusion of Header Files
 */

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ci.fakenews.Main;
import com.ci.fakenews.bean.UserInfo;
import com.ci.fakenews.sdo.OperationFile;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



/*
 * Class LoginController
 */
public class LoginController implements Initializable {
	
	/*
	 * The static variable mainRegisterScene is declared to keep 
	 * the reference of the Register Page 
	 */	
    public static Scene mainRegisterScene;

   
    /*
     * FXML Fields present on the Login Window
     * Username, Password, Message Label
     */
    @FXML
    private TextField username;

    @FXML
    private TextField loginPass;
    
    @FXML
    private Label loginNotify;

    
    /**
     * Initializes the controller class.
     * This is the first method called whenever the 
     * Controller class is to be initialized
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    /**
	 * Method Name: [login]
	 * This method is called when the user clicks on Login button
	 * If the Login is successful it launches the Startup Screen for the Game
	 * @param ActionEvent 
	 * 
	 */
    public void login(ActionEvent event) {
    	
    	//Taking Username and Password from the Login Window
        String enteredUsername = username.getText();
        String enteredPassword = loginPass.getText();

        /*
         * Validation for blank Username or Password from the Window
         */
        if (enteredUsername == null || "".equals(enteredUsername)) {
            loginNotify.setContentDisplay(ContentDisplay.CENTER);
            loginNotify.setText("Username can't be left blank");
        }else if (enteredPassword == null || "".equals(enteredPassword)) {
            loginNotify.setText("Password can't be left blank");
        }
        
        /*
         * Validation for valid username and password
         */
        else if (enteredUsername != null && !" ".equals(enteredUsername)
                && enteredPassword != null && !" ".equals(enteredPassword)) {

        	/*
        	 * Creating UserInfo bean object to store Username, Password from Login Window
        	 */
            UserInfo userDetails = new UserInfo();
            userDetails.setUsername(enteredUsername);
            userDetails.setPassword(enteredPassword);
            String status = "";
            
            
            /*
             * Operation:
             * Creating an object of OperationFile Service Data Object
             * and calling method checkUserStatus via it
             * 
             * status is returned success if successfully Credentials are validated,
             * failure if password is invalid and invalid is the user doesn't exists
             * 
             */
            OperationFile cntrlObj = new OperationFile();
            try {
                status = cntrlObj.checkUserStatus(userDetails);
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, "Database Error! Could not validate the Username, Password");
                loginNotify.setText("Error! Please Try Again");
            }
            if (status.equalsIgnoreCase("success")) {
                loginNotify.setText(" ");
                Stage startupScreen = new Stage();
                gameStartup(startupScreen);
            } else if (status.equalsIgnoreCase("failure")) {
                loginNotify.setText("Incorrect Password");
            } else if (status.equalsIgnoreCase("invalid")) {
                loginNotify.setText("User doesn't exist");
            } else{
                loginNotify.setText("Error! Please Try Again");
            }
            /*
             * Operation Performed
             */
        }
    }
    /**
	 * End of Method [readRatingCSVFile]
	 */
    
    
    
    /**
	 * Method Name: [callRegister]
	 * This method is called when the user clicks on Register button
	 * The method launches the Register Window
	 * @param ActionEvent 
	 * 
	 */    
    public void callRegister(ActionEvent event) {
    	
    	//Creating a new Stage
        Stage register = new Stage();
        
        //Creating a base class variable root
        Parent root;
        try {
        	
        	//Reading and Loading the Register FXML file
            root = FXMLLoader.load(getClass().getResource("/com/ci/fakenews/ui/Register.fxml"));
            
            //Creating a Scene using the Register FXML
            Scene scene = new Scene(root);
            scene.setFill(null);
            
            /*
             * Assigning the Scene Reference to the variable mainRegisterScene
             * This reference is used to close the Register Window from further 
             * windows
             */
            mainRegisterScene = scene;
            
            /*
             * Setting the scene for the stage and 
             * disabling its decorations e.g. close,
             * minimize, maximize button
             */
            register.setScene(scene);
            register.initStyle(StageStyle.TRANSPARENT);
            
            /*
             * Enabling the display of the stage
             */
            register.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, "Problem while Loading the Register FXML");
        }
    }
    /**
	 * End of Method [callRegister]
	 */

    

    /**
	 * Method Name: [gameStartup]
	 * This method is called when the user credentials are validated
	 * after clicking the Login Button.
	 * The method launches the Game Startup Screen Window
	 * @param Stage 
	 * 
	 */     
    public void gameStartup(Stage startupScreen) {
        try {
        	
        	//Reading and Loading the StartupScreen FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ci/fakenews/ui/StartupScreen.fxml"));
            
            //Creating a new Stage
            Stage stage = new Stage();
            
            //Creating a Scene using the StartupScreen FXML
            Scene scene = new Scene((Pane) loader.load());
            scene.setFill(null);
            
            
            /*
             * Assigning the Scene Reference to the variable mainRegisterScene
             * This reference is used to close the StartupScreen Window from further 
             * windows
             */
            mainRegisterScene = scene;
            
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
            controller.initData(username.getText());
            
            /*
             * Enabling the display of the stage
             */
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, "Problem while Loading the Startup FXML");
        }
    }
    /**
	 * End of Method [gameStartup]
	 */
    
    
    /**
	 * Method Name: [closeWindow]
	 * This method is called when the user clicks on Cross button on the Login  Window
	 * The method closes the Login window and Program Exits..
	 * Other windows associated with the Application are also exited
	 * @param Stage 
	 * 
	 */       
    public void closeWindow() {
    	
    	/*
    	 * Prompt to ask if the User wants to exit the Game
    	 */
        Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to exit the Game ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        
        if (alert.getResult() == ButtonType.YES) {
            Main.mainLoginScene.getWindow().hide(); //Hiding the Login Window using reference created in Main.java
            System.exit(0);
        }
    }
    /**
	 * End of Method [closeWindow]
	 */

}
/**
 * End of File [LoginController.java]
 */