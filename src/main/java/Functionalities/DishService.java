package Functionalities;

import Classes.*;
import Classes.Audit;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class DishService extends PlatformService{
    private static List<Dish> dishes = new ArrayList<>();
    private static DishService instance;
    private static Map<Long, Dish> dishesById = new Hashtable<>();
    Audit audit;

    public static List<Dish> getDishes() {
        return dishes;
    }

    public static void setDishes(List<Dish> dishes) {
        DishService.dishes = dishes;
    }

    public static Map<Long, Dish> getDishesById() {
        return dishesById;
    }

    public static void setDishesById(Map<Long, Dish> dishesById) {
        DishService.dishesById = dishesById;
    }

    private DishService()
    {
        audit = Audit.getInstance("D:\\Users\\Sofia\\HermesDelivery\\src\\main\\java\\Audit\\Audit.csv");
    }

    public static DishService getInstance()
    {
        if(instance==null)
        {
            instance = new DishService();
        }
        return instance;
    }
    

    public void addIngredientToDish(Restaurant restaurant, Dish dish, String ingredient, Double number, String unit_of_measurement)
    {
        audit.writeToFile();
        if(loggedInUser instanceof Owner && restaurant.getOwner().equals(loggedInUser))
        {
            var recipe = dish.getRecipe();
            recipe.add(new Triplet<String, Double, String>(ingredient, number, unit_of_measurement));
            dish.setRecipe(recipe);
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

    public void removeIngredient(Restaurant restaurant, Dish dish, String ingredient)
    {
        audit.writeToFile();
        if(loggedInUser instanceof Owner && restaurant.getOwner().equals(loggedInUser))
        {
            var recipe = dish.getRecipe();
            recipe.removeIf(p -> p.getValue0().equals(ingredient));
            dish.setRecipe(recipe);
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

//    public void editString(Restaurant restaurant, Dish dish, String ingredient, String newString)
//    {
//        audit.writeToFile();
//        if(loggedInUser instanceof Owner && restaurant.getOwner().equals(loggedInUser))
//        {
//            Pair<Integer, String> pair = dish.getRecipe().get(ingredient);
//            pair = pair.setAt1(newString);
//            var ingr = dish.getRecipe();
//            ingr.removeIf(ingredient, pair);
//            dish.setRecipe(ingr);
//        }
//        else if(loggedInUser==null)
//        {
//            System.out.println("You're not logged in!");
//        }
//        else if(!(loggedInUser instanceof Owner))
//        {
//            System.out.println("You're not an owner!");
//        }
//        else
//        {
//            System.out.println("You don't own this restaurant!");
//        }
//    }
//
//    public void editNumberString(Restaurant restaurant, Dish dish, String ingredient, Integer  newString)
//    {
//        audit.writeToFile();
//        if(loggedInUser instanceof Owner && restaurant.getOwner().equals(loggedInUser) && verifyIngredientInDish(ingredient, dish))
//        {
//            Pair<Integer, String> pair = dish.getRecipe().get(ingredient);
//            pair = pair.setAt0(newString);
//            HashMap<String, Pair<Integer, String>> ingr = dish.getRecipe();
//            ingr.remove(ingredient, pair);
//            dish.setRecipe(ingr);
//        }
//        else if(loggedInUser==null)
//        {
//            System.out.println("You're not logged in!");
//        }
//        else if(!(loggedInUser instanceof Owner))
//        {
//            System.out.println("You're not an owner!");
//        }
//        else
//        {
//            System.out.println("You don't own this restaurant!");
//        }
//    }

    public boolean verifyIngredientInDish(String ingredient, Dish dish)
    {
        audit.writeToFile();
        return dish.getRecipe().contains(ingredient);
    }

}
