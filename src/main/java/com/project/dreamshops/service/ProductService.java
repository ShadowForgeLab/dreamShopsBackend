package com.project.dreamshops.service;

import com.project.dreamshops.dto.ImageDto;
import com.project.dreamshops.dto.ProdutDto;
import com.project.dreamshops.exception.ProductNotFoundException;
import com.project.dreamshops.model.Category;
import com.project.dreamshops.model.Image;
import com.project.dreamshops.model.Product;
import com.project.dreamshops.repo.CategoryRepo;
import com.project.dreamshops.repo.ImageRepo;
import com.project.dreamshops.repo.ProductRepo;
import com.project.dreamshops.request.AddProductRequest;
import com.project.dreamshops.request.ProductUpdateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepo repo; // Repository for handling Product-related operations
    @Autowired
    private ImageRepo imageRepo;

    @Autowired
    private CategoryRepo categoryRepo; // Repository for handling Category-related operations
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Adds a new product.
     * If the given category does not exist, it creates a new category.
     */
    @Override
    public Product addProduct(AddProductRequest request) {

        // Check if the category exists, if not, create and save a new one
        Category category = Optional.ofNullable(categoryRepo.
                        findByName(request.getCategory().getName())).
                orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepo.save(newCategory);
                });

        // Set the found or newly created category in the request
        request.setCategory(category);

        // Create and save the product
        return repo.save(createProduct(request, category));
    }

    /**
     * Creates a Product entity from the given request and category.
     */
    public Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    /**
     * Fetches all products from the database.
     */
    @Override
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    /**
     * Fetches a single product by its ID.
     * Throws ProductNotFoundException if the product is not found.
     */
    @Override
    public Product getProductById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
    }

    /**
     * Deletes a product by its ID.
     * Throws ProductNotFoundException if the product does not exist.
     */
    @Override
    public void deleteProductById(Long id) {
        repo.findById(id)
                .ifPresentOrElse(repo::delete,
                        () -> {
                            throw new ProductNotFoundException("Product Not Found");
                        });
    }

    /**
     * Updates an existing product with new data.
     * Throws ProductNotFoundException if the product does not exist.
     */
    @Override
    public Product updateProduct(ProductUpdateRequest request, Long id) {
        return repo.findById(id)
                .map(existingProduct -> updateExistingProduct(existingProduct, request)) // Update existing product
                .map(repo::save) // Save the updated product
                .orElseThrow(() -> new ProductNotFoundException("Product Not Found"));
    }

    /**
     * Updates an existing Product entity with new values from the update request.
     */
    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        // Find the category by name and update it
        Category category = categoryRepo.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    /**
     * Fetches all products that belong to a specific category.
     */
    @Override
    public List<Product> getProductByCategory(String category) {
        return repo.findByCategoryName(category);
    }

    /**
     * Fetches all products of a specific brand.
     */
    @Override
    public List<Product> getProductByBrand(String brand) {
        return repo.findByBrand(brand);
    }

    /**
     * Fetches products that belong to a specific category and brand.
     */
    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return repo.findByCategory_NameAndBrand(category, brand);
    }

    /**
     * Fetches all products with a specific name.
     */
    @Override
    public List<Product> getProductByName(String name) {
        return repo.findByName(name);
    }

    /**
     * Fetches products that match both the given name and brand.
     */
    @Override
    public List<Product> getProductByBrandAndName(String name, String brand) {
        return repo.findByBrandAndName(brand, name);
    }

    /**
     * Counts the number of products that match the given brand and name.
     */
    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return repo.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProdutDto> getConvertedProducts(List<Product> products){
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProdutDto convertToDto(Product product){
        ProdutDto produtDto = modelMapper.map(product,ProdutDto.class);
        List<Image> imageList= imageRepo.findByProductId(product.getId());
        List<ImageDto> imageDtoList = imageList.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        produtDto.setImages(imageDtoList);
        return produtDto;
    }
}