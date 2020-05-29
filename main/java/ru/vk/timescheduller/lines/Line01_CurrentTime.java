package ru.vk.timescheduller.lines;

import ru.vk.timescheduller.constants.COLOR;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

import static ru.vk.timescheduller.controller.Controller.*;

public class Line01_CurrentTime extends TimerTask {
    private int seconds60 = 0;
    private int minutes60 = 0;
    private int hours03 = 0;
    private String secondsLine = "";
    private String minutesLine = "";
    private String hoursLine = "";
    private String cursor = "";
    private String colorrr = "";
    private int tempRealSecond;
    private int independentCount = 0;
    LocalDateTime now;
    int temp = 0;

    private final COLOR color;
    private int ccc = 31;

    public Line01_CurrentTime(COLOR color) {
        this.color = color;
    }

    @Override
    public void run() {

        independentCount++;


        now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = now.format(formatter);

        int realSecond = now.getSecond();
        if (realSecond != tempRealSecond) {
            // выполни  все это тк следующая секунда наступила
            processor(formatDateTime, realSecond);
        }
        tempRealSecond = realSecond;

        // печать результатов
        processorForCursor();
        printResults(formatDateTime, realSecond);


    }

    private void processorForCursor() {
        // раскрашиваем курсов

        colorrr = "\u001B[" + ccc + "m";
        cursor = colorrr + "#";
        ccc++;
        if (ccc == 38)
            ccc = 31;

        secondsLine = colorrr + secondsLine ;



        int realSecond = now.getSecond();
        if (realSecond != tempRealSecond) {
            // выполни  все это тк следующая секунда наступила
        }

        seconds60++;
        seconds60(); // каждую секунду строка реконструируется

        if (seconds60 > 59) {
            seconds60 = 0; // у этого счетчика жизненный цикл рестартует каждые 60сек
            minutes60++;
        }

    }

    private void processor(String formatDateTime, int realSecond) {
        // секунды



        // минуты
        minutes60();

        if (minutes60 > 59) {
            minutes60 = 0; // у этого счетчика жизненный цикл рестартует каждые 60сек
            hours03++;
        }

        // 3 часа
        if (hours03 > 3) {
            hours03 = 0; // у этого счетчика жизненный цикл рестартует каждые 60сек
        }
        housr03();


    }

    private void printResults(String formatDateTime, int realSecond) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append(" ");
        }

        System.out.print(
                sb
                        + "\n"); // 01 line

        System.out.print(
                ANSI_YELLOW
                        + " 01 line clock   : " // 01 line
                        + formatDateTime
                        + sb
                        + ANSI_RESET
                        + "\n"); // 01 line
        System.out.print(
                ANSI_PURPLE
                        + " 02 line 60 secs : " // 02 line
                        + secondsLine
                        + ANSI_RESET
                        + "\n");
        System.out.print(
                ANSI_GREEN
                        + " 03 line 60 mins : " // line 03
                        + minutesLine
                        + ANSI_RESET
                        + "\n"  // в последней строке нет переноса
        );
        System.out.print(
                ANSI_CYAN
                        + " 04 line 60 mins : " // line 04
                        + hoursLine
                        + " : real seconds from time : " + realSecond + ", "
                        + "independent count : " + independentCount + "   "
                        + " cursor value : " + cursor
                        + ANSI_RESET
//                        + "\n"  // в последней строке нет переноса
        );
        ;
        System.out.print(
                "\033[F"  // back one line up - from 02 to 01
                        + "\033[F" // from 03 to 02
                        + "\033[F" // from 04 to 03
                        + "\033[F" // from 05 to 03
                        + "\r");    // back to the beginning of a line
    }


    private void seconds60() {

        StringBuilder sb = new StringBuilder();
        int a;

        if (seconds60 < 10) {
            sb.append(" : 0" + seconds60 + " : ");
        } else {
            sb.append(" : " + seconds60 + " : ");
        }

        // цвет курсора
//        cursorColor();

        // цвет отсчитанных секунд минус один
        for (a = 0; a < seconds60; a++) {
            if ((a % 10) == 0) {
                sb.append(" ");
            }
            sb.append(ANSI_YELLOW);
            sb.append("#");
        }


        // цвет оставшихся (неотсчитанных) секунд
        for (int i = a; i < 60; i++) {
            if ((i % 10) == 0) {
                sb.append(" ");
            }
            sb.append(ANSI_RED);
            sb.append("#");
        }

        secondsLine = sb.toString();

    }

    private void cursorColor() {
        Timer timer = new Timer();
        timer.schedule(new Line02_30min(), 0, 100);
    }

    private void minutes60() {
        StringBuilder sb = new StringBuilder();
        int a;

        if (minutes60 < 10) {
            sb.append(" : 0" + minutes60 + " : ");
        } else {
            sb.append(" : " + minutes60 + " : ");
        }

        String colorrr = ANSI_WHITE;

        for (a = 0; a < minutes60; a++) {
            if ((a % 10) == 0) {
                sb.append(" ");
            }

            if (a < 20)
                colorrr = ANSI_GREEN;
            else if (a < 40)
                colorrr = ANSI_YELLOW;
            else
                colorrr = ANSI_RED;

            sb.append(colorrr);
            sb.append("#");
        }


        for (int i = a; i < 60; i++) {
            if ((i % 10) == 0) {
                sb.append(" ");
            }

//            int m = 90; // white pale
            int m = 2; // even more  white pale

            if (minutes60 > 40 && i >= 40) {
                sb.append(
                        "\u001B[5m" +
                                "\u001B[" + m + "m"
                );
                sb.append("#");
            } else {
                sb.append("\u001B[" + m + "m");
                sb.append("#");
            }


        }


        minutesLine = sb.toString();

    }

    private void housr03() {
        StringBuilder sb = new StringBuilder();
        int a;

        if (hours03 < 10) {
            sb.append(" : 0" + hours03 + " : ");
        } else {
            sb.append(" : " + hours03 + " : ");
        }


        for (a = 0; a < hours03; a++) {
            if ((a % 10) == 0) {
                sb.append(" ");
            }
            sb.append(ANSI_CYAN);
            sb.append("#");
        }


        for (int i = a; i < 3; i++) {  // 3 часа
            if ((i % 10) == 0) {
                sb.append(" ");
            }
            sb.append(ANSI_RED);
            sb.append("#");
        }

        hoursLine = sb.toString();

    }
}
