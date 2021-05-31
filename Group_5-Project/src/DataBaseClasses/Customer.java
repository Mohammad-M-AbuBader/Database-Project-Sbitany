/**
 * @autor: Mohammad AbuBader
 * ID: 1190478
 * At: 17-5-2021  3:00 AM
 */
package DataBaseClasses;

public class Customer {
    private String customerID;
    private String customerName;
    private String cardID;
    private String cityId;
    private String phone;
    private String villageID;
    private String regionName;
    private String streetName;
    private String buildingNumber;
    private String address;

    public Customer() {
    }

    public Customer(String customerID, String customerName, String cardID, String cityId, String phone, String villageID,
                    String regionName, String streetName, String buildingNumber, String address) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.cardID = cardID;
        this.cityId = cityId;
        this.phone = phone;
        this.villageID = villageID;
        this.regionName = regionName;
        this.streetName = streetName;
        this.buildingNumber = buildingNumber;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVillageID() {
        return villageID;
    }

    public void setVillageID(String villageID) {
        this.villageID = villageID;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    @Override
    public String toString() {
        return
                "customerID: " + customerID +
                        ", customerName: " + customerName +
                        ", cardID: " + cardID +
                        ", cityId: " + cityId +
                        ", phone: " + phone +
                        ", villageID: " + villageID +
                        ", regionName: " + regionName +
                        ", streetName: " + streetName +
                        ", buildingNumber: " + buildingNumber +
                        ",address: " + address;

    }
}
