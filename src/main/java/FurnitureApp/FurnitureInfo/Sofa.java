package FurnitureApp.FurnitureInfo;

public class Sofa implements Furniture
{

    private String name;
    private int model;
    private int price;
    private final String type;

    public Sofa(String name, int model, int price) {
        this.name = name;
        this.model = model;
        this.price = price;
        this.type = "Sofa";
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
    public void setModel(int model)
    {
        this.model = model;
    }

    @Override
    public int getModel()
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }

        if (!(o instanceof Sofa))
        {
            return false;
        }

        Sofa other = (Sofa) o;
        return model == other.getModel() && price == other.getPrice() && name.equals(other.getName());

    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + model;
        result = prime * result + price;
        result = prime * result + (name == null ? 0 : name.hashCode());
        return result;
    }
}
