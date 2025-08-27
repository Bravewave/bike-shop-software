package src.models;

public class Handlebars extends Product {
    public enum HandlebarType {
        DROP, FLAT, RISER
    }
    private HandlebarType handlebarType;

    public Handlebars() {
        
    }

    public Handlebars(String serialNumber, String brand, String type, String name, double unitCost, int stockCount, HandlebarType handlebarType) {
        super(serialNumber, brand, type, name, unitCost, stockCount);
        this.handlebarType = handlebarType;
    }

    public HandlebarType getHandlebarType() {
        return handlebarType;
    }

    public void setHandlebarType(HandlebarType handlebarType) {
        this.handlebarType = handlebarType;
    }

    // get all fields
    public String[] getFields() {
        String[] fields = new String[7];
        fields[0] = super.getSerialNumber();
        fields[1] = super.getBrand();
        fields[2] = super.getType();
        fields[3] = super.getName();
        fields[4] = Double.toString(super.getUnitCost());
        fields[5] = Integer.toString(super.getStockCount());
        fields[6] = handlebarType.toString();
        return fields;
    }

    public String[] getFieldNames() {
        String[] fieldNames = new String[7];
        fieldNames[0] = "Serial Number";
        fieldNames[1] = "Brand";
        fieldNames[2] = "Type";
        fieldNames[3] = "Name";
        fieldNames[4] = "Unit Cost";
        fieldNames[5] = "Stock Count";
        fieldNames[6] = "Handlebar Type";
        return fieldNames;
    }

    @Override
    public String toString() {
        return "Handlebars{" + "handlebarType=" + handlebarType + '}';
    }
}
