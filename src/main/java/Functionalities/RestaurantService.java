package Functionalities;

import Classes.*;

import java.util.List;
import java.util.Map;

public class RestaurantService extends PlatformService{

    private static RestaurantService instance;
    private static Map<Restaurant, List<Review>> reviewsByRestaurant;
    Audit audit;

    private RestaurantService()
    {
        audit = Audit.getInstance("D:\\Users\\Sofia\\HermesDelivery\\src\\main\\java\\Audit\\Audit.csv ");
    }


    public static RestaurantService getInstance()
    {
        if(instance==null)
        {
            instance = new RestaurantService();
        }
        return instance;
    }
    
    public void addMenu(Menu menu, Restaurant restaurant)
    {
        audit.writeToFile();
        if(loggedInUser instanceof Owner && restaurant.getOwner()!= null && restaurant.getOwner().equals(loggedInUser))
        {
            var menusByRestaurant = MenuService.getMenusByRestaurant();
            List<Menu> menus = MenuService.getMenusByRestaurant().get(restaurant);
            menus.add(menu);
            menusByRestaurant.put(restaurant, menus);
            if(menus.contains(menu))
            {
                System.out.println("The menu is already listed!");
            }
            else
            {
                menus.add(menu);
                MenuService.setMenusByRestaurant(menusByRestaurant);
                System.out.println("Menu successfully added!");
            }
        }
        else if(loggedInUser==null)
        {
            System.out.println("You're not logged in!");
        }
        else if(!(loggedInUser instanceof Owner))
        {
            System.out.println("You're not an owner!");
        }
        else
        {
            System.out.println("You don't own this restaurant!");
        }
    }

    public void removeMenu(Restaurant restaurant, Menu menu)
    {
        audit.writeToFile();
        if(loggedInUser instanceof Owner && restaurant.getOwner().equals(loggedInUser))
        {
            var menusByRestaurant = MenuService.getMenusByRestaurant();
            List<Menu> menus = MenuService.getMenusByRestaurant().get(restaurant);
            menus.removeIf( p -> p.getId().equals(menu.getId()));
            menusByRestaurant.put(restaurant, menus);
        }
        else if(loggedInUser==null)
        {
            System.out.println("You're not logged in!");
        }
        else if(!(loggedInUser instanceof Owner))
        {
            System.out.println("You're not an owner!");
        }
        else
        {
            System.out.println("You don't own this restaurant!");
        }
    }

    public static Map<Restaurant, List<Review>> getReviewsByRestaurant() {
        return reviewsByRestaurant;
    }

    public static void setReviewsByRestaurant(Map<Restaurant, List<Review>> reviewsByRestaurant) {
        RestaurantService.reviewsByRestaurant = reviewsByRestaurant;
    }
}
