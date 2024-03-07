package ru.gb.eshop.gb_eshop.controllers;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gb.eshop.gb_eshop.enums.Role;
import ru.gb.eshop.gb_eshop.enums.Status;
import ru.gb.eshop.gb_eshop.models.*;
import ru.gb.eshop.gb_eshop.repositories.CategoryRepository;
import ru.gb.eshop.gb_eshop.repositories.ImageRepository;
import ru.gb.eshop.gb_eshop.repositories.OrderRepository;
import ru.gb.eshop.gb_eshop.security.PersonDetails;
import ru.gb.eshop.gb_eshop.services.OrderService;
import ru.gb.eshop.gb_eshop.services.PersonService;
import ru.gb.eshop.gb_eshop.services.ProductService;
import ru.gb.eshop.gb_eshop.util.ProductValidator;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Контроллер администратора
 */
@Controller
@RequestMapping("/admin")

public class AdminController {

    private final ProductValidator productValidator;
    private final ProductService productService;
    private final PersonService personService;
    private final OrderService orderService;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;
    private final ImageRepository imageRepository;
    @Value("${upload.path}")
    private String uploadPath;


    @Autowired
    public AdminController(ProductValidator productValidator, ProductService productService, PersonService personService, OrderService orderService,
                           CategoryRepository categoryRepository, OrderRepository orderRepository, ImageRepository imageRepository) {
        this.productValidator = productValidator;
        this.productService = productService;
        this.personService = personService;
        this.orderService = orderService;
        this.categoryRepository = categoryRepository;
        this.orderRepository = orderRepository;
        this.imageRepository = imageRepository;
    }

    /**
     * Страницы админа
     */
    @GetMapping()
    public String admin(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Role role = personDetails.getPerson().getRole();

        if(role==Role.ROLE_USER)
            return "redirect:/index";

        model.addAttribute("products", productService.getAllProduct());
        model.addAttribute("persons", personService.getAllPersons());
        model.addAttribute("orders", orderService.getAllOrders());
        return "/admin";
    }

    /**
     * Получение модели добавления товара
     */
    @GetMapping("/product/add")
    public String addProduct(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryRepository.findAll());
        return "product/addProduct";
    }

    /**
     * Заполнение модели товара
     * @param product добавляемый товар
     * @param bindingResult ошибки валидации
     * @param file_one файл картинки товара
     * @param file_two файл картинки товара
     * @param file_three файл картинки товара
     * @param file_four файл картинки товара
     * @param file_five файл картинки товара
     * @param category категория товара
     * @param model модель
     * @throws IOException
     */

    @PostMapping("/product/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product,
                             BindingResult bindingResult,
                             @RequestParam("file_one") MultipartFile file_one,
                             @RequestParam("file_two") MultipartFile file_two,
                             @RequestParam("file_three") MultipartFile file_three,
                             @RequestParam("file_four") MultipartFile file_four,
                             @RequestParam("file_five") MultipartFile file_five,
                             @RequestParam ("category") int category,
                             Model model) throws IOException {
        Category category_db = categoryRepository.findById(category)
                .orElseThrow(()-> new EntityNotFoundException("category not found"));
        productValidator.validate(product, bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("category", categoryRepository.findAll());
            return "/product/addProduct";
        }
        if(file_one != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_one.getOriginalFilename();
            file_one.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.updateImageProduct(image);
        }

        if(file_two != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists())
                uploadDir.mkdir();
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_two.getOriginalFilename();
            file_two.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.updateImageProduct(image);
        }

        if(file_three != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists())
                uploadDir.mkdir();
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_three.getOriginalFilename();
            file_three.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.updateImageProduct(image);
        }

        if(file_four != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists())
                uploadDir.mkdir();
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_four.getOriginalFilename();
            file_four.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.updateImageProduct(image);
        }

        if(file_five != null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists())
                uploadDir.mkdir();
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_five.getOriginalFilename();
            file_five.transferTo(new File(uploadPath + "/" + resultFileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(resultFileName);
            product.updateImageProduct(image);
        }

        productService.saveProduct(product, category_db);
        return "redirect:/admin";
    }

    // Метод по удалению товара по id
    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        productService.deleteProduct(id);
        return "redirect:/admin";
    }

    @GetMapping("/product/edit/{id}")
    public String editProduct(Model model, @PathVariable("id") int id){
        model.addAttribute("product", productService.getProductId(id));
        model.addAttribute("category", categoryRepository.findAll());
        return "product/editProduct";


    }

    @PostMapping("/product/edit/{id}")
    public String editProduct(@ModelAttribute("product") @Valid Product product,
                              BindingResult bindingResult,
                              @PathVariable("id") int id, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("category", categoryRepository.findAll());
            return "product/editProduct";
        }
        productService.updateProduct(id, product);
        return "redirect:/admin";
    }



    // Методы для работы с пользователями для администратора



    // Метод возвращает страницу с выводом пользователей и кладет объект пользователя в модель
    @GetMapping("/person")
    public String person(Model model){;
        model.addAttribute("person", personService.getAllPerson());
        return "person/person";
    }

    // Метод возвращает страницу с подробной информацией о пользователе
    @GetMapping("/person/info/{id}")
    public String infoPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personService.getPersonById(id));
        return "person/personInfo";
    }

    // Метод возвращает страницу с формой редактирования пользователя и помещает в модель объект редактируемого пользователя по id
    @GetMapping("/person/edit/{id}")
    public String editPerson(@PathVariable("id")int id, Model model){
        model.addAttribute("editPerson", personService.getPersonById(id));
        return "person/editPerson";
    }

    // Метод принимает объект с формы и обновляет пользователя
    @PostMapping("/person/edit/{id}")
    public String editPerson(@ModelAttribute("editPerson") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "person/editPerson";
        }

        personService.updatePerson(id, person);
        return "redirect:/admin/person";
    }

    @GetMapping("/person/delete/{id}")
    public String deletePerson(@PathVariable("id") int id){
        personService.deletePerson(id);
        return "redirect:/admin/person";
    }



    // Методы для работы с заказами для администратора



    // Метод возвращает страницу с выводом заказов кладет объект заказов в модель
    @GetMapping("/orders")
    public String order(Model model){;
        model.addAttribute("orders", orderService.getAllOrders());
        return "admin/orders";
    }

    //   Метод возвращает страницу с формой редактирования заказ и помещает в модель объект редактируемого заказа по id
    @GetMapping("/orders/{id}")
    public String editOrder(@PathVariable("id")int id, Model model){
        model.addAttribute("info_order", orderService.getOrderById(id));
        return "/admin/infoOrder";
    }

    // Метод принимает объект с формы и обновляет заказы
    @PostMapping("/orders/{id}")
    public String changeStatus(@PathVariable("id") int id, @RequestParam("status") Status status){
        Order order_status = orderService.getOrderById(id);
        order_status.setStatus(status);
        orderService.updateOrderStatus(order_status);
        return "redirect:/admin/orders/{id}";
    }

    // Поиск по последним сомволам номера заказа
    @PostMapping("/orders/search")
    public String searchOrderByLastSymbols(@RequestParam("value") String value, Model model) {
        model.addAttribute("search_order",orderRepository.findByNumberEndingWith(value));
        return "/admin/ordersSearch";
    }


    @GetMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable("id") int id){
        orderService.deleteOrder(id);
        return "admin/ordersSearch";
    }
}
