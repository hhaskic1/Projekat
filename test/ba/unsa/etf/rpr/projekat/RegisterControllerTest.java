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
class RegisterControllerTest {

    Stage theStage;
    BuildingManagementDAO dao = BuildingManagementDAO.getInstance();
    RegisterController ctrl;

    @Start
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
        ctrl = new RegisterController();
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
    public void testButtonLogInInvalid(FxRobot robot) {

        Button btnLogIn = robot.lookup("#buttonLogIn").queryAs(Button.class);
        robot.clickOn("#fieldUsername");
        robot.write("h1");
        robot.clickOn("#fieldPassword");
        robot.write("a1");
        //Stage stage = (Stage) btnLogIn.getScene().getWindow();
        robot.clickOn("#buttonLogIn");
        Label labela = robot.lookup("#labela").queryAs(Label.class);
        assertEquals(true,labela.isVisible());
    }

    @Test
    public void testButtonLogIn(FxRobot robot) {

        Button btnLogIn = robot.lookup("#buttonLogIn").queryAs(Button.class);
        robot.clickOn("#fieldUsername");
        robot.write("h1");
        robot.clickOn("#fieldPassword");
        robot.write("h1");
        Stage stage = (Stage) btnLogIn.getScene().getWindow();
        robot.clickOn("#buttonLogIn");
        //Label labela = robot.lookup("#labela").queryAs(Label.class);
        assertEquals(false,stage.isShowing());

        Button btnButton = robot.lookup("#MuncipalityButton").queryAs(Button.class);
        Stage stage1= (Stage) btnButton.getScene().getWindow();
        assertEquals(true,stage1.isShowing());
    }

    @Test
    public void testButtonLogInNoInputs(FxRobot robot) {

        Button btnLogIn = robot.lookup("#buttonLogIn").queryAs(Button.class);
        robot.clickOn("#buttonLogIn");
        Label labela = robot.lookup("#labela").queryAs(Label.class);
        assertEquals(true,labela.isVisible());
    }

    @Test
    public void testButtonRegister(FxRobot robot) {

        Button btnRegister = robot.lookup("#register").queryAs(Button.class);
        robot.clickOn("#register");
        Button btnButton = robot.lookup("#save").queryAs(Button.class);
        Stage stage1= (Stage) btnButton.getScene().getWindow();
        assertEquals(true,stage1.isShowing());
    }



}
