package login.interface_adapter;
import app.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
public class LoginViewModel extends ViewModel {
    public final String TITLE_LABEL = "Log In View";
    public final String USERNAME_LABEL = "Enter Username";
    public final String PASSWORD_LABEL = "Enter Password";
    public final String LOGIN_BUTTON_LABEL = "Log in";
    public final String CANCEL_BUTTON_LABEL = "Cancel";
    private LoginState state = new LoginState();

    public LoginViewModel(){super("log in");}

    public void setState(LoginState state){this.state = state;}

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    public void firePropertyChanged(){support.firePropertyChange("state", null, this.state);}

    public void addPropertyChangeListener(PropertyChangeListener listener){
        support.addPropertyChangeListener(listener);
    }

    public LoginState getState(){return state;}


}
