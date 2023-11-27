package Friend.interface_adapter;

import Friend.view.AddFriendFailedView;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class AddFriendFailedViewModel extends ViewModel{
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private int width = 250;
    private int height = 200;
    private String errorMessage = "401";


    public AddFriendFailedViewModel(String viewName){
        super(viewName);
    }
    @Override
    public void firePropertyChanged() {
        support.firePropertyChange("AddFriendFailedViewPropertyChange",null,this.errorMessage);
    }
    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }
    @Override
    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        this.support.addPropertyChangeListener(propertyChangeListener);
    }
    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }


}
