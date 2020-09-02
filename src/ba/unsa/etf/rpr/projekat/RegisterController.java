package ba.unsa.etf.rpr.projekat;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class RegisterController {

    public TextField fieldUsername;
    public TextField fieldPassword;
    public Button buttonLogIn;
    public Button buttonExit;

    private BuildingManagementDAO dao;

    public RegisterController() {
    }

    public void actionLogIn(){
        try {
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = null;
            loader = new FXMLLoader(getClass().getResource("/fxml/BuildingList.fxml"));
            BuildingController buildingController= new BuildingController(dao.getBuildingsByUser(fieldUsername.getText(),fieldPassword.getText()));
            loader.setController(buildingController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(){
        dao=BuildingManagementDAO.getInstance();
    }



}