package com.mummoorthy.badmintonstore.excel;
import com.mummoorthy.badmintonstore.repository.BrandRepository;
import com.mummoorthy.badmintonstore.entity.Brand;
import com.mummoorthy.badmintonstore.entity.Category;
import com.mummoorthy.badmintonstore.entity.Product;
import com.mummoorthy.badmintonstore.entity.ProductVariant;
import com.mummoorthy.badmintonstore.entity.ProductImage;
import com.mummoorthy.badmintonstore.repository.CategoryRepository;
import com.mummoorthy.badmintonstore.repository.SubCategoryRepository;
import com.mummoorthy.badmintonstore.repository.ProductRepository;
import com.mummoorthy.badmintonstore.repository.ProductVariantRepository;
import com.mummoorthy.badmintonstore.repository.ProductImageRepository;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;

@Service
public class ExcelImportServiceImpl implements ExcelImportService {
     	
	 private static final String CLOUDINARY_BASE_URL =
	            "https://res.cloudinary.com/hwrjcjaf/image/upload/";


	    @Autowired
	    private BrandRepository brandRepository;

	    @Autowired
	    private CategoryRepository categoryRepository;

	    @Autowired
	    private SubCategoryRepository subCategoryRepository;

	    @Autowired
	    private ProductRepository productRepository;

	    @Autowired
	    private ProductVariantRepository productVariantRepository;

	    @Autowired
	    private ProductImageRepository productImageRepository;

