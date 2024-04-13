package org.dbms_system.SQLQuery;

import org.dbms_system.Common.CommonFunctions;
import org.dbms_system.Constants.Constant;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Query {

    File file;
    FileWriter writer;
    Scanner scanner = new Scanner(System.in);
    
    /**
     * This method will create the database for the user using given database name.
     *
     * @param databaseName - (String) Accept the database name String type
     * @param username
     */
    public void createDatabase(String databaseName, String username) throws IOException {
        file = new File(Constant.FILE_PATH+databaseName+"\\"+databaseName+".txt");
        if(!file.exists()) {
            boolean result = file.createNewFile();
            if (!result) {
                CommonFunctions.consoleLog("Error occurred in creating database","RED");
            }
        }
    }

    /**
     * This method will create the table.
     */
    public void createTable() throws IOException {
        System.out.println("Use your Database. i.e USE <database_name>");
        String database_name = scanner.nextLine();
        if (CommonFunctions.validateQuery(Constant.USE_DATABASE,database_name)) {
            String filePath = Constant.FILE_PATH+CommonFunctions.sanitizeString(database_name.toUpperCase())[1]+"\\"+CommonFunctions.sanitizeString(database_name.toUpperCase())[1]+".txt";
            file = new File(filePath);
            if(file.exists()) {
                System.out.println("Enter your create table query i.e CREATE TABLE <table_name> (column1_name datatype, column2_name datatype)");
                String query = scanner.nextLine();
                String tableName = CommonFunctions.sanitizeString(query.toUpperCase())[2];
                if(CommonFunctions.validateQuery(Constant.CREATE_TABLE,query)) {
                    Pattern pattern = Pattern.compile("\\((.*?)\\)");
                    Matcher matcher = pattern.matcher(query.toUpperCase());
                    List<String> columnNames  = new ArrayList<>();
                    if (matcher.find()) {
                        String[] columns = matcher.group(1).split(",\\s*");
                        for (String column : columns) {
                            String[] columnInfo = column.trim().split("\\s+");
                            columnNames.add(columnInfo[0]);
                        }
                        writer = new FileWriter(filePath,true);
                        writer.write(tableName);
                        writer.write(System.lineSeparator());
                        for (String columnName : columnNames) {
                            writer.write(columnName + "|");
                        }
                        writer.write(System.lineSeparator());
                        writer.write(System.lineSeparator());
                        writer.close();
                        CommonFunctions.consoleLog("Success!","GREEN");
                        CommonFunctions.askUserToContinue();
                    }
                } else {
                    CommonFunctions.consoleLog("Wrong query","RED");
                    CommonFunctions.askUserToContinue();
                }
            } else{
                CommonFunctions.consoleLog("File not exist","RED");
                CommonFunctions.askUserToContinue();
            }
        }
    }

    /**
     * This method is to select the records from the Table.
     */
    public void select() throws IOException {
        System.out.println("Use your Database. i.e USE <database_name>");
        String database_name = scanner.nextLine();
        if(CommonFunctions.validateQuery(Constant.USE_DATABASE, database_name)) {
            String filePath = Constant.FILE_PATH+CommonFunctions.sanitizeString(database_name.toUpperCase())[1]+"\\"+CommonFunctions.sanitizeString(database_name.toUpperCase())[1]+".txt";
            file = new File(filePath);
            if(file.exists()) {
                System.out.println("Enter your select table query i.e SELECT * FROM <table_name>");
                String query = scanner.nextLine();
                if(CommonFunctions.validateQuery(Constant.SELECT_TABLE,query)) {
                    Pattern pattern = Pattern.compile("^SELECT\\s\\*\\sFROM\\s(\\w+)$");
                    Matcher matcher = pattern.matcher(query);
                    if (matcher.find()) {
                        String tableName = matcher.group(1);
                        int lineNumber = CommonFunctions.isTableExist(filePath,tableName);
                        BufferedReader tableReader = new BufferedReader(new FileReader(filePath));
                        int readLineNumber = 0;
                        String line;
                        if(lineNumber!=0) {
                            while ((line = tableReader.readLine()) != null) {
                                readLineNumber++;
                                if (readLineNumber>=(lineNumber+1)) {
                                    System.out.println(Constant.ANSI_WHITE_BACKGROUND+Constant.ANSI_BLACK+line+Constant.ANSI_RESET);
                                    if (line.isEmpty()) {
                                        break;
                                    }
                                }
                            }
                            CommonFunctions.consoleLog("Success!","GREEN");
                            CommonFunctions.askUserToContinue();
                        } else{
                           CommonFunctions.consoleLog("Table "+tableName+" Not Found","RED");
                           CommonFunctions.askUserToContinue();
                        }
                        tableReader.close();
                    } else {
                        CommonFunctions.consoleLog("Wrong query","RED");
                        CommonFunctions.askUserToContinue();
                    }
                }
            }
        }
    }

    /**
     * This method will insert the record into table
     */
    public void insert() throws IOException {
        System.out.println("Use your Database. i.e USE <database_name>");
        String database_name = scanner.nextLine();
        if (CommonFunctions.validateQuery(Constant.USE_DATABASE,database_name)) {
            String filePath = Constant.FILE_PATH+CommonFunctions.sanitizeString(database_name.toUpperCase())[1]+"\\"+CommonFunctions.sanitizeString(database_name.toUpperCase())[1]+".txt";
            file = new File(filePath);
            if(file.exists()){
                System.out.println("Enter your insert table query i.e INSERT INTO <table_name> values(value1, value2)");
                String query = scanner.nextLine();
                if(CommonFunctions.validateQuery(Constant.INSERT_TABLE, query)){
                    Pattern pattern = Pattern.compile("INSERT INTO (\\w+) VALUES\\((.*)\\)");
                    Matcher matcher = pattern.matcher(query.toUpperCase());
                    if (matcher.find()) {
                        String tableName = matcher.group(1);
                        String line;
                        String values = matcher.group(2);
                        int lineNumber = CommonFunctions.isTableExist(filePath, tableName);
                        String tempFilePath = Constant.FILE_PATH+CommonFunctions.sanitizeString(database_name.toUpperCase())[1]+"\\"+CommonFunctions.sanitizeString(database_name.toUpperCase())[1]+"temp.txt";
                        File inputFile = new File(filePath);
                        File tempFile = new File(tempFilePath);
                        writer = new FileWriter(tempFile);
                        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                        int readLineNumber = 0;
                        if(lineNumber!=0) {
                            while ((line = reader.readLine()) != null) {
                                readLineNumber++;
                                if(readLineNumber>=(lineNumber+1)) {
                                    if (line.isEmpty()) {
                                        writer.write(values);
                                        writer.write(System.lineSeparator());
                                        writer.write(System.lineSeparator());
                                    }else {
                                        writer.write(line);
                                        writer.write(System.lineSeparator());
                                    }
                                }else {
                                    writer.write(line);
                                    writer.write(System.lineSeparator());
                                }
                            }
                            writer.close();
                            reader.close();
                            CommonFunctions.deleteFile(inputFile);
                            CommonFunctions.renameFile(tempFile,inputFile);
                            CommonFunctions.consoleLog("Success!","GREEN");
                            CommonFunctions.askUserToContinue();
                        }
                    } else {
                        CommonFunctions.consoleLog("Wrong query","RED");
                        CommonFunctions.askUserToContinue();
                    }
                } else {
                    CommonFunctions.consoleLog("Wrong query","RED");
                    CommonFunctions.askUserToContinue();
                }
            }else{
                CommonFunctions.consoleLog("File not exist","RED");
                CommonFunctions.askUserToContinue();
            }
        }
    }

    /**
     * This method will delete the record from the table.
     */
    public void delete() throws IOException {
        System.out.println("Use your Database. i.e USE <database_name>");
        String database_name = scanner.nextLine();
        if(CommonFunctions.validateQuery(Constant.USE_DATABASE, database_name)) {
            String filePath = Constant.FILE_PATH+CommonFunctions.sanitizeString(database_name.toUpperCase())[1]+"\\"+CommonFunctions.sanitizeString(database_name.toUpperCase())[1]+".txt";
            file = new File(filePath);
            if(file.exists()) {
                System.out.println("Enter your delete query i.e DELETE FROM <table_name> WHERE condition");
                String query = scanner.nextLine();
                if(CommonFunctions.validateQuery(Constant.DELETE_TABLE, query)) {
                    Pattern pattern = Pattern.compile("^DELETE\\s+FROM\\s+(\\w+)\\s+WHERE\\s+(.+)$");
                    Matcher matcher = pattern.matcher(query.toUpperCase());
                    if(matcher.find()) {
                        String tableName = matcher.group(1);
                        String condition = matcher.group(2);
                        String[] values = condition.split(" = ");
                        String line;
                        int lineNumber = CommonFunctions.isTableExist(filePath, tableName);
                        BufferedReader reader = new BufferedReader(new FileReader(filePath));
                        int readLineNumber = 0;
                        int index = 0;
                        String[] columnNames = new String[0];
                        if(lineNumber!=0) {
                            while ((line = reader.readLine()) != null) {
                                readLineNumber++;
                                if (readLineNumber==(lineNumber+1)) {
                                   columnNames = line.split("\\|");
                                   break;
                                }
                            }

                            for(int i = 0; i<columnNames.length ; i++){
                                if (columnNames[i].equals(values[0])) {
                                    index = i;
                                    break;
                                }
                            }
                            reader.close();
                            readLineNumber = 0;
                            String tempFilePath = Constant.FILE_PATH+CommonFunctions.sanitizeString(database_name.toUpperCase())[1]+"\\"+CommonFunctions.sanitizeString(database_name.toUpperCase())[1]+"temp.txt";
                            File inputFile = new File(filePath);
                            File tempFile = new File(tempFilePath);
                            reader = new BufferedReader(new FileReader(inputFile));
                            writer = new FileWriter(tempFile);
                            while ((line = reader.readLine()) != null) {
                                readLineNumber++;
                                if(readLineNumber>=(lineNumber+2)) {
                                   String[] element = line.split(", ");
                                   if (!element[index].equals(values[1])) {
                                       writer.write(line);
                                       writer.write(System.lineSeparator());
                                   }
                                }else {
                                    writer.write(line);
                                    writer.write(System.lineSeparator());
                                }
                            }
                            reader.close();
                            writer.close();
                            CommonFunctions.deleteFile(inputFile);
                            CommonFunctions.renameFile(tempFile,inputFile);
                            CommonFunctions.consoleLog("Success!","GREEN");
                            CommonFunctions.askUserToContinue();
                        } else{
                            CommonFunctions.consoleLog("Table "+tableName+" Not Found","RED");
                            CommonFunctions.askUserToContinue();
                        }
                        reader.close();
                    }else {
                        CommonFunctions.consoleLog("Wrong query","RED");
                        CommonFunctions.askUserToContinue();
                    }
                } else{
                    CommonFunctions.consoleLog("Wrong query","RED");
                    CommonFunctions.askUserToContinue();
                }
            }
        }
    }

    /**
     * This method will update the record into the table.
     */
    public void update() throws IOException {
        System.out.println("Use your Database. i.e USE <database_name>");
        String database_name = scanner.nextLine();
        if(CommonFunctions.validateQuery(Constant.USE_DATABASE, database_name)) {
            String filePath = Constant.FILE_PATH+CommonFunctions.sanitizeString(database_name.toUpperCase())[1]+"\\"+CommonFunctions.sanitizeString(database_name.toUpperCase())[1]+".txt";
            file = new File(filePath);
            if(file.exists()) {
                System.out.println("Enter your update table query i.e UPDATE <table_name> SET COLUMN_NAME = VALUE WHERE CONDITION");
                String query = scanner.nextLine();
                if(CommonFunctions.validateQuery(Constant.UPDATE_TABLE, query)) {
                    Pattern pattern = Pattern.compile("^\\s*UPDATE\\s+(\\S+)\\s+SET\\s+(\\S+\\s*=\\s*('[^']*'|\\S+)(,\\s*\\S+\\s*=\\s*('[^']*'|\\S+))*)\\s*WHERE\\s+(\\S+\\s*=\\s*('[^']*'|\\S+))\\s*;\\s*$");
                    Matcher matcher = pattern.matcher(query.toUpperCase());
                    if (matcher.find()) {
                        String tableName = matcher.group(1);
                        String[] assignments = matcher.group(2).split(" = ");
                        String[] whereCondition = matcher.group(6).split(" = ");
                        String line;
                        int lineNumber = CommonFunctions.isTableExist(filePath, tableName);
                        BufferedReader reader = new BufferedReader(new FileReader(filePath));
                        int readLineNumber = 0;
                        String[] columnNames = new String[0];
                        int whereCondIndex = 0;
                        int dataUpdateIndex = 0;
                        if(lineNumber!=0) {
                            while ((line = reader.readLine()) != null) {
                                readLineNumber++;
                                if(readLineNumber==(lineNumber+1)) {
                                    columnNames = line.split("\\|");
                                   break;
                                }
                            }
                            reader.close();

                            for(int i = 0; i<columnNames.length ; i++){
                                if (columnNames[i].equals(whereCondition[0])) {
                                    whereCondIndex = i;
                                    break;
                                }
                            }
                            for(int i = 0; i<columnNames.length ; i++){
                                if (columnNames[i].equals(assignments[0])) {
                                    dataUpdateIndex = i;
                                    break;
                                }
                            }
                            readLineNumber = 0;
                            String tempFilePath = Constant.FILE_PATH+CommonFunctions.sanitizeString(database_name.toUpperCase())[1]+"\\"+CommonFunctions.sanitizeString(database_name.toUpperCase())[1]+"temp.txt";
                            File inputFile = new File(filePath);
                            File tempFile = new File(tempFilePath);
                            reader = new BufferedReader(new FileReader(inputFile));
                            writer = new FileWriter(tempFile);
                            while ((line = reader.readLine()) != null) {
                                readLineNumber++;
                                if(readLineNumber>=(lineNumber+2)) {
                                    String[] element = line.split(", ");
                                    if (element[whereCondIndex].equals(whereCondition[1])) {
                                        String[] updateData = line.split(", ");
                                        String oldChar = updateData[dataUpdateIndex];
                                        writer.write(line.replaceAll(oldChar,assignments[1]));
                                        writer.write(System.lineSeparator());
                                    } else  {
                                        writer.write(line);
                                        writer.write(System.lineSeparator());
                                    }
                                }else {
                                    writer.write(line);
                                    writer.write(System.lineSeparator());
                                }
                            }
                            reader.close();
                            writer.close();
                            CommonFunctions.deleteFile(inputFile);
                            CommonFunctions.renameFile(tempFile,inputFile);
                            CommonFunctions.consoleLog("Success!","GREEN");
                            CommonFunctions.askUserToContinue();
                        }

                    }else {
                        CommonFunctions.consoleLog("Wrong query","RED");
                        CommonFunctions.askUserToContinue();
                    }
                }else {
                    CommonFunctions.consoleLog("Wrong query","RED");
                    CommonFunctions.askUserToContinue();
                }
            } else {
                CommonFunctions.consoleLog("File not exist","RED");
                CommonFunctions.askUserToContinue();
            }
        } else{
            CommonFunctions.consoleLog("Wrong query","RED");
            CommonFunctions.askUserToContinue();
        }
    }
}
