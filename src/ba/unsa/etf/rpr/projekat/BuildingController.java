package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class BuildingController {

    public TableView<Building> buildingTableView;
    public TableColumn<Building,String> columnBuildingAdress;
    public TableColumn<Building,Integer> columnBuildingFlats;

    private ObservableList<Building> buildingObservableList;

    public BuildingController(ArrayList<Building> buildings) {
        buildingObservableList= FXCollections.observableArrayList(buildings);
    }

    @FXML
    public void initialize(){
        buildingTableView.setItems(buildingObservableList);
        columnBuildingAdress.setCellValueFactory(new PropertyValueFactory("adress"));
        columnBuildingFlats.setCellValueFactory(new PropertyValueFactory("numberOfFlats"));
    }

}
