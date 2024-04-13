package org.dbms_system.Authentication;

import org.dbms_system.Common.CommonFunctions;
import org.dbms_system.Constants.Constant;
import org.dbms_system.SQLQuery.Query;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

public class CreateFileSystemDatabase extends Query {
    File file;
    Scanner scanner;


    /**
     * This method will create the dicrectory for the User to store the database.
     *
     * @param directoryName - (String) Accept the name of the folder
     */
    public void createfolder(String directoryName) {
        file = new File(Constant.FILE_PATH+directoryName);
        if(!file.exists()) {
            if(file.mkdir()) {
                CommonFunctions.consoleLog("Database created","GREEN");
            }
            else{
                CommonFunctions.consoleLog("Error occur in creating directory","RED");
            }
        } else {
            CommonFunctions.consoleLog("Error occur in creating directory","RED");
        }
    }

    /**
     * This method will Create the txt file to store the user credentials.
     *
     * @param username - (String) Accept the Username
     */
    public void createFile(String username) throws Exception {
        file = new File(Constant.FILE_PATH+username+".txt");
        if(!file.exists()) {
            boolean result  = file.createNewFile();
            if (result) {
                CommonFunctions.consoleLog("Registered Successfully","GREEN");
            }
            else{
                CommonFunctions.consoleLog("Error occur in creating Database. Please try again later","RED");
                CommonFunctions.askForRegister();
            }
        }else {
            CommonFunctions.consoleLog("User with this credentials already exis","RED");
            CommonFunctions.askForRegister();
        }
    }

    /**
     * This method will write the user credentials to the txt file.
     *
     * @param username - (String) Accept the Username
     * @param password - (String) Accept the Password
     * @param security_question - (String) Accept the Security Question
     * @param security_answer - (String) Accept the Security Answer
     */
    public void writeData(String username, String password, String security_question, String security_answer) throws IOException {
        FileWriter fileWriter = new FileWriter(Constant.FILE_PATH+username+".txt", true);
        fileWriter.write("username:"+username);
        fileWriter.write(System.lineSeparator());
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        fileWriter.write("password:"+encodedPassword);
        fileWriter.write(System.lineSeparator());
        fileWriter.write("security_question:"+security_question);
        fileWriter.write(System.lineSeparator());
        fileWriter.write("security_answer:"+security_answer);
        fileWriter.write(System.lineSeparator());
        fileWriter.close();
        askToCreateDatabase(username);
    }

    /**
     * This method will read the txt file.
     *
     * @param username - (String) Accept the Username
     * @return return the content of the file
     */
    public List<String> readFile(String username) throws IOException {
        file = new File(Constant.FILE_PATH+username+".txt");
        List<String> fileData = new ArrayList<String>();
        boolean fileExist = file.exists();
        if(fileExist) {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                String data  = scanner.nextLine();
                fileData.add(data);
            }
            scanner.close();
        }else{
            CommonFunctions.consoleLog("Invalid credentials. Please try again later","RED");
            CommonFunctions.askForLogin();
        }
        return fileData;
    }

    /**
     * This method will ask user to create new Database.
     *
     */
    public void askToCreateDatabase(String username) throws IOException {
        System.out.println(" Please create your database, Write your query to create the database i.e: CREATE DATABASE <name_of_DB> ");
        scanner = new Scanner(System.in);
        String createDatabaseQuery = scanner.nextLine();
        if(CommonFunctions.validateQuery(Constant.CREATE_DATABASE, createDatabaseQuery)) {
            file = new File(Constant.FILE_PATH+CommonFunctions.sanitizeString(createDatabaseQuery)[2]);
            if(!file.exists()){
                createfolder(CommonFunctions.sanitizeString(createDatabaseQuery)[2]);
                createDatabase(CommonFunctions.sanitizeString(createDatabaseQuery)[2], username);
                CommonFunctions.addDataBaseNameToTxtFile(CommonFunctions.sanitizeString(createDatabaseQuery)[2],username);
            } else {
                createDatabase(CommonFunctions.sanitizeString(createDatabaseQuery)[2], username);
                CommonFunctions.addDataBaseNameToTxtFile(CommonFunctions.sanitizeString(createDatabaseQuery)[2],username);
            }
            CommonFunctions.getUserInputQuery();
        } else {
            CommonFunctions.consoleLog("Wrong Query","RED");
        }
    }
}
