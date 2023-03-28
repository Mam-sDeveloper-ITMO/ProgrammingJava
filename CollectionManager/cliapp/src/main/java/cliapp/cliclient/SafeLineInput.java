package cliapp.cliclient;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;

/**
 * A supplier that provides user input from the command line in a safe way.
 * 
 * Prevents errors with Ctrl+Z or Ctrl+D (maybe more will founded)
 * 
 * This implementation uses a {@link java.util.Scanner} object to read input
 * from the console.
 * 
 * If an {@link java.util.NoSuchElementException} is thrown, it creates a new
 * scanner object to prevent any issues with the previous scanner.
 */
@RequiredArgsConstructor
public class SafeLineInput implements Supplier<String> {
    private Scanner scanner = new Scanner(System.in);

    /**
     * Returns the next line of user input.
     * 
     * @return the next line of user input from the console
     */
    @Override
    public String get() {
        try {
            return scanner.nextLine();
        } catch (NoSuchElementException e) {
            // Create a new scanner object to prevent any issues with the previous scanner
            scanner = new Scanner(System.in);
            return "";
        }
    }
}