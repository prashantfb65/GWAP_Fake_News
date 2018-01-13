/**
Created By:               P R A S H A N T   G A R  G  |  STUDENT ID : 16201447
Created Date:             20-April-2017
Copyright:                University College Dublin
Subject:				  COLLECTIVE INTELLIGENCE (GWAP Application)
Description:              Main File for the GWAP Application - The Program Starts from here
Version:                  00.00.00.01

Modification history:
----------------------------------------------------------------------------------------------------------------------------
Modified By         Modified Date (dd/mm/yyyy)                Version               Description
---------------------------------------------------------------------------------------------------------------------------
 **/
package com.ci.fakenews;


/*
 * Inclusion of Header Files
 */
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * The Main Class Extends the Application Class
 * which is an entry point to the Java FX Applications
 *
 */
public class Main extends Application {

	/*
	 * The static variable mainLoginScene is declared to keep 
	 * the reference of the Login Page 
	 */
    public static Scene mainLoginScene;		

    
    /**
	 * Method Name: [main]
	 * Calls the start(javafx.stage.Stage) method
	 * @param command line arguments
	 * 
	 */
    public static void main(String[] args) {
        launch(args);
    }
    /**
	 * End of Method [main]
	 */
    
    
    /**
	 * Method Name: [start]
	 * Java launcher loads and initializes the Login Screen using its fxml
	 * @param Stage reference
	 * 
	 */
    @Override
    public void start(Stage stage) {
        
    	//Creating a base class variable root
    	Parent root;
    	
        try {
        	
        	//Reading and Loading the Login FXML file
            root = FXMLLoader.load(getClass().getResource("ui/Login.fxml"));
            
            //Creating a Scene using the Login FXML
            Scene scene = new Scene(root);
            scene.setFill(null);
            
            /*
             * Assigning the Scene Reference to the variable mainLoginScence
             * This reference is used to close the Login Window from further 
             * windows
             */
            mainLoginScene = scene;
            
            /*
             * Setting the scene for the stage and 
             * disabling its decorations e.g. close,
             * minimize, maximize button
             */
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            
            /*
             * Enabling the display of the stage
             */
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Problem while Loading the Login FXML ", ex);
        }
        
       
    } /**
	 * End of Method [start]
	 */

}   
/**
 * End of File [Main.java]
 */
   

