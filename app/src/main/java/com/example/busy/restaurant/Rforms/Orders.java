package com.example.busy.restaurant.Rforms;

class Orders {
    private String name;
    private String phone;
    private String address;
    private String order;
    private String status = "0";

    public Orders() {
        this.name = "0";
        this.phone = "0";
        this.address = "0";
        this.order = "0";
    }

    public Orders(String name, String phone, String address, String order) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", order='" + order + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
