/**
Created By:               P R A S H A N T   G A R  G  |  STUDENT ID : 16201447
Created Date:             20-April-2017
Copyright:                University College Dublin
Subject:				  COLLECTIVE INTELLIGENCE (GWAP Application)
Description:              Service Data Objects - Operation File
						  This file stores the methods which involve operation
						  with database.
Version:                  00.00.00.01

Modification history:
----------------------------------------------------------------------------------------------------------------------------
Modified By         Modified Date (dd/mm/yyyy)                Version               Description
---------------------------------------------------------------------------------------------------------------------------
 **/
package com.ci.fakenews.sdo;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Inclusion of Header Files
 */
import com.ci.fakenews.bean.NewsDetails;
import com.ci.fakenews.bean.UserGameInfo;
import com.ci.fakenews.bean.UserInfo;
import com.jdbc.connection.ConnectionFactory;

/*
 * Class OperationFile
 */
public class OperationFile {

	/**
	 * Method Name - checkUserStatus
	 * Checks whether the Eneter Username and Password in Login window
	 * are valid
	 * 
	 * @param userDetails [UserInfo bean Consisting User's username and password]
	 * @return status - Success, Failure, Invalid, Exception
	 * @throws SQLException
	 */
    public String checkUserStatus(UserInfo userDetails) throws SQLException {
    	
        String status = "failure";	// Initializing status as failure
        ConnectionFactory cf = new ConnectionFactory();		//Creating Connection class object
        String userName = userDetails.getUsername(); 		//Fetching Username from the UserInfo Bean
        String userPassword = userDetails.getPassword();	//Fetching Password from the UserInfo Bean
        
        
        /*
         * Using Prepared statement, the user is checked to present in the Users table,
         * The resultset is stored in the rs. 
         */
        PreparedStatement pstmt = null;						
        ResultSet rs = null;
        try {
            Connection conn = cf.getConnection();	//Getting the connection
            pstmt = conn.prepareStatement("select * from Users u where u.username = ? "); //Preparing the Query
            pstmt.setString(1, userName);	//Setting the parameter as username
            rs = pstmt.executeQuery();		//Executing the Query prepared
            int rowcount = 0;				//Initializing number of fetched records to zero
            
            /*
             * Operation:
             * If no record is fetched
             * "invalid" is returned indicating User doesn't exists
             * "success" is returned if username and password provided
             * are same as stored in the database, else "failure" is returned
             * 
             * in case of exception, "exception" string is returned
             * 
             */
            if (rs.last()) {
                rowcount = rs.getRow();
                rs.beforeFirst(); 
            }
            if (rowcount < 1) {
                return "invalid";
            }
            while (rs.next()) {
                if (userPassword.equals(rs.getString("password"))) {
                    return "success";
                } else {
                    return "failure";
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(OperationFile.class.getName()).log(Level.SEVERE, "Some error while verifying user details");
            return "exception";
        } finally {
            cf.releaseConnection();
        }
        
        /*
         * End Operation
         */
        return status;
    }
    
    /*
     * End of Method [checkUserStatus]
     */

    
    /**
	 * Method Name - registerUserDetais
	 * Enters user's name, username and password from Register Screen to Database
	 * The user can sign in to the Game only if he is Registered
	 * 
	 * @param userDetails [UserInfo bean Consisting User's username and password]
	 * @return status - Success, Failure, Invalid, Exception
	 */
    public String registerUserDetais(UserInfo userDetails) {

    	String status = "failure";	// Initializing status as failure
        ConnectionFactory cf = new ConnectionFactory();		//Creating Connection class object
        String userName = userDetails.getUsername(); 		//Fetching Username from the UserInfo Bean
        String userPassword = userDetails.getPassword();	//Fetching Password from the UserInfo Bean
        String name = userDetails.getName();				//Fetching Name from the UserInfo Bean

        /*
         * Operation:
         * 
         * The First Prepared statement checks if the User with
         * the same user name is present in the system. If the user is already present in
         * the system "invalid" string is returned
         * 
         * If the user is not present previously, the second Prepared statement inserts
         * the username, password and name in Users Table
         * 
         */
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;

        int inserStat = 0;

        try {
            Connection conn = cf.getConnection();

            pstmt1 = conn.prepareStatement("select * from Users u where u.username = ? ");
            pstmt1.setString(1, userName);
            
            rs = pstmt1.executeQuery(); //Executing Query 1
            
            int rowcount = 0;
            if (rs.last()) {
                rowcount = rs.getRow();
                rs.beforeFirst(); 
            }
            if (rowcount > 0) {
                return "invalid";
            }else {
                pstmt2 = conn.prepareStatement("insert into Users (username, password, name ) values (?,?,?)");
                pstmt2.setString(1, userName);
                pstmt2.setString(2, userPassword);
                pstmt2.setString(3, name);
                
                inserStat = pstmt2.executeUpdate(); //Executing Query 2
                
                if (inserStat > 0) {
                    return "success";
                } else if (inserStat == 0) {
                    return "failure";
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(OperationFile.class.getName()).log(Level.SEVERE, "Error while Registering the User");
            return "exception";
        }
        return status;
    }
    /*
     * End of Method [registerUserDetais]
     */

    
    /**
	 * Method Name - fetchNews
	 * Fetches all News Metadata Info for every News which is Active
	 * @return List of News Info stored in NewsDetails Bean
	 * @throws SQLException
	 */ 
    public List<NewsDetails> fetchNews() throws SQLException {

        List<NewsDetails> news = new ArrayList<>();		//Initializing the NewDetails Bean
        ConnectionFactory cf = new ConnectionFactory();	//Creating ConnectionFactory object to get Connection to Database
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Connection conn = cf.getConnection();  //Getting Connection 
            String query = "select * from news_data u where u.status = ?";	//Query Creation to Fews News Items which are active
            pstmt = conn.prepareStatement(query);	//Creating the Statement
            
            //Setting Parameters to PreparedStament 
            pstmt.setString(1, "A");
            
            rs = pstmt.executeQuery();	//Executing the Query
            
            /*
             * Iterating over Resultset and storing each line in bean of NewsDetaiks
             * and adding that Bean to the List 
             */
            while (rs.next()) {
                NewsDetails detail = new NewsDetails();
                detail.setNewsID(rs.getInt("News_ID"));
                detail.setHeadline(rs.getString("Headline"));
                detail.setSource(rs.getString("Source"));
                detail.setImageURL(rs.getString("Image_URL"));
                detail.setType(rs.getString("Type"));
                detail.setBiasCount(rs.getInt("Bias_Count"));
                detail.setConspiracyCount(rs.getInt("Conspiracy_Count"));
                detail.setDeceptionCount(rs.getInt("Deception_Count"));
                detail.setPassCount(rs.getInt("Pass_Count"));
                detail.setSatireCount(rs.getInt("Satire_Count"));
                detail.setTrueCount(rs.getInt("True_Count"));
                detail.setStatus(rs.getString("Status"));
                news.add(detail);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OperationFile.class.getName()).log(Level.SEVERE, "Some error while fetching news list");
        } finally {
            cf.releaseConnection();
        }
        return news;
    }
    /*
     * End of Method [fetchNews]
     */

    
    /**
	 * Method Name - fetchUserGameDetails
	 * Fetches Game Details for the User like Energy, Score, Coins, etc.
     * @param userName
     * @return UserGameInfo bean
     * @throws SQLException
	 */ 
    
    public UserGameInfo fetchUserGameDetails(String userName) throws SQLException {

        UserGameInfo detail = new UserGameInfo();	//Initializing the Bean
        ConnectionFactory cf = new ConnectionFactory();	// Creating object of ConnectionFactory class to get Connection
        
        
        /*
         * Creating prepared statment to find the details of the user 
         * and setting it to the bean of UserGameInfo
         */
        PreparedStatement pstmt = null;		
        ResultSet rs = null;
        try {
            Connection conn = cf.getConnection();		//Getting Connection 
            String query = "select * from user_info u where u.status = ? and u.username=?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "A");
            pstmt.setString(2, userName);
            rs = pstmt.executeQuery(); 					//Executing the Query
            
            /*
             * Iterating over ResultSet and storing retrieved in
             * the bean of UserGameInfo
             */
            while (rs.next()) {
                detail.setUsername(rs.getString("username"));
                detail.setName(rs.getString("name"));
                detail.setLevel(rs.getInt("level"));
                detail.setScore(rs.getInt("score"));
                detail.setCoins(rs.getInt("coins"));
                detail.setEnergy(rs.getInt("energy"));
                detail.setNewsBusted(rs.getInt("news_busted"));
                detail.setWrong(rs.getInt("wrong"));
                detail.setCorrect(rs.getInt("correct"));
                detail.setPass(rs.getInt("pass"));
                detail.setContinousWrong(rs.getInt("cont_wrong_count"));
                detail.setContinousCorrect(rs.getInt("cont_correct_count"));
                detail.setContinousPass(rs.getInt("cont_pass_count"));
                detail.setReputation(rs.getDouble("reputation"));
                detail.setStatus(rs.getString("Status"));

            }

        } catch (SQLException ex) {
            Logger.getLogger(OperationFile.class.getName()).log(Level.SEVERE, "Some error while fetching user game details");
        } finally {
            cf.releaseConnection();
        }
        return detail;
    }
    /*
     * End of Method - fetchUserGameDetails
     */
    
    
    /**
	 * Method Name - updateUserGameDetails
	 * Updates the User's Game Detail
     * @param UserGameInfo bean
     * @return boolean status of whether the user game info was updated or not
     * @throws SQLException
	 */ 
    public boolean updateUserGameDetails(UserGameInfo detail) throws SQLException {
        
    	boolean gameSaved = false;		//Status of Updation of Info
        ConnectionFactory cf = new ConnectionFactory();	
        PreparedStatement pstmt = null;
        
        /*
         * Operation
         * Creating Update Query, Setting Parameters and then return the status of Update
         */
        try {
            Connection conn = cf.getConnection();

            String query = "UPDATE user_info SET name = ? ,level = ? ,score = ? ,coins = ?,"
                    + "energy = ? ,news_busted = ? ,wrong = ?,correct = ? ,pass = ? ,"
                    + "reputation = ? ,cont_wrong_count =?,cont_correct_count = ?,"
                    + "cont_pass_count =? ,Status =? WHERE username = ?";

            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, detail.getName());
            pstmt.setInt(2, detail.getLevel());
            pstmt.setInt(3, detail.getScore());
            pstmt.setInt(4, detail.getCoins());
            pstmt.setInt(5, detail.getEnergy());
            pstmt.setInt(6, detail.getNewsBusted());
            pstmt.setInt(7, detail.getWrong());
            pstmt.setInt(8, detail.getCorrect());
            pstmt.setInt(9, detail.getPass());
            pstmt.setDouble(10, detail.getReputation());
            pstmt.setInt(11, detail.getContinousWrong());
            pstmt.setInt(12, detail.getContinousCorrect());
            pstmt.setInt(13, detail.getContinousPass());
            pstmt.setString(14, "A");
            pstmt.setString(15, detail.getUsername());

            int updatedRows = pstmt.executeUpdate();

            if (updatedRows > 0) {
                gameSaved = true;
            }
            /*
             * Operation Complete
             */

        } catch (SQLException ex) {
            Logger.getLogger(OperationFile.class.getName()).log(Level.SEVERE, "Some error while updating user game details");
            return false;
        } finally {
            cf.releaseConnection();
        }
        return gameSaved;
    }
    /*
     * End of Method - fetchUserGameDetails
     */

    
    /**
	 * Method Name - updateNewsData
	 * Updates the News Details while the Reputated Players are playing the Game
	 * This will change the type of existing categorized news and also 
	 * assign categories to the new news
	 * 
     * @param NewsDetails bean
     * @return boolean status of whether the news was updated or not
     * @throws SQLException
	 */ 
    
    public boolean updateNewsData(NewsDetails detail) throws SQLException {
        
    	boolean newsSaved = false;
    	ConnectionFactory cf = new ConnectionFactory();		//Creating Connection class object
        PreparedStatement pstmt = null;
        
        try {
            Connection conn = cf.getConnection();	//Getting Connection

            String query = "UPDATE news_data SET type=?, bias_count=? ,conspiracy_count=? , pass_count=? ,satire_count =? ,true_count =? ,Status =? WHERE news_id = ?";

            pstmt = conn.prepareStatement(query);	//Preparing the Query
            pstmt.setString(1, detail.getType());
            pstmt.setInt(2, detail.getBiasCount());
            pstmt.setInt(3, detail.getConspiracyCount());
            pstmt.setInt(4, detail.getPassCount());
            pstmt.setInt(5, detail.getSatireCount());
            pstmt.setInt(6, detail.getTrueCount());
            pstmt.setString(7, detail.getStatus());
            pstmt.setInt(8, detail.getNewsID());

            int updatedRows = pstmt.executeUpdate(); //Executing Update Query

            if (updatedRows > 0) {
                newsSaved = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(OperationFile.class.getName()).log(Level.SEVERE, "Some error while updating news data" + ex);
            return false;
        } finally {
            cf.releaseConnection();
        }
        return newsSaved;
    }
    /*
     * End of Method - updateNewsData
     */

    
    
    /**
	 * Method Name - fetchUserDetails
	 * Fetches the Users Detail to display the Leaderboard on the Game Startup Window
	 * 
     * @return List of all Users with their details
     * @throws SQLException
     */
    public List<UserGameInfo> fetchUserDetails() throws SQLException {
        
    	List<UserGameInfo> details = new ArrayList<>();		//List for storing Users Game Details
        ConnectionFactory cf = new ConnectionFactory();		//Creating ConnectionFactory Object
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            Connection conn = cf.getConnection();			//Getting the Connection
            String query = "select * from user_info u where u.status = ? order by score desc";	//Query for extracting Game details in descending order
            pstmt = conn.prepareStatement(query); //Preparing the Query
            pstmt.setString(1, "A");
            
            rs = pstmt.executeQuery();		//Executing the Query
            
            
            /*
             * Iterating over ResultSet and storing each line in bean of UserGameInfo
             * and adding that Bean to the List 
             */
            while (rs.next()) {

                UserGameInfo detail = new UserGameInfo();
                detail.setUsername(rs.getString("username"));
                detail.setName(rs.getString("name"));
                detail.setLevel(rs.getInt("level"));
                detail.setScore(rs.getInt("score"));
                detail.setCoins(rs.getInt("coins"));
                detail.setEnergy(rs.getInt("energy"));
                detail.setNewsBusted(rs.getInt("news_busted"));
                detail.setWrong(rs.getInt("wrong"));
                detail.setCorrect(rs.getInt("correct"));
                detail.setPass(rs.getInt("pass"));
                detail.setContinousWrong(rs.getInt("cont_wrong_count"));
                detail.setContinousCorrect(rs.getInt("cont_correct_count"));
                detail.setContinousPass(rs.getInt("cont_pass_count"));
                detail.setReputation(rs.getDouble("reputation"));
                detail.setStatus(rs.getString("Status"));

                details.add(detail);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OperationFile.class.getName()).log(Level.SEVERE, "Some error while fetching user ranks");
        } finally {
            cf.releaseConnection();
        }
        return details;
    }
    /*
     * End of Method - fetchUserDetails
     */
    

    /**
     * Method Name - getSources
     * Fetches the  News Sources List
     * @return List of Distinct News Sources
     * @throws SQLException
     */
    
    public List<String> getSources() throws SQLException {
        
    	List<String> sources = new ArrayList<>();	//List for Storing News Sources
        ConnectionFactory cf = new ConnectionFactory();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            Connection conn = cf.getConnection(); //Getting Connection
            String query = "select distinct source from news_data";
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            
            /*
             * Iterating over the list of news sources and adding them to 
             * a list
             */
            while (rs.next()) {
                String source = rs.getString("Source"); //News Sources
                sources.add(source);
            }

        } catch (SQLException ex) {
            Logger.getLogger(OperationFile.class.getName()).log(Level.SEVERE, "Some error while fetching news sources");
            return sources;
        } finally {
            cf.releaseConnection();
        }
        return sources;
    }
    /*
     * End of Method-getSources
     */

    
    /**
     * Method Name - insertBonusAnswer
     * Insert the opinion of Reputed User of what he thinks about source being Fake or Genuine
     * 
     * @param bonusAnswer - Answer from the User - Yes for Fake, No for Genuine News
     * @param source
     * @return insertion status of it in bonus_answer table
     * @throws SQLException
     */
    public boolean insertBonusAnswer(String bonusAnswer, String source) throws SQLException {
        
    	boolean newsSaved = false;			//Status of whether user preference is saved successfully. Initialy assigned False
    	
        ConnectionFactory cf = new ConnectionFactory();	//Creation of ConnectionFactory Object
        
        PreparedStatement pstmt = null;

        int correct = 0;	//Initially Genuine = 0
        int fake = 0;		//Initially Genuine = 1
        
        /*
         * If the User says the source is Fake...Increase count of Fake
         * otherwise increase count of Correct/Genuine
         */
        if ("No".equalsIgnoreCase(bonusAnswer)) {
            correct++;
        } else if ("Yes".equalsIgnoreCase(bonusAnswer)) {
            fake++;
        }

        try {
            Connection conn = cf.getConnection();		//Get Connection

            
            /*
             * Query will insert the New Source info if it is not present in the database table bonus_answer
             * But will update the count of fake/genuine by 1 depending on user preference if the 
             * news source is already present in the table
             */
            String query = "INSERT INTO bonus_answer (Source, Fake, Genuine) VALUES (?,?,?) "
                    + "ON DUPLICATE KEY UPDATE Fake=Fake+?, Genuine=Genuine+?";

            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, source);
            pstmt.setInt(2, fake);
            pstmt.setInt(3, correct);
            pstmt.setInt(4, 1);
            pstmt.setInt(5, 1);

            int updatedRows = pstmt.executeUpdate(); //Query Executed

            if (updatedRows > 0) {
                newsSaved = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(OperationFile.class.getName()).log(Level.SEVERE, "Some error while inserting bonus answer data");
            return false;
        } finally {
            cf.releaseConnection();
        }
        return newsSaved;
    }
    /*
     * End of Method - insertBonusAnswer
     */
}

/*
 * End of File
 */
