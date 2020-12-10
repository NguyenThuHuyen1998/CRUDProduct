package com.example.crud.controller;

import com.example.crud.constants.InputParam;
import com.example.crud.entity.*;
import com.example.crud.form.OrderForm;
import com.example.crud.form.OrderLineForm;
import com.example.crud.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/*
    created by HuyenNgTn on 15/11/2020
*/
@RestController
public class OrderController {
    public static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private OrderService orderService;
    private CartService cartService;
    private CartItemService cartItemService;
    private UserService userService;
    private OrderLineService orderLineService;

    @Autowired
    public OrderController(OrderService orderService, CartService cartService, CartItemService cartItemService, UserService userService, OrderLineService orderLineService) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.userService = userService;
        this.orderLineService = orderLineService;
    }


    //tạo mới đơn hàng
    @CrossOrigin
    @PostMapping(value = "/userPage/orders")
    public ResponseEntity<Order> createOrder(@RequestParam(name = "user-id", required = false) long userId) {
        Order order = null;
        try {
            List<CartItem> cartItemList = cartItemService.getListCartItemInCart(userId);
            User user = userService.findById(userId);
            Cart cart = cartService.getCartByUserId(userId);
            if (cartItemList != null && cartItemList.size() > 0) {
                order = new Order(user, cart.getTotalMoney(), InputParam.PROCESSING, new Date().getTime());
                orderService.save(order);
                List<OrderLine> orderLines = new ArrayList<>();
                for (CartItem cartItem : cartItemList) {
                    OrderLine orderLine = new OrderLine(cartItem, order);
                    orderLineService.save(orderLine);
                    cartItemService.deleteCartItem(cartItem);
                }
            }
            if (order == null) {
                return new ResponseEntity("Add product int cart!!", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //lấy danh sách đơn hàng của 1 user
    //user chỉ được lấy ds đơn hàng của mình nên bắt buộc có user-id
    @GetMapping(value = "/userPage/orders")
    public ResponseEntity<OrderForm> getlistOrder(@RequestParam(name = "user-id", required = false, defaultValue = "0") long userId,
                                                  @RequestParam(name = "status", required = false, defaultValue = InputParam.PROCESSING) String status,
                                                  @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                                  @RequestParam(name = "page", required = false, defaultValue = "1") int page) {
        List<Order> orderFilter = orderService.getListOrderByStatus(status);
        List<OrderForm> orderForms = new ArrayList<>();
        if (orderFilter != null && orderFilter.size() > 0) {
            for (Order order : orderFilter) {
                List<OrderLine> orderLines = orderLineService.getListOrderLineInOrder(order.getOrderId());
                List<OrderLineForm> orderLineForms = orderLineService.getListOrderLineForm(orderLines);
                OrderForm orderForm = new OrderForm(order, orderLineForms);
                orderForms.add(orderForm);
            }
            return new ResponseEntity(orderForms, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/userPage/orders/{order-id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable("order-id") long orderId,
                                             @RequestParam(required = false, defaultValue = "0", name = "user-id") long userId) {
        try {
            OrderForm orderForm = orderService.findById(orderId);
            if (orderForm.getUserId() != userId) {
                logger.error("User not permitt");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (!orderForm.getStatus().equals("processing")) {
                logger.error("Can't delete this order");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else {
                Order order = orderService.getOrder(orderId);
                orderService.remove(order);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //xem chi tiết 1 đơn hàng
    @CrossOrigin
    @GetMapping(value = "/userPage/order/{order-id}")
    public ResponseEntity<OrderForm> getOrder(@PathVariable("order-id") long orderId,
                                              @RequestParam("user-id") long userId) {
        try {
            OrderForm orderForm = orderService.findById(orderId);
            if (orderForm.getUserId() != userId) {
                logger.error("User not permitt");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(orderForm, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //----------------------------ADMIN-----------------------------------------

    //admin lấy danh sách đơn đặt hàng của khách, lọc theo user-id, status, time, giá trị đơn
    @GetMapping(value = "/adminPage/orders")
    public ResponseEntity<Order> getAllOrder(@RequestParam(required = false, defaultValue = "") String status) {
        List<Order> orderList;
        if (!status.equals("")) {
            orderList = orderService.getListOrderByStatus(status);
        }
        orderList = orderService.findAllOrder();
        if (orderList != null && orderList.size() > 0) {
            return new ResponseEntity(orderList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/adminPage/order/{order-id}")
    public ResponseEntity<OrderForm> getOrderByAdmin(@PathVariable("order-id") long orderId,
                                              @RequestParam("user-id") long userId) {
        try {
            OrderForm orderForm = orderService.findById(orderId);
            if (orderForm.getUserId() != userId) {
                logger.error("User not permitt");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(orderForm, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //phê duyệt các đơn đang chờ xử lý <input là list các đơn>
    @PutMapping(value = "/adminPage/orders")
    public ResponseEntity<Order> approvalOrder(@RequestParam(required = false, defaultValue = "") String status) {
        List<Order> orderList;
        if (!status.equals("")) {
            orderList = orderService.getListOrderByStatus(status);
        }
        orderList = orderService.findAllOrder();
        if (orderList != null && orderList.size() > 0) {
            return new ResponseEntity(orderList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //xóa đơn đặt của người dùng, đơn đã đặt thành công không được phép xóa
    @DeleteMapping(value = "/adminPage/orders/{order-id}")
    public ResponseEntity<Order> deleteOrder(@RequestParam(required = false, defaultValue = "") String status) {
        List<Order> orderList;
        if (!status.equals("")) {
            orderList = orderService.getListOrderByStatus(status);
        }
        orderList = orderService.findAllOrder();
        if (orderList != null && orderList.size() > 0) {
            return new ResponseEntity(orderList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
