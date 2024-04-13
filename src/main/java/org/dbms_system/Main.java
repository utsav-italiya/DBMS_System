package org.dbms_system;

import org.dbms_system.Common.CommonFunctions;

public class Main {
    public static void main(String[] args) throws Exception {
        String input = CommonFunctions.askForAccount();
        if (input.equalsIgnoreCase("Y")) {
            CommonFunctions.askForLogin();
        } else if (input.equalsIgnoreCase("N")){
            CommonFunctions.askForRegister();
        } else {
            CommonFunctions.consoleLog("You enter wrong Character","RED");
        }
    }
}