package ba.unsa.etf.rpr.projekat;

public class Building {

    private int id;
    private String adress;
    private String numberOfFlats;

    private BuildingType type;

    public BuildingType getType() {
        return type;
    }

    public void setType(BuildingType type) {
        this.type = type;
    }

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

    public String getTypeByString(){
        if(type.getLevel()==1){
            return "NewBuilding";
        }else if(type.getLevel()==2){
            return "OldBuilding";
        }else {
            return "Mall";
        }
    }

}
