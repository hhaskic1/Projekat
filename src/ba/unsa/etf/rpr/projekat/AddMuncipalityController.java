package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AddMuncipalityController {

    public TextField nameID;
    public ComboBox<User> managerID=new ComboBox<>();
    public Button saveBack;
    public Label labela;
    public Button add;
    public Button delete;
    public ListView<User> userListView;
    public Button back;


    private BuildingManagementDAO dao;
    private ObservableList<User> observableList;
    private ObservableList<User> managerList;
    private Municipality municipality=null;
    private Boolean changes = false;
    private Boolean isBack = false;

    public Button buttonExit;


    //add
    public AddMuncipalityController(ArrayList<User>users) {
        dao=BuildingManagementDAO.getInstance();
        observableList= FXCollections.observableArrayList(users);
    }

    public AddMuncipalityController(ArrayList<User>users, ArrayList<User> managerList) {
        dao=BuildingManagementDAO.getInstance();
        observableList= FXCollections.observableArrayList(users);
        this.managerList = FXCollections.observableArrayList(managerList);
    }

    //update
    public AddMuncipalityController(ArrayList<User>users,Municipality municipality) {
        dao=BuildingManagementDAO.getInstance();
        observableList= FXCollections.observableArrayList(users);
        this.municipality=municipality;
    }

    public AddMuncipalityController(ArrayList<User>users,Municipality municipality,  ArrayList<User> managerList) {
        dao=BuildingManagementDAO.getInstance();
        observableList= FXCollections.observableArrayList(users);
        this.municipality=municipality;
        this.managerList = FXCollections.observableArrayList(managerList);
    }

    public Boolean getBack() {
        return isBack;
    }

    public void actionSave(){
        if(!changes)
            return;
        if(dao.isThereMuncipality(nameID.getText()) && !changes){
            nameID.getStyleClass().add("poljeNijeIspravno");
            return;
        }

        if(managerID.getSelectionModel().getSelectedItem()==null){
            labela.setVisible(true);
            return;
        }

        if(municipality==null)
            dao.AddMuncipality(nameID.getText(),managerID.getSelectionModel().getSelectedItem());
        else {
            dao.updateMuncipality(municipality.getIdMuncipality(), nameID.getText());
        }

        isBack = true;

        Stage stage=(Stage) saveBack.getScene().getWindow();
        stage.close();
    }

    public void actionBack(){
        isBack = true;

        Stage stage=(Stage) back.getScene().getWindow();
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
                changes=true;
                nameID.getStyleClass().removeAll("poljeNijeIspravno");
            }
        });


        managerID.valueProperty().addListener((o,oldvalue,newvalue)->{

            if(!newvalue.equals(oldvalue)) {
                labela.setVisible(false);
            }
        });

        if(managerList == null){
            userListView.setDisable(true);
            add.setDisable(true);
            delete.setDisable(true);
        }else{
            userListView.setItems(managerList);
        }

    }

    public void addAction(){
        if(managerID.getSelectionModel().getSelectedItem() != null){
            dao.addUserToMunicipality(managerID.getSelectionModel().getSelectedItem(),municipality);
            managerList.setAll(dao.getAllManagersInMunicipality(municipality));
            userListView.setItems(managerList);
            changes = true;
            dao.deleteUserFromMunicipality(managerID.getSelectionModel().getSelectedItem(), municipality);
        }
    }

    public void deleteAction(){
        changes = true;
        dao.deleteUserFromMunicipality2(userListView.getSelectionModel().getSelectedItem(), municipality);
        managerList.setAll(dao.getAllManagersInMunicipality(municipality));
        userListView.setItems(managerList);
    }

    public void exitAction(){
        System.exit(0);
    }

}
