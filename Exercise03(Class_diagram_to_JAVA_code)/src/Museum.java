import java.util.ArrayList;
import java.util.List;

public class Museum {
    String name;
    String address;
    String city;
    String country;
    List<Hall> hallList;

    public Museum(String name, String address, String city, String country) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.hallList = new ArrayList<Hall>();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Hall> getHallList() {
        return hallList;
    }

}
