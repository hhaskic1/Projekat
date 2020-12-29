package ba.unsa.etf.rpr.projekat;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class AddUserController {


    public TextField firstname;
    public TextField lastname;
    public TextField phone;
    public TextField email;
    public TextField adress;
    public TextField username;
    public TextField password;
    public TextField repeatPassword;
    public RadioButton radioAdmin = new RadioButton();
    public RadioButton radioUser = new RadioButton();
    public RadioButton radioGuest = new RadioButton();
    public ToggleGroup toggleGroup = new ToggleGroup();

    public Button save;
    public Button exit;

    public Button buttonBack;
    private Boolean isBack = false;

    public Boolean getBack() {
        return isBack;
    }

    private BuildingManagementDAO dao;
    private User user=null;
    private Boolean changeProfile = false;
    private Boolean registeringNewFromFirstPage=false;

    public AddUserController() {
    }

    public AddUserController(User user) {
        this.user=user;
    }

    public AddUserController(User user,Boolean changeProfile) {
        this.user=user;
        this.changeProfile=changeProfile;
    }
    public AddUserController(Boolean registeringNewFromFirstPage) {
        this.registeringNewFromFirstPage=registeringNewFromFirstPage;
    }

    @FXML
    public void initialize() {
        dao = BuildingManagementDAO.getInstance();
        radioAdmin.setToggleGroup(toggleGroup);
        radioGuest.setToggleGroup(toggleGroup);
        radioUser.setToggleGroup(toggleGroup);
        radioAdmin.setSelected(true);

        if(user!=null){
            firstname.setText(user.getFirst_name());
            lastname.setText(user.getLast_name());
            phone.setText(user.getPhone_number());
            email.setText(user.getEmail());
            adress.setText(user.getAdress());
            username.setText(user.getUsername());
            password.setText(user.getPassword());
            repeatPassword.setText(user.getPassword());
            if(user.getType() == TypeOfUser.ADMINISTRATOR)  radioAdmin.setSelected(true);
            else if(user.getType() == TypeOfUser.USER)  radioUser.setSelected(true);
            else radioGuest.setSelected(true);
        }

        repeatPassword.textProperty().addListener((o,oldvalue,newvalue)->{

            if(!oldvalue.contentEquals(newvalue)) {
                password.getStyleClass().removeAll("poljeNijeIspravno");
                repeatPassword.getStyleClass().removeAll("poljeNijeIspravno");
                username.getStyleClass().removeAll("poljeNijeIspravno");

            }
        });

        password.textProperty().addListener((o,oldvalue,newvalue)->{

            if(!oldvalue.contentEquals(newvalue)) {
                password.getStyleClass().removeAll("poljeNijeIspravno");
                repeatPassword.getStyleClass().removeAll("poljeNijeIspravno");
                username.getStyleClass().removeAll("poljeNijeIspravno");

            }
        });


        username.textProperty().addListener((o,oldvalue,newvalue)->{

            if(!oldvalue.contentEquals(newvalue)) {
                password.getStyleClass().removeAll("poljeNijeIspravno");
                repeatPassword.getStyleClass().removeAll("poljeNijeIspravno");
                username.getStyleClass().removeAll("poljeNijeIspravno");

            }
        });


        if(changeProfile && user.getType()!=TypeOfUser.ADMINISTRATOR){
            radioUser.setDisable(true);
            radioAdmin.setDisable(true);
            radioGuest.setDisable(true);
        }

        if(registeringNewFromFirstPage){
            radioAdmin.setDisable(true);
            radioGuest.setSelected(true);
        }

    }

    public void saveAction(){

        if(user==null) {
            if (dao.isThereUser(username.getText(), password.getText())) {
                username.getStyleClass().add("poljeNijeIspravno");
                password.getStyleClass().add("poljeNijeIspravno");
                repeatPassword.getStyleClass().add("poljeNijeIspravno");
                return;
            }

        }else{
            if (dao.isThereUserByParametersExceptUser(username.getText(), password.getText(),user.getUsername(),user.getPassword())) {
                username.getStyleClass().add("poljeNijeIspravno");
                password.getStyleClass().add("poljeNijeIspravno");
                repeatPassword.getStyleClass().add("poljeNijeIspravno");
                return;
            }
        }
        if(!password.getText().equals(repeatPassword.getText())){
            password.getStyleClass().add("poljeNijeIspravno");
            repeatPassword.getStyleClass().add("poljeNijeIspravno");
            return;
        }



        if(user==null) {
            user = new User(firstname.getText(), lastname.getText(), phone.getText(), email.getText(), adress.getText(), username.getText(), password.getText());
            if(radioAdmin.isSelected())  user.setType(TypeOfUser.ADMINISTRATOR);
            else if(radioUser.isSelected())  user.setType(TypeOfUser.USER);
            else user.setType(TypeOfUser.GUEST);
            dao.addUser(user);
        }else {
            user.setFirst_name(firstname.getText());
            user.setLast_name(lastname.getText());
            user.setPhone_number(phone.getText());
            user.setEmail(email.getText());
            user.setAdress(adress.getText());
            user.setUsername(username.getText());
            user.setPassword(password.getText());

            if(radioAdmin.isSelected())  user.setType(TypeOfUser.ADMINISTRATOR);
            else if(radioUser.isSelected())  user.setType(TypeOfUser.USER);
            else user.setType(TypeOfUser.GUEST);

            dao.updateUser(user);

        }

        isBack = true;
        Stage stage=(Stage) save.getScene().getWindow();
        stage.close();
    }

    public void exitAction(){
        System.exit(0);
    }

    public void ActionButtonBack(){
        isBack = true;

        Stage stage=(Stage) buttonBack.getScene().getWindow();
        stage.close();
    }


}
