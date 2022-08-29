package com.eduards_project;

import java.io.IOException;

import com.eduards_project.RequestHandler;

public class App {
    public static void main(String[] args) throws IOException {

        RequestHandler handler = new RequestHandler();
        // int sum_of_roundups = handler.getSumOfRoundups();
        // System.out.println("Sum of roundups: " + sum_of_roundups);
        // System.out.println("No of savingGoalLists: " + handler.getSavingsGoalListSize());
        // String savingsGoalUid = handler.createAndReturnSavingsGoalUid(100000);
        // System.out.println(savingsGoalUid);
        // handler.addToSavingsGoal(savingsGoalUid, sum_of_roundups);

        System.out.println("> LAST 7 DAY TRANSACTION ROUND-UP APP < \n");
        System.out.println("The following API calls are done against the Sandbox Customer configured in RequestHandler.java. \n");
        int sum_of_roundups = handler.getSumOfRoundups();
        double sum = sum_of_roundups / 100.0;
        System.out.println("Round-up values for transactions over the past 7 days have added up to: £" + sum);
        String savingsGoalUid = handler.createAndReturnSavingsGoalUid(100000);
        handler.addToSavingsGoal(savingsGoalUid, sum_of_roundups);
        System.out.println("The amount has been added to Savings Goal: " + savingsGoalUid);
    }
}
