package ru.vk.timescheduller.constants;

public enum COLOR {
    BLACK("\u001B[30m"),
    BLUE("\u001B[34m"),
    CYAN("\u001B[36m"),
    GREEN("\u001B[32m"),
    PURPLE("\u001B[35m"),
    RED("\u001B[31m"),
    WHITE("\u001B[37m"),
    YELLOW("\u001B[33m");

    private final String color;

    COLOR(String color) {
        this.color = color;
    }

}
