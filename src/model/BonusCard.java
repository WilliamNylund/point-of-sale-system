package model;

public class BonusCard {

    String number;
    String goodThruMonth;
    String goodThruYear;
    Boolean blocked;
    Boolean expired;
    String holderName;


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
}
