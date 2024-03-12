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
import ru.gb.eshop.gb_eshop.repositories.ProductRepository;
import ru.gb.eshop.gb_eshop.services.CartService;
import ru.gb.eshop.gb_eshop.services.OrderService;
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
    private final CartService cartService;
    private final OrderService orderService;
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
    private final String PRODUCTS = "products";

    @Autowired
    public MainController(ProductRepository productRepository, PersonValidator personValidator, PersonService personService,
                          ProductService productService, CartService cartService, OrderService orderService) {
        this.productRepository = productRepository;
        this.personValidator = personValidator;
        this.personService = personService;
        this.productService = productService;
        this.cartService = cartService;
        this.orderService = orderService;
    }

    /**
     * Метод возвращающий представление страницы пользователя
     *
     * @param model модель
     * @return представление страницы пользователя
     */
    @GetMapping("/userPage")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = (Person) authentication.getPrincipal();
        model.addAttribute("person", person);
        Role role = person.getRole();
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
        return "redirect:/userPage";
    }

    /**
     * Метод возвращающий представление страницы товара
     *
     * @param id    id товара
     * @param model модель
     * @return представление страницы товара
     */
    @GetMapping("/userPage/product/info/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", productService.getProductId(id));
        return "/user/infoProduct";
    }

    /**
     * Метод добавления товара в корзину
     *
     * @param id id товара
     * @return перенаправление на корзину пользователя
     */
    @GetMapping("/cart/add/{id}")
    public String addProductInCart(@PathVariable("id") int id) {
        Product product = productService.getProductId(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = (Person) authentication.getPrincipal();
        cartService.save(new Cart(person.getId(), product.getId()));
        return "redirect:/cart";
    }

    /**
     * Метод отображения корзины пользователя
     *
     * @param model модель
     * @return корзина
     */
    @GetMapping("/cart")
    public String cart(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = (Person) authentication.getPrincipal();
        int id_person = person.getId();
        List<Cart> cartList = cartService.findByPersonId(id_person);
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
     * @return перенаправление в корину
     */
    @GetMapping("/cart/delete/{id}")
    public String removeProductFromCart(@PathVariable("id") int id) {
        cartService.deleteCartByProductId(id);
        return "redirect:/cart";
    }

    /**
     * Оформление заказа
     *
     * @return представление заказов
     */
    @GetMapping("/order/create")
    public String order() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = (Person) authentication.getPrincipal();
        int id_person = person.getId();
        List<Cart> cartList = cartService.findByPersonId(id_person);
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
            Order newOrder = new Order(uuid, product, person, 1, product.getPrice(), Status.ОЖИДАНИЕ);
            orderService.save(newOrder);
            cartService.deleteCartByProductId(product.getId());
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
        Person person = (Person) authentication.getPrincipal();
        List<Order> orderList = orderService.findByPerson(person);
        model.addAttribute("orders", orderList);
        return "/user/orders";
    }

    @GetMapping("/contacts")
    public String contactMarket() {
        return "contacts";
    }

    @GetMapping("/company")
    public String aboutCompanyMarket() {
        return "aboutCompany";
    }

    @GetMapping("/wholesalers")
    public String wholesalersMarket() {
        return "wholesalers";
    }

}
