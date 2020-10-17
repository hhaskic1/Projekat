package ba.unsa.etf.rpr.projekat;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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



    private BuildingManagementDAO dao;

    public RegisterController() {
        dao=BuildingManagementDAO.getInstance();
    }

    public void actionLogIn(){
        if(dao.isThereUser(fieldUsername.getText(),fieldPassword.getText())) {
            try {
                Stage stage = new Stage();
                Parent root;
                FXMLLoader loader = null;
                loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
                MainWindowController muncipalityController = new MainWindowController();
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

    @FXML
    public void initialize(){
        dao=BuildingManagementDAO.getInstance();
    }



}
