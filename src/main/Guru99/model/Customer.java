package Guru99.model;

public class Customer {

    private String name;
    private String gender;
    private String dob;
    private String address;
    private String city;
    private String state;
    private String pin;
    private String mobile;
    private String email;
    private String password;

    public Customer() {
    }

    public Customer(String name, String gender, String dob, String address, String city, String state, String pin, String mobile, String email, String password) {
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
        this.city = city;
        this.state = state;
        this.pin = pin;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
    }

}
