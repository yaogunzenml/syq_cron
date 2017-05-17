package com.company;

public class Main {

    public static void main(String[] args) {

        String cron = "0 0 7-21 * * ?";
        String cronDate = CronTest3.getContent(cron);
        System.out.println("===================");
        System.out.println(cronDate.toString());
    }
}
