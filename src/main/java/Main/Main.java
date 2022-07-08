package Main;

import Functionalities.*;
import Classes.*;
import com.opencsv.bean.CsvToBeanBuilder;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "D:\\Users\\Sofia\\HermesDelivery\\src\\main\\java\\Data\\Address.csv";
        List<Address> addresses = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(Address.class)
                .build()
                .parse();
        addresses.forEach(System.out::println);

        fileName = "D:\\Users\\Sofia\\HermesDelivery\\src\\main\\java\\Data\\Cart.csv";
        List<Cart> carts = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(Cart.class)
                .build()
                .parse();
        carts.forEach(System.out::println);

        fileName = "D:\\Users\\Sofia\\HermesDelivery\\src\\main\\java\\Data\\Dish.csv";
        List<Dish> dishes = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(Dish.class)
                .build()
                .parse();
        dishes.forEach(System.out::println);

        fileName = "D:\\Users\\Sofia\\HermesDelivery\\src\\main\\java\\Data\\Client.csv";
        List<Client> clients = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(Client.class)
                .build()
                .parse();
        clients.forEach(System.out::println);

        System.out.println(CartService.getCarts());
        System.out.println(AddressService.getAddresses());
        System.out.println(DishService.getDishes());
        System.out.println(ClientService.getClients());
    }

}