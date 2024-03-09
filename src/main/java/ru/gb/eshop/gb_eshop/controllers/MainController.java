package ru.gb.eshop.gb_eshop.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.gb.eshop.gb_eshop.enums.Role;
import ru.gb.eshop.gb_eshop.enums.Status;
import ru.gb.eshop.gb_eshop.models.Cart;
import ru.gb.eshop.gb_eshop.models.Order;
import ru.gb.eshop.gb_eshop.models.Person;
import ru.gb.eshop.gb_eshop.models.Product;
import ru.gb.eshop.gb_eshop.repositories.CartRepository;
import ru.gb.eshop.gb_eshop.repositories.OrderRepository;
import ru.gb.eshop.gb_eshop.repositories.ProductRepository;
import ru.gb.eshop.gb_eshop.security.PersonDetails;
import ru.gb.eshop.gb_eshop.services.PersonService;
import ru.gb.eshop.gb_eshop.services.ProductService;
import ru.gb.eshop.gb_eshop.util.PersonValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Главный контроллер
 */
@Controller
public class MainController {

    private final ProductRepository productRepository;
    private final PersonValidator personValidator;
    private final PersonService personService;
    private final ProductService productService;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
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
    private final String VALUE_SEARCH = "value_search";
    private final String PRISE_OT = "value_price_ot";
    private final String PRISE_DO = "value_price_do";

    @Autowired
    public MainController(ProductRepository productRepository, PersonValidator personValidator, PersonService personService,
                          ProductService productService, CartRepository cartRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.personValidator = personValidator;
        this.personService = personService;
        this.productService = productService;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Метод возвращающий представление страницы пользователя
     *
     * @param model
     * @return представление страницы пользователя
     */
    @GetMapping("/userPage")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person person = personDetails.getPerson();
        model.addAttribute("person", person);
        Role role = person.getRole();
        List<Product> products = productService.getAllProduct();
        for (Product prod : products) {
            boolean q = prod.getImageList().get(0).getFileName().contains("demo");
        }
        if (role == Role.ROLE_ADMIN) {
            return "redirect:/admin";
        }
        model.addAttribute("products", productService.getAllProduct());
        return "/user/userPage";
    }

    /**
     * Метод возвращающий представление страницы регистрации нового пользователя в модели
     *
     * @param person новый пользователь
     * @return представление страницы регистрации
     */
    @GetMapping("/registration")
    public String registration(@ModelAttribute("person") Person person) {
        return "registration";
    }

    /**
     * Метод регистрации нового пользователя
     *
     * @param person        пользователь с формы
     * @param bindingResult ошибки валидации
     * @return перенаправление в ЛК
     */
    @PostMapping("/registration")
    public String resultRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        personService.register(person);
        return "redirect:/personalAccount";
    }

    /**
     * Метод возвращающий представление страницы товара
     *
     * @param id    id товара
     * @param model модель
     * @return представление страницы товара
     */
    @GetMapping("/personalAccount/product/info/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", productService.getProductId(id));
        return "/user/infoProduct";
    }

    /**
     * Метод поиска товаров по параметрам
     *
     * @param search   ключевое слово для поиска
     * @param ot       нижний передел цены
     * @param Do       верхний предел цены
     * @param price    цена
     * @param category категория товара
     * @param model    модель
     * @return представление страницы с найденными товарами
     */
    @PostMapping("/personalAccount/product/search")
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
                            model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2));
                        } else if (category.equals(CATEGORY3)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3));
                        } else if (category.equals(CATEGORY4)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 4));
                        } else if (category.equals(CATEGORY5)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 5));
                        } else if (category.equals(CATEGORY6)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategoryOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 6));
                        }
                    } else {
                        model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleOrderByPriceAsc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do)));
                    }
                } else if (price.equals(SEARCH_DES)) {
                    if (!category.isEmpty()) {
                        if (category.equals(CATEGORY1)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 1));
                        } else if (category.equals(CATEGORY2)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 2));
                        } else if (category.equals(CATEGORY3)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 3));
                        } else if (category.equals(CATEGORY4)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 4));
                        } else if (category.equals(CATEGORY5)) {
                            model.addAttribute(SEARCH_PRODUCT, productRepository.findByTitleAndCategoryOrderByPriceDesc(search.toLowerCase(), Float.parseFloat(ot), Float.parseFloat(Do), 5));
                        } else if (category.equals(CATEGORY6)) {
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

        model.addAttribute(VALUE_SEARCH, search);
        model.addAttribute(PRISE_OT, ot);
        model.addAttribute(PRISE_DO, Do);
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

    /**
     * Метод добавления товара в корзину
     *
     * @param id    id товара
     * @param model модель
     * @return перенаправление на корзину пользователя
     */
    @GetMapping("/cart/add/{id}")
    public String addProductInCart(@PathVariable("id") int id, Model model) {
        Product product = productService.getProductId(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        cartRepository.save(new Cart(personDetails.getPerson().getId(), product.getId()));
        return "redirect:/cart";
    }

    /**
     * Метод отображения корзины пользователя
     *
     * @param model модель
     * @return
     */
    @GetMapping("/cart")
    public String cart(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int id_person = personDetails.getPerson().getId();
        List<Cart> cartList = cartRepository.findByPersonId(id_person);
        List<Product> productList = new ArrayList<>();
        for (Cart cart : cartList)
            productList.add(productService.getProductId(cart.getProductId()));

        float price = 0;
        for (Product product : productList) {
            price += product.getPrice();
        }

        model.addAttribute("price", price);
        model.addAttribute("cart_product", productList);
        return "/user/cart";
    }

    /**
     * Метод удаления товара из корзины
     *
     * @param id id товара
     * @return
     */
    @GetMapping("/cart/delete/{id}")
    public String removeProductFromCart(@PathVariable("id") int id) {
        cartRepository.deleteCartByProductId(id);
        return "redirect:/cart";
    }

    /**
     * Оформление заказа
     * @return представление заказов
     */
    @GetMapping("/order/create")
    public String order() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        int id_person = personDetails.getPerson().getId();
        List<Cart> cartList = cartRepository.findByPersonId(id_person);
        List<Product> productList = new ArrayList<>();

        for (Cart cart : cartList) {
            productList.add(productService.getProductId(cart.getProductId()));
        }

/*        float price = 0;
        for (Product product : productList) {
            price += product.getPrice();
        }*/

        String uuid = UUID.randomUUID().toString();
        for (Product product : productList) {
            Order newOrder = new Order(uuid, product, personDetails.getPerson(), 1, product.getPrice(), Status.WAITING);
            orderRepository.save(newOrder);
            cartRepository.deleteCartByProductId(product.getId());
        }
        return "redirect:/orders";
    }

    /**
     * Метод получение списка заказов авторизованного пользователя
     *
     * @param model модель
     * @return представление заказов
     */

    @GetMapping("/orders")
    public String orderUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        List<Order> orderList = orderRepository.findByPerson(personDetails.getPerson());
        model.addAttribute("orders", orderList);
        return "/user/orders";
    }
}
