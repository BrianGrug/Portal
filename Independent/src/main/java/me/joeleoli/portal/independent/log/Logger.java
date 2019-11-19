package me.joeleoli.portal.independent.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public static void print(String message) {
        System.out.println("[" + DATE_FORMAT.format(Calendar.getInstance().getTime()) + "] [Portal] " + message);
    }
}
