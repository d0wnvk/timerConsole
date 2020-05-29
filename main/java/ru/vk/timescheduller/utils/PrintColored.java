package ru.vk.timescheduller.utils;

import ru.vk.timescheduller.constants.COLOR;

public class PrintColored {
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static void print(COLOR color, String s) {
        switch (color) {
            case YELLOW:
                System.out.print(ANSI_YELLOW + s + ANSI_RESET + "\r");
                break;
            case BLUE:
                System.out.print(ANSI_BLUE + s + ANSI_RESET + "\r");
                break;
        }
    }
}
