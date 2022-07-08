package Functionalities;

import Classes.Address;
import Classes.Audit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddressService extends PlatformService {

    private static AddressService instance;
    private static Audit audit;
    private static HashMap<Long, Address> addressById = new HashMap<>();
    private static List<Address> addresses = new ArrayList<>();

    public static List<Address> getAddresses() {
        return addresses;
    }

    public static void setAddresses(List<Address> addresses) {
        AddressService.addresses = addresses;
    }

    private AddressService()
    {
        audit = Audit.getInstance("D:\\Users\\Sofia\\HermesDelivery\\src\\main\\java\\Audit\\Audit.csv");
    }

    public static HashMap<Long, Address> getAddressById() {
        return addressById;
    }

    public static void setAddressById(HashMap<Long, Address> addressById) {
        AddressService.addressById = addressById;
    }

    public static AddressService getInstance()
    {
        if(instance==null)
        {
            instance = new AddressService();
        }
        return instance;
    } 


}
