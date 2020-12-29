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
class MunicipalityListControllerTest {

    Stage theStage;
    BuildingManagementDAO dao = BuildingManagementDAO.getInstance();
    MuncipalityController ctrl;
    User user = dao.getUserByParameters("h1", "h1");
    ArrayList<Building> buildings=dao.getAllBuildings();
    ArrayList<Municipality> municipalities=dao.getAllMuncipality();

    @Start
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MuncipalityList.fxml"));
        ctrl = new MuncipalityController(municipalities,user);
        loader.setController(ctrl);
        Parent root = loader.load();
        stage.setTitle("Log in");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        stage.toFront();
        theStage = stage;
    }

    @Test
    public void testButtonAddMunicipality(FxRobot robot) {
        robot.clickOn("#buttonAdd");
        robot.lookup("#nameID").tryQuery().isPresent();
        robot.clickOn("#nameID");
        robot.write("Ilidza");

        robot.clickOn("#managerID");
        robot.clickOn("Haris Haskic");
        robot.clickOn("#saveBack");
        assertEquals(true, theStage.isShowing());
    }


    @Test
    public void testButtonAddMunicipalityExit(FxRobot robot) {
        robot.clickOn("#buttonAdd");
        robot.lookup("#nameID").tryQuery().isPresent();
        robot.clickOn("#nameID");
        robot.write("Jug");

        robot.clickOn("#managerID");
        robot.clickOn("Haris Haskic");
        robot.clickOn("#buttonExit");
        assertEquals(true, theStage.isShowing());
    }


    @Test
    public void testButtonAddMunicipalityNoManager(FxRobot robot) {
        robot.clickOn("#buttonAdd");
        robot.lookup("#nameID").tryQuery().isPresent();
        robot.clickOn("#nameID");
        robot.write("Zapad");
        robot.clickOn("#saveBack");
        Label labela = robot.lookup("#labela").queryAs(Label.class);
        assertEquals(true, labela.isVisible());
    }

    @Test
    public void testButtonChangeMunicipality(FxRobot robot) {
        robot.clickOn("ilidza");
        robot.clickOn("#buttonChange");
        robot.lookup("#nameID").tryQuery().isPresent();
        robot.clickOn("#nameID");
        robot.write("Ilidza1");
        robot.clickOn("#managerID");
        robot.clickOn("ALEM ALEM");
        robot.clickOn("#managerID");
        robot.clickOn("asim fdasf");
        robot.clickOn("#managerID");
        robot.clickOn("Haris Haskic");

        robot.clickOn("fudo fudo");
        robot.clickOn("#delete");

        robot.clickOn("#managerID");
        robot.clickOn("fudo fudo");
        robot.clickOn("#add");


        robot.clickOn("#saveBack");
        assertEquals(true, theStage.isShowing());
    }

    @Test
    public void testButtonDeleteMunicipality(FxRobot robot) {
        robot.clickOn("#buttonAdd");
        robot.lookup("#nameID").tryQuery().isPresent();
        robot.clickOn("#nameID");
        robot.write("Ilidza");

        robot.clickOn("#managerID");
        robot.clickOn("Haris Haskic");
        robot.clickOn("#saveBack");

        robot.clickOn("Ilidza");
        robot.clickOn("#buttonDelete");
        //robot.clickOn("#buttonExit");
        assertEquals(true, theStage.isShowing());
    }

}