package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AddMuncipalityController {

    public TextField nameID;
    public ChoiceBox<User> managerID=new ChoiceBox<>();
    public Button saveBack;

    private BuildingManagementDAO dao;
    private ObservableList<User> observableList;

    public AddMuncipalityController(ArrayList<User>users) {
        dao=BuildingManagementDAO.getInstance();
        observableList= FXCollections.observableArrayList(users);
    }

    public void actionSave(){
        dao.AddMuncipality(nameID.getText());
        Stage stage=(Stage) saveBack.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        dao = BuildingManagementDAO.getInstance();
        managerID.setItems(observableList);

    }


}
