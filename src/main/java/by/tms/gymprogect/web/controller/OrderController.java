package by.tms.gymprogect.web.controller;

import by.tms.gymprogect.database.domain.Order.Order;
import by.tms.gymprogect.database.domain.Order.PriceCalculator;
import by.tms.gymprogect.database.domain.Order.SubscriptionHelper;
import by.tms.gymprogect.database.domain.User.User;
import by.tms.gymprogect.database.dto.ModelMapper;
import by.tms.gymprogect.database.dto.OrderDTO;

import by.tms.gymprogect.database.dto.SubscriptionDTO;
import by.tms.gymprogect.database.dto.UserDTO;

import by.tms.gymprogect.database.service.OrderService;
import by.tms.gymprogect.database.service.SubscriptionService;
import by.tms.gymprogect.database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderController {

    @Autowired
    UserService userService;
    @Autowired
    SubscriptionService subscriptionService;
    @Autowired
    OrderService orderService;

    /**
     * Get page user/orderBySeason and add orderDTO in model
     */
    @GetMapping("/user/orderBySeason")
    public String orderBySeasonPage(OrderDTO orderDTO, Model model){
        model.addAttribute("order", orderDTO);
        return "user/orderBySeason";
    }

    /**
     * Get page user/orderByCountTrainings and add orderDTO in model
     */
    @GetMapping("/user/orderByCountTrainings")
    public String orderByCountPage(OrderDTO orderDTO, Model model){
        model.addAttribute("order", orderDTO);
        return "user/orderByCountTrainings";
    }

    /**
     * Get page user/price
     */
    @GetMapping("/user/price")
    public String pricePage(){
        return "user/price";
    }

    /**
     * Get page user/orderSuccess
     */
    @GetMapping("/user/orderSuccess")
    public String orderSuccess(){
        return "user/orderSuccess";
    }

    /**
     * Accept order, create subscription, save their to database, and redirect to user/orderSuccess
     */
    @PostMapping("/user/orderSuccess")
    public String orderSuccessPage(OrderDTO orderDTO, @AuthenticationPrincipal UserDTO userDTO, SubscriptionDTO subscriptionDTO){
        User user = ModelMapper.map(userDTO, User.class);
        Order order = ModelMapper.map(orderDTO, Order.class);
        if(orderDTO.getCountTrain() > 0){
            orderDTO.setPrice(PriceCalculator.mathPriceByCountTrain(orderDTO.getCountTrain(), user));
            subscriptionDTO.setCountRemainingTrain(SubscriptionHelper.mathCountRemainingTrain(orderDTO.getCountTrain(), user));
        } else {
            orderDTO.setPrice(PriceCalculator.mathPriceBySeason(orderDTO.getSeason(), user));
            subscriptionDTO.setTimeOfAction(SubscriptionHelper.mathTimeOfAction(order, user));
        }
        subscriptionDTO.setUserDTO(userDTO);
        subscriptionDTO.setId(subscriptionService.save(subscriptionDTO));
        orderDTO.setUserDTO(userDTO);
        orderDTO.setSubscriptionDTO(subscriptionDTO);
        orderService.save(orderDTO);
        return "redirect: /user/orderSuccess";
    }

    /**
     * Get page admin/price
     */
    @GetMapping("/admin/price")
    public String adminPricePage(){
        return "admin/price";
    }
}
