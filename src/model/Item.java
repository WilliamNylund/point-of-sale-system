package model;

import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

public class Item {

    private String name;
    private int id;
    private double price;
    private int barCode;
    private double vat;
    private LocalDate bestBefore;
    private List<String> keywords = new ArrayList<String>();

    public String toString(){
        return this.barCode + " -- " +this.name + " -- " + this.price + "€";        //  + " -- BBE: " + this.bestBefore
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getBarCode() {
        return barCode;
    }

    public void setBarCode(int barCode) {
        this.barCode = barCode;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public void setBestBefore(LocalDate date) {
        this.bestBefore = date;
    }

    public LocalDate getBestBefore() {
        return bestBefore;
    }

    public double calculateDiscount(Double price, Double discount) {

        Double amount = price * discount;
        Double newPrice = price - amount;
        return newPrice;
    }

}
