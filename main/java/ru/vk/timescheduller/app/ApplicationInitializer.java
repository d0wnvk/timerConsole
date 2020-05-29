package ru.vk.timescheduller.app;

import ru.vk.timescheduller.controller.Controller;

public class ApplicationInitializer {


    public static void main(String[] args) {
        System.out.println("started.");

        Controller controller = new Controller(args);
        controller.start();
    }


}
