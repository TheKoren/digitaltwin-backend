package com.koren.digitaltwin.analysis;

import com.koren.digitaltwin.models.message.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Class that analyzes stability related things.
public class StabilityAnalyzer {

    static List<Long> timeDifferences = new ArrayList<>();
    // TODO: Iterate through last 100 elements in the database.
    public static void detectDelays(List<Message> messageList) {
        for (int i = 1; i < messageList.size(); i++) {
            Message currentMessage = messageList.get(i);
            Message previousMessage = messageList.get(i - 1);

            long timeDifference = currentMessage.getTimestamp().toEpochMilli() - previousMessage.getTimestamp().toEpochMilli();
            timeDifferences.add(timeDifference);
        }
        calculateStatistics(timeDifferences);
    }

    private static void calculateStatistics(List<Long> timeDifferences) {
        if (timeDifferences.isEmpty()) {
            System.out.println("No time differences to calculate statistics.");
            return;
        }
        long sum = 0;
        for (long difference : timeDifferences) {
            sum+=difference;
        }
        long average = sum / timeDifferences.size();
        long lowest = Collections.min(timeDifferences);
        long highest = Collections.max(timeDifferences);
        Collections.sort(timeDifferences);
        long median;
        if (timeDifferences.size() % 2 == 0) {
            median = (timeDifferences.get(timeDifferences.size() / 2 - 1)) +
                     (timeDifferences.get(timeDifferences.size() / 2 + 1) / 2);
        } else {
            median = timeDifferences.get(timeDifferences.size() / 2);
        }
        System.out.println("Average time difference: " + average + " milliseconds");
        System.out.println("Lowest time difference: " + lowest + " milliseconds");
        System.out.println("Highest time difference: " + highest + " milliseconds");
        System.out.println("Median time difference: " + median + " milliseconds");
    }
}