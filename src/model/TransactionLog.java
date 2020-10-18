package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.*;

import model.Item;

//Singleton
public class TransactionLog {

    private ObservableList<Transaction> pausedTransactions = FXCollections.observableArrayList();

    private ArrayList<Transaction> completedTransactions = new ArrayList<Transaction>();
    ProductCatalog productCatalog = ProductCatalog.getInstance();


    private static TransactionLog instance = new TransactionLog();

    private TransactionLog(){

    }

    public static TransactionLog getInstance(){
        return instance;
    }





    public ObservableList<Transaction> getPausedTransactions() {
        return pausedTransactions;
    }

    public void setPausedTransactions(ObservableList<Transaction> shelvedTransactions) {
        this.pausedTransactions = shelvedTransactions;
    }

    public ArrayList<Transaction> getCompletedTransactions() {
        return completedTransactions;
    }

    public void setCompletedTransactions(ArrayList<Transaction> completedTransactions) {
        this.completedTransactions = completedTransactions;
    }

    public void createMockTransactions(){
        Transaction transaction = new Transaction();
        transaction.getItemList().add(productCatalog.getCatalog().get(0));
        transaction.getItemList().add(productCatalog.getCatalog().get(0));
        transaction.getItemList().add(productCatalog.getCatalog().get(0));
        transaction.getItemList().add(productCatalog.getCatalog().get(1));
        transaction.getItemList().add(productCatalog.getCatalog().get(4));
        transaction.getItemList().add(productCatalog.getCatalog().get(4));
        transaction.getItemList().add(productCatalog.getCatalog().get(4));
        transaction.getItemList().add(productCatalog.getCatalog().get(4));
        transaction.getItemList().add(productCatalog.getCatalog().get(4));
        transaction.getItemList().add(productCatalog.getCatalog().get(4));
        transaction.getItemList().add(productCatalog.getCatalog().get(4));
        String[] temp = new String[]{"99", "CREDIT", "ACCEPTED", null, null};
        transaction.setPaymentInformation(temp);
        transaction.setTotalCost(20.0);
        transaction.setCardAmount(10);
        transaction.setCashAmount(10);
        transaction.setPaid(true);
        transaction.setPaymentDate(LocalDate.of(2020, 10, 6));
        this.getCompletedTransactions().add(transaction);



        Transaction transaction1 = new Transaction();
        transaction1.getItemList().add(productCatalog.getCatalog().get(0));
        transaction1.getItemList().add(productCatalog.getCatalog().get(2));
        transaction1.getItemList().add(productCatalog.getCatalog().get(2));
        transaction1.getItemList().add(productCatalog.getCatalog().get(2));
        transaction1.getItemList().add(productCatalog.getCatalog().get(3));
        transaction1.getItemList().add(productCatalog.getCatalog().get(3));
        transaction1.getItemList().add(productCatalog.getCatalog().get(0));


        String[] temp1 = new String[]{"13", "DEBIT", "ACCEPTED", null, null};
        transaction1.setPaymentInformation(temp1);
        transaction1.setTotalCost(20.0);
        transaction1.setCardAmount(10);
        transaction1.setCashAmount(10);
        transaction1.setPaid(true);
        transaction1.setPaymentDate(LocalDate.of(2020, 10, 8));
        this.getCompletedTransactions().add(transaction1);
    }

    public Map getProductsSoldByDate(LocalDate startDate, LocalDate endDate, String searchWord, String sex){
        Map<String, Integer> productsSold = new HashMap<String, Integer>();
        if(endDate == null){
            endDate = LocalDate.now();
        }
        if(startDate == null){
            startDate = LocalDate.of(2000,1,1);
        }

        //for every completed transaction
        for (int i = 0; i < this.getCompletedTransactions().size(); i++){
            //check if payment date is between startdate and enddate
            if ((this.getCompletedTransactions().get(i).getPaymentDate().isAfter(startDate) || this.getCompletedTransactions().get(i).getPaymentDate().isEqual(startDate))&& (this.getCompletedTransactions().get(i).getPaymentDate().isBefore(endDate) || this.getCompletedTransactions().get(i).getPaymentDate().isEqual(endDate))){
                //if SEX is correct
                if(sex == null || this.getCompletedTransactions().get(i).getCustomer().getSex().equals(sex)) {
                    List itemList = this.getCompletedTransactions().get(i).getItemList();
                    //for every item in that transaction
                    for (int j = 0; j < itemList.size(); j++) {
                        //if contains product already, add +1 to value
                        Item item = (Item) itemList.get(j);
                        if (item.getName().toLowerCase().contains(searchWord.toLowerCase()) || String.valueOf(item.getBarCode()).equals(searchWord)) {
                            if (productsSold.containsKey(item.getName())) {
                                int currAmount = productsSold.get(item.getName());
                                productsSold.replace(item.getName(), currAmount + 1);
                            } else {
                                productsSold.put(item.getName(), 1);
                            }
                        }
                    }
                }
            }
        }

        productsSold = sortByValue(productsSold);
        return productsSold;
    }

    public Map getProductsSoldByCustomer(int customerNo){
        Map<String, Integer> productsSold = new HashMap<String, Integer>();

        //for every completed transaction
        for (int i = 0; i < this.getCompletedTransactions().size(); i++){
            if (customerNo == this.getCompletedTransactions().get(i).getCustomer().getCustomerNo()){
                List itemList = this.getCompletedTransactions().get(i).getItemList();
                for (int j = 0; j < itemList.size(); j++) {
                    //if contains product already, add +1 to value
                    Item item = (Item) itemList.get(j);
                    if (productsSold.containsKey(item.getName())) {
                        int currAmount = productsSold.get(item.getName());
                        productsSold.replace(item.getName(), currAmount + 1);
                    } else {
                        productsSold.put(item.getName(), 1);
                    }
                }
            }
        }
        productsSold = sortByValue(productsSold);
        return productsSold;
    }

    //sorts hashmap by value
    public <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
