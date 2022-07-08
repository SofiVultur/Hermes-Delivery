package Classes;

import Functionalities.ClientService;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    protected Long id;
    protected Date dateOrdered;
    protected Client client;
    protected Double finalPrice;
    protected Restaurant restaurant;
    protected List<Triplet<Dish, Integer, Double>> dishesOrdered = new ArrayList<Triplet<Dish, Integer, Double>>();
    protected static Long ID = 0l;

    public Order()
    {
        this.finalPrice = 0.0;
    }

    public Order(Date timeOrdered, Client client_id, Double finalPrice, Restaurant restaurant, List<Triplet<Dish, Integer, Double>> dishesOrdered) {
        this.dateOrdered = timeOrdered;
        this.client = client_id;
        this.finalPrice = finalPrice;
        this.restaurant = restaurant;
        this.dishesOrdered = dishesOrdered;

        var orders = ClientService.getOrdersByClient();
        var list = orders.get(client);
        list.add(this);
        orders.put(client, list);
        ClientService.setOrdersByClient(orders);
    }

    public static class Builder{
        protected Order order = new Order();

        public Builder(Restaurant restaurant, Client user){
            order.restaurant = restaurant;
            order.client = user;
        }
        public Builder withDish(Dish dish, Integer number, Double price){
            Triplet<Dish, Integer, Double> tr= new Triplet<Dish, Integer, Double> (dish, number, price);
            order.dishesOrdered.add(tr);
            return this;
        }
        public Builder withDishes(List<Triplet<Dish, Integer, Double>> dishes){
            order.dishesOrdered = dishes;
            return this;
        }

        public Builder withTimeOrdered(Date timeOrdered)
        {
            order.dateOrdered = timeOrdered;
            return this;
        }

        public Builder withId(Long id)
        {
            order.id = id;
            return this;
        }

        public Order build()
        {
            for(Triplet<Dish, Integer, Double> elem : order.dishesOrdered)
            {
                order.finalPrice+=elem.getValue2()* elem.getValue1();
            }
            order.id = ID+1;
            return this.order;
        }
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimeOrdered() {
        return dateOrdered;
    }

    public void setTimeOrdered(Date timeOrdered) {
        this.dateOrdered = timeOrdered;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<Triplet<Dish, Integer, Double>> getDishesOrdered() {
        return dishesOrdered;
    }

    public void setDishesOrdered(List<Triplet<Dish, Integer, Double>> dishesOrdered) {
        this.dishesOrdered = dishesOrdered;
    }

    public static Long getID() {
        return ID;
    }

    public static void setID(Long ID) {
        Order.ID = ID;
    }

    public static void incrementID() {
        Order.ID+=1;
    }



    @Override
    public String toString() {
        return "Order{" +
            "id=" + id +
            ", timeOrdered=" + dateOrdered +
            ", client_id=" + client +
            ", finalPrice=" + finalPrice +
            ", restaurant=" + restaurant +
            ", dishesOrdered=" + dishesOrdered +
            '}';
    }
}
