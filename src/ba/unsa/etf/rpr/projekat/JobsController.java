package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class JobsController {

    //public TextField adressID;
    public ComboBox<Building> comboBuilding = new ComboBox<>();
    public Button buttonNext;
    private User user;
    public Label labelError;
    public Button details;
    public ListView<Jobs> listOfjobs;
    public Button finish;

    public Button buttonBack;
    public Button buttonExit;

    private Boolean isBack = false;
    private ObservableList<Building> buildingObservableList;
    private ObservableList<Jobs> jobsObservableList;
    private DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public Boolean getBack() {
        return isBack;
    }

    public BuildingManagementDAO dao;

    public JobsController(User user) {
        dao = BuildingManagementDAO.getInstance();

        this.user=user;
        if(user.getType() == TypeOfUser.ADMINISTRATOR)
            buildingObservableList = FXCollections.observableArrayList(dao.getAllBuildings());
        else if(user.getType() == TypeOfUser.USER)
            buildingObservableList = FXCollections.observableArrayList(dao.getAllBuildingsFromUser(user));
        else             buildingObservableList = FXCollections.observableArrayList(dao.getAllBuildingsBYGuest(user));


    }

    @FXML
    public void initialize() {


        comboBuilding.setItems(buildingObservableList);
        labelError.setVisible(false);


        comboBuilding.getSelectionModel().selectedItemProperty().addListener((o,oldvalue,newvalue)->{
                jobsObservableList = FXCollections.observableArrayList(dao.getAllJobsForBuilding(comboBuilding.getValue()));
                listOfjobs.setItems(jobsObservableList);
        });
    }

    public void buttonNext(){
        if(comboBuilding.getSelectionModel().getSelectedItem() == null) return;
        try {
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = null;
            loader = new FXMLLoader(getClass().getResource("/fxml/AddJobs.fxml"));
            AddJobsController muncipalityController = new AddJobsController(comboBuilding.getValue());
            loader.setController(muncipalityController);
            root = loader.load();
            stage.setTitle("Add job");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();

            Stage stage1=(Stage) buttonNext.getScene().getWindow();
            stage1.close();

            stage.setOnHiding(windowEvent ->{
                if(muncipalityController.getBack()){
                    stage1.show();
                }
                jobsObservableList = FXCollections.observableArrayList(dao.getAllJobsForBuilding(comboBuilding.getValue()));
                listOfjobs.setItems(jobsObservableList);
                listOfjobs.refresh();
            } );

        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void detailsAction(){
   /*     try {

            if(adressID.getText()==null) {
                labelError.setVisible(true);
                adressID.getStyleClass().add("poljeNijeIspravno");
                return ;
            }

            Building building=dao.getBuildingByAdress(adressID.getText());
            int a = dao.getIdMunicipalityFromUser_Municipality(user);
            int b = dao.getIdMunicipalityFromBuilding_Municipality(building);

            if(user.getType() == TypeOfUser.ADMINISTRATOR)  new Report().showReport(dao.getConnection(),"/reports/jobReport.jrxml",building.getId(),"buildingID");


            if(a != b  || a == -1 || b == -1) {
                labelError.setVisible(true);
                adressID.getStyleClass().add("poljeNijeIspravno");
                return ;
            }
           new Report().showReport(dao.getConnection(),"/reports/jobReport.jrxml",building.getId(),"buildingID");
        } catch (JRException e1) {
            e1.printStackTrace();
     }*/
    }

    public void ActionButtonBack(){
        isBack = true;

        Stage stage=(Stage) buttonBack.getScene().getWindow();
        stage.close();
    }

    public void exitAction(){
        System.exit(0);
    }



    public void actionFinsih(){
        if(listOfjobs.getSelectionModel().getSelectedItem().getEndDate() != null)   return;
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Input end date");
        dialog.setHeaderText("Input end date in format dd/mm/yyyy");
        dialog.setContentText("Please enter end date:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        Jobs job = listOfjobs.getSelectionModel().getSelectedItem();
        if(dialog.getEditor().getText() != ""){
            try {
                LocalDate date = LocalDate.parse(dialog.getEditor().getText(), formatter);
                if(date.isBefore(listOfjobs.getSelectionModel().getSelectedItem().getBeginingDate())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Wrong input data");
                    alert.setContentText("Time of ending is before when the job started !!!");

                    alert.showAndWait();
                    return;
                }
                job.setEndDate(date);
            }catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Wrong input data");
                alert.setContentText("Wrong fomrmat of data!!!");

                alert.showAndWait();
                return;
            }
            dao.updateJob(job);


        }
        else{
            return;
        }
    }

}
