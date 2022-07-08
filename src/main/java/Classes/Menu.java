package Classes;

import Functionalities.MenuService;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class Menu implements Cloneable {
    private Long id;
    private String name;
    private Restaurant restaurant;
    List<Pair<Dish, Double>> elements = new ArrayList<>();
    private static Long ID = 0L;

    public Menu() {}
    public Menu(Long id, String name, Restaurant restaurant, List<Pair<Dish, Double>> elements) {
        this.id = id;
        this.name = name;
        this.restaurant = restaurant;
        this.elements = elements;

        var menus = MenuService.getMenusByRestaurant();
        var list = menus.get(restaurant);
        list.add(this);
        menus.put(restaurant, list);
        MenuService.setMenusByRestaurant(menus);
    }

    public static class Builder{
        private Menu menu = new Menu();

        public Builder(String name){
            menu.name =name;
        }
        public Builder withElements(List<Pair<Dish, Double>> elements){
            menu.elements=elements;
            return this;
        }
        public Builder withElement(Dish dish, Double price)
        {
            menu.elements.add(new Pair<Dish, Double>(dish, price));
            return this;
        }
        public Builder withRestaurant(Restaurant restaurant)
        {
           menu.restaurant = restaurant;
            return this;
        }
        public Builder withId(Long id)
        {
            menu.id = id;
            return this;
        }
        public Menu build()
        {
            var menus = MenuService.getMenusByRestaurant();
            return this.menu;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<Pair<Dish, Double>> getElements() {
        return elements;
    }

    public void setElements(List<Pair<Dish, Double>> elements) {
        this.elements = elements;
    }

    public static Long getID() {
        return ID;
    }

    public static void setID(Long ID) {
        Menu.ID = ID;
    }

    public static void incrementID(Long ID) {
        Menu.ID+=1;
    }

    @Override
    public String toString() {
        return "Menu{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", restaurant=" + restaurant +
            ", elements=" + elements +
            '}';
    }

    public Menu clone() throws CloneNotSupportedException
    {
        Menu menu = new Menu();
//        menu.elements=(HashMap<String, List<Pair<Dish, Double>>>) elements.clone();
        menu.name = name;
        return menu;
    }
}
