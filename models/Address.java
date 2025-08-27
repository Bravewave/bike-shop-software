package src.models;

public class Address {
    private int addressID;
    private String houseName;
    private String roadName;
    private String cityName;
    private String postcode;

    public Address() {
        
    }

    public Address(String houseName, String roadName, String cityName, String postcode) {
        this.houseName = houseName;
        this.roadName = roadName;
        this.cityName = cityName;
        this.postcode = postcode;
        this.addressID = -1; // if addressID is -1, then it is a new address
    }

    public Address(int addressID, String houseName, String roadName, String cityName, String postcode) {
        this.addressID = addressID;
        this.houseName = houseName;
        this.roadName = roadName;
        this.cityName = cityName;
        this.postcode = postcode;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPostcode() {
        return postcode.replaceAll("\\s", "");
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    // get all fields
    public String[] getFields() {
        String[] fields = new String[5];
        fields[0] = Integer.toString(this.addressID);
        fields[1] = this.houseName;
        fields[2] = this.roadName;
        fields[3] = this.cityName;
        fields[4] = this.postcode;
        return fields;
    }

    public String[] getFieldNames() {
        String[] fieldNames = new String[5];
        fieldNames[0] = "Address ID";
        fieldNames[1] = "House Name";
        fieldNames[2] = "Road Name";
        fieldNames[3] = "City Name";
        fieldNames[4] = "Postcode";
        return fieldNames;
    }

    @Override
    public String toString() {
        return "Address{" + "houseName=" + houseName + ", roadName=" + roadName + ", cityName=" + cityName + ", postcode=" + postcode + '}';
    }
    
}
