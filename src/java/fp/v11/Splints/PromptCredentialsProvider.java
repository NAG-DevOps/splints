package fp.v11.splints;

import java.io.Console;
import java.io.IOError;
import java.util.Scanner;

/**
 * PromptCredentialsProvider asks for username and password instead of
 * hard-coding them. A primitive implementation.
 * @author soen487-w18-team03
 */
public class PromptCredentialsProvider {

    private final Console console;
    private final Scanner scanner;
    private final String USERNAME_INPUT_TEXT = "Your username: ";
    private final String PASSWORD_INPUT_TEXT = "Your password: ";

    public PromptCredentialsProvider() {
        this.scanner = new Scanner(System.in);
        this.console = System.console();
    }

    /**
     * Prompts user for username
     * @return String 
     */
    public String getUsername() {
        String username = null;
        if (console != null) {
            try {
                username = String.valueOf(console.readLine(USERNAME_INPUT_TEXT));
            } catch (IOError error) {
                error.printStackTrace();
            }

        } else {
            System.out.println(USERNAME_INPUT_TEXT);
            username = scanner.nextLine();
        }
        return username;
    }

    /**
     * Prompts user for password
     * @return String
     */
    public String getPassword() {
        String password = null;
        if (console != null) {
            try {
                password = String.valueOf(console.readPassword(PASSWORD_INPUT_TEXT));
            } catch (IOError error) {
                error.printStackTrace();
            }

        } else {
            System.out.println(PASSWORD_INPUT_TEXT);
            password = scanner.nextLine();
        }
        return password;
    }
}
