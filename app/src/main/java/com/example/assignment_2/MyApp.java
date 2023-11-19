package com.example.assignment_2;

import android.app.Application;

import java.util.ArrayList;

public class MyApp extends Application{
    //application data
    //2 array lists: 1 product type array list, 1 history type
    public ArrayList<Product> productlist = new ArrayList<>(0);
    public ArrayList<History> historyList = new ArrayList<>(0);

    //function that intializes the productList with hardcoded data
    public void setProductlistData(){
        if(productlist.isEmpty()){
            productlist.add(new Product("Shirts", 13.15, 20));
            productlist.add(new Product("Pants", 17.99, 25));
            productlist.add(new Product("Hats", 12.55, 25));
            productlist.add(new Product("Jeans", 49.99, 30));
            productlist.add(new Product("Dress-shirts", 21.99, 25));
            productlist.add(new Product("Socks", 1.98, 200));
        }
    }

}