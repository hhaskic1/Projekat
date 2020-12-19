package ba.unsa.etf.rpr.projekat;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class RegisterController {

    public TextField fieldUsername;
    public PasswordField fieldPassword;
    public Button buttonLogIn;
    public Button buttonExit;
    public Button register;
    public Label labela=new Label();



    private BuildingManagementDAO dao;

    public RegisterController() {
        dao=BuildingManagementDAO.getInstance();
    }

    public void actionLogIn(){
        if(dao.checkUser(fieldUsername.getText(),fieldPassword.getText())) {
            User user = dao.getUserByParameters(fieldUsername.getText(),fieldPassword.getText());
            try {
                Stage stage = new Stage();
                Parent root;
                FXMLLoader loader = null;
                loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
                MainWindowController muncipalityController = new MainWindowController(user);
                loader.setController(muncipalityController);
                root = loader.load();
                stage.setTitle("Main Window");
                stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
                //stage.setResizable(false);
                stage.show();

                Stage stage1 = (Stage) buttonLogIn.getScene().getWindow();
                stage1.hide();

                stage.setOnHiding(windowEvent -> {
                    if(muncipalityController.getExit())
                        stage1.show();
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            labela.setVisible(true);
            fieldUsername.getStyleClass().add("poljeNijeIspravno");
            fieldPassword.getStyleClass().add("poljeNijeIspravno");

            fieldUsername.textProperty().addListener((o,oldvalue,newvalue)->{

                if(!oldvalue.contentEquals(newvalue)) {
                    fieldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                    fieldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                    labela.setVisible(false);
                }
            });

            fieldPassword.textProperty().addListener((o,oldvalue,newvalue)->{

                if(!oldvalue.contentEquals(newvalue)) {
                    fieldUsername.getStyleClass().removeAll("poljeNijeIspravno");
                    fieldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                    labela.setVisible(false);
                }
            });

        }
    }

    @FXML
    public void initialize(){
        dao=BuildingManagementDAO.getInstance();
        labela.setVisible(false);

    }

    public void buttonExit(){
        Stage stage=(Stage) buttonExit.getScene().getWindow();
        stage.close();
    }


    public void register(){
        try {
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = null;
            loader = new FXMLLoader(getClass().getResource("/fxml/AddUsers.fxml"));
            AddUserController muncipalityController = new AddUserController(true);
            loader.setController(muncipalityController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
