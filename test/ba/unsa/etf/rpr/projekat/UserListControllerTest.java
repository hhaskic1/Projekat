package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.ArrayList;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
class UserListControllerTest {

    Stage theStage;
    BuildingManagementDAO dao = BuildingManagementDAO.getInstance();
    UserWindowController ctrl;
    User user = dao.getUserByParameters("h1", "h1");
    //ArrayList<Building> buildings=dao.getAllBuildings();
    ArrayList<User> users=dao.getAllUsers();

    @Start
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserWindow.fxml"));
        ctrl = new UserWindowController(users);
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Log in");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        stage.toFront();
        theStage = stage;
    }

    @Test
    public void testButtonAddUser(FxRobot robot) {
        robot.clickOn("#addUser");
        robot.clickOn("#firstname");
        robot.write("Meho");
        robot.clickOn("#lastname");
        robot.write("Mehic");
        robot.clickOn("#phone");
        robot.write("123");
        robot.clickOn("#email");
        robot.write("h@gmail.com");
        robot.clickOn("#adress");
        robot.write("Gazijska");
        robot.clickOn("#username");
        robot.write("ha1");
        robot.clickOn("#password");
        robot.write("ha1");
        robot.clickOn("#repeatPassword");
        robot.write("ha1");
        robot.clickOn("#radioUser");
        robot.clickOn("#save");


        assertEquals(true, theStage.isShowing());
    }


    @Test
    public void testButtonChangeUser(FxRobot robot) {
        robot.clickOn("ALEM");
        robot.clickOn("#changeUser");
        robot.clickOn("#firstname");
        robot.write("Meho1");
        robot.clickOn("#lastname");
        robot.write("Mehic1");
        robot.clickOn("#phone");
        robot.write("1233");
        robot.clickOn("#email");
        robot.write("h1@gmail.com");
        robot.clickOn("#adress");
        robot.write("Gazijska1");
        robot.clickOn("#username");
        robot.write("ha1");
        robot.clickOn("#password");
        robot.write("ha1");
        robot.clickOn("#repeatPassword");
        robot.write("ha1");
        robot.clickOn("#radioGuest");
        robot.clickOn("#save");
        assertEquals(true, theStage.isShowing());
    }


    @Test
    public void testButtonChangeuser(FxRobot robot) {
        robot.clickOn("ALEMMeho1");
        robot.clickOn("#deleteUser");
        assertEquals(true, theStage.isShowing());
    }

    @Test
    public void testButtonExit(FxRobot robot) {
        //robot.clickOn("ALEM");
        robot.clickOn("#addUser");
        robot.clickOn("#firstname");
        robot.write("Meho1");
        robot.clickOn("#lastname");
        robot.write("Mehic1");
        robot.clickOn("#phone");
        robot.write("1233");
        robot.clickOn("#email");
        robot.write("h1@gmail.com");
        robot.clickOn("#adress");
        robot.write("Gazijska1");
        robot.clickOn("#username");
        robot.write("ha1");
        robot.clickOn("#password");
        robot.write("ha1");
        robot.clickOn("#repeatPassword");
        robot.write("ha1");
        robot.clickOn("#radioGuest");
        robot.clickOn("#buttonBack");
        assertEquals(true, theStage.isShowing());
    }

    @Test
    public void testButtonBackFromUserWindow(FxRobot robot) {

        Button btn = robot.lookup("#buttonBack").queryAs(Button.class);
        Stage stage = (Stage) btn.getScene().getWindow();
        robot.clickOn("#buttonBack");
        assertEquals(false, theStage.isShowing());

    }

}