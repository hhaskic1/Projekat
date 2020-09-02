package ba.unsa.etf.rpr.projekat;

public class Building {

    private int id;
    private String adress;
    private String numberOfFlats;

    public Building(int id, String adress, String numberOfFlats) {
        this.id = id;
        this.adress = adress;
        this.numberOfFlats = numberOfFlats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getNumberOfFlats() {
        return numberOfFlats;
    }

    public void setNumberOfFlats(String numberOfFlats) {
        this.numberOfFlats = numberOfFlats;
    }
}
