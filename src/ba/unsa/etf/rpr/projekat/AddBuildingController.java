package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddBuildingController {

    public TextField adress;
    public TextField flats;
    public TextField garage;
    public Button buttonSave;
    public Label labela;
    public Label labelMunciplaity;
    public TextField flors;
    public TextField elevators;
    public TextField yearOfBulit;
    public Button back;

    public RadioButton newbuilding;
    public RadioButton oldBuilding;
    public RadioButton mall;
    public ComboBox<User> comboHead = new ComboBox<>();
    public ComboBox<Municipality> combo = new ComboBox<>();

    public ToggleGroup toggleGroup=new ToggleGroup();
    private Building buidling;

    private ObservableList<Municipality> observableList;
    private ObservableList<User> observableListOfGuests;
    private User user;
    private Municipality municipality;
    private BuildingManagementDAO dao;
    private Boolean updateState = false;
    private Boolean isBack = true;


    public AddBuildingController(Building building) {
        this.buidling=building;
    }

    public AddBuildingController(Building building, User user) {
        dao=BuildingManagementDAO.getInstance();
        observableList = FXCollections.observableArrayList(dao.getAllMuncipality());
        this.buidling=building;
        this.user = user;
    }

    public AddBuildingController(Building building, User user, Boolean updateState) {
        dao=BuildingManagementDAO.getInstance();
        observableList = FXCollections.observableArrayList(dao.getAllMuncipality());
        observableListOfGuests = FXCollections.observableArrayList(dao.getAllGuests());
        this.buidling=building;
        this.user = user;
        this.updateState = updateState;
    }

    public AddBuildingController() {
    }

    public AddBuildingController(User user) {
        dao=BuildingManagementDAO.getInstance();
        observableList = FXCollections.observableArrayList(dao.getAllMuncipality());
        observableListOfGuests = FXCollections.observableArrayList(dao.getAllGuests());
        this.user = user;
    }

    public Boolean getBack() {
        return isBack;
    }

    @FXML
    public void initialize(){

        comboHead.setItems(observableListOfGuests);
        combo.setItems(observableList);
        combo.setDisable(true);

        adress.textProperty().addListener((o,oldvalue,newvalue)->{

            if(!oldvalue.contentEquals(newvalue)) {
                adress.getStyleClass().removeAll("poljeNijeIspravno");
                labela.setVisible(false);

            }
        });


        labela.setVisible(false);


        newbuilding.setToggleGroup(toggleGroup);
        oldBuilding.setToggleGroup(toggleGroup);
        mall.setToggleGroup(toggleGroup);

        if(buidling==null) {

            newbuilding.setSelected(false);
            oldBuilding.setSelected(false);
            mall.setSelected(false);

            garage.setDisable(true);
        }else {
            adress.setText(buidling.getAdress());
            flats.setText(String.valueOf(buidling.getNumberOfFlats()));
            flors.setText(String.valueOf(buidling.getNumberOfFloors()));
            yearOfBulit.setText(String.valueOf(buidling.getYearOfBuilt()));
            elevators.setText(String.valueOf(buidling.getNumberOfElevators()));
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
            comboHead.getSelectionModel().select(dao.getUserByID(buidling.getGuestId()));

        }

        if(user.getType() == TypeOfUser.ADMINISTRATOR) combo.setDisable(false);
        if(user.getType() == TypeOfUser.USER) {
            combo.setVisible(false);
            combo.getSelectionModel().select(dao.getMuncipalityForUser(user));
            labelMunciplaity.setVisible(false);
        }



        if(updateState) combo.setDisable(true);

    }

    public void actionBuilding(){

        if(oldBuilding.isSelected()) {
            garage.setDisable(true);
        }else {
            garage.setDisable(false);
        }

    }

    public void actionSave(){

        if(!updateState) {
            if ((adress.getText() == "" || flats.getText() == "" || flors.getText() == "" || elevators.getText() == "" || yearOfBulit.getText() == "" || (!oldBuilding.isSelected() && !newbuilding.isSelected() && !mall.isSelected()) || combo.getSelectionModel().getSelectedItem() == null || comboHead.getSelectionModel().getSelectedItem() == null)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Wrong data!!!");
                alert.setContentText("Fields are not filled.");
                alert.showAndWait();
                return;
            }
        }
        if(buidling==null){
            if (dao.isThereBuildingOnThatAdress(adress.getText())) {
                adress.getStyleClass().add("poljeNijeIspravno");
                labela.setVisible(true);
                return;
            }
        }else{

            if (dao.isThereBuildingOnThatAdressUpdate(adress.getText(),buidling.getAdress())) {
                adress.getStyleClass().add("poljeNijeIspravno");
                labela.setVisible(true);
                return;
            }

        }

        Boolean stanje = false;
        if(buidling == null) {
            buidling = new Building(dao.getNextBuildingId(), adress.getText(), Integer.parseInt(flats.getText()));
            stanje = true;
        }
        buidling.setAdress(adress.getText());
        buidling.setNumberOfFlats(Integer.parseInt(flats.getText()));
        buidling.setYearOfBuilt(Integer.parseInt(yearOfBulit.getText()));
        buidling.setNumberOfElevators(Integer.parseInt(elevators.getText()));
        buidling.setNumberOfFloors(Integer.parseInt(flors.getText()));
        //buidling.setGarage(Integer.parseInt(garage.getText()));

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

        int headOfBuilding = comboHead.getSelectionModel().getSelectedItem().getId();
        buidling.setGuestId(headOfBuilding);

        if(user.getType() == TypeOfUser.USER)   municipality = dao.getMuncipalityForUser(user);
        if(user.getType() == TypeOfUser.ADMINISTRATOR) municipality = combo.getSelectionModel().getSelectedItem();


        if(stanje)
            dao.addBuilding(buidling, municipality);
        else
            dao.updateBuilding(buidling);
        isBack = true;
        Stage stage=(Stage) buttonSave.getScene().getWindow();
        stage.close();

    }

    public void actionBack(){
        isBack = true;

        Stage stage=(Stage) back.getScene().getWindow();
        stage.close();
    }


}
