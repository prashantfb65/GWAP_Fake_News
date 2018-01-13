/**
Created By:               P R A S H A N T   G A R  G  |  STUDENT ID : 16201447
Created Date:             20-April-2017
Copyright:                University College Dublin
Subject:				  COLLECTIVE INTELLIGENCE (GWAP Application)
Description:              Bean Class for Storing and accessing User Info
Version:                  00.00.00.01

Modification history:
----------------------------------------------------------------------------------------------------------------------------
Modified By         Modified Date (dd/mm/yyyy)                Version               Description
---------------------------------------------------------------------------------------------------------------------------
 **/

package com.ci.fakenews.bean;

public class UserGameInfo {

    private String username;		// Will hold the value for User's username
    private String name;			// Will hold the value for User's Name
    private int level;				// Will hold the value for Level at which the user currently is		
    private int score;				// Will hold the value for User's score
    private int coins;				// Will hold the value for User's coins
    private int energy;				// Will hold the value for User's Energy
    private int newsBusted;			// Reserved for Future Use 
    private int wrong;				// Will hold the value of wrong predictions by User
    private int correct;			// Will hold the value of correct predictions by User
    private int pass;				// Will hold the value of pass predictions by User
    private double reputation;		// Will hold the value of User's Reputation
    private int continousPass;		// Reserved for Future Use
    private int continousCorrect;	// Reserved for Future Use
    private int continousWrong;		// Reserved for Future Use
    private String Status;			// Status of whether the User is Present or not
    
    
    /**
	 * Getter Setter 
	 *
	 */
    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    public int getCoins() {
        return coins;
    }

    public int getEnergy() {
        return energy;
    }

    public int getNewsBusted() {
        return newsBusted;
    }

    public int getWrong() {
        return wrong;
    }

    public int getCorrect() {
        return correct;
    }

    public int getPass() {
        return pass;
    }

    public double getReputation() {
        return reputation;
    }

    public String getStatus() {
        return Status;
    }

    public int getContinousPass() {
        return continousPass;
    }

    public int getContinousCorrect() {
        return continousCorrect;
    }

    public int getContinousWrong() {
        return continousWrong;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setNewsBusted(int newsBusted) {
        this.newsBusted = newsBusted;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public void setReputation(double reputation) {
        this.reputation = reputation;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public void setContinousPass(int continousPass) {
        this.continousPass = continousPass;
    }

    public void setContinousCorrect(int continousCorrect) {
        this.continousCorrect = continousCorrect;
    }

    public void setContinousWrong(int continousWrong) {
        this.continousWrong = continousWrong;
    }
	
    /**
	 * End
	 */

}

/**
 * End of File
 */