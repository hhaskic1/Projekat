package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddJobsController {

    public TextField nameJobs;
    public TextField dateJob;
    public TextField jobFinish;
    public TextField contractor;
    public Button buttonSave;

    private DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");


    private Jobs jobs;

    private Building building;
    private BuildingManagementDAO dao;

    public AddJobsController(Building building) {
        this.building = building;
    }

    @FXML
    public void initialize() {
        dao = BuildingManagementDAO.getInstance();
    }

    public void buttonSave(){

        jobs=new Jobs(dao.getNextJobId(),nameJobs.getText(),LocalDate.parse(dateJob.getText(),formatter),LocalDate.parse(jobFinish.getText(),formatter),contractor.getText() );

        building.addJob(jobs);

        dao.addJobsToBuilding(jobs,building);

        Stage stage=(Stage) buttonSave.getScene().getWindow();
        stage.close();

    }


}
