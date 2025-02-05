package FurnitureApp.CustomerInfo;

public class Customer {
    // name of customer
    private String name;
    private Address address = new Address();

    // Sets name of customer
    public void setName(String name) {
        this.name = name;
    }

    // Returns name of customer
    public String getName() {
        return name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    // Need to add .equals and hashcode
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) o;
        return name.equals(other.name) && address.equals(other.address);
    }

    // Override toString() so the ListView shows the customer's name.
    @Override
    public String toString() {
        return getName();
    }
}
