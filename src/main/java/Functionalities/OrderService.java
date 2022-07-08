package Functionalities;

import Classes.Audit;
import Classes.Dish;
import Classes.Order;
import org.javatuples.Triplet;

public class OrderService extends PlatformService{
    protected static OrderService instance;
    Audit audit;

    public static OrderService getInstance()
    {
        if(instance==null)
        {
            instance= new OrderService();
            instance.audit = Audit.getInstance("D:\\Users\\Sofia\\HermesDelivery\\src\\main\\java\\Audit\\Audit.csv");
        }
        return instance;
    }

    public void addDish(Order order, Dish dish, Double price, Integer numberOfPortions)
    {
        audit.writeToFile();
        var dishes = order.getDishesOrdered();
        dishes.add(new Triplet<Dish, Integer, Double>(dish, numberOfPortions, price));
        order.setFinalPrice(order.getFinalPrice() + price*numberOfPortions);
        order.setDishesOrdered(dishes);
    }
    public void removeDish(Order order, Dish dish)
    {
        audit.writeToFile();
        var dishes = order.getDishesOrdered();
        for(var elem : dishes)
        {
            if(elem.getValue0().equals(dish))
            {
                dishes.remove(elem);
                order.setFinalPrice(order.getFinalPrice() - elem.getValue1()*elem.getValue2());
                order.setDishesOrdered(dishes);
                break;
            }
        }
    }
    public void setPortionsForDish(Order order, Dish dish, Integer number)
    {
        audit.writeToFile();
        var dishes = order.getDishesOrdered();
        for(Triplet<Dish, Integer, Double> elem : dishes)
        {
            if(dish.equals(elem.getValue0()))
            {
                order.setFinalPrice(order.getFinalPrice() - (elem.getValue1()-number)*elem.getValue2());
                dishes.remove(elem);
                if(number>0)
                {
                    elem= elem.setAt1(number);
                    dishes.add(elem);
                }
                order.setDishesOrdered(dishes);
                return;
            }
        }
    }
}
