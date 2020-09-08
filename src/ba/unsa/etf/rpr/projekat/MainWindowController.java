package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class MainWindowController {

    public Button buildingButton;
    public Button userButton;
    public Button MuncipalityButton;

    private BuildingManagementDAO dao;

    public MainWindowController() {
        dao=BuildingManagementDAO.getInstance();
    }

    public void muncipalityAction(){
        try {
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = null;
            loader = new FXMLLoader(getClass().getResource("/fxml/MuncipalityList.fxml"));
            MuncipalityController muncipalityController = new MuncipalityController(dao.getAllMuncipality());
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

}
