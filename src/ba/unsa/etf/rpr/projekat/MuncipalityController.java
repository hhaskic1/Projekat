package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MuncipalityController {

    public TableView<Municipality> TableView;
    public TableColumn<Municipality,Integer> columnid;
    public TableColumn<Municipality,String> columName;
    public TableColumn<Municipality,Integer> columnBuildings;

    public Button buttonAdd;
    public Button buttonChange;
    public Button buttonDelete;
    public Button buttonDetails;
    public Button buttonExit;
    public Button buttonBack;




    private ObservableList<Municipality> buildingObservableList;

    private BuildingManagementDAO dao;
    private User user;
    private Boolean isBack = false;

    public MuncipalityController(ArrayList<Municipality> buildings) {
        buildingObservableList= FXCollections.observableArrayList(buildings);
        dao=BuildingManagementDAO.getInstance();
    }

    public MuncipalityController(ArrayList<Municipality> buildings, User user) {
        buildingObservableList= FXCollections.observableArrayList(buildings);
        dao=BuildingManagementDAO.getInstance();
        this.user = user;
    }

    public MuncipalityController(User user){}

    public Boolean getBack() {
        return isBack;
    }

    @FXML
    public void initialize(){
        TableView.setItems(buildingObservableList);
        columnid.setCellValueFactory(new PropertyValueFactory("idMuncipality"));
        columName.setCellValueFactory(new PropertyValueFactory("nameOfMuncipality"));
        columnBuildings.setCellValueFactory(new PropertyValueFactory("numberOfBuildings"));
        if(user.getType() == TypeOfUser.GUEST)  {
            buttonAdd.setDisable(true);
            buttonChange.setDisable(true);
            buttonDelete.setDisable(true);
            buttonDetails.setDisable(true);
        }
    }

    public void actionAddMuncipality(){
        try {
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = null;
            loader = new FXMLLoader(getClass().getResource("/fxml/AddMuncipality.fxml"));
            AddMuncipalityController muncipalityController = new AddMuncipalityController(dao.getAllUsersExeceptGuests());
            loader.setController(muncipalityController);
            root = loader.load();
            stage.setTitle("Add municipality");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();

            Stage stage1 = (Stage) buttonAdd.getScene().getWindow();
            stage1.hide();

            stage.setOnHiding(windowEvent -> {
                if(muncipalityController.getBack())
                    stage1.show();

                buildingObservableList.setAll(dao.getAllMuncipality());
                TableView.setItems(buildingObservableList);
            });

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void deleteMuncipality(){
        Municipality municipality=TableView.getSelectionModel().getSelectedItem();
        dao.deleteMuncipality(municipality);
        buildingObservableList.setAll(dao.getAllMuncipality());
    }

    public void changeAction(){
        Municipality municipality=TableView.getSelectionModel().getSelectedItem();
        if(municipality == null)    return;
        try {
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = null;
            loader = new FXMLLoader(getClass().getResource("/fxml/AddMuncipality.fxml"));
            AddMuncipalityController muncipalityController = new AddMuncipalityController(dao.getAllUsersExeceptGuests(),municipality,dao.getAllManagersInMunicipality(municipality));
            loader.setController(muncipalityController);
            root = loader.load();
            stage.setTitle("Update municipality");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();

            Stage stage1 = (Stage) buttonAdd.getScene().getWindow();
            stage1.hide();

            stage.setOnHiding(windowEvent -> {
                if(muncipalityController.getBack())
                    stage1.show();
                buildingObservableList.setAll(dao.getAllMuncipality());
                TableView.setItems(buildingObservableList);
            });

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void detailsAction(){
        try {
            new Report().showReport(dao.getConnection(),"/reports/muncipalityReport.jrxml");
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    public void ActionButtonBack(){
        isBack = true;

        Stage stage = (Stage) buttonAdd.getScene().getWindow();
        stage.close();
    }

    public void exitAction(){
        System.exit(0);
    }

}
