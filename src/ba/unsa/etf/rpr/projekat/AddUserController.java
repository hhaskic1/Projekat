package ba.unsa.etf.rpr.projekat;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddUserController {


    public TextField firstname;
    public TextField lastname;
    public TextField phone;
    public TextField email;
    public TextField adress;
    public TextField username;
    public TextField password;

    public Button save;
    public Button exit;

    private BuildingManagementDAO dao;
    private User user=null;

    public AddUserController() {
    }

    public AddUserController(User user) {
        this.user=user;

    }

    @FXML
    public void initialize() {
        dao = BuildingManagementDAO.getInstance();

        if(user!=null){
            firstname.setText(user.getFirst_name());
            lastname.setText(user.getLast_name());
            phone.setText(user.getPhone_number());
            email.setText(user.getEmail());
            adress.setText(user.getAdress());
            username.setText(user.getUsername());
            password.setText(user.getPassword());
        }

    }

    public void saveAction(){
        if(user==null) {
            user = new User(firstname.getText(), lastname.getText(), phone.getText(), email.getText(), adress.getText(), username.getText(), password.getText());
            dao.addUser(user);
        }else {
            user.setFirst_name(firstname.getText());
            user.setLast_name(lastname.getText());
            user.setPhone_number(phone.getText());
            user.setEmail(email.getText());
            user.setAdress(adress.getText());
            user.setUsername(username.getText());
            user.setPassword(password.getText());

            dao.updateUser(user);

        }
        Stage stage=(Stage) save.getScene().getWindow();
        stage.close();
    }

    public void exitAction(){

    }

}
