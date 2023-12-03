package Workout.view;

import Workout.interface_adapter.ModifyWorkout.ModifyWorkoutController;
import Workout.interface_adapter.ModifyWorkout.ModifyWorkoutViewModel;
import Workout.interface_adapter.SearchWorkout.WorkoutController;
import Workout.interface_adapter.SearchWorkout.WorkoutState;
import Workout.interface_adapter.SearchWorkout.WorkoutViewModel;
import app.ScheduleUseCaseFactory;
import app.ViewManagerModel;
import app.WorkoutUseCaseFactory;
import data_access.ExercisesDAO;
import com.google.gson.Gson;
import data_access.FacadeDAO;
import data_access.FirestoreDAO;
import data_access.GoogleCalendarDAO;
import entity.Exercise;
import menu.interface_adapter.MenuViewModel;
import signup.interface_adapter.SignupViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WorkoutView extends JPanel implements ActionListener, PropertyChangeListener {


    public final String viewname = "Workout View";
    private List<String> database; // Simulated database

    final JTextField searchField;
    final JTextArea resultArea;

    final JPanel buttonPanel;

    public boolean isShowing = false;

    public final String viewName = "Workout Creator";

    private final WorkoutViewModel workoutViewModel;

    final JButton saveWorkout;
    final JButton exitWorkout;

    final JButton addExercise;

    private final JTextField searchInputField = new JTextField(15);
    //final JButton addExercise;
    private List<Exercise> exerciseList;

    private Exercise standby;

    public WorkoutView(WorkoutController workoutController, WorkoutViewModel workoutViewModel) {
        this.workoutViewModel = workoutViewModel;
        this.workoutViewModel.addPropertyChangeListener(this);


        JFrame frame = new JFrame("Workout Creator");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            // search bar panel
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        JButton searchButton = new JButton(WorkoutViewModel.SEARCH_LABEL);

        JPanel secondPanel = new JPanel();
        JLabel daySelect = new JLabel("Select Day: ");
        JTextField dayInput = new JTextField(10);
        secondPanel.add(daySelect);
        secondPanel.add(dayInput);
        mainPanel.add(secondPanel);



        buttonPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(buttonPanel);

        // Create a result area panel
        JPanel resultPanel = new JPanel();
        resultPanel.setVisible(true);
        resultArea = new JTextArea(10, 30);// Make it read-only

        JPanel buttons = new JPanel();
        addExercise = new JButton(WorkoutViewModel.MAKE_WORKOUT_LABEL);
        buttons.add(addExercise);
        saveWorkout = new JButton(WorkoutViewModel.SAVE_LABEL);
        buttons.add(saveWorkout);
        exitWorkout = new JButton(WorkoutViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(exitWorkout);


            // Add components to the search panel
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);


        //resultPanel.add(new JScrollPane(resultArea));

        // Add the scroll pane to the main panel
        mainPanel.add(searchPanel);
        mainPanel.add(resultPanel);
        mainPanel.add(scrollPane);
        //mainPanel.add(resultPanel);
        mainPanel.add(buttons);


        frame.add(mainPanel);

        frame.setVisible(true);
            // Add ActionListener to the search button
        searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(searchField.getText());
                    WorkoutState workoutState = workoutViewModel.getState();
                    try {
                        workoutController.execute(workoutState.getWorkout(), searchField.getText());

                    } catch (GeneralSecurityException | IOException | NullPointerException |ExecutionException |
                             InterruptedException ex) {
                        JOptionPane.showMessageDialog(null,
                                "Give a valid input",
                                "Invalid",
                                JOptionPane.ERROR_MESSAGE);

                    }

                    Exercise[] exercises = new Gson().fromJson(workoutState.getExercises(), Exercise[].class);
                    System.out.println(exercises.toString());
                    exerciseList = Arrays.asList(exercises);
                    buttonPanel.removeAll();
                    buttonPanel.revalidate();
                    buttonPanel.repaint();
                    addButtons();


                }
            });

        addExercise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ModifyWorkoutViewModel modifyWorkoutViewModel = new ModifyWorkoutViewModel();
                    ModifyWorkoutController mcontroller = ScheduleUseCaseFactory.create(modifyWorkoutViewModel);
                    mcontroller.export(workoutViewModel.currentUser, standby.getName(),
                            Integer.parseInt(dayInput.getText()));
                    workoutController.export(workoutViewModel.currentUser, standby.getName(),
                            Integer.parseInt(dayInput.getText()));
                } catch (NullPointerException ex) {

                    JOptionPane.showMessageDialog(null,
                            "Select a valid number day and/or exercise",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                System.out.println(workoutViewModel.currentUser.getUsername());
            }
        });


        saveWorkout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModifyWorkoutViewModel modifyWorkoutViewModel = new ModifyWorkoutViewModel();
                ScheduleView scheduleView = new ScheduleView(modifyWorkoutViewModel);
                scheduleView.setVisible(true);
            }
        });


        exitWorkout.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e){
//                workoutController.execute(workoutViewModel.currentUser);
//                System.out.println(workoutViewModel.currentUser.getUsername());
                frame.dispose();
            }

        });


    }

    private void addButtons() {
        for (Exercise exercise : exerciseList) {
            JButton button = new JButton(exercise.getName());
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    displayFullEntry(exercise);
                    standby = exercise;
                    System.out.println(standby);
                }
            });
            button.setBorderPainted(false); // Remove the button border
            button.setContentAreaFilled(false);
            buttonPanel.add(button);


            buttonPanel.revalidate();
            buttonPanel.repaint();
        }
    }


    private void displayFullEntry(Exercise exercise) {
        String totalInfo = exercise.toString() + "\n\n" +
                "Type: " + exercise.getType() + "\n" +
                "Muscle: " + exercise.getMuscle() + "\n" +
                "Equipment: " + exercise.getEquipment() + "\n" +
                "Difficulty: " + exercise.getDifficulty() + "\n" +
                "Instructions: " + exercise.getInstructions();
        System.out.println(totalInfo);
        resultArea.setText(totalInfo);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) throws GeneralSecurityException, IOException {
        WorkoutViewModel workoutViewModel = new WorkoutViewModel();
        SignupViewModel signupViewModel = new SignupViewModel();
        MenuViewModel menuViewModel = new MenuViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        ExercisesDAO appDAO = new ExercisesDAO();
        FirestoreDAO firestoreDAO = new FirestoreDAO();
        GoogleCalendarDAO google = new GoogleCalendarDAO();
        FacadeDAO DAO = new FacadeDAO(firestoreDAO, google, appDAO);
        WorkoutController workoutController = WorkoutUseCaseFactory.createWorkoutUseCase(viewManagerModel,
                workoutViewModel, menuViewModel, DAO);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //new WorkoutView(workoutView, workoutViewModel);
                new WorkoutView(workoutController, workoutViewModel);
            }
        });
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
