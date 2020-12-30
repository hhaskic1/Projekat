package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddJobsController {

    public TextField nameJobs;
    public TextField dateJob;
    public TextField jobFinish;
    public TextField contractor;
    public Button buttonSave;
    public Label labela1;
    public Label labela2;
    public Button back;

    private DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");


    private Jobs jobs;
    private Boolean isBack=false;
    private Building building;
    private BuildingManagementDAO dao;

    public AddJobsController(Building building) {
        this.building = building;
    }

    public Boolean getBack() {
        return isBack;
    }

    @FXML
    public void initialize() {
        dao = BuildingManagementDAO.getInstance();
        labela1.setVisible(false);
        labela2.setVisible(false);

        dateJob.textProperty().addListener((o,oldvalue,newvalue)->{

            if(!oldvalue.contentEquals(newvalue)) {
                dateJob.getStyleClass().removeAll("poljeNijeIspravno");
                jobFinish.getStyleClass().removeAll("poljeNijeIspravno");
                labela1.setVisible(false);
                labela2.setVisible(false);

            }
        });

        jobFinish.textProperty().addListener((o,oldvalue,newvalue)->{

            if(!oldvalue.contentEquals(newvalue)) {
                dateJob.getStyleClass().removeAll("poljeNijeIspravno");
                jobFinish.getStyleClass().removeAll("poljeNijeIspravno");
                labela2.setVisible(false);
                labela1.setVisible(false);
            }
        });
    }

    public void buttonSave(){
        if(nameJobs.getText()=="" || dateJob.getText()=="" || contractor.getText()=="") return;
        try{
               if(dateJob.getText()!="" && jobFinish.getText()!=""){
                   if(LocalDate.parse(dateJob.getText(),formatter).isAfter(LocalDate.parse(jobFinish.getText(),formatter))){
                dateJob.getStyleClass().add("poljeNijeIspravno");
                jobFinish.getStyleClass().add("poljeNijeIspravno");
                labela1.setVisible(true);
                labela2.setVisible(true);
                return;
                   }
            }else if(dateJob.getText()=="" && jobFinish.getText()==""){
                   dateJob.getStyleClass().add("poljeNijeIspravno");
                   jobFinish.getStyleClass().add("poljeNijeIspravno");
                   labela1.setVisible(true);
                   labela2.setVisible(true);
               }

        }catch (DateTimeParseException e){
            dateJob.getStyleClass().add("poljeNijeIspravno");
            jobFinish.getStyleClass().add("poljeNijeIspravno");
            labela1.setVisible(true);
            labela2.setVisible(true);
            return;
           }
        if(dateJob.getText()!="" && jobFinish.getText()!="")
        jobs=new Jobs(dao.getNextJobId(),nameJobs.getText(),LocalDate.parse(dateJob.getText(),formatter),LocalDate.parse(jobFinish.getText(),formatter),contractor.getText() );
        else if(dateJob.getText()!="" && jobFinish.getText()=="")
            jobs=new Jobs(dao.getNextJobId(),nameJobs.getText(),LocalDate.parse(dateJob.getText(),formatter),null,contractor.getText() );
        building.addJob(jobs);

        dao.addJobsToBuilding(jobs,building);

        isBack = true;

        Stage stage=(Stage) buttonSave.getScene().getWindow();
        stage.close();

    }

    public void backAction(){
        isBack = true;

        Stage stage=(Stage) back.getScene().getWindow();
        stage.close();
    }


}
