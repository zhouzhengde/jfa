/*
 * Copyright (c) 2016. Bond(China), java freestyle app
 */

package com.lscsoft.jfa.commons.json.example;

import com.lscsoft.jfa.commons.json.annotation.JsonPathValue;

import java.io.Serializable;

public class Person implements Serializable {

    @JsonPathValue(path = "Id")
    private int id;

    @JsonPathValue(path = "Name")
    private String name;

    @JsonPathValue(path = "Contact.MobilePhone", dataType = JsonPathValue.DataType.Date, pattern = "yyyy-NM-ddTHH:mm:ss 08:00")
    private String mobilePhone;

    @JsonPathValue(path = "Contact.Email")
    private String email;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
