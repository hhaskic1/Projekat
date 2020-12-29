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

public class UserWindowController {

    public Button addUser;
    public Button changeUser;
    public Button deleteUser;
    public Button details;
    public Button buttonExit;
    public Button buttonBack;

    private Boolean isBack = false;

    public TableView<User> tableView;
    public TableColumn<User,String> firstname;
    public TableColumn<User,String> lastname;
    public TableColumn<User,String> phone;
    public TableColumn<User,String> email;
    public TableColumn<User,String> adress;

    private ObservableList<User> userObservableList;
    private BuildingManagementDAO dao;

    public UserWindowController(ArrayList<User> user) {
        userObservableList= FXCollections.observableArrayList(user);
        dao=BuildingManagementDAO.getInstance();
    }

    public Boolean getBack() {
        return isBack;
    }

    @FXML
    public void initialize(){
        tableView.setItems(userObservableList);
        firstname.setCellValueFactory(new PropertyValueFactory("first_name"));
        lastname.setCellValueFactory(new PropertyValueFactory("last_name"));
        phone.setCellValueFactory(new PropertyValueFactory("phone_number"));
        email.setCellValueFactory(new PropertyValueFactory("email"));
        adress.setCellValueFactory(new PropertyValueFactory("adress"));

    }

    public void addAction(){
        try {
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = null;
            loader = new FXMLLoader(getClass().getResource("/fxml/AddUsers.fxml"));
            AddUserController muncipalityController = new AddUserController();
            loader.setController(muncipalityController);
            root = loader.load();
            stage.setTitle("Add user");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();


            Stage stage1 = (Stage) addUser.getScene().getWindow();
            stage1.hide();

            stage.setOnHiding(windowEvent -> {
                if(muncipalityController.getBack())
                    stage1.show();

                userObservableList.setAll(dao.getAllUsers());
                tableView.setItems(userObservableList);
            });

        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void changeUser(){
        User u = tableView.getSelectionModel().getSelectedItem();
        if(u == null)   return;
        try {
            Stage stage = new Stage();
            Parent root;
            FXMLLoader loader = null;
            loader = new FXMLLoader(getClass().getResource("/fxml/AddUsers.fxml"));
            AddUserController muncipalityController = new AddUserController(u);
            loader.setController(muncipalityController);
            root = loader.load();
            stage.setTitle("Change user");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            //stage.setResizable(false);
            stage.show();

            stage.setOnHiding(windowEvent -> {
                userObservableList.setAll(dao.getAllUsers());
                tableView.setItems(userObservableList);
            });

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void deleteAction(){
        User user=tableView.getSelectionModel().getSelectedItem();
        dao.deleteUser(user);
        userObservableList.setAll(dao.getAllUsers());
    }

    public void exitAction(){
        System.exit(0);
    }

    public void detailsAction(){
        try {
            new Report().showReport(dao.getConnection(),"/reports/usersReport.jrxml");
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    public void ActionButtonBack(){
        isBack = true;
        Stage stage=(Stage) buttonBack.getScene().getWindow();
        stage.close();
    }

}
