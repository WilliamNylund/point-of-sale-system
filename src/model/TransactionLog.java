package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

//Singleton
public class TransactionLog {

    private ObservableList<Transaction> pausedTransactions = FXCollections.observableArrayList();

    private ArrayList<Transaction> completedTransactions = new ArrayList<Transaction>();


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
}
