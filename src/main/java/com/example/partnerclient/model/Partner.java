package com.example.partnerclient.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Partner {
    private Long id;
    private final StringProperty name;
    private final StringProperty director;
    private final StringProperty address;
    private final StringProperty phone;
    private final StringProperty fax;

    public Partner() {
        this("", "", "", "", "");
    }

    public Partner(String name, String director, String address, String phone, String fax) {
        this.name = new SimpleStringProperty(name);
        this.director = new SimpleStringProperty(director);
        this.address = new SimpleStringProperty(address);
        this.phone = new SimpleStringProperty(phone);
        this.fax = new SimpleStringProperty(fax);
    }

    // ID
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // Name
    public String getName() { return name.get(); }
    public void setName(String value) { name.set(value); }
    public StringProperty nameProperty() { return name; }

    // Director
    public String getDirector() { return director.get(); }
    public void setDirector(String value) { director.set(value); }
    public StringProperty directorProperty() { return director; }

    // Address
    public String getAddress() { return address.get(); }
    public void setAddress(String value) { address.set(value); }
    public StringProperty addressProperty() { return address; }

    // Phone
    public String getPhone() { return phone.get(); }
    public void setPhone(String value) { phone.set(value); }
    public StringProperty phoneProperty() { return phone; }

    // Fax
    public String getFax() { return fax.get(); }
    public void setFax(String value) { fax.set(value); }
    public StringProperty faxProperty() { return fax; }
}
