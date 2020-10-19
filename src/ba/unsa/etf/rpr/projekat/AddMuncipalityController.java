package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AddMuncipalityController {

    public TextField nameID;
    public ChoiceBox<User> managerID=new ChoiceBox<>();
    public Button saveBack;
    public Label labela;

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
        if(dao.isThereMuncipality(nameID.getText())){
            nameID.getStyleClass().add("poljeNijeIspravno");
            return;
        }
        if(managerID.getSelectionModel().getSelectedItem()==null){
            labela.setVisible(true);
            return;
        }

        if(municipality==null)
        dao.AddMuncipality(nameID.getText(),managerID.getSelectionModel().getSelectedItem());
        else{
            dao.updateMuncipality(municipality.getIdMuncipality(),nameID.getText(),managerID.getSelectionModel().getSelectedItem());
        }
        Stage stage=(Stage) saveBack.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        labela.setVisible(false);
        dao = BuildingManagementDAO.getInstance();
        managerID.setItems(observableList);
        if(municipality!=null){
            nameID.setText(municipality.getNameOfMuncipality());
            managerID.getSelectionModel().select(dao.getUserFromMuncipality(municipality));
        }

        nameID.textProperty().addListener((o,oldvalue,newvalue)->{

            if(!oldvalue.contentEquals(newvalue)) {
                nameID.getStyleClass().removeAll("poljeNijeIspravno");
            }
        });


        managerID.valueProperty().addListener((o,oldvalue,newvalue)->{

            if(!newvalue.equals(oldvalue)) {
                labela.setVisible(false);
            }
        });

    }


}
