package ba.unsa.etf.rpr.projekat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
class MainWindowsControllerTest {

    Stage theStage;
    BuildingManagementDAO dao = BuildingManagementDAO.getInstance();
    MainWindowController ctrl;
    User user =dao.getUserByParameters("h1","h1");

    @Start
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
        ctrl = new MainWindowController(user);
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Log in");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        stage.toFront();
        theStage = stage;
    }


    @Test
    public void testButtonExitRegisterController(FxRobot robot) {

        Button btnExit = robot.lookup("#buttonExit").queryAs(Button.class);
        Stage stage = (Stage) btnExit.getScene().getWindow();
        robot.clickOn("#buttonExit");
        assertEquals(false,stage.isShowing());
    }

    @Test
    public void testButtonMunicipalityRegisterController(FxRobot robot) {

        Button btn = robot.lookup("#MuncipalityButton").queryAs(Button.class);
        Stage stage = (Stage) btn.getScene().getWindow();
        robot.clickOn("#MuncipalityButton");

        Button btn2 = robot.lookup("#buttonAdd").queryAs(Button.class);
        Stage stage1 = (Stage) btn2.getScene().getWindow();

        assertEquals(true,stage1.isShowing());

        //Button btnBack = robot.lookup("#buttonBack").queryAs(Button.class);
        robot.clickOn("#buttonBack");

        assertEquals(true,stage.isShowing());

        //Button btn1 = robot.lookup("#userButton").queryAs(Button.class);
        robot.clickOn("#userButton");
        assertEquals(false,stage1.isShowing());

        Button btn3 = robot.lookup("#addUser").queryAs(Button.class);
        Stage stage3 = (Stage) btn3.getScene().getWindow();
        assertEquals(true,stage3.isShowing());

        robot.clickOn("#buttonBack");
        assertEquals(true,stage.isShowing());

        robot.clickOn("#buildingButton");
        assertEquals(false,stage.isShowing());

        Button btn4 = robot.lookup("#addBuilding").queryAs(Button.class);
        Stage stage4 = (Stage) btn4.getScene().getWindow();
        assertEquals(true,stage4.isShowing());

        robot.clickOn("#buttonBack");
        assertEquals(true,stage.isShowing());


        Button btn5 = robot.lookup("#idJobs").queryAs(Button.class);
        Stage stage5 = (Stage) btn5.getScene().getWindow();
        robot.clickOn("#idJobs");
        assertEquals(false,stage5.isShowing());

        robot.clickOn("#buttonBack");
        assertEquals(true,stage.isShowing());


        Button btn6 = robot.lookup("#updateProfile").queryAs(Button.class);
        Stage stage6 = (Stage) btn6.getScene().getWindow();
        robot.clickOn("#updateProfile");
        assertEquals(false,stage6.isShowing());

        robot.clickOn("#buttonBack");
        assertEquals(true,stage.isShowing());

        robot.clickOn("#buttonExit");
        assertEquals(false,stage.isShowing());

    }

}
