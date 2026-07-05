package vn.edu.vitacademy.model;

public class UserCredential {
    private String username;
    private String password;
    private String registerDate; // Định dạng yyyy-MM-dd

    public UserCredential() {}

    public UserCredential(String username, String password, String registerDate) {
        this.username = username;
        this.password = password;
        this.registerDate = registerDate;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRegisterDate() { return registerDate; }
    public void setRegisterDate(String registerDate) { this.registerDate = registerDate; }
}
