package org.dbms_system.SQLQuery;

import org.dbms_system.Main;
import org.junit.*;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
public class ApplicationTest {

    /**
     * This method will remove the color codes from the console output.
     *
     * @param str - (String) Accept the log string
     * @return return the modified string
     */
    private String removeColorCodes(String str) {
        return str.replaceAll("\u001B\\[[;\\d]*m", "");
    }

//    check the starting point user will ask to Enter Y or N if they have account or not
//    This method will test that
    @Test
    public void checkInputCharacter() throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream("X".getBytes());
        System.setIn(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Main.main(null);

        String expectedOutput = """
                You already have an Account? press Y or N
                .....You enter wrong Character.....
                """;
        String actualOutput = removeColorCodes(out.toString());
        expectedOutput = expectedOutput.replaceAll("\\r\\n|\\r|\\n", System.lineSeparator());
        actualOutput = actualOutput.replaceAll("\\r\\n|\\r|\\n", System.lineSeparator());
        assertEquals(expectedOutput,actualOutput);
    }
}
