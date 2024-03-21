package ru.gb.eshop.gb_eshop.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gb.eshop.gb_eshop.enums.Role;
import ru.gb.eshop.gb_eshop.enums.Status;
import ru.gb.eshop.gb_eshop.models.Order;
import ru.gb.eshop.gb_eshop.models.Person;
import ru.gb.eshop.gb_eshop.models.Product;
import ru.gb.eshop.gb_eshop.repositories.CategoryRepository;
import ru.gb.eshop.gb_eshop.repositories.OrderRepository;
import ru.gb.eshop.gb_eshop.services.OrderService;
import ru.gb.eshop.gb_eshop.services.PersonService;
import ru.gb.eshop.gb_eshop.services.ProductService;
import ru.gb.eshop.gb_eshop.utils.ImageUploader;
import ru.gb.eshop.gb_eshop.utils.ProductValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Контроллер администратора
 *
 * @author Пакулин Ю.А., Строев Д.В., Брылин М.В.
 * @version 1.0
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    /**
     * Поле productValidator
     */
    private final ProductValidator productValidator;

    /**
     * Поле productService
     */
    private final ProductService productService;

    /**
     * Поле personService
     */
    private final PersonService personService;

    /**
     * Поле orderService
     */
    private final OrderService orderService;

    /**
     * Поле categoryRepository
     */
    private final CategoryRepository categoryRepository;

    /**
     * Поле orderRepository
     */
    private final OrderRepository orderRepository;

    /**
     * Поле imageUploader
     */
    private  final ImageUploader imageUploader;

    @Autowired
    public AdminController(ProductValidator productValidator, ProductService productService, PersonService personService, OrderService orderService,
                           CategoryRepository categoryRepository, OrderRepository orderRepository, ImageUploader imageUploader) {
        this.productValidator = productValidator;
        this.productService = productService;
        this.personService = personService;
        this.orderService = orderService;
        this.categoryRepository = categoryRepository;
        this.orderRepository = orderRepository;
        this.imageUploader = imageUploader;
    }

    /**
     * Метод перехода на страницу админа
     *
     * @return страницу admin.html
     */
    @GetMapping()
    public String admin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = (Person) authentication.getPrincipal();
        model.addAttribute("person", person);
        Role role = person.getRole();
        if (role == Role.ROLE_USER)
            return "redirect:/userPage";
        model.addAttribute("products", productService.getAllProduct());
        model.addAttribute("persons", personService.getAllPersons());
        model.addAttribute("orders", orderService.getAllOrders());
        return "/admin";
    }

    /**
     * Метод получения модели добавления товара
     *
     * @return страницу addProduct.html
     */
    @GetMapping("/product/add")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryRepository.findAll());
        return "product/addProduct";
    }

    /**
     * Заполнение модели товара
     *
     * @param product       добавляемый товар
     * @param bindingResult ошибки валидации
     * @param image1        файл картинки товара
     * @param image2        файл картинки товара
     * @param image3        файл картинки товара
     * @param image4        файл картинки товара
     * @param image5        файл картинки товара
     * @param model         модель
     * @throws IOException если возникнут ошибки
     * @return страницу admin.html
     */
    @PostMapping("/product/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product,
                             BindingResult bindingResult,
                             @RequestParam("image1") MultipartFile image1,
                             @RequestParam("image2") MultipartFile image2,
                             @RequestParam("image3") MultipartFile image3,
                             @RequestParam("image4") MultipartFile image4,
                             @RequestParam("image5") MultipartFile image5,
                             Model model) throws IOException {
        productValidator.validate(product, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", categoryRepository.findAll());
            return "/product/addProduct";
        }
        List<MultipartFile> images = new ArrayList<>(Arrays.asList(image1,image2,image3,image4,image5));
        for (MultipartFile image: images)
            imageUploader.setImage(image,product);
        productService.saveProduct(product);
        return "redirect:/admin";
    }

    /**
     * Метод удалению товара по id
     *
     * @param id id товара
     * @return редирект на ендпоинт админа
     */
    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        if(orderRepository.findByProduct_id(id)!=null){
            return "redirect:/admin";
        }
        productService.deleteProduct(id);
        return "redirect:/admin";
    }

    /**
     * Метод получения модели редактирования товара
     *
     * @param model модель
     * @param id    id товара
     * @return представление редактирования товара
     */
    @GetMapping("/product/edit/{id}")
    public String editProduct(Model model, @PathVariable("id") int id) {
        model.addAttribute("product", productService.getProductId(id));
        model.addAttribute("category", categoryRepository.findAll());
        return "product/editProduct";
    }

    /**
     * Метод заполнения модели товара
     *
     * @param product       переданный товар
     * @param bindingResult ошибки валидации
     * @param id            id товара
     * @param model         модель
     * @return редирект на страницу админа
     */
    @PostMapping("/product/edit/{id}")
    public String editProduct(@ModelAttribute("product") @Valid Product product,
                              BindingResult bindingResult,
                              @PathVariable("id") int id, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("category", categoryRepository.findAll());
            return "product/editProduct";
        }
        productService.updateProduct(id, product);
        return "redirect:/admin";
    }

    /**
     * Метод показывающий список пользователей
     *
     * @param model модель
     * @return представление списка пользователей
     */
    @GetMapping("/person")
    public String person(Model model) {
        model.addAttribute("person", personService.getAllPersons());
        return "person/person";
    }

    /**
     * Метод показывающий данные пользователя
     *
     * @param id    id пользователя
     * @param model модель
     * @return представление страницы информации о пользователе
     */
    @GetMapping("/person/info/{id}")
    public String infoPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personService.getPersonById(id));
        return "person/personInfo";
    }

    /**
     * Метод возвращает страницу с формой редактирования пользователя и помещает
     * в модель объект редактируемого пользователя по id
     *
     * @param id    id пользователя
     * @param model модель
     * @return страница редактирования пользователя
     */
    @GetMapping("/person/edit/{id}")
    public String editPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("editPerson", personService.getPersonById(id));
        return "person/editPerson";
    }

    /**
     * Метод принимает объект с формы и обновляет пользователя
     *
     * @param person        пользователь
     * @param bindingResult ошибки валидации
     * @param id            id пользователя
     * @return представление Список пользователей или Редактирование при ошибке
     */
    @PostMapping("/person/edit/{id}")
    public String editPerson(@ModelAttribute("editPerson") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "person/editPerson";
        }
        personService.updatePersons(id, person);
        return "redirect:/admin/person";
    }

    /**
     * Метод возвращает страницу с формой редактирования профиля пользователя самим пользователем
     * и помещает в модель объект редактируемого пользователя по id
     *
     * @param id    id пользователя
     * @param model модель
     * @return страницу editPersonUser.html
     */
    @GetMapping("/personUser/edit/{id}")
    public String editPersonUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("editPerson", personService.getPersonById(id));
        return "person/editPersonUser";
    }

    /**
     * Метод принимает объект с формы и обновляет пользователя
     *
     * @param person        пользователь
     * @param bindingResult ошибки валидации
     * @param id            id пользователя
     * @return представление Список пользователей или Редактирование при ошибке
     */
    @PostMapping("/personUser/edit/{id}")
    public String editPersonUser(@ModelAttribute("editPerson") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "person/editPersonUser";
        }
        personService.updatePersons(id, person);
        return "redirect:/userPage";

    }

    /**
     * Метод удаления пользователя по id
     *
     * @param id id пользователя
     * @return перенаправление на список пользователей
     */
    @GetMapping("/person/delete/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personService.deletePerson(id);
        return "redirect:/admin/person";
    }

    /**
     * Метод возвращает страницу с выводом заказов кладет объект заказов в модель
     *
     * @param model модель
     * @return представление Заказы
     */
    @GetMapping("/orders")
    public String order(Model model) {
        ;
        model.addAttribute("orders", orderService.getAllOrders());
        return "admin/orders";
    }

    /**
     * Метод возвращает страницу с формой редактирования заказ и помещает в модель объект редактируемого заказа по id
     *
     * @param id    id заказа
     * @param model модель
     * @return представление информации о заказе
     */
    @GetMapping("/orders/{id}")
    public String editOrder(@PathVariable("id") int id, Model model) {
        model.addAttribute("info_order", orderService.getOrderById(id));
        return "/admin/infoOrder";
    }

    /**
     * Метод обновления заказа с формы
     *
     * @param id     id заказа
     * @param status статус заказа
     * @return перенаправление на заказ
     */
    @PostMapping("/orders/{id}")
    public String changeStatus(@PathVariable("id") int id, @RequestParam("status") Status status) {
        Order order = orderService.getOrderById(id);
        order.setStatus(status);
        orderService.save(order);
        return "redirect:/admin/orders/{id}";
    }

    /**
     * Метод поиска заказа по номеру
     *
     * @param value номер заказа
     * @param model модель
     * @return представление Результат поиска по номеру заказа
     */
    @PostMapping("/orders/search")
    public String searchOrderByLastSymbols(@RequestParam("value") String value, Model model) {
        model.addAttribute("search_order", orderRepository.findByNumberEndingWith(value));
        return "/admin/ordersSearch";
    }

    /**
     * Метод удаления заказа по id
     *
     * @param id id заказа
     * @return представление Результат поиска по номеру заказа
     */
    @GetMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable("id") int id) {
        orderService.deleteOrder(id);
        return "admin/ordersSearch";
    }
}
