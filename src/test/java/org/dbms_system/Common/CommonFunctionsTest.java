package org.dbms_system.Common;

import org.dbms_system.Constants.Constant;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;

public class CommonFunctionsTest {

//    Test askForAccount function
    @Test
    public void testAskForAccount() {
        ByteArrayInputStream in = new ByteArrayInputStream("X".getBytes());
        System.setIn(in);
        String data = CommonFunctions.askForAccount();
        assertEquals(data,"X");
    }

//    Test valid create database query
    @Test
    public void testValidCreateDatabaseQuery() {
        String query = "CREATE DATABASE USER";
        String queryType = Constant.CREATE_DATABASE;
        boolean isValid = CommonFunctions.validateQuery(queryType,query);
        assertTrue(isValid);
    }

//    Test invalid create database query
    @Test
    public void testInvalidCreateDatabaseQuery() {
        String query = "CREATE DATA_BASE USER";
        String queryType = Constant.CREATE_DATABASE;
        boolean isValid = CommonFunctions.validateQuery(queryType,query);
        assertFalse(isValid);
    }

//    test valid create table query
    @Test
    public void testValidCreateTableQuery() {
        String query = "CREATE TABLE TEST";
        String queryType = Constant.CREATE_TABLE;
        boolean isValid = CommonFunctions.validateQuery(queryType,query);
        assertTrue(isValid);
    }

    //    test invalid create table query
    @Test
    public void testInValidCreateTableQuery() {
        String query = "CREATE TABLES TEST";
        String queryType = Constant.CREATE_TABLE;
        boolean isValid = CommonFunctions.validateQuery(queryType,query);
        assertFalse(isValid);
    }

    //    test valid use database query
    @Test
    public void testValidUseDatabaseQuery() {
        String query = "USE USER";
        String queryType = Constant.USE_DATABASE;
        boolean isValid = CommonFunctions.validateQuery(queryType,query);
        assertTrue(isValid);
    }

    //    test invalid use database query
    @Test
    public void testInValidUseDatabaseQuery() {
        String query = "USE DATA_BASE USER";
        String queryType = Constant.USE_DATABASE;
        boolean isValid = CommonFunctions.validateQuery(queryType,query);
        assertFalse(isValid);
    }

    //    test valid Insert Table query
    @Test
    public void testValidInsertTableQuery() {
        String query = "INSERT INTO USER VALUES(1, 'TEST_NAME')";
        String queryType = Constant.INSERT_TABLE;
        boolean isValid = CommonFunctions.validateQuery(queryType,query);
        assertTrue(isValid);
    }

    //    test Invalid Insert Table query
    @Test
    public void testInValidInsertTableQuery() {
        String query = "INSERT INTO USER VALUE(1, 'TEST_NAME')";
        String queryType = Constant.INSERT_TABLE;
        boolean isValid = CommonFunctions.validateQuery(queryType,query);
        assertFalse(isValid);
    }

    //    test valid select Table query
    @Test
    public void testValidSelectTableQuery() {
        String query = "SELECT * FROM USER";
        String queryType = Constant.SELECT_TABLE;
        boolean isValid = CommonFunctions.validateQuery(queryType,query);
        assertTrue(isValid);
    }

    //    test Invalid select Table query
    @Test
    public void testInValidSelectTableQuery() {
        String query = "SELECT FROM USER";
        String queryType = Constant.SELECT_TABLE;
        boolean isValid = CommonFunctions.validateQuery(queryType,query);
        assertFalse(isValid);
    }

    //    test valid update Table query
    @Test
    public void testValidUpdateTableQuery() {
        String query = "UPDATE PERSON SET PERSON_NAME = MAXY WHERE PERSON_ID = P1;";
        String queryType = Constant.UPDATE_TABLE;
        boolean isValid = CommonFunctions.validateQuery(queryType,query);
        assertTrue(isValid);
    }

    //    test Invalid update Table query
    @Test
    public void testInValidUpdateTawbleQuery() {
        String query = "UPDATE SET PERSON_NAME = MAXY WHERE PERSON_ID = P1;";
        String queryType = Constant.UPDATE_TABLE;
        boolean isValid = CommonFunctions.validateQuery(queryType,query);
        assertFalse(isValid);
    }

    //    test valid Delete Table query
    @Test
    public void testValidDeleteTableQuery() {
        String query = "DELETE FROM USER WHERE PERSON_ID = P1";
        String queryType = Constant.UPDATE_TABLE;
        boolean isValid = CommonFunctions.validateQuery(queryType,query);
        assertTrue(isValid);
    }

    //    test Invalid Delete Table query
    @Test
    public void testInValidDeleteTableQuery() {
        String query = "DELETE USER WHERE PERSON_ID = P1";
        String queryType = Constant.UPDATE_TABLE;
        boolean isValid = CommonFunctions.validateQuery(queryType,query);
        assertFalse(isValid);
    }
}
