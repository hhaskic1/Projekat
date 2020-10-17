package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class AddBuildingController {

    public TextField adress;
    public TextField flats;
    public TextField garage;
    public Button buttonSave;

    public RadioButton newbuilding;
    public RadioButton oldBuilding;
    public RadioButton mall;

    public ToggleGroup toggleGroup=new ToggleGroup();
    private Building buidling;

    private BuildingManagementDAO dao;

    public AddBuildingController(Building building) {
        this.buidling=building;
    }

    public AddBuildingController() {
    }

    @FXML
    public void initialize(){
        dao=BuildingManagementDAO.getInstance();

        if(buidling==null) {

            newbuilding.setToggleGroup(toggleGroup);
            oldBuilding.setToggleGroup(toggleGroup);
            mall.setToggleGroup(toggleGroup);

            newbuilding.setSelected(false);
            oldBuilding.setSelected(false);
            mall.setSelected(false);

            garage.setDisable(true);
        }else {
            adress.setText(buidling.getAdress());
            flats.setText(buidling.getNumberOfFlats());
            garage.setText(String.valueOf(buidling.getGarage()));
            if(buidling.getType()==BuildingType.Mall){
                mall.setSelected(true);
                newbuilding.setSelected(false);
                oldBuilding.setSelected(false);
            }else if(buidling.getType()==BuildingType.NewBuilding){
                mall.setSelected(false);
                newbuilding.setSelected(true);
                oldBuilding.setSelected(false);
            }else {
                mall.setSelected(false);
                newbuilding.setSelected(false);
                oldBuilding.setSelected(true);
            }

        }

    }

    public void actionBuilding(){

        if(newbuilding.isSelected() || mall.isSelected()) {
            garage.setDisable(false);
        }else {
            garage.setDisable(true);
        }

    }

    public void actionSave(){
        Boolean stanje = false;
        if(buidling == null) {
            buidling = new Building(0, adress.getText(), flats.getText());
            stanje = true;
        }
        buidling.setAdress(adress.getText());
        buidling.setNumberOfFlats(flats.getText());
        if(newbuilding.isSelected()){
            buidling.setType(BuildingType.NewBuilding);
            buidling.setGarage(Integer.parseInt(garage.getText()));
        }else if(oldBuilding.isSelected()){
            buidling.setType(BuildingType.OldBuilding);
            buidling.setGarage(0);
        }else {
            buidling.setType(BuildingType.Mall);
            buidling.setGarage(Integer.parseInt(garage.getText()));

        }
        if(stanje)
            dao.addBuilding(buidling);
        else
            dao.updateBuilding(buidling);
        
        Stage stage=(Stage) buttonSave.getScene().getWindow();
        stage.close();

    }


}