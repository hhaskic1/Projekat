package ba.unsa.etf.rpr.projekat;

public class User {

    private String first_name;
    private String last_name;
    private String phone_number;
    private String email;
    private String adress;
    private int id;
    private TypeOfUser type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    private String username;
    private String password;

    public User(String first_name, String last_name, String phone_number, String email, String adress, String username, String password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.email = email;
        this.adress = adress;
        this.username = username;
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TypeOfUser getType() {
        return type;
    }

    public void setType(TypeOfUser type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return  first_name + " " +
                 last_name ;
    }

}
