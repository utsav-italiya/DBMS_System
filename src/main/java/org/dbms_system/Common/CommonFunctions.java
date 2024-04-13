package org.dbms_system.Common;

import org.dbms_system.Authentication.Login;
import org.dbms_system.Authentication.Register;
import org.dbms_system.Constants.Constant;
import org.dbms_system.SQLQuery.Query;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonFunctions {
    static Scanner scanner = new Scanner(System.in);
    static Query query = new Query();
    static Login login = new Login();
    static Register register = new Register();

    /**
     * This method is to ask if user already have Account or not.
     *
     * @return return the Y or N character
     */
    public static String askForAccount() {
        System.out.println("You already have an Account? press Y or N");
        scanner = new Scanner(System.in);
        String ch = scanner.nextLine();
        if (ch.isEmpty()) {
            return askForAccount();
        }else {
            return ch;
        }
    }

    /**
     * This method will get the SQL query from the user based on CODE.
     *
     */
    public static void getUserInputQuery() throws IOException {
        scanner = new Scanner(System.in);
        System.out.println(Constant.ANSI_YELLOW_BACKGROUND+Constant.ANSI_BLACK+".....Type following code to write the query i.e CTL....."+Constant.ANSI_RESET);
        System.out.println(Constant.ANSI_PURPLE_BACKGROUND+Constant.ANSI_BLACK+" CTL "+Constant.ANSI_RESET+"- CREATE TABLE");
        System.out.println(Constant.ANSI_PURPLE_BACKGROUND+Constant.ANSI_BLACK+" ITL "+Constant.ANSI_RESET+"- INSERT TABLE");
        System.out.println(Constant.ANSI_PURPLE_BACKGROUND+Constant.ANSI_BLACK+" UTL "+Constant.ANSI_RESET+"- UPDATE TABLE");
        System.out.println(Constant.ANSI_PURPLE_BACKGROUND+Constant.ANSI_BLACK+" DTL "+Constant.ANSI_RESET+"- DELETE TABLE");
        System.out.println(Constant.ANSI_PURPLE_BACKGROUND+Constant.ANSI_BLACK+" STL "+Constant.ANSI_RESET+"- SELECT TABLE");
        System.out.println(Constant.ANSI_PURPLE_BACKGROUND+Constant.ANSI_BLACK+" EXIT "+Constant.ANSI_RESET+"- EXIT");
        String code = scanner.nextLine();

        switch (code.toUpperCase()) {
            case "CTL" -> query.createTable();
            case "ITL" -> query.insert();
            case "UTL" -> query.update();
            case "DTL" -> query.delete();
            case "STL" -> query.select();
            case "EXT" -> System.exit(0);
            default -> {
                CommonFunctions.consoleLog("Wrong Code","RED");
                getUserInputQuery();
            }
        }
    }

    /**
     * This method will ask user to login in the system.
     *
     */
    public static void askForLogin() throws IOException {
        scanner = new Scanner(System.in);
        System.out.println(".....Continue again.....");
        System.out.println(Constant.ANSI_BLUE_BACKGROUND+Constant.ANSI_BLACK+" Enter Username: "+Constant.ANSI_RESET);
        String username = scanner.nextLine();
        System.out.println(Constant.ANSI_BLUE_BACKGROUND+Constant.ANSI_BLACK+" Enter password: "+Constant.ANSI_RESET);
        String password = scanner.nextLine();
        login.userLogin(username,password);
    }

    /**
     * This method will ask user for Registration.
     *
     */
    public static void askForRegister() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println(Constant.ANSI_BLUE_BACKGROUND+Constant.ANSI_BLACK+" Enter Username: "+Constant.ANSI_RESET);
        String username = scanner.nextLine();
        System.out.println(Constant.ANSI_BLUE_BACKGROUND+Constant.ANSI_BLACK+" Enter password: "+Constant.ANSI_RESET);
        String password = scanner.nextLine();
        System.out.println(Constant.ANSI_BLUE_BACKGROUND+Constant.ANSI_BLACK+" Enter Security Question: "+Constant.ANSI_RESET);
        String securityQuestion = scanner.nextLine();
        System.out.println(Constant.ANSI_BLUE_BACKGROUND+Constant.ANSI_BLACK+" Enter Security Answer: "+Constant.ANSI_RESET);
        String securityAnswer = scanner.nextLine();
        register.registerUser(username,password,securityQuestion,securityAnswer);
    }

    /**
     * If query contains more than one spaces this function will sanitize it.
     *
     * @param query - (String) Accept the SQL query for the sanitization
     * @return return the split query by space
     */
    public static String[] sanitizeString(String query) {
        String sanitizedString = query.replaceAll("\\s+", " ");
        String[] splitQuery;
        splitQuery = sanitizedString.split(" ");
        return splitQuery;
    }

    /**
     * Validate the query using regular expression.
     *
     * @param queryType - (String) Accept the type of the query
     * @param query - (String) Accept the SQL query for the validation
     * @return return true for valid SQL query
     */
    public static boolean validateQuery(String queryType, String query) {
        switch (queryType.toUpperCase()) {
            case Constant.CREATE_DATABASE:
                if(query.toUpperCase().contains("CREATE DATABASE")) {
                    String pattern = "^CREATE\\s+DATABASE\\s+"+ CommonFunctions.sanitizeString(query)[2]+"$";
                    Pattern p = Pattern.compile(pattern);
                    Matcher match = p.matcher(query.toUpperCase());
                    if(match.find()) {
                        return true;
                    }
                }
            case Constant.CREATE_TABLE:
                if (query.toUpperCase().contains("CREATE TABLE")) {
                    String pattern = "^CREATE\\s+TABLE\\s+"+CommonFunctions.sanitizeString(query)[2]+"$";
                    Pattern p = Pattern.compile(pattern);
                    Matcher match = p.matcher(query.toUpperCase());
                    if(match.find()) {
                        return true;
                    }
                }
            case Constant.USE_DATABASE:
                if (query.toUpperCase().contains("USE")) {
                    String pattern = "^USE\\s+"+CommonFunctions.sanitizeString(query)[1]+"$";
                    Pattern p = Pattern.compile(pattern);
                    Matcher match = p.matcher(query.toUpperCase());
                    if(match.find()) {
                        return true;
                    }
                }
            case Constant.INSERT_TABLE:
                if (query.toUpperCase().contains("INSERT INTO")) {
                    String pattern = "INSERT INTO (\\w+) VALUES\\((.*)\\)";
                    Pattern p = Pattern.compile(pattern);
                    Matcher match = p.matcher(query.toUpperCase());
                    if(match.find()) {
                        return true;
                    }
                }
            case Constant.SELECT_TABLE:
                if (query.toUpperCase().contains("SELECT * FROM")) {
                    String pattern = "^SELECT\\s\\*\\sFROM\\s(\\w+)$";
                    Pattern p = Pattern.compile(pattern);
                    Matcher match = p.matcher(query.toUpperCase());
                    if(match.find()) {
                        return true;
                    }
                }
            case Constant.UPDATE_TABLE:
                if (query.toUpperCase().contains("UPDATE") && query.toUpperCase().contains("SET") && query.toUpperCase().contains("WHERE")) {
                    Pattern p = Pattern.compile("^\\s*UPDATE\\s+\\S+\\s+SET\\s+\\S+\\s*=\\s*('[^']*'|\\S+)\\s*(,\\s*\\S+\\s*=\\s*('[^']*'|\\S+)\\s*)*WHERE\\s+\\S+\\s*=\\s*('[^']*'|\\S+)\\s*;\\s*$");
                    Matcher match = p.matcher(query.toUpperCase());
                    if (match.find()) {
                        return true;
                    }
                }
            case  Constant.DELETE_TABLE:
                if (query.toUpperCase().contains("DELETE FROM")) {
                    String pattern = "^DELETE\\s+FROM\\s+(\\w+)\\s+WHERE\\s+(.+)$";
                    Pattern p = Pattern.compile(pattern);
                    Matcher match = p.matcher(query.toUpperCase());
                    if(match.find()) {
                        return true;
                    }
                }
            default:
                return false;
        }
    }

    /**
     * This method will delete the file.
     *
     * @param fileName - (File) Accept the filename
     */
    public static void deleteFile(File fileName) {
        if (!fileName.delete()) {
            consoleLog("Error occurred in tables File not deleted","RED");
        }
    }

    /**
     * This method will rename the file.
     *
     * @param oldFile - (File) Accept the old filename
     * @param newFile - (File) Accept the new filename to rename
     */
    public static void renameFile(File oldFile, File newFile) {
        if (!oldFile.renameTo(newFile)) {
            consoleLog("File not renamed","RED");
        }
    }

    /**
     * This method will find the table in file.
     *
     * @param filePath - (String) Accept the filepath
     * @param tableName - (String) Accept the table name to find
     */
    public static int isTableExist(String filePath, String tableName) throws IOException {
        BufferedReader tableReader = new BufferedReader(new FileReader(filePath));
        String line;
        int lineNumber = 0;
        boolean isTableExist = false;
        while ((line = tableReader.readLine())!=null) {
            lineNumber++;
            if(line.contains(tableName)) {
                isTableExist = true;
                break;
            }
        }
        tableReader.close();
        if (isTableExist) {
            return lineNumber;
        } else {
            CommonFunctions.consoleLog("Table Not Found","RED");
            return 0;
        }
    }

    /**
     * This method will delete the file.
     *
     * @param log - (String) Accept the log that will display in the console
     * @param color - (String) Accept the color to beautify the log in console
     */
    public static void consoleLog(String log, String color) {
        switch (color) {
            case "GREEN" -> System.out.println(Constant.ANSI_GREEN_BACKGROUND + Constant.ANSI_BLACK + "....." + log + "....." + Constant.ANSI_RESET);
            case "RED" -> System.out.println(Constant.ANSI_RED_BACKGROUND + Constant.ANSI_BLACK + "....." + log + "....." + Constant.ANSI_RESET);
            case "BLUE" -> System.out.println(Constant.ANSI_BLUE_BACKGROUND + Constant.ANSI_BLACK + "....." + log + "....." + Constant.ANSI_RESET);
            case "YELLOW" -> System.out.println(Constant.ANSI_YELLOW_BACKGROUND + Constant.ANSI_BLACK + "....." + log + "....." + Constant.ANSI_RESET);
            case "BLACK" -> System.out.println(Constant.ANSI_BLACK_BACKGROUND + Constant.ANSI_WHITE + "....." + log + "....." + Constant.ANSI_RESET);
            case "PURPLE" -> System.out.println(Constant.ANSI_PURPLE_BACKGROUND + Constant.ANSI_BLACK + "....." + log + "....." + Constant.ANSI_RESET);
            default -> System.out.println(Constant.ANSI_RED_BACKGROUND + Constant.ANSI_BLACK + "..... Please provide the color for log....." + Constant.ANSI_RESET);
        }
    }

    /**
     * This method will delete the file.
     *
     */
    public static void askUserToContinue() throws IOException {
        System.out.println(Constant.ANSI_YELLOW_BACKGROUND+Constant.ANSI_BLACK+"Do you Want to continue? type Y or N"+Constant.ANSI_RESET);
        scanner = new Scanner(System.in);
        String ch = scanner.nextLine();
        if (ch.isEmpty()) {
            System.exit(0);
        }else if (ch.equalsIgnoreCase("Y")){
            getUserInputQuery();
        } else if (ch.equalsIgnoreCase("N")) {
            System.exit(0);
        }
    }

    /**
     * This method will add database anme to the TXT file.
     *
     * @param databaseName - (String) Accept the log that will display in the console
     * @param username - (String) Accept the color to beautify the log in console
     */
    public static void addDataBaseNameToTxtFile(String databaseName, String username) throws IOException {
        FileWriter writer = new FileWriter(Constant.FILE_PATH+username+".txt",true);
        writer.write("DatabaseName:"+databaseName);
        writer.write(System.lineSeparator());
        writer.close();
    }
}
