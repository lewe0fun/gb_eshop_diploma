package ru.gb.eshop.gb_eshop.controllers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.eshop.gb_eshop.models.Person;
import ru.gb.eshop.gb_eshop.models.Product;
import ru.gb.eshop.gb_eshop.repositories.ProductRepository;
import ru.gb.eshop.gb_eshop.services.ProductService;

import java.util.List;

/**
 * Контроллер продуктов
 *
 * @author Пакулин Ю.А., Строев Д.В., Брылин М.В.
 * @version 1.0
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    /**
     * Поле productService
     */
    private final ProductService productService;

    /**
     * Поле productRepository
     */
    private final ProductRepository productRepository;

    /**
     * Поле CATEGORY1
     */
    @Value("${category.1}")
    private String CATEGORY1;

    /**
     * Поле CATEGORY2
     */
    @Value("${category.2}")
    private String CATEGORY2;

    /**
     * Поле CATEGORY3
     */
    @Value("${category.3}")
    private String CATEGORY3;

    /**
     * Поле CATEGORY4
     */
    @Value("${category.4}")
    private String CATEGORY4;

    /**
     * Поле CATEGORY5
     */
    @Value("${category.5}")
    private String CATEGORY5;

    /**
     * Поле CATEGORY6
     */
    @Value("${category.6}")
    private String CATEGORY6;

    /**
     * Поле SEARCH_PRODUCT
     */
    private final String SEARCH_PRODUCT = "search_product";

    /**
     * Поле SEARCH_ASC
     */
    private final String SEARCH_ASC = "sorted_by_ascending_price";

    /**
     * Поле SEARCH_DES
     */
    private final String SEARCH_DES = "sorted_by_descending_price";

    /**
     * Поле VALUE_SEARCH
     */
    private final String VALUE_SEARCH = "value_search";

    /**
     * Поле PRISE_OT
     */
    private final String PRISE_OT = "min_price";

    /**
     * Поле PRISE_DO
     */
    private final String PRISE_DO = "max_price";

    /**
     * Поле PRODUCTS
     */
    private final String PRODUCTS = "products";

    /**
     * Поле PRODUCT
     */
    private final String PRODUCT = "product";

    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    /**
     * Метод возвращает представление со всеми товарами
     *
     * @param model модель
     * @return представление со всеми товарами
     */
    @GetMapping("")
    public String getAllProduct(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            Person person = (Person) auth.getPrincipal();
            Person guest = new Person();
            guest.setLogin("гость");
            if (person != null) {
                model.addAttribute("person", person);
            } else {
                model.addAttribute("person", guest);
            }
        }
        model.addAttribute(PRODUCTS, productService.getAllProduct());
        return "/product/product";
    }

    /**
     * Метод возвращает представление с инфой о товаре
     *
     * @param id    id товара
     * @param model модель
     * @return представление с инфой о товаре
     */
    @GetMapping("/info/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model) {
        model.addAttribute(PRODUCT, productService.getProductId(id));
        return "/product/infoProduct";
    }

    /**
     * Метод поиска товаров по параметрам
     *
     * @param search   ключевое слово для поиска
//     * @param ot       нижний передел цены
//     * @param Do       верхний предел цены
     * @param sort    цена
     * @param category категория товара
     * @param model    модель
     * @return представление страницы с найденными товарами
     */
    @PostMapping("/search")
    public String productSearch(@RequestParam(value = "search", required = false, defaultValue = "") String search,
                                @RequestParam(value = "min", defaultValue = "1") String min,
                                @RequestParam(value = "max", defaultValue ="100000000") String max,
                                @RequestParam(value = "sort", required = false, defaultValue = SEARCH_ASC) String sort,
                                @RequestParam(value = "category", required = false, defaultValue = "") String category,
                                Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            Person person = (Person) auth.getPrincipal();
            Person guest = new Person();
            guest.setLogin("гость");
            if (person != null) {
                model.addAttribute("person", person);
            } else {
                model.addAttribute("person", guest);
            }
        }

        if (!min.isEmpty() & !max.isEmpty()) {//есть цены

            if (!sort.isEmpty()) {//есть сортировка
                if (sort.equals(SEARCH_ASC)) {//сортировка++

                    if (!category.isEmpty()) {//категория


                        if (category.equals(CATEGORY1)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(min), Float.parseFloat(max), 1));
                        } else if (category.equals(CATEGORY2)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(min), Float.parseFloat(max), 2));
                        } else if (category.equals(CATEGORY3)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(min), Float.parseFloat(max), 3));
                        } else if (category.equals(CATEGORY4)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(min), Float.parseFloat(max), 4));
                        } else if (category.equals(CATEGORY5)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(min), Float.parseFloat(max), 5));
                        } else if (category.equals(CATEGORY6)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(min), Float.parseFloat(max), 6));
                        }


                    } else {
                        model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(min), Float.parseFloat(max)));
                    }


                } else if (sort.equals(SEARCH_DES)) {//сортировка --


                    if (!category.isEmpty()) {
                        if (category.equals(CATEGORY1)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(min), Float.parseFloat(max), 1));
                        } else if (category.equals(CATEGORY2)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(min), Float.parseFloat(max), 2));
                        } else if (category.equals(CATEGORY3)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(min), Float.parseFloat(max), 3));
                        } else if (category.equals(CATEGORY4)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(min), Float.parseFloat(max), 4));
                        } else if (category.equals(CATEGORY5)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(min), Float.parseFloat(max), 5));
                        } else if (category.equals(CATEGORY6)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(min), Float.parseFloat(max), 6));
                        }


                    } else {//если нет категории, но с сортировкой и от и до цен
                        model.addAttribute(SEARCH_PRODUCT, productRepository
                                .findByTitleOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(min), Float.parseFloat(max)));
                    }


                }
            } else {//если от и до есть, но нет сортировки, категории нет
                model.addAttribute(SEARCH_PRODUCT, productRepository
                        .findByTitleAndPriceGreaterThanEqualAndPriceLessThanEqual(search.toLowerCase(), Float.parseFloat(min), Float.parseFloat(max)));
            }
        }
        else if (!sort.isEmpty()) {
            if (sort.equals(SEARCH_ASC)) {
                if (!category.isEmpty()) {
                    if (category.equals(CATEGORY1)) {
                        model.addAttribute(SEARCH_PRODUCT, productRepository
                                .findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), 1));
                    } else if (category.equals(CATEGORY2)) {
                        model.addAttribute(SEARCH_PRODUCT, productRepository
                                .findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), 2));
                    } else if (category.equals(CATEGORY3)) {
                        model.addAttribute(SEARCH_PRODUCT, productRepository
                                .findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), 3));
                    } else if (category.equals(CATEGORY4)) {
                        model.addAttribute(SEARCH_PRODUCT, productRepository
                                .findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), 4));
                    } else if (category.equals(CATEGORY5)) {
                        model.addAttribute(SEARCH_PRODUCT, productRepository
                                .findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), 5));
                    } else if (category.equals(CATEGORY6)) {
                        model.addAttribute(SEARCH_PRODUCT, productRepository
                                .findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), 6));
                    }
                } else {
                    model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleOrderByPriceAsc(search.toLowerCase()));
                }
            } else if (sort.equals(SEARCH_DES)) {
                if (!category.isEmpty()) {
                    if (category.equals(CATEGORY1)) {
                        model.addAttribute(SEARCH_PRODUCT, productRepository
                                .findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), 1));
                    } else if (category.equals(CATEGORY2)) {
                        model.addAttribute(SEARCH_PRODUCT, productRepository
                                .findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), 2));
                    } else if (category.equals(CATEGORY3)) {
                        model.addAttribute(SEARCH_PRODUCT, productRepository
                                .findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), 3));
                    } else if (category.equals(CATEGORY4)) {
                        model.addAttribute(SEARCH_PRODUCT, productRepository
                                .findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), 4));
                    } else if (category.equals(CATEGORY5)) {
                        model.addAttribute(SEARCH_PRODUCT, productRepository
                                .findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), 5));
                    } else if (category.equals(CATEGORY6)) {
                        model.addAttribute(SEARCH_PRODUCT, productRepository
                                .findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), 6));
                    }
                } else {
                    model.addAttribute(SEARCH_PRODUCT, productRepository
                            .findByTitleOrderByPriceDesc(search.toLowerCase()));
                }
            }
        } else if (!category.isEmpty()) {//только категории
            if (category.equals(CATEGORY1)) {
                model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategory(search.toLowerCase(), 1));
            } else if (category.equals(CATEGORY2)) {
                model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategory(search.toLowerCase(), 2));
            } else if (category.equals(CATEGORY3)) {
                model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategory(search.toLowerCase(), 3));
            } else if (category.equals(CATEGORY4)) {
                model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategory(search.toLowerCase(), 4));
            } else if (category.equals(CATEGORY5)) {
                model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategory(search.toLowerCase(), 5));
            } else if (category.equals(CATEGORY6)) {
                model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategory(search.toLowerCase(), 6));
            }
        }
        else {// всв товары
            model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleContainingIgnoreCase(search.toLowerCase()));
        }

        model.addAttribute(VALUE_SEARCH, search);
        model.addAttribute(PRISE_OT, min);
        model.addAttribute(PRISE_DO, max);
        model.addAttribute(SEARCH_ASC, sort);
        model.addAttribute(SEARCH_DES, sort);
        model.addAttribute(CATEGORY1, category);
        model.addAttribute(CATEGORY2, category);
        model.addAttribute(CATEGORY3, category);
        model.addAttribute(CATEGORY4, category);
        model.addAttribute(CATEGORY5, category);
        model.addAttribute(CATEGORY6, category);
        model.addAttribute(PRODUCTS, productService.getAllProduct());
        return "/product/product";
    }

    /**
     * Поиск в хедере
     *
     * @param search ключевое слово
     */
    @PostMapping("/searchHeader")
    public String productSearchHeader(@RequestParam("search") String search, Model model) {
        model.addAttribute("search_product_header", productRepository.findByTitleContainingIgnoreCase(search));
        model.addAttribute("value_search", search);
        model.addAttribute(PRODUCTS, productService.getAllProduct());
        return "/product/product";
    }
}


