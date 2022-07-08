package Classes;

import Functionalities.DishService;
import com.opencsv.bean.CsvBindByPosition;
import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.List;

public class Dish implements Cloneable{
    @CsvBindByPosition(position = 0)
    private Long id;
    @CsvBindByPosition(position = 1)
    private String name;
    private List<Triplet<String, Double , String>> recipe= new ArrayList<>();

    private static Long ID = 0L;

    public Dish(){
        var dishes = DishService.getDishes();
        dishes.add(this);
        DishService.setDishes(dishes);
    }
    public Dish(Long id, String name, List<Triplet<String, Double, String>> recipe) {
        this.id = id;
        this.name = name;
        this.recipe = recipe;
        var dishes = DishService.getDishes();
        dishes.add(this);
        DishService.setDishes(dishes);
    }

    public Dish(Long id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public static class Builder {

        private Dish dish = new Dish();

        public Dish build() {
            var dishes = DishService.getDishes();
            dishes.add(dish);
            DishService.setDishes(dishes);
            return this.dish;
        }

        public Builder(String name) {
            dish.name = name;
        }

        public Builder withRecipe(List<Triplet<String, Double, String>> recipe) {
            dish.recipe = recipe;
            return this;
        }

        public Builder withIngredient(String name, Double quantity, String unit_of_measurement) {
            dish.recipe.add(new Triplet<>(name, quantity, unit_of_measurement));
            return this;
        }

        public Builder withId (Long id)
        {
            dish.id = id;
            return this;
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

    public List<Triplet<String, Double, String>> getRecipe() {
        return recipe;
    }

    public void setRecipe(List<Triplet<String, Double, String>> recipe) {
        this.recipe = recipe;
    }

    public static Long getID() {
        return ID;
    }

    public static void setID(Long ID) {
        Dish.ID = ID;
    }

    public static void incrementID() {
        Dish.ID+=1;
    }


    @Override
    public String toString() {
        return "Dish{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", recipe=" + recipe +
            '}';
    }

    public Dish clone() throws CloneNotSupportedException
    {
        Dish dish =new Dish();
        dish.name = name;
        List<Triplet<String, Double, String>> recipe = new ArrayList<>();
        for(var elem: this.recipe)
        {
            recipe.add(new Triplet<String, Double, String>(elem.getValue0(), elem.getValue1(), elem.getValue2()));
        }
        return dish;
    }
}
