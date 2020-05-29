package ru.vk.timescheduller.lines;

import ru.vk.timescheduller.constants.COLOR;
import ru.vk.timescheduller.utils.PrintColored;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;

import static ru.vk.timescheduller.controller.Controller.ANSI_RESET;
import static ru.vk.timescheduller.controller.Controller.ANSI_WHITE;

public class Line02_30min extends TimerTask {

    public Line02_30min() {
    }

    @Override
    public void run() {
        String colorrr = ANSI_WHITE;
        for (int i = 31; i < 38; i++) {
            colorrr = "\u001B[" + i + "m";
            System.out.println(colorrr + "W" + ANSI_RESET + "\r");
        }
    }

}
