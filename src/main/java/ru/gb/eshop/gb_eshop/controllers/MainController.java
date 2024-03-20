package ru.gb.eshop.gb_eshop.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.gb.eshop.gb_eshop.enums.Role;
import ru.gb.eshop.gb_eshop.enums.Status;
import ru.gb.eshop.gb_eshop.models.Cart;
import ru.gb.eshop.gb_eshop.models.Order;
import ru.gb.eshop.gb_eshop.models.Person;
import ru.gb.eshop.gb_eshop.models.Product;
import ru.gb.eshop.gb_eshop.services.CartService;
import ru.gb.eshop.gb_eshop.services.OrderService;
import ru.gb.eshop.gb_eshop.services.PersonService;
import ru.gb.eshop.gb_eshop.services.ProductService;
import ru.gb.eshop.gb_eshop.utils.PersonValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Главный контроллер
 *
 * @author Пакулин Ю.А., Строев Д.В., Брылин М.В.
 * @version 1.0
 */
@Controller
public class MainController {

    /**
     * Поле personValidator
     */
    private final PersonValidator personValidator;

    /**
     * Поле personService
     */
    private final PersonService personService;

    /**
     * Поле productService
     */
    private final ProductService productService;

    /**
     * Поле cartService
     */
    private final CartService cartService;

    /**
     * Поле orderService
     */
    private final OrderService orderService;

    @Autowired
    public MainController(PersonValidator personValidator, PersonService personService, ProductService productService,
                          CartService cartService, OrderService orderService) {
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
        Person person = getAuthPerson();
        Role role = person.getRole();
        if (role == Role.ROLE_ADMIN) {
            return "redirect:/admin";
        }
        model.addAttribute("person", person);
        model.addAttribute("products", productService.getAllProduct());
        model.addAttribute("name", person.getLogin());
        model.addAttribute("orders", orderService.findByPerson(person));
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
        Person person = getAuthPerson();
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
        Person person = getAuthPerson();
        List<Cart> cartList = cartService.findByPersonId(person.getId());
        List<Product> productList = new ArrayList<>();
        for (Cart cart : cartList)
            productList.add(productService.getProductId(cart.getProductId()));
        float price = 0;
        for (Product product : productList) {
            price += product.getPrice();
        }
        model.addAttribute("person", person);
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
        Person person = getAuthPerson();
        List<Cart> cartList = cartService.findByPersonId(person.getId());
        List<Product> productList = new ArrayList<>();

        for (Cart cart : cartList) {
            productList.add(productService.getProductId(cart.getProductId()));
        }

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
        Person person = getAuthPerson();
        List<Order> orderList = orderService.findByPerson(person);
        model.addAttribute("person", person);
        model.addAttribute("orders", orderList);
        return "/user/orders";
    }

    /**
     * Метод обновления пароля (получаем форму для заполнения)
     *
     * @param model модель
     * @return форма заполнения нового пароля
     */
    @GetMapping("/person/updatePassword")
    public String passForm(Model model) {
        model.addAttribute("person", getAuthPerson());
        return "/person/updatePassword";
    }

    /**
     * Метод возвращает аунтифицированного пользователя
     *
     * @return аунтифицированный пользователь
     */
    private Person getAuthPerson() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Person) authentication.getPrincipal();
    }

    /**
     * Метод обновления пароля (заполняем форму для заполнения)
     *
     * @param id            id пользователя
     * @param person        модель пользователя
     * @param bindingResult ошибки
     * @return либо страница логина, либо форма заполнения пароля
     */
    @PostMapping("/person/updatePassword/{id}")
    public String passUp(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/person/updatePassword";
        }
        personService.changePassword(id, person.getPassword());
        return "redirect:/login";
    }

    /**
     * Метод перехода на страницу контакты
     *
     * @return страницу contacts.html
     */
    @GetMapping("/contacts")
    public String contactMarket() {
        return "contacts";
    }

    /**
     * Метод перехода на страницу о компании
     *
     * @return страницу aboutCompany.html
     */
    @GetMapping("/company")
    public String aboutCompanyMarket() {
        return "aboutCompany";
    }

    /**
     * Метод перехода на страницу оптовикам
     *
     * @return страницу wholesalers.html
     */
    @GetMapping("/wholesalers")
    public String wholesalersMarket() {
        return "wholesalers";
    }

}
