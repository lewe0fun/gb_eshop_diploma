package ru.gb.eshop.gb_eshop.controllers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.eshop.gb_eshop.models.Person;
import ru.gb.eshop.gb_eshop.repositories.ProductRepository;
import ru.gb.eshop.gb_eshop.services.ProductService;

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
     * Поле MAX_PRICE
     */
    private final String MAX_PRICE = "min_price";

    /**
     * Поле MIN_PRICE
     */
    private final String MIN_PRICE = "max_price";

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
        addNameAttribute(model);
        model.addAttribute(PRODUCTS, productService.getAllProduct());
        return "/product/product";
    }

    /**
     * Метод добавляет в модель имя автоизированного поьзователя
     *
     * @param model модель
     */
    private void addNameAttribute (Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null)
            model.addAttribute("name", ((Person) auth.getPrincipal()).getLogin());
        else
            model.addAttribute("name", "покупатель");
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
     * @param min      нижний передел цены
     * @param max      верхний предел цены
     * @param sort     цена
     * @param category категория товара
     * @param model    модель
     * @return представление страницы с найденными товарами
     */
    @PostMapping("/search")
    public String productSearch(@RequestParam(value = "search", required = false, defaultValue = "") String search,
                                @RequestParam(value = "min") String min,
                                @RequestParam(value = "max") String max,
                                @RequestParam(value = "sort", required = false, defaultValue = SEARCH_ASC) String sort,
                                @RequestParam(value = "category", required = false, defaultValue = "") String category,
                                Model model) {

        if (!min.isEmpty() & !max.isEmpty()) {//есть цены

            if (!sort.isEmpty()) {//есть цены -> есть сортировка

                if (sort.equals(SEARCH_ASC)) {//есть цены -> есть сортировка -> возрастание

                    if (!category.isEmpty()) {//есть цены -> есть сортировка -> возрастание-> категории

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

                    } else {//есть цены -> есть сортировка -> возрастание ->без категории
                        model.addAttribute(SEARCH_PRODUCT, productRepository
                                .findByTitleOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(min), Float.parseFloat(max)));
                    }

                } else if (sort.equals(SEARCH_DES)) {//есть цены -> есть сортировка -> убывание

                    if (!category.isEmpty()) {//есть цены -> есть сортировка -> убывание -> категории

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

                    } else {//есть цены -> есть сортировка -> убывание ->без категории
                        model.addAttribute(SEARCH_PRODUCT, productRepository
                                .findByTitleOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(min), Float.parseFloat(max)));
                    }

                }
            } else {//есть цены -> нет сортировки-> категории нет
                model.addAttribute(SEARCH_PRODUCT, productRepository
                        .findByTitleAndPriceGreaterThanEqual(search.toLowerCase(), Float.parseFloat(min), Float.parseFloat(max)));
            }
        } else if (!min.isEmpty() & max.isEmpty()) {//есть мин цена

            if (!sort.isEmpty()) {//есть мин цена -> есть сортировка

                if (sort.equals(SEARCH_ASC)) {//есть мин цена -> есть сортировка -> возрастание

                    if (!category.isEmpty()) {//есть мин цена  -> есть сортировка -> возрастание-> категории

                        if (category.equals(CATEGORY1)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(min), 1));
                        } else if (category.equals(CATEGORY2)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(min), 2));
                        } else if (category.equals(CATEGORY3)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(min), 3));
                        } else if (category.equals(CATEGORY4)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(min), 4));
                        } else if (category.equals(CATEGORY5)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(min), 5));
                        } else if (category.equals(CATEGORY6)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(min), 6));
                        }

                    } else {//есть мин цена -> есть сортировка -> возрастание ->без категории
                        model.addAttribute(SEARCH_PRODUCT, productRepository
                                .findByTitleOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(min)));
                    }

                } else if (sort.equals(SEARCH_DES)) {//есть мин цена -> есть сортировка -> убывание

                    if (!category.isEmpty()) {//есть мин цена -> есть сортировка -> убывание -> категории

                        if (category.equals(CATEGORY1)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(min), 1));
                        } else if (category.equals(CATEGORY2)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(min), 2));
                        } else if (category.equals(CATEGORY3)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(min), 3));
                        } else if (category.equals(CATEGORY4)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(min), 4));
                        } else if (category.equals(CATEGORY5)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(min), 5));
                        } else if (category.equals(CATEGORY6)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(min), 6));
                        }

                    } else {//есть мин цена  -> есть сортировка -> убывание ->без категории
                        model.addAttribute(SEARCH_PRODUCT, productRepository
                                .findByTitleOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(min)));
                    }

                }
            } else {//есть мин цена -> нет сортировки-> категории нет
                model.addAttribute(SEARCH_PRODUCT, productRepository
                        .findByTitleAndPriceGreaterThanEqual(search.toLowerCase(), Float.parseFloat(min)));
            }
        } else if (!max.isEmpty()) {//есть мах цена

            if (!sort.isEmpty()) {//есть мах цена -> есть сортировка

                if (sort.equals(SEARCH_ASC)) {//есть мах цена -> есть сортировка -> возрастание

                    if (!category.isEmpty()) {//есть мах цена -> есть сортировка -> возрастание-> категории

                        if (category.equals(CATEGORY1)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryAndMaxPriceOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(max), 1));
                        } else if (category.equals(CATEGORY2)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryAndMaxPriceOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(max), 2));
                        } else if (category.equals(CATEGORY3)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryAndMaxPriceOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(max), 3));
                        } else if (category.equals(CATEGORY4)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryAndMaxPriceOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(max), 4));
                        } else if (category.equals(CATEGORY5)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryAndMaxPriceOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(max), 5));
                        } else if (category.equals(CATEGORY6)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryAndMaxPriceOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(max), 6));
                        }

                    } else {//есть мах цена -> есть сортировка -> возрастание ->без категории
                        model.addAttribute(SEARCH_PRODUCT, productRepository
                                .findByTitleAndMaxPriceOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(max)));
                    }

                } else if (sort.equals(SEARCH_DES)) {//есть мах цена -> есть сортировка -> убывание

                    if (!category.isEmpty()) {//есть мах цена -> есть сортировка -> убывание -> категории

                        if (category.equals(CATEGORY1)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryAndMaxPriceOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(max), 1));
                        } else if (category.equals(CATEGORY2)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryAndMaxPriceOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(max), 2));
                        } else if (category.equals(CATEGORY3)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryAndMaxPriceOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(max), 3));
                        } else if (category.equals(CATEGORY4)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryAndMaxPriceOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(max), 4));
                        } else if (category.equals(CATEGORY5)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryAndMaxPriceOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(max), 5));
                        } else if (category.equals(CATEGORY6)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository
                                    .findByTitleAndCategoryAndMaxPriceOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(max), 6));
                        }

                    } else {//есть мах цена -> есть сортировка -> убывание ->без категории
                        model.addAttribute(SEARCH_PRODUCT, productRepository
                                .findByTitleAndMaxPriceOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(max)));
                    }

                }
            } else {//есть мах цена -> нет сортировки-> категории нет
                model.addAttribute(SEARCH_PRODUCT, productRepository
                        .findByTitleAndPriceLesserThanEqual(search.toLowerCase(), Float.parseFloat(max)));
            }
        } else if (!sort.isEmpty()) {//нет цен -> есть сортировка

            if (sort.equals(SEARCH_ASC)) {//нет цен -> есть сортировка -> возрастание

                if (!category.isEmpty()) {//нет цен -> есть сортировка -> возрастание -> категории

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

                } else {//нет цен -> есть сортировка -> возрастание ->без категории
                    model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleOrderByPriceAsc(search.toLowerCase()));
                }

            } else if (sort.equals(SEARCH_DES)) {//нет цен -> есть сортировка -> убывание

                if (!category.isEmpty()) {//есть цены -> есть сортировка -> убывание -> категории

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

                } else {//нет цен -> есть сортировка -> убывание ->без категории
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

        } else {// все товары
            model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleContainingIgnoreCase(search.toLowerCase()));
        }


        addNameAttribute(model);
        model.addAttribute(VALUE_SEARCH, search);
        model.addAttribute(MAX_PRICE, min);
        model.addAttribute(MIN_PRICE, max);
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
     *
     * @return представление страницы с найденными товарами
     */
    @PostMapping("/searchHeader")
    public String productSearchHeader(@RequestParam("search") String search, Model model) {
        addNameAttribute(model);
        model.addAttribute("search_product_header", productRepository.findByTitleContainingIgnoreCase(search));
        model.addAttribute("value_search", search);
        model.addAttribute(PRODUCTS, productService.getAllProduct());
        return "/product/product";
    }
}


