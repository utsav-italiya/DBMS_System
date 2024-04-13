package org.dbms_system.Authentication;

import org.dbms_system.Common.CommonFunctions;
import org.dbms_system.Constants.Constant;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

public class Login {

    CreateFileSystemDatabase createFileSystemDatabase;
    Scanner scanner;

    /**
     * This method is responsible for the user Authentication.
     *
     * @param username - (String) Accept the username
     * @param password - (String) Accept the password
     */
    public void userLogin(String username, String password) throws IOException {
        createFileSystemDatabase = new CreateFileSystemDatabase();
        scanner = new Scanner(System.in);
        if (!username.isEmpty() && !password.isEmpty()) {
            List<String> fileData = createFileSystemDatabase.readFile(username);
            if(fileData.size() != 0) {

                String[] usernameArray = fileData.get(0).split(":");
                String[] passwordArray = fileData.get(1).split(":");
                String[] securityQuestionArray = fileData.get(2).split(":");
                String[] securityAnswerArray = fileData.get(3).split(":");

                byte[] decodedBytes = Base64.getDecoder().decode(passwordArray[1]);
                String decodedPassword = new String(decodedBytes);

                if(usernameArray[1].equals(username) && decodedPassword.equals(password)) {
                    System.out.println(Constant.ANSI_BLUE_BACKGROUND+Constant.ANSI_BLACK+" "+securityQuestionArray[1]+Constant.ANSI_RESET);
                    String securityAnswer = scanner.nextLine();
                    if(securityAnswer.equals(securityAnswerArray[1])) {
                        CommonFunctions.consoleLog("You are successfully logged in","GREEN");
                        System.out.println("Your DATABASE name is "+ fileData.get(4).split(":")[1]);
                        CommonFunctions.getUserInputQuery();
                    } else {
                        CommonFunctions.consoleLog("Wrong Answer of the security question","RED");
                        CommonFunctions.askForLogin();
                    }
                } else {
                    CommonFunctions.consoleLog("Invalid credentials","RED");
                    CommonFunctions.askForLogin();
                }
            } else {
               CommonFunctions.consoleLog("You are trying to read an empty file","RED");
               CommonFunctions.askForLogin();
            }
        } else {
            CommonFunctions.consoleLog("Invalid credentials","RED");
            CommonFunctions.askForLogin();
        }
    }
}
