package src.models;

public class Product {

    private String serialNumber;
    private String brand;
    private String type;
    private String name;
    private double unitCost;
    private int stockCount;

    public Product() {

    }

    public Product(String serialNumber, String brand, String type, String name, double unitCost, int stockCount) {
        this.serialNumber = serialNumber;
        this.brand = brand;
        this.type = type;
        this.name = name;
        this.unitCost = unitCost;
        this.stockCount = stockCount;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    public int getStockCount() {
        return stockCount;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

    // get all fields

    public String[] getFields() {
        String[] fields = new String[6];
        fields[0] = serialNumber;
        fields[1] = brand;
        fields[2] = type;
        fields[3] = name;
        fields[4] = Double.toString(unitCost);
        fields[5] = Integer.toString(stockCount);
        return fields;
    }

    public String[] getFieldNames() {
        String[] fieldNames = new String[6];
        fieldNames[0] = "Serial Number";
        fieldNames[1] = "Brand";
        fieldNames[2] = "Type";
        fieldNames[3] = "Name";
        fieldNames[4] = "Unit Cost";
        fieldNames[5] = "Stock Count";
        return fieldNames;
    }

    @Override
    public String toString() {
        return "Product{" + "serialNumber=" + serialNumber + ", brand=" + brand + ", type=" + type + ", name=" + name + ", unitCost=" + unitCost + ", stockCount=" + stockCount + '}';
    }
}
