package Friend.interface_adapter;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class FriendViewModel extends ViewModel {
    private String currentUserName;
    private final int width = 350;
    private final int height = 700;
    private final int fontSize = 27;
    private final int userNameWidthRatio = 32;
    private final int userNameHeightRatio = 23;
    private final int firstLineYcoordinate = height/16;
    private final int secondLineYcoordinate = height - (height/8);
    private Color friendPageBackgroundColour = Color.BLACK;
    private Color friendListPageBackgroundColour = Color.GRAY;
    private Color friendPageFontColour = Color.WHITE;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    public FriendViewModel(String viewName) {
        super(viewName);
    }
    @Override
    public void firePropertyChanged() {
        this.support.firePropertyChange("friendViewUsername",null,this.currentUserName);
    }
    @Override
    public void addPropertyChangeListener(PropertyChangeListener x) {
        this.support.addPropertyChangeListener(x);
    }
    public String getCurrentUserName(){
        return this.currentUserName;
    }
    public void setCurrentUserName(String userName){
        this.currentUserName = userName;
    }
    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }
    public int getFontSize(){
        return this.fontSize;
    }
    public int getUserNameWidthRatio(){
        return this.userNameWidthRatio;
    }
    public int getUserNameHeightRatio(){
        return this.userNameHeightRatio;
    }
    public int getFirstLineYcoordinate(){
        return this.firstLineYcoordinate;
    }
    public int getSecondLineYcoordinate(){
        return this.secondLineYcoordinate;
    }
    public Color getFriendPageBackgroundColour(){
        return this.friendPageBackgroundColour;
    }
    public Color getFriendListPageBackgroundColour(){
        return this.friendListPageBackgroundColour;
    }
    public Color getFriendPageFontColour(){
        return this.friendPageFontColour;
    }
}
