package Functionalities;

import Classes.*;
import Classes.Owner;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class OwnerService extends PlatformService {
    protected static OwnerService instance;
    private static Map<String, Owner> ownersByEmail = new Hashtable<>();
    private static Map<Owner, List<Restaurant>> restaurantsByOwner = new Hashtable<>();

    Audit audit;

    public static Map<String, Owner> getOwnersByEmail() {
        return ownersByEmail;
    }

    public static void setOwnersByEmail(Map<String, Owner> ownersByEmail) {
        OwnerService.ownersByEmail = ownersByEmail;
    }

    public static Map<Owner, List<Restaurant>> getRestaurantsByOwner() {
        return restaurantsByOwner;
    }

    public static void setRestaurantsByOwner(Map<Owner, List<Restaurant>> restaurantsByOwner) {
        OwnerService.restaurantsByOwner = restaurantsByOwner;
    }

    public static OwnerService getInstance()
    {
        if(instance==null)
        {
            instance= new OwnerService();
            instance.audit =  Audit.getInstance("D:\\Users\\Sofia\\HermesDelivery\\src\\main\\java\\Audit\\Audit.csv");
        }
        return instance;
    }

    public void RegisterAsOwner(String email, String password)
    {
        audit.writeToFile();
        PlatformService platformService=getInstance();

        if(ownersByEmail.containsKey(email))
        {
            System.out.println("This email already has an account!");
            return;
        }

        if(!platformService.validateEmail(email))
        {
            System.out.println("You need to type a valid email!");
            return;
        }

        if(!validatePassword(password)){
            System.out.println("You need to type a valid password (1 uppercase, 1 lowercase, 1 number)");
            return;
        }

        System.out.println("Successfully logged in!");
        loggedInUser = new Owner.Builder(email, password).build();
        ownersByEmail.put(loggedInUser.getEmail(), (Owner) loggedInUser);
    }


    public void removeRestaurant(Restaurant restaurant)
    {
        audit.writeToFile();
        if(loggedInUser instanceof Owner && restaurant.getOwner().equals(loggedInUser))
        {
            var restaurants = restaurantsByOwner.get(loggedInUser);
            restaurants.remove(restaurant);
            restaurantsByOwner.put((Owner) loggedInUser, restaurants);
            System.out.println("Restaurant was successfully removed!");
        }
    }

    public void addRestaurant(Restaurant restaurant)
    {
        audit.writeToFile();
        if(loggedInUser instanceof Owner && restaurant.getOwner()!=null && restaurant.equals(loggedInUser))
        {
            var restaurants = restaurantsByOwner.get(loggedInUser);
            restaurants.add(restaurant);
            restaurantsByOwner.put((Owner) loggedInUser, restaurants);
            System.out.println("Restaurant was successfully added!");
        }
        else if(loggedInUser instanceof Owner && restaurant.getOwner()==null)
        {
            var restaurants = restaurantsByOwner.get(loggedInUser);
            restaurants.add(restaurant);
            restaurantsByOwner.put((Owner) loggedInUser, restaurants);
            restaurant.setOwner((Owner) loggedInUser);
            System.out.println("Restaurant was successfully added!");
        }
    }




}
