package org.dbms_system.Authentication;


import org.dbms_system.Common.CommonFunctions;

public class Register {

    /**
     * This method will check the validation for the user Credentials.
     *
     * @param username - (String) Accept the Username
     * @param password - (String) Accept the Password
     * @param security_question - (String) Accept the Security Question
     * @param security_answer - (String) Accept the Security Answer
     * @return return true or false (boolean) based on validation
     */
    public boolean checkValidation(String username, String password, String security_question, String security_answer) throws Exception{

        boolean isValid = true;

        if (username != null && password != null && security_question != null && security_answer != null) {
            if (!password.contains("@")) {
                isValid = false;
                CommonFunctions.consoleLog("Password must be contains @ special character for security purpose","RED");
                CommonFunctions.askForRegister();
            }
        } else{
            isValid = false;
            CommonFunctions.consoleLog("username, password, Security Question, Security Answer can not be Empty!","RED");
            CommonFunctions.askForRegister();
        }
        return isValid;
    }

    /**
     * This method will register the user in the system.
     *
     * @param username - (String) Accept the Username
     * @param password - (String) Accept the Password
     * @param security_question - (String) Accept the Security Question
     * @param security_answer - (String) Accept the Security Answer
     */
    public void registerUser(String username, String password, String security_question, String security_answer) throws Exception {
        CreateFileSystemDatabase createFileSystemDatabase;
        createFileSystemDatabase = new CreateFileSystemDatabase();

       if (checkValidation(username,password,security_question,security_answer)) {
           createFileSystemDatabase.createFile(username);
           createFileSystemDatabase.writeData(username,password,security_question,security_answer);
       }
    }
}
