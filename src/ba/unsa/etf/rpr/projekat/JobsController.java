package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class JobsController {

    public TextField adressID;
    public Button buttonNext;
    private User user;
    public Label labelError;
    public Button details;

    public BuildingManagementDAO dao;

    public JobsController(User user) {
       this.user=user;
    }

    @FXML
    public void initialize() {
        dao = BuildingManagementDAO.getInstance();
        labelError.setVisible(false);

        adressID.textProperty().addListener((o,oldvalue,newvalue)->{

            if(!oldvalue.contentEquals(newvalue)) {
                adressID.getStyleClass().removeAll("poljeNijeIspravno");
                labelError.setVisible(false);

            }
        });

    }

    public void buttonNext(){
        Building building=dao.getBuildingByAdress(adressID.getText());
        int a = dao.getIdMunicipalityFromUser_Municipality(user);
        int b = dao.getIdMunicipalityFromBuilding_Municipality(building);

        if(a != b  || a == -1 || b == -1) {
            labelError.setVisible(true);
            adressID.getStyleClass().add("poljeNijeIspravno");
            return ;
        }

        try {
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = null;
            loader = new FXMLLoader(getClass().getResource("/fxml/AddJobs.fxml"));
            AddJobsController muncipalityController = new AddJobsController(building);
            loader.setController(muncipalityController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();

            stage.setOnHiding(windowEvent ->{
                Stage stage2=(Stage) buttonNext.getScene().getWindow();
                stage2.close();
            } );

        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void detailsAction(){
        try {

            if(adressID.getText()==null) {
                labelError.setVisible(true);
                adressID.getStyleClass().add("poljeNijeIspravno");
                return ;
            }

            Building building=dao.getBuildingByAdress(adressID.getText());
            int a = dao.getIdMunicipalityFromUser_Municipality(user);
            int b = dao.getIdMunicipalityFromBuilding_Municipality(building);

            if(a != b  || a == -1 || b == -1) {
                labelError.setVisible(true);
                adressID.getStyleClass().add("poljeNijeIspravno");
                return ;
            }
           new Report().showReport(dao.getConnection(),"/reports/jobReport.jrxml",building.getId(),"buildingID");
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }


}
