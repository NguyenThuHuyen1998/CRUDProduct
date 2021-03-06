package com.example.crud.controller;

import com.example.crud.constants.InputParam;
import com.example.crud.entity.*;
import com.example.crud.helper.TimeHelper;
import com.example.crud.response.OrderResponse;
import com.example.crud.output.OrderLineForm;
import com.example.crud.service.*;
import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private SendEmailService emailService;
    private JwtService jwtService;
    private VoucherService voucherService;
    private ShipService shipService;

    @Autowired
    public OrderController(OrderService orderService, CartService cartService, CartItemService cartItemService, UserService userService, SendEmailService service, OrderLineService orderLineService, JwtService jwtService, ShipService shipService, VoucherService voucherService) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.userService = userService;
        this.orderLineService = orderLineService;
        this.emailService= service;
        this.jwtService = jwtService;
        this.shipService= shipService;
        this.voucherService= voucherService;
    }


    //tạo mới đơn hàng
//    @CrossOrigin
//    @PostMapping(value = "/userPage/orders")
//    public ResponseEntity<Order> createOrder(HttpServletRequest request) {
//        Order order = null;
//
//        if (jwtService.isCustomer(request)) {
//            try {
//                long userId = jwtService.getCurrentUser(request).getUserId();
//                List<CartItem> cartItemList = cartItemService.getListCartItemInCart(userId);
//                User user = userService.findById(userId);
//                Cart cart = cartService.getCartByUserId(userId);
//                if (cartItemList != null && cartItemList.size() > 0) {
//                    order = new Order(user, cart.getTotalMoney(), InputParam.PROCESSING, new Date().getTime());
//                    orderService.save(order);
//                    for (CartItem cartItem : cartItemList) {
//                        OrderLine orderLine = new OrderLine(cartItem, order);
//                        orderLineService.save(orderLine);
//                        cartItemService.deleteCartItem(cartItem);
//                    }
//                    cart.setTotalMoney(0);
//                    cartService.save(cart);
//                    //set lại thời gian hoạt động cuối cùng để sau có thể xóa cart của user nếu đã 3 tháng không hoạt động
//                    user.setLastActive(new Date().getTime());
//                    userService.add(user);
//                }
//                if (order == null) {
//                    return new ResponseEntity("Add product int cart!!", HttpStatus.NO_CONTENT);
//                }
//                return new ResponseEntity<>(order, HttpStatus.OK);
//
//            } catch (Exception e) {
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//        }
//        return new ResponseEntity("Login before processing", HttpStatus.METHOD_NOT_ALLOWED);
//
//    }

    //tạo đơn hàng mới từ giỏ hàng, điền các thông tin trong form ship, nhap ma giam gia
    @PostMapping(value = "/userPage/order")
    public ResponseEntity<Order> createOrder(@RequestBody String data,
                                             HttpServletRequest request){
        if(jwtService.isCustomer(request)){
            User user= jwtService.getCurrentUser(request);
            JSONObject jsonObject= new JSONObject(data);
            Address address= new Address(jsonObject.toString());
            address.setUser(user);
            shipService.save(address);
            Cart cart= cartService.getCartByUserId(user.getUserId());
        }
        return new ResponseEntity("Login before processing", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @GetMapping(value = "/userPage/order")
    public ResponseEntity<OrderResponse> getOrder(HttpServletRequest request){
        if (jwtService.isCustomer(request)){
            User user= jwtService.getCurrentUser(request);
            OrderResponse orderResponse= orderService.createOrder(user);
            return new ResponseEntity<>(orderResponse, HttpStatus.OK);
        }
        return new ResponseEntity("Login before processing", HttpStatus.METHOD_NOT_ALLOWED);
    }


    @GetMapping(value = "/userPage/voucher")
    public ResponseEntity<Cart> applyVoucher(@RequestBody String data,
                                             HttpServletRequest request){
        if (jwtService.isCustomer(request)) {
            User user= jwtService.getCurrentUser(request);
            Cart cart= cartService.getCartByUserId(user.getUserId());

            JSONObject jsonObject= new JSONObject(data);
            String code= jsonObject.getString("code");
            List<Voucher> list= voucherService.getListVoucher();
            Voucher voucher= null;
            for (Voucher index: list){
                if (index.getCode().equals(code)){
                    voucher= index;
                }
            }
            if (voucher!= null){
                return new ResponseEntity("This code is not exist", HttpStatus.BAD_REQUEST);
            }
            TypeDiscount typeDiscount= voucher.getTypeDiscount();
            double valueDiscount= voucher.getValueDiscount();
            if (typeDiscount== TypeDiscount.PERCENT){
                cart.setDiscount((cart.getTotalMoney())*valueDiscount/100);
            }

        }
        return new ResponseEntity("Login before processing", HttpStatus.METHOD_NOT_ALLOWED);
    }
    //lấy danh sách đơn hàng của 1 user
    //user chỉ được lấy ds đơn hàng của mình nên bắt buộc có user-id
    @GetMapping(value = "/userPage/orders")
    public ResponseEntity<OrderResponse> getlistOrder(@RequestParam(name = "status", required = false, defaultValue = "") String status,
                                                      @RequestParam(name = "dateStart", required = false, defaultValue = "-1") String dateStart,
                                                      @RequestParam(name = "dateEnd", required = false, defaultValue = "-1") String dateEnd,
                                                      @RequestParam(name = "priceMin", required = false, defaultValue = "-1") double priceMin,
                                                      @RequestParam(name = "priceMax", required = false, defaultValue = "-1") double priceMax,
                                                      @RequestParam(name = "sortBy", required = false, defaultValue = InputParam.DECREASE) String sortBy,
                                                      @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                                      @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                                      HttpServletRequest request) throws ParseException {
        if(jwtService.isCustomer(request)){

            long userId= jwtService.getCurrentUser(request).getUserId();
            Map<String, Object> filter= new HashMap<>();
            filter.put(InputParam.USER_ID, userId);
            filter.put(InputParam.STATUS, status);
            filter.put(InputParam.TIME_START, dateStart);
            filter.put(InputParam.TIME_END, dateEnd);
            filter.put(InputParam.SORT_BY, sortBy);
            filter.put(InputParam.PRICE_MIN, priceMin);
            filter.put(InputParam.PRICE_MAX, priceMax);

            List<Order> orderFilter = orderService.filterOrder(filter);
            List<Order> orderTotal= orderService.getListOrderByUserId(userId);
            List<OrderResponse> orderResponses = new ArrayList<>();
            Map<String, Object> result= new HashMap<>();

            if (orderFilter != null && orderFilter.size() > 0) {
                for (Order order : orderFilter) {
                    List<OrderLine> orderLines = orderLineService.getListOrderLineInOrder(order.getOrderId());
                    List<OrderLineForm> orderLineForms = orderLineService.getListOrderLineForm(orderLines);
                    OrderResponse orderResponse = new OrderResponse(order, orderLineForms);
                    orderResponses.add(orderResponse);
                }
                result.put(InputParam.DATA, orderResponses);

            }
            Map<String, Object> paging= new HashMap<>();
            int totalPage = (orderTotal.size()) / limit + ((orderTotal.size() % limit == 0) ? 0 : 1);
            int totalCount=orderFilter.size();
            paging.put(InputParam.RECORD_IN_PAGE, limit);
            paging.put(InputParam.TOTAL_COUNT, totalCount);
            paging.put(InputParam.CURRENT_PAGE, page);
            paging.put(InputParam.TOTAL_PAGE, totalPage);
            result.put(InputParam.PAGING, paging);
            return new ResponseEntity(result, HttpStatus.OK);
        }
        return new ResponseEntity("Login before processing", HttpStatus.METHOD_NOT_ALLOWED);
    }

    //Xóa 1 đơn hàng by user
    @DeleteMapping(value = "/userPage/orders/{order-id}")
    public ResponseEntity<Order> cancelOrder(@PathVariable("order-id") long orderId,
                                             HttpServletRequest request) {
        if(jwtService.isCustomer(request)){
            long userId= jwtService.getCurrentUser(request).getUserId();
            try {
                Order order = orderService.findById(orderId);
                if (order.getUser().getUserId() != userId) {
                    logger.error("User not permitt");
                    return new ResponseEntity("Login before processing", HttpStatus.METHOD_NOT_ALLOWED);
                }
                if (!order.getStatus().equals("processing")) {
                    logger.error("Can't delete this order");
                    return new ResponseEntity("You can't delete this order", HttpStatus.BAD_REQUEST);
                } else {
//                    List<OrderLine> orderLines= orderLineService.getListOrderLineInOrder(orderId);
//                    for(OrderLine orderLine: orderLines){
//                        orderLineService.remove(orderLine);
//                    }
                    order.setStatus(OrderStatus.CANCEL);
                    orderService.save(order);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            } catch (Exception e) {
                logger.error(String.valueOf(e));
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity("Login before processing", HttpStatus.METHOD_NOT_ALLOWED);
    }

    //xem chi tiết 1 đơn hàng
    @CrossOrigin
    @GetMapping(value = "/userPage/orders/{order-id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable("order-id") long orderId,
                                                  HttpServletRequest request) {
        if(jwtService.isCustomer(request)){
            long userId= jwtService.getCurrentUser(request).getUserId();
            try {
                Order order= orderService.findById(orderId);
                List<OrderLine> orderLines= orderLineService.getListOrderLineInOrder(orderId);
                List<OrderLineForm> orderLineForms= new OrderLineForm().change(orderLines);
                OrderResponse orderResponse = new OrderResponse(order, orderLineForms);
                if (order.getUser().getUserId() != userId) {
                    logger.error("User not permitt");
                    return new ResponseEntity("Login before processing", HttpStatus.METHOD_NOT_ALLOWED);
                }
                return new ResponseEntity(orderResponse, HttpStatus.OK);
            } catch (Exception e) {
                logger.error(String.valueOf(e));
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity("Login before processing", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @PutMapping(value = "/userPage/order/{order-id}")
    public ResponseEntity<Order> finishOrder(@PathVariable(name = "order-id") long orderId,
                                             HttpServletRequest request){
        if(jwtService.isCustomer(request)){
            long userId= jwtService.getCurrentUser(request).getUserId();
            try{
                Order order= orderService.findById(orderId);
                User user= order.getUser();
                if(userId != user.getUserId() || order.getStatus()!= OrderStatus.SHIPPING){
                    return new ResponseEntity("Login before processing", HttpStatus.METHOD_NOT_ALLOWED);
                }
                order.setStatus(OrderStatus.FINISHED);
                orderService.save(order);
            }
            catch (Exception e){
                logger.error("Order is not exist");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity("Login before processing", HttpStatus.METHOD_NOT_ALLOWED);
    }

    //----------------------------ADMIN-----------------------------------------

    //admin lấy danh sách đơn đặt hàng của khách, lọc theo user-id, productId, status, time, giá trị đơn
    @GetMapping(value = "/adminPage/orders")
    public ResponseEntity<OrderResponse> getlistOrderByAdmin(@RequestParam(name = "status", required = true, defaultValue = "") String status,
                                                             @RequestParam(name = "dateStart", required = false, defaultValue = "-1") String dateStart,
                                                             @RequestParam(name = "dateEnd", required = false, defaultValue = "-1") String dateEnd,
                                                             @RequestParam(name = "priceMin", required = false, defaultValue = "-1") double priceMin,
                                                             @RequestParam(name = "priceMax", required = false, defaultValue = "-1") double priceMax,
                                                             @RequestParam(name = "userId", required = false, defaultValue = "-1") long userId,
                                                             @RequestParam(name = "sortBy", required = false, defaultValue = InputParam.DECREASE) String sortBy,
                                                             @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
                                                             @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                                             HttpServletRequest request) throws ParseException {
        if(jwtService.isAdmin(request)){
            Map<String, Object> filter= new HashMap<>();
            filter.put(InputParam.USER_ID, userId);
            filter.put(InputParam.STATUS, status);
            filter.put(InputParam.TIME_START, dateStart);
            filter.put(InputParam.TIME_END, dateEnd);
            filter.put(InputParam.SORT_BY, sortBy);
            filter.put(InputParam.PRICE_MIN, priceMin);
            filter.put(InputParam.PRICE_MAX, priceMax);

            List<Order> orderFilter = orderService.filterOrder(filter);
            List<Order> orderTotal= orderService.findAllOrder();
            List<OrderResponse> orderResponses = new ArrayList<>();
            Map<String, Object> result= new HashMap<>();


            if (orderFilter != null && orderFilter.size() > 0) {
                for (Order order : orderFilter) {
                    List<OrderLine> orderLines = orderLineService.getListOrderLineInOrder(order.getOrderId());
                    List<OrderLineForm> orderLineForms = orderLineService.getListOrderLineForm(orderLines);
                    OrderResponse orderResponse = new OrderResponse(order, orderLineForms);
                    orderResponses.add(orderResponse);
                }
                result.put(InputParam.DATA, orderResponses);
                Map<String, Object> paging= new HashMap<>();
                int totalPage = (orderTotal.size()) / limit + ((orderTotal.size() % limit == 0) ? 0 : 1);
                int recordInPage=limit;
                int currentPage=page;
                int totalCount=orderFilter.size();
                paging.put(InputParam.RECORD_IN_PAGE, recordInPage);
                paging.put(InputParam.TOTAL_COUNT, totalCount);
                paging.put(InputParam.CURRENT_PAGE, currentPage);
                paging.put(InputParam.TOTAL_PAGE, totalPage);
                result.put(InputParam.PAGING, paging);
                return new ResponseEntity(result, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity("You aren't admin", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @GetMapping(value = "/adminPage/order/{order-id}")
    public ResponseEntity<OrderResponse> getOrderByAdmin(@PathVariable("order-id") long orderId,
                                                         HttpServletRequest request) {
        if(jwtService.isAdmin(request)){
            try {
                Order order = orderService.findById(orderId);
                List<OrderLine> orderLine= orderLineService.getListOrderLineInOrder(orderId);
                List<OrderLineForm> orderLineForms= new OrderLineForm().change(orderLine);
                OrderResponse orderResponse = new OrderResponse(order, orderLineForms);
                return new ResponseEntity(orderResponse, HttpStatus.OK);
            } catch (Exception e) {
                logger.error(String.valueOf(e));
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity("You aren't admin", HttpStatus.METHOD_NOT_ALLOWED);
    }

    //phê duyệt các đơn đang chờ xử lý <input là list các đơn>
    @PutMapping(value = "/adminPage/orders/{order-id}")
    public ResponseEntity<Order> approvalOrder(@PathVariable(name = "order-id") long orderId,
                                               HttpServletRequest request) {
        if(jwtService.isAdmin(request)){
            try{
                Order order= orderService.findById(orderId);
                if(order.getStatus()== OrderStatus.PROCESSING){
                    User user= order.getUser();
                    if(user == null){
                        logger.error("User is not exist, need delete this order");
                        userService.delete(user);
                    }
                    order.setStatus(OrderStatus.SHIPPING);
                    orderService.save(order);

                    // send email notification
                    Calendar calendar= Calendar.getInstance();
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    Date date1= calendar.getTime();
                    String dateStr= new SimpleDateFormat("dd/MM/yyyy").format(date1);
                    String message= "Đơn hàng có mã "+ order.getOrderId()+ " của bạn đã được giao cho shipper. Đơn sẽ được giao muộn nhất vào ngày " + dateStr+". Hãy để ý điện thoại.";
                    emailService.notifyOrder(message, user.getEmail());
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                else {
                    return new ResponseEntity("You cannot perform this action", HttpStatus.METHOD_NOT_ALLOWED);
                }
            }
            catch (Exception e){
                return new ResponseEntity("Order is not exist", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity("You isn't admin", HttpStatus.METHOD_NOT_ALLOWED);

    }

    //xóa đơn đặt của người dùng, đơn đã đặt thành công không được phép xóa
    @DeleteMapping(value = "/adminPage/orders/{order-id}")
    public ResponseEntity<Order> deleteOrderByAdmin(@PathVariable(name = "order-id") long orderId,
                                                    HttpServletRequest request) {
        if(jwtService.isAdmin(request)){
            try{
                Order order= orderService.findById(orderId);
                if (!order.getStatus().equals(InputParam.PROCESSING)) {
                    return new ResponseEntity("You cannot perform this action", HttpStatus.METHOD_NOT_ALLOWED);
                }
//                List<OrderLine> orderLines= orderLineService.getListOrderLineInOrder(orderId);
//                for(OrderLine orderLine: orderLines){
//                    orderLineService.remove(orderLine);
//                }
                orderService.remove(order);
                return new ResponseEntity("Success", HttpStatus.OK);
            }
            catch (Exception e){
                logger.error("Order is not exist");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity("You isn't admin", HttpStatus.METHOD_NOT_ALLOWED);
    }


}
