package ba.unsa.etf.rpr.projekat;

import java.util.ArrayList;

public class Building {

    private int id;
    private String adress;
    private int numberOfFlats;
    private int garage;
    private ArrayList<Jobs> jobs = new ArrayList<>();
    private int guestId;
    private int numberOfFloors;
    private int numberOfElevators;
    private int yearOfBuilt;

    public Building(int id, String adress, int numberOfFlats, int garage, int guestId, int numberOfFloors, int numberOfElevators, int yearOfBuilt, BuildingType type) {
        this.id = id;
        this.adress = adress;
        this.numberOfFlats = numberOfFlats;
        this.garage = garage;
        this.guestId = guestId;
        this.numberOfFloors = numberOfFloors;
        this.numberOfElevators = numberOfElevators;
        this.yearOfBuilt = yearOfBuilt;
        this.type = type;
    }

    public int getGarage() {
        return garage;
    }

    public void setGarage(int garage) {
        this.garage = garage;
    }


    public ArrayList<Jobs> getJobs() {
        return jobs;
    }

    public void setJobs(ArrayList<Jobs> jobs) {
        this.jobs = jobs;
    }

    public void addJob(Jobs job){
        jobs.add(job);
    }

    private BuildingType type;

    public BuildingType getType() {
        return type;
    }

    public void setType(BuildingType type) {
        this.type = type;
    }

    public Building(int id, String adress, int numberOfFlats) {
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

    public int getNumberOfFlats() {
        return numberOfFlats;
    }

    public void setNumberOfFlats(int numberOfFlats) {
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

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public int getNumberOfElevators() {
        return numberOfElevators;
    }

    public void setNumberOfElevators(int numberOfElevators) {
        this.numberOfElevators = numberOfElevators;
    }

    public int getYearOfBuilt() {
        return yearOfBuilt;
    }

    public void setYearOfBuilt(int yearOfBuilt) {
        this.yearOfBuilt = yearOfBuilt;
    }

    @Override
    public String toString() {
        return getAdress();
    }

    public boolean equals(Building obj) {
        if(this.getId() == obj.getId() && obj != null)return true;
        return false;
    }
}
