package menu.interface_adapter;

import Workout.interface_adapter.WorkoutViewModel;
import Workout.view.WorkoutViewManager;
import Workout.view.WorkoutViewManagerModel;
import entity.User;

public class CreateEventPresenter {
    private final WorkoutViewManager workoutViewManager;
    private final WorkoutViewModel workoutViewModel;
    private final WorkoutViewManagerModel workoutViewManagerModel;
    public CreateEventPresenter(WorkoutViewManager w, WorkoutViewModel m){
        this.workoutViewManager = w;
        this.workoutViewModel = m;
        this.workoutViewManagerModel = this.workoutViewManager.getWorkoutViewManagerModel();

    }
    public void prepareSuccessView(User u){
        this.workoutViewManagerModel.setActiveView("Workout View");
        System.out.println("prepare success");
        this.workoutViewManagerModel.firePropertyChanged();
        this.workoutViewModel.setCurrentUser(u);
        this.workoutViewModel.firePropertyChanged();
    }































}
