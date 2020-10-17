package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class JobsController {

    public TextField adressID;
    public Button buttonNext;

    public BuildingManagementDAO dao;

    @FXML
    public void initialize() {
        dao = BuildingManagementDAO.getInstance();


    }

    public void buttonNext(){
        Building building=dao.getBuildingByAdress(adressID.getText());
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


}
