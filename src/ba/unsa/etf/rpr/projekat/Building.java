package ba.unsa.etf.rpr.projekat;

import java.util.ArrayList;

public class Building {

    private int id;
    private String adress;
    private String numberOfFlats;
    private int garage;
    private ArrayList<Jobs> jobs = new ArrayList<>();

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
