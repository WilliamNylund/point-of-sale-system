package model;

import java.util.List;

public class Transaction {
    private List itemList;
    private double totalCost;


    public Transaction(){
        itemList = null;
        totalCost = 0.0;
    }

    public List getItemList() {
        return itemList;
    }

    public void setItemList(List itemList) {
        this.itemList = itemList;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
