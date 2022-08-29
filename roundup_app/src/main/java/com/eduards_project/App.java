// App designed by Eduard Vasilescu | eduard@vasy.co
// Version: 0.1

package com.eduards_project;

import java.io.IOException;
import java.util.Scanner;

import com.eduards_project.RequestHandler;

public class App {

    // the terminal 'interface' is booted up from main
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        // the handler object is used to make all API calls
        RequestHandler handler = new RequestHandler();
        System.out.println(">> LAST 7 DAYS | TRANSACTION ROUND-UP APP << \n");
        System.out.println(
                "The following API calls are done against the Sandbox Customer configured in RequestHandler.java. \n");
        int sum_of_roundups = handler.getSumOfRoundups();
        double sum = sum_of_roundups / 100.0;
        System.out.println("Round-up values for transactions over the past 7 days have added up to: £" + sum + "\n");
        System.out.print("Enter goal amount in pence for new Savings Goal (eg 100000): ");
        int goal_amount = sc.nextInt();
        String savingsGoalUid = handler.createAndReturnSavingsGoalUid(goal_amount);

        System.out.print("\nDeposit £" + sum + " to Savings Goal? (Y/N): ");
        String user_input = sc.next();

        if (user_input.equals("Y")) {
            handler.addToSavingsGoal(savingsGoalUid, sum_of_roundups);
            System.out.println("£" + sum + " has been added to Savings Goal: " + savingsGoalUid);
        } else {
            System.out.println("Sum of round-ups has not been deposited to Savings Goal.");
        }
        System.out.println("\n>> END OF TRANSACTION ROUND-UP APP <<");
        sc.close();
    }
}
