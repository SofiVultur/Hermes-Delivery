package Classes;

import java.util.concurrent.atomic.AtomicLong;

public class Restaurant implements Cloneable{
    private Long id;
    private String name;
    private static AtomicLong ID;
    private Address address;
    private Owner owner;
    private RestaurantType restaurantType;

    protected static Long newID()
    {
        System.out.println(ID);
        return ID.incrementAndGet();
    }
    Restaurant()
    {

    }
    public Restaurant(Address address, Owner owner, RestaurantType restaurantType) {
        this.address = address;
        this.owner = owner;
        this.restaurantType = restaurantType;
    }

    public static class Builder{
        private Restaurant restaurant = new Restaurant();

        public Builder(Owner owner, Address address, String name){
            restaurant.owner = owner;
            restaurant.address = address;
            restaurant.name =name;
        }

        public Builder(Address address, String name){
            restaurant.address = address;
            restaurant.name =name;
        }
        public Builder withRestaurantType(RestaurantType restaurantType)
        {
            restaurant.restaurantType=restaurantType;
            return this;
        }

        public Builder withId(Long id)
        {
            restaurant.id=id;
            return this;
        }

        public Restaurant build()
        {
            return this.restaurant;
        }
    }


//    public Restaurant clone() throws CloneNotSupportedException
//    {
//        Restaurant restaurant = new Restaurant();
//        restaurant.name = name;
//        restaurant.address = address.clone(); // Here i'm cloning the address.
//        List<Review> rev = new ArrayList<Review>();
//        for(var review : this.reviews)
//        {
//            rev.add(review.clone());
//        }
//        restaurant.reviews=rev;
//        restaurant.owner = owner.clone();
//        List<Menu> men = new ArrayList<>();
//        for(var m : menus)
//        {
//            men.add(m.clone());
//        }
//        restaurant.menus=men;
//        restaurant.restaurantType = restaurantType;
//        return restaurant;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static AtomicLong getID() {
        return ID;
    }

    public static void setID(AtomicLong ID) {
        Restaurant.ID = ID;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public RestaurantType getRestaurantType() {
        return restaurantType;
    }

    public void setRestaurantType(RestaurantType restaurantType) {
        this.restaurantType = restaurantType;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", address=" + address +
            ", owner=" + owner +
//            ", restaurantType=" + restaurantType +
            '}';
    }

    public Restaurant clone() throws CloneNotSupportedException
    {
        Restaurant restaurant = new Restaurant();
        restaurant.name = name;
        restaurant.address = address.clone(); // Here i'm cloning the address.
        restaurant.restaurantType = restaurantType;
        return restaurant;
    }
}
