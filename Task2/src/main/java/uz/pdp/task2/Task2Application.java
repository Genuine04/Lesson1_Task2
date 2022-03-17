package uz.pdp.task2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

@SpringBootApplication
public class Task2Application {

    public static void main(String[] args) throws FileNotFoundException {
        SpringApplication.run(Task2Application.class, args);


        try
        {
            // Save original out stream.
            PrintStream originalOut = System.out;
            // Save original err stream.
            PrintStream originalErr = System.err;
            // Create a new file output stream.
            PrintStream fileOut = new PrintStream("./out.txt");
            // Create a new file error stream.
            PrintStream fileErr = new PrintStream("./err.txt");
            // Redirect standard out to file.
            System.setOut(fileOut);
            // Redirect standard err to file.
            System.setErr(fileErr);
            // Wrapped Scanner to get user input.
            Scanner scanner = new Scanner(System.in);
            // Print data in command console.
            originalOut.println("Please input your email. ");
            // Read string line.
            String line = scanner.nextLine();
            while(true)
            {
                // If user input 'quit' then break the loop.
                if("quit".equalsIgnoreCase(line))
                {
                    break;
                }
                if(!isValidEmail(line))
                {
                    // If user input is not a valid email then write log data to ./err.txt file and console.
                    originalErr.println("Email " + line + " is not a valid email. Please input again.");
                    System.err.println("Email " + line + " is not a valid email. ");
                }else
                {
                    // If user input a valid email then write the email to ./out.txt and console.
                    originalOut.println("Email " + line + " is valid. Please input another one.");
                    System.out.println("Email " + line + " is valid. Please input another one.");
                }
                // Get next user input line text.
                line = scanner.nextLine();
            }
            originalOut.println("Program exit. ");
            System.out.println("Program exit. ");
            // Do not forget set original output and error stream back again.
            System.setOut(originalOut);
            System.setErr(originalErr);
        }catch(FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
    }
    /* Check whether the string is an email address or not. */
    private static boolean isValidEmail(String email)
    {
        boolean ret = true;
        if(email==null || email.trim().length()==0)
        {
            ret = false;
        }else
        {
            int index = email.indexOf("@");
            if(index == -1)
            {
                ret = false;
            }
        }
        return ret;
    }
}

