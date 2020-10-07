package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Transaction {

    private ObservableList<Item> itemList = FXCollections.observableArrayList();

    private double totalCost;

    private double cardAmount;

    private double cashAmount;

    private double outstanding;

    private final int id;

    private boolean paid;
    private String paymentCardNumber;
    private String paymentCardType;
    private String paymentState;
    private String bonusState;
    private String bonusCardNumber;

    private static int idCounter = 0;

    public boolean testInfoMessage;  //ONLY FOR TESTING

    public Transaction(){
        totalCost = 0.0;
        id = this.idCounter;
        idCounter++;
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
        //testInfoMessage = false;      //ONLY FOR TESTING
        if(ChronoUnit.DAYS.between(LocalDate.now(), item.getBestBefore()) <= 2){
            //testInfoMessage = true;    //ONLY FOR TESTING
            infoMessage(item.getName(), item.getBestBefore());    // DISABLE FOR TESTING
        }
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

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void setPaymentInformation(String[] paymentInformation){

        this.paymentCardNumber = paymentInformation[0];
        this.paymentCardType = paymentInformation[1];
        this.paymentState = paymentInformation[2];
        this.bonusState = paymentInformation[3];
        this.bonusCardNumber = paymentInformation[4];


    }

    public String getPaymentCardNumber() {
        return paymentCardNumber;
    }

    public void setPaymentCardNumber(String paymentCardNumber) {
        this.paymentCardNumber = paymentCardNumber;
    }

    public String getPaymentCardType() {
        return paymentCardType;
    }

    public void setPaymentCardType(String paymentCardType) {
        this.paymentCardType = paymentCardType;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }

    public String getBonusState() {
        return bonusState;
    }

    public void setBonusState(String bonusState) {
        this.bonusState = bonusState;
    }

    public String getBonusCardNumber() {
        return bonusCardNumber;
    }

    public void setBonusCardNumber(String bonusCardNumber) {
        this.bonusCardNumber = bonusCardNumber;
    }

    /*
            paymentInformation[0] = paymentCardNumber;
            paymentInformation[1] = paymentCardType;
            paymentInformation[2] = paymentState;
            paymentInformation[3] = bonusState;
            paymentInformation[4] = bonusCardNumber;
    * */

    private void infoMessage(String name, LocalDate date) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFO");
        alert.setHeaderText(null);
        alert.setContentText(name + " expires soon! (BBE: " + date + ")");
        alert.showAndWait();
    }

    public String toString(){

        return "Transaction " + this.id;
    }

    public int getId() {
        return id;

    }
}