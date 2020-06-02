package ru.vk.timescheduller.lines;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.TimerTask;

import static ru.vk.timescheduller.controller.Controller.*;

public class CalcProcessor extends TimerTask {
    private final PrintProcessor printProcessor;
    private LocalDateTime localDateTimeStarted;
    private LocalDateTime localDateTimeNow;
    private int seconds60 = 0;
    private int minutes60 = 0;
    private int hours03 = 0;
    protected static String secondsLine;
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


    public CalcProcessor(LocalDateTime localDateTimeNow) {
        this.localDateTimeStarted = localDateTimeNow;
        this.secondsFromStart = 0;
        this.printProcessor = new PrintProcessor();
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
        // todo seconds params - 1 time per 1 sec
        // todo print secondLine 10 times per 1 sec

        secondsConstants(); // one time per sec

    }

    private void secondsLineConstructor() {
        StringBuilder seconds = new StringBuilder();
        seconds = seconds60 < 10 ? seconds.append("0" + seconds60) : seconds.append(seconds60);
        seconds.append(" : ");

        // цвет отсчитанных секунд минус один
        int a;
        for (a = 0; a < seconds60; a++) {
            if ((a % 10) == 0) {
                seconds.append(" ");
            }
            seconds.append(ANSI_YELLOW);
            seconds.append("#");
        }

        // цвет оставшихся (неотсчитанных) секунд
        for (int i = a; i < 60; i++) {
            if ((i % 10) == 0) {
                seconds.append(" ");
            }
            seconds.append(ANSI_RED);
            seconds.append("#");
        }

        secondsLine = seconds.toString();

    }

    private void secondsConstants() {

        seconds60++;
        if (seconds60 > 59) {
            seconds60 = 0; // у этого счетчика жизненный цикл рестартует каждые 60сек
            minutes60++;
        }

    }

    private void minutesLine() {
        if (minutes60 > 59) {
            minutes60 = 0; // у этого счетчика жизненный цикл рестартует каждый час
            hours03++;
        }
        StringBuilder minutes = new StringBuilder();

        minutes = minutes60 < 10 ? minutes.append("0" + minutes60) : minutes.append(minutes60);
        minutes.append(" : ");

        String colorrr = ANSI_WHITE;

        int a;
        for (a = 0; a < minutes60; a++) {
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
            minutes.append("#");
        }


        for (int i = a; i < 60; i++) {
            if ((i % 10) == 0) {
                minutes.append(" ");
            }

//            int m = 90; // white pale
            int m = 2; // even more  white pale

            if (minutes60 > 40 && i >= 40) {
                minutes.append(
                        "\u001B[5m" +   // blinking color
                                "\u001B[" + m + "m"
                );
                minutes.append("#");
            } else {
                minutes.append("\u001B[" + m + "m");
                minutes.append("#");
            }


        }


        minutesLine = minutes.toString();

    }

    private void hoursLine() {
        // 3 часа
        if (hours03 > 3) {
            hours03 = 0; // у этого счетчика жизненный цикл рестартует каждые 60сек
        }
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
