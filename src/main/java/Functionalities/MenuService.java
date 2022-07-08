package Functionalities;

import Classes.*;
import org.javatuples.Pair;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class MenuService extends PlatformService{
    private static MenuService instance;
    private static Map<Restaurant, List<Menu>> menusByRestaurant = new Hashtable<>();
    Audit audit;
    private MenuService()
    {
        audit = Audit.getInstance("D:\\Users\\Sofia\\HermesDelivery\\src\\main\\java\\Audit\\Audit.csv");
    }

    public static Map<Restaurant, List<Menu>> getMenusByRestaurant() {
        return menusByRestaurant;
    }

    public static void setMenusByRestaurant(Map<Restaurant, List<Menu>> menusByRestaurant) {
        MenuService.menusByRestaurant = menusByRestaurant;
    }

    public static MenuService getInstance()
    {
        if(instance==null)
        {
            instance = new MenuService();
        }
        return instance;
    }

    public void editDishPrice(Menu menu, Dish dish, Double price)
    {
        audit.writeToFile();
        if(loggedInUser instanceof Owner && menu.getRestaurant().getOwner().equals(loggedInUser))
        {
            var elements = menu.getElements();
            Consumer<Pair<Dish, Double>> method = (elem) -> { if(elem.getValue0().equals(dish))
                                                                {
                                                                    elem.setAt1(price);
                                                                }
            };
            elements.forEach(method);
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


    public void addDish(Restaurant restaurant, Menu menu, Dish dish, Double price)
    {
        audit.writeToFile();
        if(loggedInUser instanceof Owner && restaurant.getOwner()!= null && restaurant.getOwner().equals(loggedInUser))
        {
            var elements = menu.getElements();

            elements.add(new Pair<>(dish, price));
            menu.setElements(elements);

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

    public void removeDish(Restaurant restaurant, Menu menu, Dish dish)
    {
        audit.writeToFile();
        if(loggedInUser instanceof Owner && restaurant.getOwner().equals(loggedInUser))
        {
            var elements = menu.getElements();
            elements.removeIf(x -> x.getValue0().equals(dish));
            menu.setElements(elements);
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

}
