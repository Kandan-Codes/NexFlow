package com.mani.nexflow.util;

import java.util.Scanner;

public class TerminalReader {

    private static final Scanner SCANNER = new Scanner(System.in);

    private TerminalReader() {
    }

    public static Scanner getScanner() {
        return SCANNER;
    }
}
