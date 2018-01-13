/**
Created By:               P R A S H A N T   G A R  G  |  STUDENT ID : 16201447
Created Date:             20-April-2017
Copyright:                University College Dublin
Subject:				  COLLECTIVE INTELLIGENCE (GWAP Application)
Description:              Pojo for User Details
Version:                  00.00.00.01

Modification history:
----------------------------------------------------------------------------------------------------------------------------
Modified By         Modified Date (dd/mm/yyyy)                Version               Description
---------------------------------------------------------------------------------------------------------------------------
 **/
package com.ci.fakenews.bean;

public class UserInfo {

	private String username;	// Will hold the value for User's username
	private String password;	// Will hold the value for User's pswd
	private String name;		// Will hold the value for User's name


	/**
	 * Getter Setter 
	 *
	 */
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * End
	 */

}

/**
 * End of File
 */
