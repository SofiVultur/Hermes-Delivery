package Functionalities;

import Classes.*;
import Classes.Cart;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class ClientService extends PlatformService {

    private static ClientService instance;
    private Audit audit;
    private static Map<String, Client> clientsByEmail = new Hashtable<String, Client>();
    private static Map<Client, List<Order>> ordersByClient = new Hashtable<>();
    private static List<Client> clients = new ArrayList<>();

    public static List<Client> getClients() {
        return clients;
    }

    public static void setClients(List<Client> clients) {
        ClientService.clients = clients;
    }

    private ClientService()
    {
        audit = Audit.getInstance("D:\\Users\\Sofia\\HermesDelivery\\src\\main\\java\\Audit\\Audit.csv");
    }

    public static void setClientsByEmail(Map<String, Client> clientsByEmail) {
        ClientService.clientsByEmail = clientsByEmail;
    }

    public static void setOrdersByClient(Map<Client, List<Order>> ordersByClient) {
        ClientService.ordersByClient = ordersByClient;
    }

    public static Map<String, Client> getClientsByEmail() {
        return clientsByEmail;
    }

    public static Map<Client, List<Order>> getOrdersByClient() {
        return ordersByClient;
    }

    public static ClientService getInstance()
    {
        if(instance==null)
        {
            instance = new ClientService();
        }
        return instance;
    }

    public void Register(String email, String password)
    {
        audit.writeToFile();
        if(clientsByEmail.containsKey(email))
        {
            System.out.println("This email already has an account!");
            return;
        }

        if(!validateEmail(email))
        {
            System.out.println("You need to type a valid email!");
            return;
        }


        if(!validatePassword(password)){
            System.out.println("You need to type a valid password (1 uppercase, 1 lowercase, 1 number)");
            return;
        }
        else
        {
            System.out.println("Successfully registered!");
            loggedInUser = new Client.Builder(email, password).build();
        }

    }


    public void editAddress(Address address)
    {
        audit.writeToFile();
        if(loggedInUser instanceof Client)
        {
           ((Client) loggedInUser).setAddress(address);
        }
    }


    public void deleteAddress(Address address)
    {
        audit.writeToFile();
        if(loggedInUser instanceof Client)
        {
           ((Client) loggedInUser).setAddress(null);
        }
    }

//
//    public void addRestaurantToFavourites(Restaurant restaurant)
//    {
//        audit.writeToFile();
//        if(loggedInUser instanceof Client)
//        {
//            var favourites = ((Client) loggedInUser).getFavourites();
//            if(favourites.containsKey(restaurant.getRestaurantType()) && !favourites.get(restaurant.getRestaurantType()).contains(restaurant))
//            {
//                var ofRestaurantType = favourites.get(restaurant.getRestaurantType());
//                ofRestaurantType.add(restaurant);
//                favourites.put(restaurant.getRestaurantType(), ofRestaurantType);
//                ((Client) loggedInUser).setFavourites(favourites);
//                System.out.println("Restaurant "+ restaurant + " was successfully added to your favourites!");
//            }
//            else if (favourites.containsKey(restaurant.getRestaurantType()) && favourites.get(restaurant.getRestaurantType()).contains(restaurant))
//            {
//                System.out.println("Restaurant is already marked as favourite!");
//            }
//            else
//            {
//                List<Restaurant> restaurants = new ArrayList<Restaurant>();
//                restaurants.add(restaurant);
//                favourites.put(restaurant.getRestaurantType(), restaurants);
//                System.out.println("Restaurant "+ restaurant + " was successfully added to your favourites!");
//            }
//        }
//        else
//        {
//            System.out.println("You are not a client!");
//        }
//    }

//    public void removeRestaurantToFavourites(Restaurant restaurant)
//    {
//        audit.writeToFile();
//        if(loggedInUser instanceof Client)
//        {
//            var favourites = ((Client) loggedInUser).getFavourites();
//            if(favourites.values().contains(restaurant))
//            {
//                var ofRestaurantType = favourites.get(restaurant.getRestaurantType());
//                ofRestaurantType.add(restaurant);
//                favourites.remove(restaurant.getRestaurantType(), ofRestaurantType);
//                ((Client) loggedInUser).setFavourites(favourites);
//                System.out.println("Restaurant "+ restaurant + " was successfully removed from your favourites!");
//            }
//            else
//            {
//                System.out.println("Restaurant is not marked as favourite!");
//            }
//        }
//        else
//        {
//            System.out.println("You are not a client!");
//        }
//    }

    public void addToCart(Restaurant restaurant, Menu menu, String category, Dish dish, Integer number_of_portions)
    {
        audit.writeToFile();
        if(loggedInUser instanceof Client && number_of_portions>0)
        {
            Cart cart = ((Client) loggedInUser).getCart();
            var dishes = cart.getDishes();
            if(dishes.containsKey(restaurant))
            {
                var list = dishes.get(restaurant);
                var price = getPriceOf(dish, restaurant, menu);
                if(price!= null)
                {
                    list.add(new Triplet<Dish, Integer, Double>(dish, number_of_portions, price));
                    dishes.put(restaurant, list);
                    cart.setDishes(dishes);
                    cart.setPrice(cart.getPrice()+price*number_of_portions);
                    ((Client) loggedInUser).setCart(cart);
                    System.out.println("The dish "+ dish + " was successfully added to your cart!");
                }
                else
                {
                    System.out.println("The price for this dish is null, cannot continue!");
                }
            }
            else
            {
                 var list = new ArrayList<Triplet<Dish, Integer, Double>>();
                 var price = getPriceOf(dish, restaurant, menu);
                 if(price!=null)
                 {
                     list.add(new Triplet<>(dish, number_of_portions, price));
                     dishes.put(restaurant, list);
                     cart.setDishes(dishes);
                     cart.setPrice(cart.getPrice()+price*number_of_portions);
                     ((Client) loggedInUser).setCart(cart);
                     System.out.println("The dish "+ dish + " was successfully added to your cart!");
                 }
            }
        }
    }

    public void removeFromCart(Restaurant restaurant, Dish dish, Integer number_of_portions)
    {
        audit.writeToFile();
        if(loggedInUser instanceof Client && number_of_portions>0)
        {
            Cart cart = ((Client) loggedInUser).getCart();
            var dishes = cart.getDishes();
            if(dishes.containsKey(restaurant))
            {
                var list = dishes.get(restaurant);

                for(var elem: list)
                {
                    if(elem.getValue0().equals(dish))
                    {
                        if(elem.getValue1()<=number_of_portions)
                        {
                            list.remove(elem);
                            cart.setPrice(cart.getPrice()-elem.getValue1()*elem.getValue2());
                            System.out.println("The dish "+ dish + " was successfully removed from your cart!");
                            break;
                        }
                        else
                        {
                            elem = elem.setAt1(elem.getValue1()-number_of_portions);
                            cart.setPrice(cart.getPrice()-number_of_portions* elem.getValue2());
                            System.out.println("The dish "+ dish + " was successfully removed from your cart!");
                            break;
                        }
                    }
                }

                dishes.put(restaurant, list);
                cart.setDishes(dishes);
                ((Client) loggedInUser).setCart(cart);

            }
        }
    }

    public void removeFromCart(Restaurant restaurant, Dish dish)
    {
        audit.writeToFile();
        if(loggedInUser instanceof Client)
        {
            Cart cart = ((Client) loggedInUser).getCart();
            var dishes = cart.getDishes();
            if(dishes.containsKey(restaurant))
            {
                var list = dishes.get(restaurant);

                for(var elem: list)
                {
                    if(elem.getValue0().equals(dish))
                    {

                        list.remove(elem);
                        cart.setPrice(cart.getPrice()-elem.getValue1()*elem.getValue2());
                        System.out.println("The dish "+ dish + " was successfully added to your cart!");
                        break;
                    }
                }

                dishes.put(restaurant, list);
                cart.setDishes(dishes);
                ((Client) loggedInUser).setCart(cart);

            }
        }
    }

    public Double getPriceOf(Dish dish, Restaurant restaurant, Menu menu)
    {
        audit.writeToFile();
        Predicate<Pair<Dish, Double>> c1 = p -> p.getValue0().getId()==dish.getId();
        var elements = menu.getElements().stream().filter(c1).toList();
        if(elements.size()>0)
        {
            return elements.get(0).getValue1();
        }
        return null;
    }

    public void finishOrder() throws CloneNotSupportedException
    {
        audit.writeToFile();
        if(loggedInUser instanceof Client)
        {
            var cart = ((Client) loggedInUser).getCart();
            var orders = ordersByClient.get(loggedInUser);
            for(var restaurant : cart.getDishes().keySet())
            {
                Order order = new Order.Builder(restaurant, (Client) loggedInUser).build();
                OrderService ord = OrderService.getInstance();
                for(var dish : cart.getDishes().get(restaurant))
                {
                    // Here I clone the dish because any of the further changes on the dish should not be visible in the order.
                    ord.addDish(order, dish.getValue0().clone(), dish.getValue2(), dish.getValue1());
                }
                PlatformService platformService = getInstance();
            }
            cart = new Cart();
            ((Client) loggedInUser).setCart(cart);
            System.out.println("Your order was successfully processed!");
        }
        else
        {
            System.out.println("You are not a client!");
        }
    }

//    public void finishDelivery() throws CloneNotSupportedException
//    {
//        audit.writeToFile();
//        if(loggedInUser instanceof Client)
//        {
//            var cart = ((Client) loggedInUser).getCart();
//            var orders = ordersByClient.get(loggedInUser);
//            var address = ((Client) loggedInUser).getAddress();
//            for(var restaurant : cart.getDishes().keySet())
//            {
//                Delivery order = new Delivery.Builder(restaurant, (Client) loggedInUser, address).build();
//                DeliveryService ord = DeliveryService.getInstance();
//                for (var dish : cart.getDishes().get(restaurant))
//                {
//                    // Here I clone the dish because any of the further changes on the dish should not be visible in the delivery.
//                    ord.addDish(order, dish.getValue0().clone(), dish.getValue2(), dish.getValue1());
//                }
//                PlatformService platformService = getInstance();
//                CartService cartService = CartService.getInstance();
//                if (platformService.assignDelivery(order)) {
//                    orders.add(order);
//                    System.out.println("Your delivery was successfully processed!");
//                    var dishes = cart.getDishes();
//                    dishes.remove(restaurant);
//                    cart.setDishes(dishes);
//                }
//                else {
//                    System.out.println("Sorry, there aren't enough drivers to process this delivery!");
//                }
//            }
//            ((Client) loggedInUser).setOrders(orders);
//            ((Client) loggedInUser).setCart(cart);
//        }
//        else
//        {
//            System.out.println("You are not a client!");
//        }
//    }

    public boolean verifyDishInOrder(Dish dish, Order order)
    {
        audit.writeToFile();
        var dishesOrdered = order.getDishesOrdered();
        for(var triplet : dishesOrdered)
        {
            if(triplet.getValue0().equals(dish))
            {
                return true;
            }
        }
        return false;
    }

    public boolean verifyDishInOrders(Dish dish)
    {
        audit.writeToFile();
        if(loggedInUser instanceof Client)
        {
            var orders = ordersByClient;
            for(var client : orders.keySet())
            {
                for(var order : orders.get(client))
                {
                    if(verifyDishInOrder(dish, order))
                        return true;
                }
            }
        }
        return false;
    }


    public boolean verifyRestaurantInOrders(Restaurant restaurant)
    {
        audit.writeToFile();
        if(loggedInUser instanceof Client)
        {
            var orders = ordersByClient;
            for(var client : orders.keySet())
            {
                for(var order : orders.get(client))
                {
                    if(order.getRestaurant().equals(restaurant))
                        return true;
                }
            }
        }
        return false;
    }

    public void leaveAReview(Review review, Restaurant restaurant) throws CloneNotSupportedException
    {
        audit.writeToFile();
        if(loggedInUser instanceof Client)
        {
            if(verifyRestaurantInOrders(restaurant))
            {
                var reviews = RestaurantService.getReviewsByRestaurant().get(restaurant);
                reviews.add(review.clone());
                var reviewsByRestaurant = RestaurantService.getReviewsByRestaurant();
                reviewsByRestaurant.put(restaurant, reviews);
                RestaurantService.setReviewsByRestaurant(reviewsByRestaurant);
                System.out.println("Your review was successfully added!");
            }
            else
            {
                System.out.println("You can't review a restaurant if you've never ordered from it!");
            }
        }
        else
        {
            System.out.println("You are not a client!");
        }
    }
    public void leaveAReview(Integer nrStars, Restaurant restaurant) throws CloneNotSupportedException {
        audit.writeToFile();
        if(loggedInUser instanceof Client)
        {
            Review review = new Review.Builder(loggedInUser, restaurant, nrStars).build();
            if(verifyRestaurantInOrders(restaurant))
            {
                var reviews = RestaurantService.getReviewsByRestaurant().get(restaurant);
                reviews.add(review.clone());
                var reviewsByRestaurant = RestaurantService.getReviewsByRestaurant();
                reviewsByRestaurant.put(restaurant, reviews);
                RestaurantService.setReviewsByRestaurant(reviewsByRestaurant);
                System.out.println("Your review was successfully added!");
            }
            else
            {
                System.out.println("You can't review a restaurant if you've never ordered from it!");
            }
        }
        else
        {
            System.out.println("You are not a client!");
        }
    }

    public void leaveAReview(Integer nrStars, String text, Restaurant restaurant) throws CloneNotSupportedException {
        audit.writeToFile();
        if(loggedInUser instanceof Client)
        {
            Review review = new Review.Builder(loggedInUser, restaurant, nrStars).withText(text).build();
            if(verifyRestaurantInOrders(restaurant))
            {
                var reviews = RestaurantService.getReviewsByRestaurant().get(restaurant);
                reviews.add(review.clone());
                var reviewsByRestaurant = RestaurantService.getReviewsByRestaurant();
                reviewsByRestaurant.put(restaurant, reviews);
                RestaurantService.setReviewsByRestaurant(reviewsByRestaurant);
                System.out.println("Your review was successfully added!");
            }
            else
            {
                System.out.println("You can't review a restaurant if you've never ordered from it!");
            }
        }
        else
        {
            System.out.println("You are not a client!");
        }
    }

    public Address getLoggedInUserAddress()
    {
        if(loggedInUser instanceof Client)
        {
            return ((Client) loggedInUser).getAddress();
        }
        return null;
    }




}
