package FurnitureApp.FurnitureInfo;

public class Table implements Furniture
{
    private String name;
    private int model;
    private int price;

    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void setModelNr(int model)
    {
        this.model = model;
    }

    @Override
    public int getModelNr()
    {
        return model;
    }

    @Override
    public void setPrice(int price)
    {
        this.price = price;
    }

    @Override
    public int getPrice()
    {
        return price;
    }
}
