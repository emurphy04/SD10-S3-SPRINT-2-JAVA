public class Items {
    String itemName;
    String itemSku;
    double itemPrice;
    String itemDesc;
    String itemCat;
    String userListed;

    public Items(String itemName, String itemSku, double itemPrice, String itemDesc, String itemCat, String userListed){
        this.itemName = itemName;
        this.itemSku = itemSku;
        this.itemPrice = itemPrice;
        this.itemDesc = itemDesc;
        this.itemCat = itemCat;
        this.userListed = userListed;
    }

    public String getItemCat() {
        return itemCat;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }
    public String getItemSku() {
        return itemSku;
    }

    public void setItemCat(String itemCat) {
        this.itemCat = itemCat;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemSku(String itemSku) {
        this.itemSku = itemSku;
    }

    @Override
    public String toString() {
        return "Item: "+itemName+" | Category: "+itemCat+" | Price: "+itemPrice+" | SKU: "+itemSku+" | Listed by: "+userListed+"\nDescription: "+itemDesc;
    }
}
