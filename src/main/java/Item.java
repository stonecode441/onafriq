/**
 * Created By Chivo
 */

public class Item {
    public Item(String label, String price) {
        this.label = label;
        this.price = price;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    String label;
    String price;
    public String toString() {
        return "Label: " + label + ", Price: " + price;
    }
}


