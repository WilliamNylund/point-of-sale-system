package model;

public class Item {

    private String name;
    private int id;
    private double price;

    public Item(String name, int id, double price){
        this.setName(name);
        this.setPrice(price);
        this.setId(id);
    }
    public String toString(){
        return this.getId() + "    ----    " + this.getName() + "    ----    Price: " + this.getPrice() + "€" ;
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
}
