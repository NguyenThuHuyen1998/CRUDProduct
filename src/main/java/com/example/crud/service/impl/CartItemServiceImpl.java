package com.example.crud.service.impl;

import com.example.crud.entity.Cart;
import com.example.crud.entity.CartItem;
import com.example.crud.entity.Product;
import com.example.crud.repository.CartItemRepository;
import com.example.crud.repository.CartRepository;
import com.example.crud.repository.ProductRepository;
import com.example.crud.repository.UserRepository;
import com.example.crud.service.CartItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
    created by HuyenNgTn on 22/11/2020
*/
@Service
public class CartItemServiceImpl implements CartItemService {
    public static final Logger logger = LoggerFactory.getLogger(CartItemServiceImpl.class);

    private CartItemRepository cartItemRepository;

    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository){
        this.cartItemRepository = cartItemRepository;
        this.cartRepository= cartRepository;
        this.userRepository= userRepository;
        this.productRepository= productRepository;
    }


    @Override
    public void save(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }



    @Override
    public CartItem getCartLineById(long cartItemId) {
        Optional<CartItem> optionalCartItem= cartItemRepository.findById(cartItemId);
        return optionalCartItem.get();
    }

    @Override
    public CartItem getCartItemByProductId(long userId, long productId) {
        return cartItemRepository.findCartItemByProductId(userId, productId);
    }


    @Override
    public void updateQuantityCartItem(CartItem cartItem) {
        save(cartItem);
    }


    @Override
    public void deleteCartItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }


    @Override
    public void deleteAllCartItem(long userId){
        try{
            List<CartItem> cartItemList= cartItemRepository.getListCartItemInCart(userId);
            Cart cart= cartRepository.getCartByUserId(userId);
            if(cartItemList.size()>0){
                for(CartItem cartItem: cartItemList){
                    cartItemList.remove(cartItem);
                    cartRepository.save(cart);
                    cartItemRepository.delete(cartItem);
                }
            }
        }
        catch (Exception e){
            logger.error(String.valueOf(e));
        }
    }

    @Override
    public List<CartItem> getListCartItemInCart(long userId) {
        return cartItemRepository.getListCartItemInCart(userId);
    }

    @Override
    public CartItem getCartItem(long cartItemId) {
        return cartItemRepository.findById(cartItemId).get();
    }


}