	    @Override
	    public String importHundredProducts() {

	        try {

	            InputStream inputStream =
	                    getClass().getResourceAsStream("/excel/Master_catalog_(6.6).xlsx");

	            if (inputStream == null) {
	                return "Excel file not found!";
	            }

	            Workbook workbook = new XSSFWorkbook(inputStream);

	            System.out.println("=================================");
	            System.out.println("Workbook Loaded Successfully");
	            System.out.println("=================================");
	            
	            System.out.println("Total Sheets : " + workbook.getNumberOfSheets());

	            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {

	                System.out.println(workbook.getSheetName(i));

	            }
	            
	            
	            Sheet brandSheet = workbook.getSheet("Brands");
	            
	         // ===================== CATEGORIES =====================

	            Sheet categorySheet = workbook.getSheet("Categories");

	            System.out.println();
	            System.out.println("=================================");
	            System.out.println("Sheet Name : " + categorySheet.getSheetName());
	            System.out.println("=================================");

	            for (Row row : categorySheet) {

	                if (row.getRowNum() == 0) {
	                    continue;
	                }

	                String categoryName = row.getCell(1).getStringCellValue().trim();

	                if (categoryRepository.findByName(categoryName).isEmpty()) {

	                    Category category = new Category();
	                    category.setName(categoryName);

	                    categoryRepository.save(category);

	                    System.out.println("Saved Category : " + categoryName);

	                } else {

	                    System.out.println("Category already exists : " + categoryName);

	                }
	            }

	            System.out.println("Sheet Name : " + brandSheet.getSheetName());

	            for (Row row : brandSheet) {

	                if (row.getRowNum() == 0) {
	                    continue;
	                }

	                String brandName = row.getCell(1).getStringCellValue().trim();

	                if (brandRepository.findByName(brandName).isEmpty()) {

	                    Brand brand = new Brand();
	                    brand.setName(brandName);

	                    brandRepository.save(brand);

	                    System.out.println("Saved Brand : " + brandName);

	                } else {

	                    System.out.println("Brand already exists : " + brandName);

	                }
	            }

	         // ===================== PRODUCTS =====================

	            Sheet productSheet = workbook.getSheet("Products");
	            
	         // Excel Product ID -> Actual Database Product ID
	            Map<Long, Long> productIdMap = new HashMap<>();

	            System.out.println();
	            System.out.println("=================================");
	            System.out.println("Sheet Name : " + productSheet.getSheetName());
	            System.out.println("=================================");

	            for (Row row : productSheet) {

	                if (row.getRowNum() == 0) {
	                    continue;
	                }

	                
	                Long excelProductId =
	                        (long) row.getCell(0).getNumericCellValue();

	               
	                
	                String sku =
	                        row.getCell(1).getStringCellValue().trim();

	                String brandName =
	                        row.getCell(2).getStringCellValue().trim();

	                String categoryName =
	                        row.getCell(3).getStringCellValue().trim();

	                String productName =
	                        row.getCell(4).getStringCellValue().trim();

	                String status =
	                        row.getCell(5).getStringCellValue().trim();

	                String shortDescription =
	                        row.getCell(6).getStringCellValue().trim();

	                double mrp =
	                        row.getCell(7).getNumericCellValue();

	                double sellingPrice =
	                        row.getCell(8).getNumericCellValue();

	                Brand brand =
	                        brandRepository.findByName(brandName).orElse(null);

	                Category category =
	                        categoryRepository.findByName(categoryName).orElse(null);

	                if (brand == null || category == null) {

	                    System.out.println(
	                            "Brand or Category not found for : "
	                                    + productName
	                    );

	                    continue;
	                }

	                // Check whether product already exists
	                Product product =
	                        productRepository.findByName(productName).orElse(null);

	                if (product != null) {

	                    System.out.println(
	                            "Product already exists : "
	                                    + productName
	                                    + " | DB ID : "
	                                    + product.getId()
	                    );

	                } else {

	                    product = new Product();

	                    product.setName(productName);
	                    product.setSlug(sku.toLowerCase());
	                    product.setShortDescription(shortDescription);
	                    product.setBrand(brand);
	                    product.setCategory(category);
	                    product.setActive(
	                            status.equalsIgnoreCase("Active")
	                    );

	                    product = productRepository.save(product);

	                    System.out.println(
	                            "Saved Product : "
	                                    + productName
	                                    + " | DB ID : "
	                                    + product.getId()
	                    );
	                }

	                // VERY IMPORTANT
	                // Store Excel ID -> Database ID
	                productIdMap.put(
	                        excelProductId,
	                        product.getId()
	                );

	                System.out.println(
	                        "Product Mapping : Excel ID "
	                                + excelProductId
	                                + " -> DB ID "
	                                + product.getId()
	                );
	            }
	            Sheet variantSheet = workbook.getSheet("Product Variants");

	            System.out.println();
	            System.out.println("=================================");
	            System.out.println("Sheet Name : " + variantSheet.getSheetName());
	            System.out.println("=================================");
	            
	            for (Row row : variantSheet) {

	                if (row.getRowNum() == 0) {
	                    continue;
	                }

	                Long excelProductId =
	                        (long) row.getCell(1).getNumericCellValue();

	                String variantType =
	                        row.getCell(2).getStringCellValue().trim();

	                String variant =
	                        row.getCell(3).getStringCellValue().trim();

	                Double mrp =
	                        row.getCell(4).getNumericCellValue();

	                Double sellingPrice =
	                        row.getCell(5).getNumericCellValue();

	                String color =
	                        row.getCell(6).getStringCellValue().trim();

	                Integer stock =
	                        (int) row.getCell(7).getNumericCellValue();

	                String weight = "";

	                if (row.getCell(8) != null) {

	                    Cell weightCell = row.getCell(8);

	                    if (weightCell.getCellType()
	                            == org.apache.poi.ss.usermodel.CellType.STRING) {

	                        weight =
	                                weightCell.getStringCellValue().trim();

	                    } else if (weightCell.getCellType()
	                            == org.apache.poi.ss.usermodel.CellType.NUMERIC) {

	                        weight =
	                                String.valueOf(
	                                        weightCell.getNumericCellValue()
	                                );
	                    }
	                }

	                String gripSize = "";

	                if (row.getCell(9) != null) {

	                    Cell gripCell = row.getCell(9);

	                    if (gripCell.getCellType()
	                            == org.apache.poi.ss.usermodel.CellType.STRING) {

	                        gripSize =
	                                gripCell.getStringCellValue().trim();

	                    } else if (gripCell.getCellType()
	                            == org.apache.poi.ss.usermodel.CellType.NUMERIC) {

	                        gripSize =
	                                String.valueOf(
	                                        gripCell.getNumericCellValue()
	                                );
	                    }
	                }

	                Long dbProductId =
	                        productIdMap.get(excelProductId);

	                if (dbProductId == null) {

	                    System.out.println(
	                            "Product mapping not found for Excel ID : "
	                                    + excelProductId
	                    );

	                    continue;
	                }

	                Product product =
	                        productRepository.findById(dbProductId).orElse(null);

	                if (product == null) {

	                    System.out.println(
	                            "Product not found : " + dbProductId
	                    );

	                    continue;
	                }

	                String sku =
	                        product.getSlug()
	                                + "-"
	                                + variant.toLowerCase()
	                                .replace(" ", "-")
	                                + "-"
	                                + color.toLowerCase()
	                                .replace("/", "-")
	                                .replace(" ", "-");

	                if (productVariantRepository
	                        .findBySku(sku)
	                        .isPresent()) {

	                    System.out.println(
	                            "Variant already exists : " + sku
	                    );

	                    continue;
	                }

	                ProductVariant productVariant =
	                        new ProductVariant();

	                productVariant.setProduct(product);
	                productVariant.setSku(sku);
	                productVariant.setMrp(mrp);
	                productVariant.setSellingPrice(sellingPrice);
	                productVariant.setStock(stock);
	                productVariant.setColor(color);
	                productVariant.setWeight(weight);
	                productVariant.setSize(variant);
	                productVariant.setGripSize(gripSize);
	                productVariant.setActive(true);

	                productVariantRepository.save(productVariant);

	                System.out.println(
	                        "Saved Variant : " + sku
	                );
	            }


				// ===================== PRODUCT IMAGES =====================

				Sheet imageSheet = workbook.getSheet("Product Images");

				System.out.println();
				System.out.println("=================================");
				System.out.println("Sheet Name : " + imageSheet.getSheetName());
				System.out.println("=================================");

				for (Row row : imageSheet) {

					if (row.getRowNum() == 0) {
						continue;
					}

					try {

						// Excel Product ID
						Long excelProductId =
								(long) row.getCell(0).getNumericCellValue();

						// Image file name
						String imageName = "";

						if (row.getCell(1) != null) {
							imageName =
									row.getCell(1).toString().trim();
						}

						// Primary
						String primary = "";

						if (row.getCell(2) != null) {
							primary =
									row.getCell(2).toString().trim();
						}

						// Display order
						Integer displayOrder = 1;

						if (row.getCell(3) != null) {

							if (row.getCell(3).getCellType()
									== org.apache.poi.ss.usermodel.CellType.NUMERIC) {

								displayOrder =
										(int) row.getCell(3).getNumericCellValue();
							}
						}

						// =====================================================
						// IMAGE URL COLUMN
						// Column E = Image URL
						// =====================================================

						String imageUrl = "";

						if (row.getCell(4) != null) {

							imageUrl =
									row.getCell(4).toString().trim();
						}

						// =====================================================
						// FALLBACK FOR NORMAL IMAGE FILES
						// =====================================================

						if (imageUrl.isEmpty()) {

							String lowerName =
									imageName.toLowerCase();

							if (lowerName.endsWith(".jpg")
									|| lowerName.endsWith(".jpeg")
									|| lowerName.endsWith(".png")
									|| lowerName.endsWith(".webp")) {

								imageUrl =
										CLOUDINARY_BASE_URL + imageName;

							} else {

								System.out.println(
										"Skipping MHTML image because no Image URL was provided : "
												+ imageName
								);

								continue;
							}
						}

						// =====================================================
						// EXCEL PRODUCT ID -> DATABASE PRODUCT ID
						// =====================================================

						Long dbProductId =
								productIdMap.get(excelProductId);

						if (dbProductId == null) {

							System.out.println(
									"Image product mapping not found for Excel ID : "
											+ excelProductId
							);

							continue;
						}

						Product product =
								productRepository
										.findById(dbProductId)
										.orElse(null);

						if (product == null) {

							System.out.println(
									"Product not found for DB ID : "
											+ dbProductId
							);

							continue;
						}

						// =====================================================
						// CHECK DUPLICATE
						// =====================================================

						if (productImageRepository
								.existsByProductAndImageUrl(
										product,
										imageUrl)) {

							System.out.println(
									"Image already exists : "
											+ imageUrl
							);

							continue;
						}

						// =====================================================
						// SAVE IMAGE
						// =====================================================

						ProductImage productImage =
								new ProductImage();

						productImage.setProduct(product);

						productImage.setImageUrl(imageUrl);

						productImage.setAltText(
								product.getName()
						);

						productImage.setDisplayOrder(
								displayOrder
						);

						productImage.setPrimaryImage(
								primary.equalsIgnoreCase("Yes")
						);

						productImageRepository.save(productImage);

						System.out.println(
								"Saved Image : "
										+ imageName
										+ " -> "
										+ imageUrl
										+ " -> "
										+ product.getName()
						);

					} catch (Exception e) {

						System.out.println(
								"Image import error at row : "
										+ row.getRowNum()
						);

						e.printStackTrace();
					}
				}
	         // ===================== PRODUCT SPECIFICATIONS =====================

	            Sheet specificationSheet = workbook.getSheet("Product Specifications");

	            System.out.println();
	            System.out.println("=================================");
	            System.out.println("Sheet Name : " + specificationSheet.getSheetName());
	            System.out.println("=================================");
	            
	            for (Row row : specificationSheet) {

	                if (row.getRowNum() == 0) {
	                    continue;
	                }

	                Long excelProductId =
	                        (long) row.getCell(0).getNumericCellValue();

	                String specification =
	                        row.getCell(1).getStringCellValue().trim();

	                String value =
	                        row.getCell(2).getStringCellValue().trim();

	                Long dbProductId =
	                        productIdMap.get(excelProductId);

	                if (dbProductId == null) {

	                    System.out.println(
	                            "Specification product mapping not found for Excel ID : "
	                                    + excelProductId
	                    );

	                    continue;
	                }

	                Product product =
	                        productRepository.findById(dbProductId).orElse(null);

	                if (product == null) {

	                    System.out.println(
	                            "Product not found for DB ID : "
	                                    + dbProductId
	                    );

	                    continue;
	                }

	                if (product == null) {
	                    System.out.println("Product not found : " + dbProductId);
	                    continue;
	                }

	                List<ProductVariant> variants =
	                        productVariantRepository.findByProductId(dbProductId);

	                if (variants.isEmpty()) {
	                	System.out.println("Variant not found : " + dbProductId);
	                    continue;
	                }
	                
	                System.out.println("Number of variants for Product " + dbProductId + " : " + variants.size());

	                for (ProductVariant productVariant : variants) {
	                	
	                	System.out.println("Updating Variant ID : " + productVariant.getId()
	                    + " | SKU : " + productVariant.getSku());
	                
	                switch (specification) 
	                {

		                case "Material":
		                    productVariant.setFrameMaterial(value);
		                    break;
	
		                case "Skill Level":
		                    productVariant.setPlayingLevel(value);
		                    break;
	
		                default:
		                    continue;
	               }
	                
	                
	                
	                productVariantRepository.save(productVariant);
	                }
	                System.out.println("Updated " + specification + " for Product DB ID : " + dbProductId);
	                
	                

	                System.out.println("--------------------------------");
	                System.out.println("Product ID : " + dbProductId);
	                System.out.println("Specification : " + specification);
	                System.out.println("Value : " + value);
	            }

	            workbook.close();

	            return "Workbook Loaded Successfully";

	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Import Failed";
	        }
	    }
	}
	
   