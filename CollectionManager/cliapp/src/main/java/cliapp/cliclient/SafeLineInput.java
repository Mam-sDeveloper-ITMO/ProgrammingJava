package cliapp.cliclient;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SafeLineInput implements Supplier<String> {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public String get() {
        try {
            return scanner.nextLine();
        } catch (NoSuchElementException e) {
            scanner = new Scanner(System.in);
            return "";
        }
    }
}
