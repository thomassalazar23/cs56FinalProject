package FurnitureApp;

import FurnitureApp.CustomerInfo.Customer;
import FurnitureApp.FurnitureInfo.Furniture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FurnitureBusiness
{
    private HashMap<Customer, List<Furniture>> customerLog = new HashMap<>();

    public ArrayList<Customer> searchCustomers(String searchName)
    {
        ArrayList<Customer> foundCustomers = new ArrayList<>();

        searchName = searchName.toLowerCase();
        for (Customer c : customerLog.keySet())
        {
            if (searchName.equals(c.getName().toLowerCase()))
            {
                foundCustomers.add(c);
            }
        }

        return foundCustomers;
    }

    public void addCustomer(Customer c)
    {
        if (c == null)
        {
            return;
        } else
        {
            customerLog.put(c, new ArrayList<Furniture>());
        }
    }

    public void purchase(Customer c, Furniture ...f)
    {
        //If customer is in customerLog hashmap
        if (customerLog.containsKey(c))
        {
            //loop through inputted array of furniture
            for (Furniture furniture : f)
            {
                //add each piece of furniture to the customers list
                customerLog.get(c).add(furniture);
            }
        }

        //otherwise customer isn't in map
        else
        {
            //add customer to hashmap with an empty Arraylist
            customerLog.put(c, new ArrayList<Furniture>());

            //loop through array of furniture f
            for (Furniture furniture : f)
            {
                //add each piece to the customers list
                customerLog.get(c).add(furniture);
            }
        }
    }


    public Boolean hasBought(Customer c, Furniture f)
    {
        //loop through each piece of furniture is customers purchase history
        for(Furniture furniture : customerLog.get(c))
        {
            //if the piece is the same as the one we are checking
            if (furniture.equals(f))
            {
                return true;
            }
        }

        //if we are here, customer has not bought the furniture
        return false;
    }

    public List<Furniture> getPurchases(Customer c)
    {
        //fetch corresponding list to the customer
        return customerLog.get(c);
    }

    public int moneySpent(Customer c)
    {
        int totalSpent = 0;
        List<Furniture> purchases = customerLog.get(c);

        // If the customer has no purchase history (or isn't in the map), return 0.
        if (purchases == null) {
            return 0;
        }

        // Loop through the purchase history and sum the prices.
        for (Furniture furniture : purchases)
        {
            totalSpent += furniture.getPrice();
        }
        return totalSpent;
    }

}
