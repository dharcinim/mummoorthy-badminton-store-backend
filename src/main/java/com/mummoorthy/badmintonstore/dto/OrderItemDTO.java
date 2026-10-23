package com.mummoorthy.badmintonstore.dto;


public class OrderItemDTO {

    private Long id;
    private Long productId;
    private Long productVariantId;
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getProductVariantId() {
		return productVariantId;
	}
	public void setProductVariantId(Long productVariantId) {
		this.productVariantId = productVariantId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductImageUrl() {
		return productImageUrl;
	}
	public void setProductImageUrl(String productImageUrl) {
		this.productImageUrl = productImageUrl;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
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
	private String productName;
    private String productImageUrl;
    private String brandName;
    private String weight;
    private String gripSize;
    private Integer quantity;
    private Double price;
    
	
    
}