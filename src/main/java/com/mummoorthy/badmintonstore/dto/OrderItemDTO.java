package com.mummoorthy.badmintonstore.dto;


public class OrderItemDTO {

    private Long id;
    private String productName;
    private String weight;
    private String gripSize;
    private Integer quantity;
    private Double price;
    private String productImageUrl;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getGripSize() {
		return gripSize;
	}
	public void setGripSize(String gripSize) {
		this.gripSize = gripSize;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getProductImageUrl() {
		return productImageUrl;
	}
	public void setProductImageUrl(String productImageUrl) {
		this.productImageUrl = productImageUrl;
	}

    // getters/setters
}