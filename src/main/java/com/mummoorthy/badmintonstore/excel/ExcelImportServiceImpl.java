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
	                    getClass().getResourceAsStream("/excel/Master_catalog (2).xlsx");

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

	            System.out.println();
	            System.out.println("=================================");
	            System.out.println("Sheet Name : " + productSheet.getSheetName());
	            System.out.println("=================================");

	            for (Row row : productSheet) {

	                if (row.getRowNum() == 0) {
	                    continue;
	                }

	                // Read only first 6 products
	                if (row.getRowNum() > 6) {
	                    break;
	                }

	                String sku = row.getCell(1).getStringCellValue().trim();
	                String brandName = row.getCell(2).getStringCellValue().trim();
	                String categoryName = row.getCell(3).getStringCellValue().trim();
	                String productName = row.getCell(4).getStringCellValue().trim();
	                String status = row.getCell(5).getStringCellValue().trim();
	                String shortDescription = row.getCell(6).getStringCellValue().trim();
	                double mrp = row.getCell(7).getNumericCellValue();
	                double sellingPrice = row.getCell(8).getNumericCellValue();

	                Brand brand = brandRepository.findByName(brandName).orElse(null);
	                Category category = categoryRepository.findByName(categoryName).orElse(null);

	                if (brand == null || category == null) {
	                    System.out.println("Brand or Category not found for : " + productName);
	                    continue;
	                }

	                if (productRepository.findByName(productName).isPresent()) {
	                    System.out.println("Product already exists : " + productName);
	                    continue;
	                }

	                Product product = new Product();

	                product.setName(productName);
	                product.setSlug(sku.toLowerCase());
	                product.setShortDescription(shortDescription);
	                product.setBrand(brand);
	                product.setCategory(category);
	                product.setActive(status.equalsIgnoreCase("Active"));

	                productRepository.save(product);

	                System.out.println("Saved Product : " + productName);
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

	                if (row.getRowNum() > 9) {
	                    break;
	                }

	                Long productId = (long) row.getCell(1).getNumericCellValue();

	                String variantType = row.getCell(2).getStringCellValue().trim();
	                String variant = row.getCell(3).getStringCellValue().trim();

	                Double mrp = row.getCell(4).getNumericCellValue();
	                Double sellingPrice = row.getCell(5).getNumericCellValue();

	                String color = row.getCell(6).getStringCellValue().trim();

	                Integer stock = (int) row.getCell(7).getNumericCellValue();

	                String weight = "";
	                if (row.getCell(8) != null) {
	                    weight = row.getCell(8).getStringCellValue().trim();
	                }
	                
	                String gripSize = "";

	                if (row.getCell(9) != null) {
	                    gripSize = row.getCell(9).getStringCellValue().trim();
	                }
	               


	         
	                Product product = productRepository.findById(productId).orElse(null);

	                if (product == null) {
	                    System.out.println("Product not found : " + productId);
	                    continue;
	                }

	                String sku = product.getSlug()
	                        + "-"
	                        + variant.toLowerCase().replace(" ", "-")
	                        + "-"
	                        + color.toLowerCase().replace("/", "-").replace(" ", "-");
	                
	                if (productVariantRepository.findBySku(sku).isPresent()) {
	                    System.out.println("Variant already exists : " + sku);
	                    continue;
	                }
	                
	                
	                
		               ProductVariant productVariant = new ProductVariant();
		                
		                productVariant.setProduct(product);
		                productVariant.setSku(sku);
		                productVariant.setMrp(mrp);
		                productVariant.setSellingPrice(sellingPrice);
		                productVariant.setStock(stock);
		                productVariant.setColor(color);
		                productVariant.setWeight(weight);
		                productVariant.setGripSize(gripSize);
		                productVariant.setActive(true);
	                
	               productVariantRepository.save(productVariant);
	               System.out.println("Saved Variant : " + sku);

	                

	                System.out.println("-----------------------------");
	                System.out.println("Product ID : " + productId);
	                System.out.println("Variant Type : " + variantType);
	                System.out.println("Variant : " + variant);
	                System.out.println("MRP : " + mrp);
	                System.out.println("Selling Price : " + sellingPrice);
	                System.out.println("Color : " + color);
	                System.out.println("Stock : " + stock);
	                System.out.println("Weight : " + weight);
	                System.out.println("Grip Size : " + gripSize);
	            }

	                
	            Sheet imageSheet = workbook.getSheet("Product Images");

	            System.out.println();
	            System.out.println("=================================");
	            System.out.println("Sheet Name : " + imageSheet.getSheetName());
	            System.out.println("=================================");

	            for (Row row : imageSheet) {

	                if (row.getRowNum() == 0) {
	                    continue;
	                }

	                // For testing (remove later)
	                if (row.getRowNum() > 30) {
	                    break;
	                }

	                Long productId = (long) row.getCell(0).getNumericCellValue();

	                String imageName = row.getCell(1).getStringCellValue().trim();

	                String primary = row.getCell(2).getStringCellValue().trim();

	                Integer displayOrder = (int) row.getCell(3).getNumericCellValue();

	                Product product = productRepository.findById(productId).orElse(null);

	                if (product == null) {
	                    System.out.println("Product not found : " + productId);
	                    continue;
	                }

	                ProductImage productImage = new ProductImage();

	                productImage.setProduct(product);

	                

	                productImage.setImageUrl(CLOUDINARY_BASE_URL +imageName);

	                productImage.setAltText(product.getName());

	                productImage.setDisplayOrder(displayOrder);

	                productImage.setPrimaryImage(primary.equalsIgnoreCase("Yes"));

	                productImageRepository.save(productImage);

	                System.out.println("Saved Image : " + imageName);
	            }
	            

	            workbook.close();

	            return "Workbook Loaded Successfully";

	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Import Failed";
	        }
	    }
	}
	
   