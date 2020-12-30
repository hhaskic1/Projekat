package ba.unsa.etf.rpr.projekat;

import java.time.LocalDate;

public class Jobs {

    private int id;
    private String name;
    private LocalDate beginingDate;
    private LocalDate endDate;
    private String contractor;

    public Jobs(int id, String name, LocalDate beginingDate, LocalDate endDate, String contractor) {
        this.id = id;
        this.name = name;
        this.beginingDate = beginingDate;
        this.endDate = endDate;
        this.contractor = contractor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBeginingDate() {
        return beginingDate;
    }

    public void setBeginingDate(LocalDate beginingDate) {
        this.beginingDate = beginingDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    @Override
    public String toString() {
        String s = "";
        s += getName() + ": from " + getBeginingDate().toString();
        if(getEndDate()!= null) s += " to " + getEndDate().toString();
        return s;
    }
}
