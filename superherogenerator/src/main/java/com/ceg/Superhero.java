package com.ceg;

//package com.example.superhero;

import java.util.List;

public class Superhero {
    private String heroName;
    private String civilianFirstName;
    private String civilianLastName;
    private List<String> powerSet;
    private List<Address> addresses;

    public Superhero() {
    }

    public Superhero(String heroName,
            String civilianFirstName,
            String civilianLastName,
            List<String> powerSet,
            List<Address> addresses) {
        this.heroName = heroName;
        this.civilianFirstName = civilianFirstName;
        this.civilianLastName = civilianLastName;
        this.powerSet = powerSet;
        this.addresses = addresses;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getCivilianFirstName() {
        return civilianFirstName;
    }

    public void setCivilianFirstName(String civilianFirstName) {
        this.civilianFirstName = civilianFirstName;
    }

    public String getCivilianLastName() {
        return civilianLastName;
    }

    public void setCivilianLastName(String civilianLastName) {
        this.civilianLastName = civilianLastName;
    }

    public List<String> getPowerSet() {
        return powerSet;
    }

    public void setPowerSet(List<String> powerSet) {
        this.powerSet = powerSet;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
