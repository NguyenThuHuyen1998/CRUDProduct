//package com.example.crud.controller;
//
//import com.example.crud.constants.InputParam;
//import com.example.crud.entity.Cart;
//import com.example.crud.entity.CartItem;
//import com.example.crud.entity.Product;
//import com.example.crud.entity.User;
//import com.example.crud.service.CartItemService;
//import com.example.crud.service.CartService;
//import com.example.crud.service.ProductService;
//import com.example.crud.service.UserService;
//import org.json.JSONObject;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//public class CartItemController {
//
//    private CartItemService cartItemService;
//    private UserService userService;
//    private ProductService productService;
//    private CartService cartService;
//
//    public CartItemController(CartItemService cartItemService, UserService userService, ProductService productService, CartService cartService){
//        this.cartItemService= cartItemService;
//        this.userService= userService;
//        this.productService= productService;
//        this.cartService= cartService;
//    }
//
//    //thêm sản phâm vào giỏ hàng
//    @PostMapping("/carts/items/{product-id}")
//    public ResponseEntity<CartItem> addProduct(@PathVariable(name = "product-id") long productId,
//                                               @RequestParam(name = "user-id") long userId){
//        // tạm thời truyền userId vào, sau lấy userId qua token nên chắc chắn user có tồn tại, không cần check
//        User user= userService.findById(userId);
//        Cart cart= cartService.getCartByUserId(userId);
//        List<CartItem> cartItemList= cartItemService.getListCartItemInCart(userId);
//        Product product= productService.findById(productId);
//        CartItem cartItemTarget= null;
//        if(cartItemList.size()>0){
//            for(CartItem index: cartItemList) {
//                if (product.getId() == index.getProduct().getId()) {
//                    cartItemTarget= index;
//                }
//            }
//        }
//        if(cartItemTarget!= null){
//            cartItemTarget.setQuantity(cartItemTarget.getQuantity() + 1);
//            cartItemService.save(cartItemTarget);
//        } else if(cartItemTarget== null){
//            cartItemTarget= new CartItem(cart, product, 1);
//            cartItemList.add(cartItemTarget);
//            cartItemService.save(cartItemTarget);
//        }
//        return new ResponseEntity("Success", HttpStatus.OK);
//    }
//
//    //Thêm số lượng sản phẩm trong giỏ hàng
//    @PutMapping("/carts/items/{product-id}")
//    public ResponseEntity<CartItem> updateQuantity(@PathVariable(name = "product-id") long productId,
//                                                   @RequestParam(name = "user-id") long userId,
//                                                   @RequestBody String data)  {
//        JSONObject jsonObject= new JSONObject(data);
//        int quantity= jsonObject.getInt(InputParam.QUANTITY);
//        User user= userService.findById(userId);
//        Cart cart= cartService.getCartByUserId(userId);
//        Product product= productService.findById(productId);
//        List<CartItem> cartItemList= cartItemService.getListCartItemInCart(userId);
//        for (CartItem cartItem: cartItemList){
//            if(cartItem.getProduct().getId()== productId){
//                cartItem.setQuantity(quantity);
//                if(quantity!=0){
//                    cartItemService.save(cartItem);
//                }
//                else cartItemService.deleteCartItem(cartItem);
//                return new ResponseEntity<>(HttpStatus.OK);
//            }
//        }
//        CartItem cartItem= new CartItem(cart, product, quantity);
//        cartItemService.save(cartItem);
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
//
//    @GetMapping(value = "/carts/items/{user-id}")
//    public ResponseEntity<Cart> getListProduct(@PathVariable("user-id") long userId){
//        Cart cart= cartService.getCartByUserId(userId);
//        double amount= 0;
//        if(cart== null){
//            return new ResponseEntity("User is not exist", HttpStatus.BAD_REQUEST);
//        }
//        List<CartItem> cartItems= cartItemService.getListCartItemInCart(userId);
//        if(cartItems!= null && cartItems.size()>0) {
//            for(CartItem cartItem: cartItems){
//                amount+= cartItem.getQuantity()* cartItem.getProduct().getPrice();
//            }
//            cartService.save(cart);
//            return new ResponseEntity(cartItems, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//
//    @DeleteMapping("carts/items")
//    public ResponseEntity<CartItem> removeAllProduct(@RequestParam(name = "user-id") long userId){
//        User user= userService.findById(userId);
//        cartItemService.deleteAllCartItem(userId);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//
//}
