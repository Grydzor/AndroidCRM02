package com.grydzor.androidcrm02.form;

import java.math.BigDecimal;


public class ProductForm {
    private Long id;

    private String name;

    private BigDecimal price;

    private String filename;

    private String description;

    public ProductForm() {}

    public ProductForm(String name, BigDecimal price, String pathImage, String description) {
        this.name = name;
        this.price = price;
        this.filename = pathImage;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {return price;}

    public void setPrice(BigDecimal price) {this.price = price;}

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
