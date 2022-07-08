package Classes;

import Functionalities.AddressService;
import com.opencsv.bean.CsvBindByPosition;

public class Address {
    @CsvBindByPosition(position = 0)
    private Long id;
    @CsvBindByPosition(position = 1)
    private String street;
    @CsvBindByPosition(position = 2)
    private String houseNumber;
    private static Long ID = 0L;


    public Address(){
        var addresses = AddressService.getAddresses();
        addresses.add(this);
        AddressService.setAddresses(addresses);
    }
    public Address(String street, String houseNumber) {
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public Address(Long id, String street, String houseNumber) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.id = id;
        var addresses = AddressService.getAddressById();
        addresses.put(id, this);
        AddressService.setAddressById(addresses);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse_number() {
        return houseNumber;
    }

    public void setHouse_number(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public static Long getID() {
        return ID;
    }

    public static void setID(Long ID) {
        Address.ID = ID;
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + id +
            ", street='" + street + '\'' +
            ", houseNumber=" + houseNumber +
            '}';
    }

    public Address clone() throws CloneNotSupportedException
    {
        Address address = new Address();
        address.street=street;
        address.houseNumber = houseNumber;
        return address;
    }

}
