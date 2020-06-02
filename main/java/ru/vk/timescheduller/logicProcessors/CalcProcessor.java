package ru.vk.timescheduller.logicProcessors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.TimerTask;

import static ru.vk.timescheduller.controller.Controller.*;

public class CalcProcessor extends TimerTask {
    private final PrintProcessor printProcessor;
    private LocalDateTime localDateTimeStarted;
    private LocalDateTime localDateTimeNow;
    private int seconds60ForCount = 0;
    private int minutes60ForCount = 0;
    private int hours03 = 0;
    protected static StringBuilder secondsGraphicLine;
    protected static String minutesLine;
    protected static String hoursLine;
    protected static String ANSI_RAINBOW_COLORS;
    private int tempRealSecond;
    protected static int independentCount = 0;
    protected static String currentTime;
    private long secondsFromStart;

    private int colorCode;
    private long hoursDelta;
    private long minutesDelta;
    private int currentSeconds;
    private String passedTime;
    private long secondsDelta;
    protected static StringBuilder secondsToPrint;


    public CalcProcessor(LocalDateTime localDateTimeNow) {
        this.localDateTimeStarted = localDateTimeNow;
        this.secondsFromStart = 0;
        this.printProcessor = new PrintProcessor();
        secondsToPrint = new StringBuilder();
    }

    @Override
    public void run() {
        independentCount++;

        localDateTimeNow = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        currentTime = localDateTimeNow.format(formatter);

        // выполни все это тк следующая секунда наступила
        currentSeconds = localDateTimeNow.getSecond();
        if (currentSeconds != tempRealSecond) {  // блок 1 раз в 1 секунду
            oneTimeInASecondProcessor();
        }
        tempRealSecond = currentSeconds;


        ansiRainbowColors();   // 10 раз в секунду

        secondsLineConstructor(); // 10 times per second

        printProcessor.printResults();  // 10 times per second

    }

    private void oneTimeInASecondProcessor() {
        secondsFromStart++;

        passedTimeCalc();

        secondsLine(); // каждую секунду строка реконструируется
        minutesLine();
        hoursLine();
    }

    private void ansiRainbowColors() {
        // 10 раз в секунду
        // раскраска курсора
        int random = new Random().nextInt(8);
        colorCode = 90 + random;
        ANSI_RAINBOW_COLORS = "\u001B[" + colorCode + "m";
    }

    private void passedTimeCalc() {
        // 1 раз в секунду
        secondsDelta = secondsFromStart % 60;
        minutesDelta = (secondsFromStart / 60) % 60;
        hoursDelta = (secondsFromStart / 60 / 60) % 60;

        passedTime = (hoursDelta < 10 ? "0" + hoursDelta : hoursDelta)
                + ":" + (minutesDelta < 10 ? "0" + minutesDelta : minutesDelta)
                + ":" + (secondsDelta < 10 ? "0" + secondsDelta : secondsDelta);
    }

    private void secondsLine() {
        secondsConstants(); // one time per sec
        makeSecondsPrintable();// one time per sec

    }

    private void makeSecondsPrintable() {
        secondsToPrint.setLength(0);
        if (seconds60ForCount < 10) {
            secondsToPrint.append("0").append(seconds60ForCount);
        } else {
            secondsToPrint.append(seconds60ForCount);
        }
    }

    private void secondsLineConstructor() {
        // 10 times per second


        secondsGraphicLine = new StringBuilder();
        // draw already counted seconds - its yellow
        int a;
        for (a = 1; a < seconds60ForCount; a++) {
            // 1 -> zero is bypassed cause I want the very first second to be in rainbow colors
            secondsGraphicLine.append(ANSI_YELLOW);
            secondsGraphicLine.append("!");
            if ((a % 10 == 0 && a != 0)) {    // разбивка 60 сек на 6 групп по 10
                secondsGraphicLine.append(" "); // каждые 10 сек добавляшка пробел между группами
            }
        }

        // draw cursor in rainbow colors, cursor is going with the seconds
        int random = new Random().nextInt(126 - 33);
        secondsGraphicLine.append(ANSI_RAINBOW_COLORS);
//        secondsLine.append((char) (33 + random)); // 33-126 is visible ascii symbols
        secondsGraphicLine.append("!");

        // цвет оставшихся (неотсчитанных) секунд
        for (int i = a + 1; i <= 60; i++) {
            secondsGraphicLine.append(ANSI_RED);
            secondsGraphicLine.append("#");
            if ((i % 10) == 0) {
                secondsGraphicLine.append(" ");
            }
        }
    }

    private void secondsConstants() {
        // one time per sec
        seconds60ForCount = (int) secondsFromStart % 60;
        // todo use between long minutes = ChronoUnit.MINUTES.between(fromDate, toDate);
    }

    private void minutesLine() {
        if (minutes60ForCount > 59) {
            minutes60ForCount = 0; // у этого счетчика жизненный цикл рестартует каждый час
            hours03++;
        }
        StringBuilder minutes = new StringBuilder();

        if (minutes60ForCount < 10) {
            minutes.append("0").append(minutes60ForCount);
        } else {
            minutes.append(minutes60ForCount);
        }
        minutes.append("   ");

        String colorrr;

        int a;
        for (a = 0; a < minutes60ForCount; a++) {
            if ((a % 10) == 0) {
                minutes.append(" ");
            }

            if (a < 20)
                colorrr = ANSI_GREEN;
            else if (a < 40)
                colorrr = ANSI_YELLOW;
            else
                colorrr = ANSI_RED;

            minutes.append(colorrr);
            minutes.append(">");
        }


        for (int i = a; i < 60; i++) {
            if ((i % 10) == 0) {
                minutes.append(" ");
            }

//            int m = 90; // white pale
            int m = 2; // even more  white pale

            if (minutes60ForCount > 40 && i >= 40) {
                minutes.append("\u001B[5m" +   // blinking color
                        "\u001B[")
                        .append(m)
                        .append("m");
                minutes.append("#");
            } else {
                minutes.append("\u001B[")
                        .append(m)
                        .append("m");
                minutes.append("#");
            }


        }


        minutesLine = minutes.toString();

    }

    private void hoursLine() {
        // 3 часа
        StringBuilder sb = new StringBuilder();
        sb.append("   0").append(hours03).append("   ");

        if (hours03 > 3) {
            hours03 = 0; // у этого счетчика жизненный цикл рестартует каждые 60сек
        }

        int a;
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
            sb.append("#");
        }

        hoursLine = sb.toString();

    }

}
