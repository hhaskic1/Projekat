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
    private Municipality municipality=null;

    public AddMuncipalityController(ArrayList<User>users) {
        dao=BuildingManagementDAO.getInstance();
        observableList= FXCollections.observableArrayList(users);
    }

    public AddMuncipalityController(ArrayList<User>users,Municipality municipality) {
        dao=BuildingManagementDAO.getInstance();
        observableList= FXCollections.observableArrayList(users);
        this.municipality=municipality;
    }

    public void actionSave(){
        if(municipality==null)
        dao.AddMuncipality(nameID.getText(),managerID.getSelectionModel().getSelectedItem());
        else{
            //dao.updateMuncipality(nameID.getText(),managerID.getSelectionModel().getSelectedItem(), );
        }
        Stage stage=(Stage) saveBack.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        dao = BuildingManagementDAO.getInstance();
        managerID.setItems(observableList);
        if(municipality!=null){
            nameID.setText(municipality.getNameOfMuncipality());
            managerID.getSelectionModel().select(dao.getUserFromMuncipality(municipality));
        }

    }


}
