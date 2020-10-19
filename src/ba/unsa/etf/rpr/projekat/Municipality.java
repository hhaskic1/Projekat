package ba.unsa.etf.rpr.projekat;

public class Municipality {

    private String nameOfMuncipality;
    private int numberOfBuildings;
    private int idMuncipality;

    public Municipality(int id, String nameOfMuncipality, int numberOfBuildings) {
        this.nameOfMuncipality = nameOfMuncipality;
        this.numberOfBuildings = numberOfBuildings;
        this.idMuncipality = id;
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


    public boolean equals(Municipality obj) {
        if(obj==null && this!=null) return true;
        if(this.getNameOfMuncipality()==obj.getNameOfMuncipality())
        return true;
        else return false;
    }

    @Override
    public String toString() {
        return nameOfMuncipality;
    }
}
