package FurnitureApp.CustomerInfo;

import java.util.Objects;

public class Address
{
    private int houseNumber;
    private String street;
    private String city;
    private int zipCode;

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + houseNumber;
        //if city or street is null, use the number 0, otherwise use hashcode on string
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((street == null) ? 0 : street.hashCode());
        result = prime * result + zipCode;

        return result;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }

        if (!(o instanceof Address))
        {
            return false;
        }

        Address other = (Address) o;

        //check if variable in each class is equal to each other
        return Objects.equals(this.houseNumber,other.houseNumber)
                && Objects.equals(this.city,other.city)
                && Objects.equals(this.street,other.street)
                && Objects.equals(this.zipCode,other.zipCode);
    }
}
