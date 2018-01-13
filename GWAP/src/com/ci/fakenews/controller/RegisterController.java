/**
Created By:               P R A S H A N T   G A R  G  |  STUDENT ID : 16201447
Created Date:             21-April-2017
Copyright:                University College Dublin
Subject:				  COLLECTIVE INTELLIGENCE (GWAP Application)
Description:              This is the controller file for Register Window. 
						  All the actions taken from Game Window are handled here.
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
import com.ci.fakenews.bean.UserInfo;
import com.ci.fakenews.sdo.OperationFile;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/*
 * Class RegisterController
 */
public class RegisterController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /*
     * FXML Fields , Image and Label present on the Register Window
     */
    
    @FXML
    private Label registerNotify;

    @FXML
    private Label registerSuccess;

    @FXML
    private TextField name;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField cPassword;

    @FXML
    private ImageView closeRegisterScreen;

    
    /**
   	 * Method Name: [register]
   	 * This method is called when the user clicks on Register button on the Register  Window
   	 * 
   	 */  
    public void register(ActionEvent event) {

        registerNotify.setText("");
        registerSuccess.setText("");

        String enteredUsername = username.getText();
        String enteredPassword = password.getText();
        String enteredCnfrmPassword = cPassword.getText();
        String enteredName = name.getText();


        if (enteredName == null || "".equals(enteredName)) {
            registerNotify.setText("Name can't be left blank");
        } else if (enteredUsername == null || "".equals(enteredUsername)) {
            registerNotify.setText("Username can't be left blank");
        } else if (enteredPassword == null || "".equals(enteredPassword)) {
            registerNotify.setText("Password can't be left blank");
        } else if (enteredCnfrmPassword == null || "".equals(enteredCnfrmPassword)) {
            registerNotify.setText("Confirm the Password");
        } else if (enteredUsername != null && !" ".equals(enteredUsername) && enteredPassword != null
                && !" ".equals(enteredPassword)
                && enteredCnfrmPassword != null && !" ".equals(enteredCnfrmPassword)) {

            if (!enteredCnfrmPassword.equals(enteredPassword)) {
                registerNotify.setText("Passwords do not match");
            } else if (enteredCnfrmPassword.equals(enteredPassword)) {
                UserInfo userDetails = new UserInfo();
                userDetails.setUsername(enteredUsername);
                userDetails.setPassword(enteredPassword);
                userDetails.setName(enteredName);
                String status = "";
                OperationFile cntrlObj = new OperationFile();
                status = cntrlObj.registerUserDetais(userDetails);
                if (status.equals("success")) {
                    registerNotify.setText(" ");
                    name.setText("");
                    username.setText("");
                    password.setText("");
                    cPassword.setText("");
                    registerSuccess.setText("User registered successfully");
                } else if (status.equals("failure")) {
                    registerNotify.setText("Error! Please Try Again");
                } else if (status.equals("invalid")) {
                    registerNotify.setText("User " + enteredUsername + " already exists");
                } else {
                    registerNotify.setText("Error! Please Try Again");
                }
            }
        }
    }
    /**
   	 * End of Method [register]
   	 */

    
    /**
	 * Method Name: [closeWindow]
	 * This method is called when the user clicks on Cross button on the Register  Window
	 * The method closes the Register window 
	 * 
	 * 
	 */    
    public void closeWindow() {
        LoginController.mainRegisterScene.getWindow().hide();
    }
    /**
   	 * End of Method [closeWindow]
   	 */
}
