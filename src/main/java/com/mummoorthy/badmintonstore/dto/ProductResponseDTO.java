package com.mummoorthy.badmintonstore.dto;

public class ProductResponseDTO {

    private Long id;

    private String name;

    private String slug;

    private String description;

    private String shortDescription;

    private String hsnCode;

    private Double gstPercentage;

    private String warranty;

    private String countryOfOrigin;

    private Boolean featured;

    private Boolean bestSeller;

    private Boolean newArrival;

    private Boolean active;

    private String brandName;

    private String categoryName;

    private String subCategoryName;
    
    private Double mrp;

    private Double sellingPrice;

    private Integer stock;

    private String imageUrl;
    
    private Double rating;

    private Integer reviews;
    
    private String weight;
    private String flexibility;
    private String playingLevel;
    private String balance;
    private String frameMaterial;

    public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getFlexibility() {
		return flexibility;
	}

	public void setFlexibility(String flexibility) {
		this.flexibility = flexibility;
	}

	public String getPlayingLevel() {
		return playingLevel;
	}

	public void setPlayingLevel(String playingLevel) {
		this.playingLevel = playingLevel;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getFrameMaterial() {
		return frameMaterial;
	}

	public void setFrameMaterial(String frameMaterial) {
		this.frameMaterial = frameMaterial;
	}

	public Double getMrp() {
		return mrp;
	}

	public void setMrp(Double mrp) {
		this.mrp = mrp;
	}

	public Double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Integer getReviews() {
		return reviews;
	}

	public void setReviews(Integer reviews) {
		this.reviews = reviews;
	}

	public ProductResponseDTO() {
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

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}

	public Double getGstPercentage() {
		return gstPercentage;
	}

	public void setGstPercentage(Double gstPercentage) {
		this.gstPercentage = gstPercentage;
	}

	public String getWarranty() {
		return warranty;
	}

	public void setWarranty(String warranty) {
		this.warranty = warranty;
	}

	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}

	public void setCountryOfOrigin(String countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}

	public Boolean getFeatured() {
		return featured;
	}

	public void setFeatured(Boolean featured) {
		this.featured = featured;
	}

	public Boolean getBestSeller() {
		return bestSeller;
	}

	public void setBestSeller(Boolean bestSeller) {
		this.bestSeller = bestSeller;
	}

	public Boolean getNewArrival() {
		return newArrival;
	}

	public void setNewArrival(Boolean newArrival) {
		this.newArrival = newArrival;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
}