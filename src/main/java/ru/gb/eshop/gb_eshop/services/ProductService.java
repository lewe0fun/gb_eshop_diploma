package ru.gb.eshop.gb_eshop.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.eshop.gb_eshop.models.Category;
import ru.gb.eshop.gb_eshop.models.Product;
import ru.gb.eshop.gb_eshop.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    public Product getProductId(int id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    @Transactional
    public void saveProduct(Product product, Category category_db){

//        product.setCategory(category);
        productRepository.save(product);
    }

    @Transactional
    public void updateProduct(int id, Product product){
        product.setId(id);
        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(int id){
        productRepository.deleteById(id);
    }

    public Product  getProductFindByTitle(Product product){
        Optional<Product> product_db = productRepository.findByTitle(product.getTitle());
        return product_db.orElse(null);
    }
}
