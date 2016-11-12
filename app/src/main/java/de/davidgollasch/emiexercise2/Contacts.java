package de.davidgollasch.emiexercise2;

import java.io.Serializable;

/**
 * Created by tomg on 10/28/16.
 */

public class Contacts implements Serializable{

    private String titel, forename, surname, adress, city, zip, country;
    private int ID;

    public Contacts(String titel, String forename, String surname, String adress, String city, String zip, String country) {

        this.titel = titel;
        this.forename = forename;
        this.surname = surname;
        this.adress = adress;
        this.city = city;
        this.zip = zip;
        this.country = country;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getID() {

        return this.ID;
    }

    public void setID(int ID) {

        this.ID = ID;
    }

    public String toString() {

        return forename + " " + surname;
    }
}
