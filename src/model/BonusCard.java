package model;

public class BonusCard {

    String number;
    String goodThruMonth;
    String goodThruYear;
    Boolean blocked;
    Boolean expired;
    String holderName;
    double points;

    Transaction transaction;
    


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGoodThruMonth() {
        return goodThruMonth;
    }

    public void setGoodThruMonth(String goodThruMonth) {
        this.goodThruMonth = goodThruMonth;
    }

    public String getGoodThruYear() {
        return goodThruYear;
    }

    public void setGoodThruYear(String goodThruYear) {
        this.goodThruYear = goodThruYear;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public void addBonusPoints(Transaction transaction, String bonusCardNo) {
        try {
            points += transaction.getTotalCost() * 0.05;
            System.out.println("Added 5% of total cost worth of bonus points!");
            System.out.println("Your points: "+points);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
