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




    @FXML
    public void initialize(){

        dao=BuildingManagementDAO.getInstance();

        newbuilding.setToggleGroup(toggleGroup);
        oldBuilding.setToggleGroup(toggleGroup);
        mall.setToggleGroup(toggleGroup);

        newbuilding.setSelected(false);
        oldBuilding.setSelected(false);
        mall.setSelected(false);

        garage.setDisable(true);


    }

    public void actionBuilding(){

        if(newbuilding.isSelected() || mall.isSelected()) {
            garage.setDisable(false);
        }else {
            garage.setDisable(true);
        }

    }

    public void actionSave(){

        if(buidling==null) {
            buidling = new Building(0,adress.getText(),flats.getText());
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
            dao.addBuilding(buidling);
        }
        Stage stage=(Stage) buttonSave.getScene().getWindow();
        stage.close();

    }


}
