package ru.vk.timescheduller.controller;

import ru.vk.timescheduller.lines.Line01_CurrentTime;
import ru.vk.timescheduller.constants.COLOR;
import ru.vk.timescheduller.lines.Line02_30min;

import java.util.Arrays;
import java.util.Timer;
import java.util.stream.Collectors;

public class Controller {
    private final String[] args;
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";

    public Controller(String[] args) {
        this.args = args;
        String collect = Arrays.asList(args).stream().collect(Collectors.joining(" "));
        System.out.println(ANSI_CYAN + "Arguments are : " + collect + ANSI_RESET);
    }

    private void printExampleColors() {
        System.out.println(ANSI_BLACK + "This text is black!" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "This text is blue!" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "This text is cyan!" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "This text is green!" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "This text is purple!" + ANSI_RESET);
        System.out.println(ANSI_RED + "This text is red!" + ANSI_RESET);
        System.out.println(ANSI_WHITE + "This text is white!" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "This text is yellow!\n" + ANSI_RESET);

        for (int i = 0; i < 8; i++) {
            System.out.println("\u001B[" + i + "m" +
                    "This color cod is : " + i + ANSI_RESET);
        }
        for (int i = 30; i < 47; i++) {
            System.out.println("\u001B[" + i + "m" +
                    "This color cod is : " + i + ANSI_RESET);
        }
        for (int i = 89; i < 108; i++) {
            System.out.println("\u001B[" + i + "m" +
                    "This color cod is : " + i + ANSI_RESET);
        }
    }

    public void start() {
        printExampleColors();
        printLine01CurrentTime();
//        printLine02();

    }

    private void printLine01CurrentTime() {
        Timer timer = new Timer();
        timer.schedule(new Line01_CurrentTime(COLOR.YELLOW), 0, 1000);
    }



}
