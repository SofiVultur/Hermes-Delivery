package Classes;

import Functionalities.CartService;
import com.opencsv.bean.CsvBindByPosition;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Cart implements Cloneable {
    @CsvBindByPosition(position = 0)
    private Long id;
    @CsvBindByPosition(position = 1)
    private Double price;
    protected HashMap<Restaurant, List<Triplet<Dish, Integer, Double>>> dishes = new HashMap<Restaurant, List<Triplet<Dish, Integer, Double>>>();

    private static AtomicLong ID;

    protected static Long newID()
    {
        System.out.println(ID);
        return ID.incrementAndGet();
    }

    public Cart() {
        var carts = CartService.getCarts();
        carts.add(this);
        CartService.setCarts(carts);
    }
    public Cart(Double price, HashMap<Restaurant, List<Triplet<Dish, Integer, Double>>> dishes) {
        this.price = price;
        this.dishes = dishes;
    }
    public Cart(Long id, double Price)
    {
        this.id = id;
        this.price = price;
        var carts = CartService.getCartsById();
        carts.put(id, this);
        CartService.setCartsById(carts);
    }

    public Cart(Double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public HashMap<Restaurant, List<Triplet<Dish, Integer, Double>>> getDishes() {
        return dishes;
    }

    public void setDishes(HashMap<Restaurant, List<Triplet<Dish, Integer, Double>>> dishes) {
        this.dishes = dishes;
    }

    public static AtomicLong getID() {
        return ID;
    }

    public static void setID(AtomicLong ID) {
        Cart.ID = ID;
    }

    @Override
    public String toString() {
        return "Cart{" +
            "id=" + id +
            ", price=" + price +
            '}';
    }

    public Cart clone() throws CloneNotSupportedException
    {
        Cart cart = new Cart();
        cart.price = price;
        var dishes = new HashMap<Restaurant, List<Triplet<Dish, Integer, Double>>>();
        for(var restaurant : this.getDishes().keySet())
        {
            List<Triplet<Dish, Integer, Double>> list = new ArrayList<Triplet<Dish, Integer, Double>>();
            for(var dish : this.getDishes().get(restaurant))
            {
                list.add(new Triplet<Dish, Integer, Double>(dish.getValue0().clone(), dish.getValue1(), dish.getValue2()));
            }
            dishes.put(restaurant.clone(), list);
        }
        cart.dishes=dishes;
        return cart;
    }
}
