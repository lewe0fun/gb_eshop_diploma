package ru.gb.eshop.gb_eshop.controllers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.eshop.gb_eshop.repositories.ProductRepository;
import ru.gb.eshop.gb_eshop.services.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final ProductRepository productRepository;
    @Value("${category.1}")
    private String CATEGORY1;
    @Value("${category.2}")
    private String CATEGORY2;
    @Value("${category.3}")
    private String CATEGORY3;
    @Value("${category.4}")
    private String CATEGORY4;
    @Value("${category.5}")
    private String CATEGORY5;
    @Value("${category.6}")
    private String CATEGORY6;
    private final String SEARCH_PRODUCT = "search_product";
    private final String SEARCH_ASC = "sorted_by_ascending_price";
    private final String SEARCH_DES = "sorted_by_descending_price";

    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @GetMapping("")
    public String getAllProduct(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        return "/product/product";
    }

    @GetMapping("/info/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", productService.getProductId(id));
        return "/product/infoProduct";
    }

    @PostMapping("/search")
    public String productSearch(@RequestParam("search") String search,
                                @RequestParam("ot") String ot,
                                @RequestParam("do") String Do,
                                @RequestParam(value = "price", required = false, defaultValue = "") String price,
                                @RequestParam(value = "category", required = false, defaultValue = "") String category, Model model) {
        model.addAttribute("products", productService.getAllProduct());

        if (!ot.isEmpty() & !Do.isEmpty()) {
            if (!price.isEmpty()) {
                if (price.equals(SEARCH_ASC)) {
                    if (!category.isEmpty()) {
                        if (category.equals(CATEGORY1)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1));
                        } else if (category.equals(CATEGORY2)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3));
                        } else if (category.equals(CATEGORY3)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2));
                        }else if (category.equals(CATEGORY4)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 4));
                        }else if (category.equals(CATEGORY5)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 5));
                        }else if (category.equals(CATEGORY6)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 6));
                        }
                    } else {
                        model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do)));
                    }
                } else if (price.equals(SEARCH_DES)) {
                    if (!category.isEmpty()) {
                        System.out.println(category);
                        if (category.equals(CATEGORY1)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1));
                        } else if (category.equals(CATEGORY2)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3));
                        } else if (category.equals(CATEGORY3)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2));
                        }else if (category.equals(CATEGORY4)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 4));
                        }else if (category.equals(CATEGORY5)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 5));
                        }else if (category.equals(CATEGORY6)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 6));
                        }
                    } else {
                        model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do)));
                    }
                }
            } else {
                model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndPriceGreaterThanEqualAndPriceLessThanEqual(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do)));
            }
        } else {
            model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleContainingIgnoreCase(search));
        }

        model.addAttribute("value_search", search);
        model.addAttribute("value_price_ot", ot);
        model.addAttribute("value_price_do", Do);
        model.addAttribute(SEARCH_ASC, price);
        model.addAttribute(SEARCH_DES, price);
        model.addAttribute(CATEGORY1, category);
        model.addAttribute(CATEGORY2, category);
        model.addAttribute(CATEGORY3, category);
        model.addAttribute(CATEGORY4, category);
        model.addAttribute(CATEGORY5, category);
        model.addAttribute(CATEGORY6, category);
        model.addAttribute("products", productService.getAllProduct());
        return "/product/product";
    }

    // Метод для поиска по части наименования (для header)
    @PostMapping("/searchHeader")
    public String productSearchHeader(@RequestParam("search") String search, Model model) {
        model.addAttribute("products", productService.getAllProduct());
        model.addAttribute("search_product_header", productRepository.findByTitleContainingIgnoreCase(search));
        model.addAttribute("value_search", search);
        model.addAttribute("products", productService.getAllProduct());
        return "/product/product";
    }
}


