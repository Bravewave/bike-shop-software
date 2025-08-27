package src.models;

public class Bike extends Product {
    private Wheels wheels;
    private FrameSet frameSet;
    private Handlebars handlebars;
    private boolean custom;
    private int bikeID;
    
    public Bike() {
        
    }

    public Bike(String serialNumber, String brand, String type, String name, Wheels wheels, FrameSet frameSet, Handlebars handlebars, boolean custom) {
        super(serialNumber, brand, type, name, 0, 0);
        this.wheels = wheels;
        this.frameSet = frameSet;
        this.handlebars = handlebars;
        this.custom = custom;
        this.bikeID = -1; // if bikeID is -1, then it is a new bike
        super.setUnitCost(calculateTotalCost()); // TODO: add surcharge?
        super.setStockCount(calculateTotalStock());
    }

    public Bike(int bikeID, String serialNumber, String brand, String type, String name, Wheels wheels, FrameSet frameSet, Handlebars handlebars, boolean custom) {
        super(serialNumber, brand, type, name, 0, 0);
        this.wheels = wheels;
        this.frameSet = frameSet;
        this.handlebars = handlebars;
        this.custom = custom;
        this.bikeID = bikeID;
        super.setUnitCost(calculateTotalCost()); // TODO: add surcharge?
        super.setStockCount(calculateTotalStock());
    }

    public Wheels getWheels() {
        return wheels;
    }

    public void setWheels(Wheels wheels) {
        this.wheels = wheels;
    }

    public FrameSet getFrameSet() {
        return frameSet;
    }

    public void setFrameSet(FrameSet frameSet) {
        this.frameSet = frameSet;
    }

    public Handlebars getHandlebars() {
        return handlebars;
    }

    public void setHandlebars(Handlebars handlebars) {
        this.handlebars = handlebars;
    }

    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }

    public int getBikeID() {
        return bikeID;
    }

    public void setBikeID(int bikeID) {
        this.bikeID = bikeID;
    }

    // return all fields
    public String[] getFields() {
        String[] fields = new String[9];
        fields[0] = String.valueOf(bikeID);
        fields[1] = super.getSerialNumber();
        fields[2] = super.getBrand();
        fields[3] = super.getType();
        fields[4] = super.getName();
        fields[5] = String.valueOf(wheels.getName());
        fields[6] = String.valueOf(frameSet.getName());
        fields[7] = String.valueOf(handlebars.getName());
        fields[8] = String.valueOf(custom);
        return fields;
    }

    public String[] getFieldNames() {
        String[] fieldNames = new String[9];
        fieldNames[0] = "Bike ID";
        fieldNames[1] = "Serial Number";
        fieldNames[2] = "Brand";
        fieldNames[3] = "Type";
        fieldNames[4] = "Name";
        fieldNames[5] = "Wheels";
        fieldNames[6] = "Frame Set";
        fieldNames[7] = "Handlebars";
        fieldNames[8] = "Custom";
        return fieldNames;
    }

    @Override
    public String toString() {
        return "Bike{" + "name=" + super.getName() + ", brand=" + super.getBrand() + ", type=" + super.getType() + ", wheels=" + wheels + ", frameSet=" + frameSet + ", handlebars=" + handlebars + ", serial=" + super.getSerialNumber() + '}';
    }

    public double calculateTotalCost() {
        // calculate cost of each individual component
        double wheelsCost = wheels.getUnitCost();
        double frameSetCost = frameSet.getUnitCost();
        double handlebarsCost = handlebars.getUnitCost();

        // calculate total cost of bike
        double totalCost = wheelsCost + frameSetCost + handlebarsCost;

        return totalCost;
    }

    public int calculateTotalStock() {
        // calculate lowest stock of each individual component
        int wheelsStock = wheels.getStockCount();
        int frameSetStock = frameSet.getStockCount();
        int handlebarsStock = handlebars.getStockCount();

        // calculate total stock of bike
        int totalStock = Math.min(wheelsStock, Math.min(frameSetStock, handlebarsStock));

        return totalStock;
    }

    public String generateSerialNumber() {
        // generate a single serial number for the bike
        // based on the serial numbers of each individual component
        // and convert to hex
        String serialNumber = Integer.toHexString(wheels.getSerialNumber().hashCode()) + Integer.toHexString(frameSet.getSerialNumber().hashCode()) + Integer.toHexString(handlebars.getSerialNumber().hashCode());
        return serialNumber;
    }
}
