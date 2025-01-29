package FurnitureApp.CustomerInfo;

public class Customer
{
    //name of customer
    private String name;
    private Address address = new Address();

    //Sets name of customer
    public void setName(String name)
    {
        this.name = name;
    }

    //returns name of customer
    public String getName() {
        return name;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public Address getAddress()
    {
        return address;
    }

    //Need to add .equals and hashcode

}
