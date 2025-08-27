package src.models;

public class Wheels extends Product {
    public enum WheelType {
        ROAD, MOUNTAIN, HYBRID
    }
    public enum BrakeType {
        DISC, RIM
    }
    private int diameter;
    private WheelType wheelType;
    private BrakeType brakeType;
    private String brakeLevers;

    public Wheels() {
        
    }

    public Wheels(String serialNumber, String brand, String type, String name, double unitCost, int stockCount, int diameter, WheelType wheelType, BrakeType brakeType) {
        super(serialNumber, brand, type, name, unitCost, stockCount);
        this.diameter = diameter;
        this.wheelType = wheelType;
        this.brakeType = brakeType;
        this.brakeLevers = "Standard";
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public WheelType getWheelType() {
        return wheelType;
    }

    public void setWheelType(WheelType wheelType) {
        this.wheelType = wheelType;
    }

    public BrakeType getBrakeType() {
        return brakeType;
    }

    public void setBrakeType(BrakeType brakeType) {
        this.brakeType = brakeType;
    }

    public String getBrakeLevers() {
        return brakeLevers;
    }

    public void setBrakeLevers(String brakeLevers) {
        this.brakeLevers = brakeLevers;
    }

    // get all fields
    public String[] getFields() {
        String[] fields = new String[10];
        fields[0] = super.getSerialNumber();
        fields[1] = super.getBrand();
        fields[2] = super.getType();
        fields[3] = super.getName();
        fields[4] = String.valueOf(super.getUnitCost());
        fields[5] = String.valueOf(super.getStockCount());
        fields[6] = String.valueOf(this.diameter);
        fields[7] = String.valueOf(this.wheelType);
        fields[8] = String.valueOf(this.brakeType);
        fields[9] = String.valueOf(this.brakeLevers);
        return fields;
    }

    // get all fields
    public String[] getFieldNames() {
        String[] fields = new String[10];
        fields[0] = "Serial Number";
        fields[1] = "Brand";
        fields[2] = "Type";
        fields[3] = "Name";
        fields[4] = "Unit Cost";
        fields[5] = "Stock Count";
        fields[6] = "Diameter";
        fields[7] = "Wheel Type";
        fields[8] = "Brake Type";
        fields[9] = "Brake Levers";
        return fields;
    }

    @Override
    public String toString() {
        return "Wheels{" + "diameter=" + diameter + ", wheelType=" + wheelType + ", brakeType=" + brakeType + ", brakeLevers=" + brakeLevers + '}';
    }
}
