package ba.unsa.etf.rpr.projekat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuildingManagementDAOTest {

    private BuildingManagementDAO dao = BuildingManagementDAO.getInstance();

    @BeforeEach
    public void resetujBazu() throws SQLException {
        dao.vratiBazuNaDefault();
    }

    @Test
    void regenerateFile() {
        // Testiramo da li će fajl ponovo biti kreiran nakon brisanja
        // Ovaj test može padati na Windowsu zbog lockinga, u tom slučaju pokrenite ovaj test
        // odvojeno od ostalih
        BuildingManagementDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        this.dao = BuildingManagementDAO.getInstance();
        ArrayList<User> korisini = dao.getAllUsers();
        assertEquals("Haris", korisini.get(0).getFirst_name());
        assertEquals("Vedran", korisini.get(1).getFirst_name());
    }
/*
    @Test
    void glavniGrad() {
        Grad nepoznat = dao.glavniGrad("Bosna i Hercegovina");
        assertNull(nepoznat);
        Grad bech = dao.glavniGrad("Austrija");
        assertEquals("Beč", bech.getNaziv());
    }*/

}
