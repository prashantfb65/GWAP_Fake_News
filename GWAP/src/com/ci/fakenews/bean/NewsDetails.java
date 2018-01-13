/**
Created By:               P R A S H A N T   G A R  G  |  STUDENT ID : 16201447
Created Date:             20-April-2017
Copyright:                University College Dublin
Subject:				  COLLECTIVE INTELLIGENCE (GWAP Application)
Description:              Bean Class for Storing and accessing News Data
Version:                  00.00.00.01

Modification history:
----------------------------------------------------------------------------------------------------------------------------
Modified By         Modified Date (dd/mm/yyyy)                Version               Description
---------------------------------------------------------------------------------------------------------------------------
 **/

package com.ci.fakenews.bean;

public class NewsDetails {

	private int newsID;			// Will hold the value for News Id
	private String headline;	// Will hold the value for News Headline
	private String source;		// Will hold the value for News Sources
	private String imageURL;	// Will hold the value of News Headline Image
	private String type;		// Will hold the value for News type - bias, satire, truth, empty, conspiracy
	private int biasCount;		// Will hold the value for assignment of bias count to the News Item by users
	private int conspiracyCount;// Will hold the value for assignment of conspiracy count to the News Item by users
	private int deceptionCount;	// Reserved for Future Use 
	private int passCount;		// Will hold the value of number of time this news was passed by the user
	private int satireCount;	// Will hold the value for assignment of satire count to the News Item by users 
	private int trueCount;		// Will hold the value for assignment of genuine count to the News Item by users  
	private String status;		// Will hold the value for the news

	
	/**
	 * Getter Setter 
	 *
	 */	
	public int getNewsID() {
		return newsID;
	}

	public String getHeadline() {
		return headline;
	}

	public String getSource() {
		return source;
	}

	public String getImageURL() {
		return imageURL;
	}

	public String getType() {
		return type;
	}

	public int getBiasCount() {
		return biasCount;
	}

	public int getConspiracyCount() {
		return conspiracyCount;
	}

	public int getDeceptionCount() {
		return deceptionCount;
	}

	public int getPassCount() {
		return passCount;
	}

	public int getSatireCount() {
		return satireCount;
	}

	public int getTrueCount() {
		return trueCount;
	}

	public String getStatus() {
		return status;
	}

	public void setNewsID(int newsID) {
		this.newsID = newsID;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setBiasCount(int biasCount) {
		this.biasCount = biasCount;
	}

	public void setConspiracyCount(int conspiracyCount) {
		this.conspiracyCount = conspiracyCount;
	}

	public void setDeceptionCount(int deceptionCount) {
		this.deceptionCount = deceptionCount;
	}

	public void setPassCount(int passCount) {
		this.passCount = passCount;
	}

	public void setSatireCount(int satireCount) {
		this.satireCount = satireCount;
	}

	public void setTrueCount(int trueCount) {
		this.trueCount = trueCount;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "NewsDetails{" + "newsID=" + newsID + ", headline=" + headline + ", source=" + source + ", imageURL=" + imageURL + ", type=" + type + ", biasCount=" + biasCount + ", conspiracyCount=" + conspiracyCount + ", deceptionCount=" + deceptionCount + ", passCount=" + passCount + ", satireCount=" + satireCount + ", trueCount=" + trueCount + ", status=" + status + '}';
	}
	
	/**
	 * End
	 */

}

/**
 * End of File
 */