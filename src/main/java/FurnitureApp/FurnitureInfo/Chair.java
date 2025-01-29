package FurnitureApp.FurnitureInfo;

public class Chair implements Furniture
{
    private String name;
    private int model;
    private int price;
    private final String type;

    public Chair(String name, int model, int price) {
        this.name = name;
        this.model = model;
        this.price = price;
        this.type = "Chair";
    }

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

    public String getType() {
        return type;
    }

    //Need to add .equals and hashcode
}
