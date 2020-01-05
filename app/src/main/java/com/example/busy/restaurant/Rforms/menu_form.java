package com.example.busy.restaurant.Rforms;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;



public class menu_form {
    private ArrayList<dish_form> starters_list;
    private ArrayList<dish_form> main_list;
    private ArrayList<dish_form> drink_list;
    private ArrayList<dish_form> deserts_list;




    public menu_form() {
        starters_list = new ArrayList<dish_form>();
        main_list = new ArrayList<dish_form>();
        drink_list = new ArrayList<dish_form>();
        deserts_list = new ArrayList<dish_form>();

    }

    public void setStarters_list(ArrayList<dish_form> starters_list) {
        this.starters_list.addAll(starters_list);
    }

    public void setMain_list(ArrayList<dish_form> main_list) {
        this.main_list.addAll(main_list);
    }

    public void setDrink_list(ArrayList<dish_form> drink_list) {
        this.drink_list.addAll(drink_list);
    }

    public void setDeserts_list(ArrayList<dish_form> deserts_list) {
        this.deserts_list.addAll(deserts_list);
    }



    public ArrayList<dish_form> getStarters_list() {
        return starters_list;
    }

    public ArrayList<dish_form> getMain_list() {
        return main_list;
    }

    public ArrayList<dish_form> getDrink_list() {
        return drink_list;
    }

    public ArrayList<dish_form> getDeserts_list() {
        return deserts_list;
    }
    public boolean exist(dish_form dish){
        for (int i = 0; i < this.deserts_list.size(); i++){
            if (deserts_list.get(i).check_equal(dish)){
                return true;
            }
        }
        for (int i = 0; i < main_list.size(); i++){
            if(main_list.get(i).check_equal(dish)){
                return true;
            }
        }
        for (int i = 0; i < drink_list.size(); i++){
            if (drink_list.get(i).check_equal(dish)){
                return true;
            }
        }
        for (int i = 0; i < starters_list.size(); i++){
            if(starters_list.get(i).check_equal(dish)){
                return true;
            }
        }

            return false;

    }
    public boolean add_start_dish(dish_form dish){
      return starters_list.add(dish);
    }
    public boolean add_main_dish(dish_form dish){
       return main_list.add(dish);

    }
    public boolean add_drink(dish_form drink){
        return drink_list.add(drink);
    }
    public boolean add_desert_dish(dish_form desert){
        return deserts_list.add(desert);
    }
}
