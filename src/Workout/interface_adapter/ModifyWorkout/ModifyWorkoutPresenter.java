package Workout.interface_adapter.ModifyWorkout;

import Workout.use_case.ModifyWorkout.ModifyWorkoutOutputBoundary;
import Workout.use_case.ModifyWorkout.ModifyWorkoutOutputData;
import data_access.GoogleCalendarDAO;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class ModifyWorkoutPresenter implements ModifyWorkoutOutputBoundary {

    private ModifyWorkoutViewModel modViewModel;

    public ModifyWorkoutPresenter(ModifyWorkoutViewModel modViewModel) {
        this.modViewModel = modViewModel;

    }
//    @Override
//    public void prepareSuccessView(ModifyWorkoutOutputData outputData) {
//        String[][] schedule = convertToNestedArray(outputData.getSchedule());
//        System.out.println(outputData.getExerciseAdded());
//        System.out.println(convertToString(schedule));
//        ModifyWorkoutState workoutState = modViewModel.getState();
//        //modviewModel is null
//        workoutState.setSchedule(schedule);
//        this.modViewModel.setState(workoutState);
//        this.modViewModel.firePropertyChanged();
//    }

    @Override
    public void prepareSuccessView(ModifyWorkoutOutputData outputData) throws GeneralSecurityException, IOException {
        String[][] schedule = convertToNestedArray(outputData.getSchedule());
        System.out.println(convertToString(schedule));
        ModifyWorkoutState workoutState = modViewModel.getState();
        GoogleCalendarDAO googleCalendarDAO = new GoogleCalendarDAO();
        String googleID = googleCalendarDAO.findIdByName("Fitness Tracker");
        String startDate = getDateTimeForDay(1, 8);
        String endDate = getDateTimeForDay(1, 9);
        System.out.println("Start date: " + startDate);
        googleCalendarDAO.createEvent(googleID, convertToString(schedule), "Workout at this time, " +
                        "get to the gym", startDate, endDate);
        //modviewModel is null
        workoutState.setSchedule(schedule);
        this.modViewModel.setState(workoutState);
        this.modViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(ModifyWorkoutOutputData outputData) {
        String[][] schedule = convertToNestedArray(outputData.getSchedule());
        System.out.println("Nothing added to " + outputData.getExerciseAdded());
        ModifyWorkoutState workoutState = modViewModel.getState();
        workoutState.setSchedule(schedule);
        this.modViewModel.setState(workoutState);
        this.modViewModel.firePropertyChanged();
    }

    public static String getDateTimeForDay(int dayOfWeekNumber, int hour) {
        // Ensure the input is valid (0 to 6)
        if (dayOfWeekNumber < 0 || dayOfWeekNumber > 6) {
            throw new IllegalArgumentException("Invalid day of week number. It should be between 0 and 6.");
        }

        LocalDateTime currentDate = LocalDateTime.now();

        // Calculate the difference between the desired day of the week and the current day of the week
        int daysToAdd = dayOfWeekNumber - currentDate.getDayOfWeek().getValue();
        LocalDateTime desiredDate = currentDate.plusDays(daysToAdd);

        desiredDate = desiredDate.withHour(hour).withMinute(0).withSecond(0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return desiredDate.format(formatter);
    }

    public String[][] convertToNestedArray(ArrayList<ArrayList<String>> arrayList) {
        int rows = arrayList.size();
        int cols = 5;

        String[][] nestedArray = new String[rows][cols];

        for (int i = 0; i < rows; i++) {
            ArrayList<String> innerList = arrayList.get(i);

            int innerListSize = innerList.size();
            if (innerListSize < cols) {
                // If inner list has less than 'cols' elements, fill remaining spots with "empty"
                for (int j = 0; j < cols; j++) {
                    nestedArray[i][j] = (j < innerListSize) ? innerList.get(j) : "empty";
                }
            } else {
                // If inner list has 'cols' or more elements, copy them to the nested array
                for (int j = 0; j < cols; j++) {
                    nestedArray[i][j] = innerList.get(j);
                }
            }
        }

        return nestedArray;
    }
    public static String convertToString(String[][] nestedArray) {
        StringBuilder result = new StringBuilder();

        result.append("{");
        for (int i = 0; i < nestedArray.length; i++) {
            result.append("{");
            for (int j = 0; j < nestedArray[i].length; j++) {
                result.append(nestedArray[i][j]);

                if (j < nestedArray[i].length - 1) {
                    result.append(", ");
                }
            }
            result.append("}");
            if (i < nestedArray.length - 1) {
                result.append(", ");
            }
        }

        result.append("}");

        return result.toString();
    }


    public static void main(String[] args) throws GeneralSecurityException, IOException {
        GoogleCalendarDAO googleCalendarDAO = new GoogleCalendarDAO();
        String googleID = googleCalendarDAO.findIdByName("Birthdays");
        String startDate = getDateTimeForDay(6, 8);
        String endDate = getDateTimeForDay(6, 9);
        System.out.println("Start date: " + startDate);
        googleCalendarDAO.createEvent(googleID, "baloney, subronie", "Workout at this time, " +
                "get to the gym", "2023-12-01T10:00:00", "2023-12-01T11:00:00");
    }
}

