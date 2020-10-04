package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Transaction {

    private ObservableList<Item> itemList = FXCollections.observableArrayList();

    private double totalCost;

    private double cardAmount;

    private double cashAmount;

    private double outstanding;

    public Transaction(){
        totalCost = 0.0;
    }

    public List getItemList() {
        return itemList;
    }

    public void setItemList(List itemList) {
        this.itemList = (ObservableList<Item>) itemList;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getOutstanding() {
        return outstanding;
    }

    public void setOutstanding() {
        this.outstanding = this.totalCost - this.cardAmount - this.cashAmount;
    }

    public double getCardAmount() {
        return cardAmount;
    }

    public void setCardAmount(double cardAmount) {
        this.cardAmount = cardAmount;
    }

    public double getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(double cashAmount) {
        this.cashAmount = cashAmount;
    }

    public void addItem(Item item){
        itemList.add(item);
        totalCost += item.getPrice();
    }

    public void removeItem(Item item){
        itemList.remove(item);
        totalCost -= item.getPrice();
    }

    public double calculateCost(List itemList){
        for( int i=0;i<itemList.size();i++){
            Item item = new Item();
            totalCost += item.getPrice();
        }
        return totalCost;
    }

    public double calculateDiscount(Double price, Double discount) {

        Double amount = price * discount;
        Double newPrice = price - amount;
        return newPrice;
    }




}