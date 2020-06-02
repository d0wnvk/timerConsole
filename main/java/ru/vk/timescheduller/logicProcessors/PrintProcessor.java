package ru.vk.timescheduller.logicProcessors;

import static ru.vk.timescheduller.controller.Controller.*;
import static ru.vk.timescheduller.controller.Controller.ANSI_RESET;
import static ru.vk.timescheduller.logicProcessors.CalcProcessor.*;

public class PrintProcessor {
    public void printResults() {

        StringBuilder blankLine = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            blankLine.append(" ");
        }

        System.out.print(                       // 01 line
                blankLine
                        + "\n");

        System.out.print(                       // 02 line
                "\u001B[90m"
                        + " 02 line clock   :          "
                        + currentTime
                        + " <---> "
                        + ANSI_WHITE
//                        + passedTime
                        + ANSI_RESET
                        + blankLine
                        + "\n"); // 01 line
        System.out.print(
                ANSI_PURPLE
                        + " 03 line 60 secs :    " // 03 line
                        + secondsLine
                        + ANSI_RESET
                        + "\n");
        System.out.print(
                ANSI_GREEN
                        + " 04 line 60 mins :    " // line 04
                        + minutesLine
                        + ANSI_RESET
                        + "\n"  // в последней строке нет переноса
        );
        System.out.print(
                ANSI_CYAN
                        + " 05 line 60 hours :"                         // line 05
                        + hoursLine
                        + " independent count : "
                        + independentCount + "   "
                        + ANSI_RAINBOW_COLORS + "!"
                        + ANSI_RESET
                // + "\n" в последней строке нет переноса
        );
        System.out.print(
                "\033[F"  // back one line up - from 02 to 01
                        + "\033[F" // from 03 to 02
                        + "\033[F" // from 04 to 03
                        + "\033[F" // from 05 to 04
                        + "\r");    // back to the beginning of a line
    }
}
