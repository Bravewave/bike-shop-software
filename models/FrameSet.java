package src.models;

public class FrameSet extends Product {
    private int size;
    private Boolean hasShockAbsorbers;
    private int gears;
    private FrameType frameType;
    public enum FrameType {
        ROAD, MOUNTAIN, HYBRID
    }

    public FrameSet() {
        
    }

    public FrameSet(String serialNumber, String brand, String type, String name, double unitCost, int stockCount, int size, Boolean hasShockAbsorbers, int gears, FrameType frameType) {
        super(serialNumber, brand, type, name, unitCost, stockCount);
        this.size = size;
        this.hasShockAbsorbers = hasShockAbsorbers;
        this.gears = gears;
        this.frameType = frameType;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Boolean getHasShockAbsorbers() {
        return hasShockAbsorbers;
    }

    public void setHasShockAbsorbers(Boolean hasShockAbsorbers) {
        this.hasShockAbsorbers = hasShockAbsorbers;
    }

    public int getGears() {
        return gears;
    }

    public void setGears(int gears) {
        this.gears = gears;
    }

    public FrameType getFrameType() {
        return frameType;
    }

    public void setFrameType(FrameType frameType) {
        this.frameType = frameType;
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
        fields[6] = String.valueOf(this.size);
        fields[7] = String.valueOf(this.hasShockAbsorbers);
        fields[8] = String.valueOf(this.gears);
        fields[9] = String.valueOf(this.frameType);
        return fields;
    }

    public String[] getFieldNames() {
        String[] fieldNames = new String[10];
        fieldNames[0] = "Serial Number";
        fieldNames[1] = "Brand";
        fieldNames[2] = "Type";
        fieldNames[3] = "Name";
        fieldNames[4] = "Unit Cost";
        fieldNames[5] = "Stock Count";
        fieldNames[6] = "Size";
        fieldNames[7] = "Has Shock Absorbers";
        fieldNames[8] = "Gears";
        fieldNames[9] = "Frame Type";
        return fieldNames;
    }
    @Override
    public String toString() {
        return "FrameSet{" + "size=" + size + ", hasShockAbsorbers=" + hasShockAbsorbers + ", gears=" + gears + '}';
    }


}
