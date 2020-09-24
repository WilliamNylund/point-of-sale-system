package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Transaction {

    private ObservableList<Item> itemList = FXCollections.observableArrayList();

    //private List itemList;
    private double totalCost;

    private double cardAmount;

    private double cashAmount;

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

    public void addItem(Item item){
        itemList.add(item);
    }

    public void removeItem(Item item){
        itemList.remove(item);
    }

    public double calculateCost(List itemList){
        for( int i=0;i<itemList.size();i++){
            Item item = new Item();
            totalCost += item.getPrice();
        }
        return totalCost;
    }
}