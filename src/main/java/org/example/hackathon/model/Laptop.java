package org.example.hackathon.model;

import jakarta.validation.constraints.*;

public class Laptop {

    private Long id;
    @NotBlank(message = "Tên laptop không được để trống")
    @Size(min = 3, max = 100, message = "Tên laptop phải từ 3 đến 100 ký tự")
    private String modelName;
    @NotBlank(message = "Tên hãng không được để trống")
    private String brand;
    @NotNull(message = "Giá không được để trống")
    @Min(value = 1, message = "Giá phải lớn hơn 0")
    private Double price;
    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0, message = "Số lượng phải lớn hơn hoặc bằng 0")
    private Integer quantity;
    private String image;
    public Laptop() {}
    public Laptop(Long id, String modelName, String brand,
                  Double price, Integer quantity, String image) {
        this.id = id;
        this.modelName = modelName;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getModelName() {
        return modelName;
    }
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}