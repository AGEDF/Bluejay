import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Bluejay {
    public static void main(String[] args) {
        String filePath = "timecard.csv"; // Taking the csv file as input 

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            // Created a map to store employee information
            Map<String, List<String[]>> employeeData = new HashMap<>();

    
            for (String line : lines) {
                // skipping the header row
                if (line.startsWith("Position ID")) {
                    continue;
                }
                //because it is a csv file i.e entities seperated by ',' we use it to store values in our
                //Storing columns in a string array 
                String[] columns = line.split(",");

                String employeeName = columns[7].replace("\"", ""); //  'employee name' is in column H
                String position = columns[0].replace("\"", ""); //  'position' is in column A
                String timeInStr = columns[2].replace("\"", ""); //  'time' is in column C
                String timeOutStr = columns[3].replace("\"", ""); //  'time out' is in column D

                // Perform analysis based on the specified criteria
                //for part a: who has worked for 7 consecutive days.
                analyzeConsecutiveDays(employeeData, employeeName, columns);

                //for part b: who have less than 10 hours of time between shifts but greater than 1 hour
                analyzeShortShifts(employeeData, employeeName, position, timeInStr, timeOutStr);

                //for part c: Who has worked for more than 14 hours in a single shift
                analyzeLongShifts(employeeData, employeeName, position, timeInStr, timeOutStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // part a 
    private static void analyzeConsecutiveDays(Map<String, List<String[]>> employeeData, String employeeName, String[] columns) {
        // we store records of the employee in a list and set employeename as key 
        List<String[]> records = employeeData.computeIfAbsent(employeeName, k -> new ArrayList<>());
        records.add(columns);

        if (records.size() == 7) {
            System.out.println("Employee: " + employeeName + ", Position: " + columns[0] +
                    " has worked for 7 consecutive days.");
            records.clear(); // Reset for the next set of consecutive days
        }
    }


    //part b
    private static void analyzeShortShifts(Map<String, List<String[]>> employeeData, String employeeName, String position, String timeInStr, String timeOutStr) {
        // Logic for short shifts analysis
        if (!timeInStr.isEmpty() && !timeOutStr.isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                Date timeIn = dateFormat.parse(timeInStr);
                Date timeOut = dateFormat.parse(timeOutStr);

                long shiftDuration = timeOut.getTime() - timeIn.getTime();
                long hoursBetweenShifts = shiftDuration / (60 * 60 * 1000);

                if (hoursBetweenShifts > 1 && hoursBetweenShifts < 10) {
                    System.out.println("Employee: " + employeeName + ", Position: " + position +
                            " has a short shift with duration: " + hoursBetweenShifts + " hours.");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }


    //part c
    private static void analyzeLongShifts(Map<String, List<String[]>> employeeData, String employeeName, String position, String timeInStr, String timeOutStr) {
        // Logic for long shifts analysis
        if (!timeInStr.isEmpty() && !timeOutStr.isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                Date timeIn = dateFormat.parse(timeInStr);
                Date timeOut = dateFormat.parse(timeOutStr);

                long shiftDuration = timeOut.getTime() - timeIn.getTime();
                long hoursInShift = shiftDuration / (60 * 60 * 1000);

                if (hoursInShift > 14) {
                    System.out.println("Employee: " + employeeName + ", Position: " + position +
                            " has worked for more than 14 hours in a single shift.");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
