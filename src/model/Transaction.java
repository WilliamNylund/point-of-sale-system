package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    private ImageGenerator imageGenerator;
    private LocalDate paymentDate;

    private static int idCounter = 0;

    public boolean testInfoMessage;  //ONLY FOR TESTING

    private Customer customer;
    private BonusCard bonuscard;

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

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

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


    public void printReceipt() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Would you lika a receipt?",ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            try {
                String element = "";
                int height = 200;
                int length = 0;
                for (int i = 0; i < getItemList().size(); i++) {
                    element += getItemList().get(i).toString() + "   "+System.getProperty("line.separator");
                    height += 20;
                }
                String text = "Receipt " + System.getProperty("line.separator")
                        + System.getProperty("line.separator") + "Items: " + System.getProperty("line.separator")
                        + element + System.getProperty("line.separator") + "Total: " + getTotalCost();
                char[] array = text.toCharArray();
                System.out.println(array);
                length += array.length;
                BufferedImage bi = imageGenerator.createImageWithText(array, length, height);
                File outputfile = new File("receipt.png");
                ImageIO.write(bi, "png", outputfile);

            } catch (Exception e) {
                System.out.println("poop");
                e.printStackTrace();
            }
        } else{
            alert.close();
        }
    }

    public Customer getCustomer() {
        try{
            customer = CustomerRegister.getInstance().findByCustomerBonusCard(Integer.parseInt(getBonusCardNumber()), 2023, 4);

        } catch (Exception e){
            System.out.println("wrong bonus card number/mm/yyyy combination");
        }
        return customer;
    }
}