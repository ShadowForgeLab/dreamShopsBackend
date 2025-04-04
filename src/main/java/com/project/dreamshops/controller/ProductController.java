package com.project.dreamshops.controller;

import com.project.dreamshops.dto.ImageDto;
import com.project.dreamshops.dto.ProdutDto;
import com.project.dreamshops.model.Product;
import com.project.dreamshops.request.AddProductRequest;
import com.project.dreamshops.request.ProductUpdateRequest;
import com.project.dreamshops.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable long id){
        try {
            Product product = service.getProductById(id);
            ProdutDto productDto = service.convertToDto(product);
            return new ResponseEntity<>(productDto,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("product not found",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts(){
        List<Product> products = service.getAllProducts();
        List<ProdutDto> convertedProducts =  service.getConvertedProducts(products);
        return new ResponseEntity<>(convertedProducts,HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestBody AddProductRequest product){
        Product product1 =service.addProduct(product);
        return new ResponseEntity<>(product1,HttpStatus.OK);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody ProductUpdateRequest productUpdateRequest,@PathVariable long id){
        Product product = service.updateProduct(productUpdateRequest,id);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id){
        service.deleteProductById(id);
        return new ResponseEntity<>("Product deleted successfully",HttpStatus.OK);
    }

    @GetMapping("/products/by/brand-name")
    public ResponseEntity<?> getProductByBrandAndName(@PathVariable String brand,@PathVariable String name){
        List<Product> products = service.getProductByBrandAndName(brand,name);
        List<ProdutDto> convertedProducts = service.getConvertedProducts(products);
        return new ResponseEntity<>(convertedProducts,HttpStatus.OK);
    }

    @GetMapping("/products/by/category-brand/")
    public ResponseEntity<?> getProductByCategoryAndBrand(@PathVariable String category,@PathVariable String brand){
        List<Product> products = service.getProductByCategoryAndBrand(category,brand);
        List<ProdutDto> convertedProducts = service.getConvertedProducts(products);
        return new ResponseEntity<>(convertedProducts,HttpStatus.OK);
    }
    @GetMapping("/product/by/{category}")
    public ResponseEntity<?> getProductByCategory(@PathVariable String category){
        List<Product> products = service.getProductByCategory(category);
        List<ProdutDto> convertedProducts = service.getConvertedProducts(products);
        return new ResponseEntity<>(convertedProducts,HttpStatus.OK);
    }

    @GetMapping("/product /by/brand")
    public ResponseEntity<?> getProductByBrand(@RequestParam String brand){
        List<Product> products = service.getProductByBrand(brand);
        List<ProdutDto> convertedProducts= service.getConvertedProducts(products);
        return new ResponseEntity<>(convertedProducts,HttpStatus.OK);
    }

    @GetMapping("/products/by/name")
    public ResponseEntity<?> getProductByName(@PathVariable String name){
        List<Product> products = service.getProductByName(name);
        List<ProdutDto> convertedProducts = service.getConvertedProducts(products);
        return new ResponseEntity<>(convertedProducts,HttpStatus.OK);
    }


}
