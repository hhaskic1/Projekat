package ba.unsa.etf.rpr.projekat;

public enum BuildingType {

    NewBuilding(1),
    OldBuilding(2),
    Mall(3);

    private final int level;

    private BuildingType(int level) {this.level=level;}

    public int getLevel() {return this.level;}




}
