package team9.clover.Model;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    String id, address, fullName, phone;
    List<String> date;
    Timestamp order;
    long noProducts, ship, total;

    public OrderModel() { }

    public OrderModel(boolean tmp) {
        id = "";
        address = "";
        fullName = "";
        phone = "";
        date = new ArrayList<>();
        noProducts = 0;
        ship = 0;
        total = 0;
        order = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getDate() {
        return date;
    }

    public void setDate(List<String> date) {
        this.date = date;
    }

    public long getNoProducts() {
        return noProducts;
    }

    public void setNoProducts(long noProducts) {
        this.noProducts = noProducts;
    }

    public long getShip() {
        return ship;
    }

    public void setShip(long ship) {
        this.ship = ship;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Timestamp getOrder() {
        return order;
    }

    public void setOrder(Timestamp order) {
        this.order = order;
    }

    public long takeShipTotal() {
        return total + ship;
    }

    public void addDate(String newDate) {
        date.add(newDate);
    }
}
