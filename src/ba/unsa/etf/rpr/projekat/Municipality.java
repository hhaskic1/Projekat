package ba.unsa.etf.rpr.projekat;

public class Municipality {

    private String nameOfMuncipality;
    private int numberOfBuildings;
    private int idMuncipality;

    public Municipality(String nameOfMuncipality, int numberOfBuildings, int idMuncipality) {
        this.nameOfMuncipality = nameOfMuncipality;
        this.numberOfBuildings = numberOfBuildings;
        this.idMuncipality = idMuncipality;
    }

    public String getNameOfMuncipality() {
        return nameOfMuncipality;
    }

    public void setNameOfMuncipality(String nameOfMuncipality) {
        this.nameOfMuncipality = nameOfMuncipality;
    }

    public int getNumberOfBuildings() {
        return numberOfBuildings;
    }

    public void setNumberOfBuildings(int numberOfBuildings) {
        this.numberOfBuildings = numberOfBuildings;
    }

    public int getIdMuncipality() {
        return idMuncipality;
    }

    public void setIdMuncipality(int idMuncipality) {
        this.idMuncipality = idMuncipality;
    }
}
