package cscie55.hw6;

import java.io.PrintStream;
import java.util.StringTokenizer;

/**
 * Created by Sohail on 4/22/14.
 */
public class ATMRunnable {

    String commandLine = null;
    PrintStream printStream;
    ATMImplementation atmImplementation;

    ATMRunnable(String commandLine, PrintStream printStream, ATMImplementation atmImplementation) {
        this.commandLine = commandLine;
        this.printStream = printStream;
        this.atmImplementation = atmImplementation;
    }

    public Float run() throws ATMException{

        // Break out the command line into String[]
        StringTokenizer tokenizer = new StringTokenizer(commandLine);
        String commandAndParam[] = new String[tokenizer.countTokens()];
        int index = 0;
        while(tokenizer.hasMoreTokens()) {
            commandAndParam[index++] = tokenizer.nextToken();
        }
        String command = commandAndParam[0];
        // Dispatch BALANCE request without further ado.
        if(command.equalsIgnoreCase(Commands.BALANCE.toString())) {
            printStream.println(atmImplementation.getBalance());
        }
        // Must have 2nd arg for amount when processing DEPOSIT/WITHDRAW commands
        if(commandAndParam.length < 2) {
            throw new ATMException("Missing amount for command \"" + command + "\"");
        }
        try {
            float amount = Float.parseFloat(commandAndParam[1]);
            if(command.equalsIgnoreCase(Commands.DEPOSIT.toString())) {
                atmImplementation.deposit(amount);
            }
            else if(command.equalsIgnoreCase(Commands.WITHDRAW.toString())) {
                atmImplementation.withdraw(amount);
            } else {
                throw new ATMException("Unrecognized command: " + command);
            }
        } catch (NumberFormatException nfe) {
            throw new ATMException("Unable to make float from input: " + commandAndParam[1]);
        }
        // BALANCE command returned result above.  Other commands return null;
        return null;
    }
}
