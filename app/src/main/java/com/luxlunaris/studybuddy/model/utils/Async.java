package com.luxlunaris.studybuddy.model.utils;

public class Async {

    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }


    public static void runTask(Runnable runnable){
        setTimeout(runnable, 0);
    }


}
