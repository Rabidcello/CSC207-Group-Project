package entity;
public class User implements CommonUser{
    /* User Class, each User is instantiated using their Google credentials*/
    private String username;
    private String password;
    private String gmail;
  
    public User(){}

    public User(String username, String pass, String gmail){
        this.username = username;
        this.password = pass;
        this.gmail = gmail;
        //TODO: Update UserFactory to include gmail String
    }

    //Getters
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){return this.password;}
    public String getGmail(){return this.gmail;}
}
