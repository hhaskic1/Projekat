package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.MediaException;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MainWindowController {

    public Button buildingButton;
    public Button userButton;
    public Button MuncipalityButton;
    public Button updateProfile;

    public Button idJobs;

    private BuildingManagementDAO dao;
    private User user;
    private Boolean muncipalityState = false;

    public MainWindowController() {
        dao=BuildingManagementDAO.getInstance();
    }
    public MainWindowController(User user) {
        dao=BuildingManagementDAO.getInstance();
        this.user = user;
    }

    @FXML
    public void initialize() {

        if(user.getType() == TypeOfUser.GUEST)  {
            idJobs.setDisable(true);
            userButton.setDisable(true);
        }

        if(user.getType() != TypeOfUser.ADMINISTRATOR) {
            MuncipalityButton.setDisable(true);
            userButton.setDisable(true);
        }




    }

    public void muncipalityAction(){
        try {
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = null;
            loader = new FXMLLoader(getClass().getResource("/fxml/MuncipalityList.fxml"));
            MuncipalityController muncipalityController = new MuncipalityController(dao.getAllMuncipality(),user);
            loader.setController(muncipalityController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void userAction(){
        try {
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = null;
            loader = new FXMLLoader(getClass().getResource("/fxml/UserWindow.fxml"));
            UserWindowController muncipalityController = new UserWindowController(dao.getAllUsers());
            loader.setController(muncipalityController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void updateProfile(){
        try {
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = null;
            loader = new FXMLLoader(getClass().getResource("/fxml/AddUsers.fxml"));
            AddUserController muncipalityController = new AddUserController(user,true);
            loader.setController(muncipalityController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void buildingAction(){
        ArrayList<Building> buildings = new ArrayList<>();
        if(user.getType() == TypeOfUser.USER) buildings = dao.getAllBuildingsFromUser(user);
                else buildings = dao.getAllBuildings();
        try {
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = null;
            loader = new FXMLLoader(getClass().getResource("/fxml/BuildingList.fxml"));
            BuildingListController muncipalityController = new BuildingListController(buildings,user);
            loader.setController(muncipalityController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void idJobs(){
        try {
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = null;
            loader = new FXMLLoader(getClass().getResource("/fxml/Jobs.fxml"));
            JobsController muncipalityController = new JobsController();
            loader.setController(muncipalityController);
            root = loader.load();
            stage.setTitle("");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();


        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
