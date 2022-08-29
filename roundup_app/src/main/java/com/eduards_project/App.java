package com.eduards_project;

import java.io.IOException;

import com.eduards_project.RequestHandler;

public class App {
    public static void main(String[] args) throws IOException {

        RequestHandler handler = new RequestHandler();
        int sum_of_roundups = handler.getSumOfRoundups();
        System.out.println("Sum of roundups: " + sum_of_roundups);
        System.out.println("No of savingGoalLists: " + handler.getSavingsGoalListSize());
        System.out.println(handler.createAndReturnSavingsGoalUid(100000));
    }
}
