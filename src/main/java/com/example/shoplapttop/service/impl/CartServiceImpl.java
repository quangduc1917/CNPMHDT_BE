package com.example.shoplapttop.service.impl;

import com.example.shoplapttop.entity.Cart;
import com.example.shoplapttop.entity.Product;
import com.example.shoplapttop.entity.User;
import com.example.shoplapttop.exception.ResourceNotFoundException;
import com.example.shoplapttop.model.response.cart.CartResponse;
import com.example.shoplapttop.repository.CartRepository;
import com.example.shoplapttop.repository.ProductRepository;
import com.example.shoplapttop.repository.UserRepository;
import com.example.shoplapttop.security.JwtTokenProvider;
import com.example.shoplapttop.service.CartService;
import com.example.shoplapttop.service.ProductService;
import com.example.shoplapttop.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private final ProductService productService;

    @Override
    public void insertItem(HttpServletRequest request, long productId, int amountItem) {
        String token = JwtUtil.getToken(request);
        Long userId = jwtTokenProvider.getUserIdFromJWT(token);

        Optional<User> findUser = userRepository.findById(userId);

        Optional<Product> resultProduct = productRepository.findById(productId);
        resultProduct.orElseThrow(()->new ResourceNotFoundException("Id not found!","ID", productId));

        try {

            Cart findProductInCart = cartRepository.findByProductCart(resultProduct.get());

            if (findProductInCart != null && findProductInCart.getState() == 0) {
                int i = findProductInCart.getCartAmount();
                Cart cart = findProductInCart;
                cart.setCartAmount(i+amountItem);
                cartRepository.save(cart);

            } else {
                Cart cart = new Cart();
                cart.setCartAmount(amountItem);
                cart.setState(0);
                cart.setUserCart(findUser.get());
                cart.setProductCart(resultProduct.get());

                cartRepository.save(cart);
            }
        }catch (Throwable ex){

        }

    }

    @Override
    public void updateItem(HttpServletRequest request, long cartId, int amountItem) {
        String token = JwtUtil.getToken(request);
        Long userId = jwtTokenProvider.getUserIdFromJWT(token);

        Optional<User> findUser = userRepository.findById(userId);



        Cart cart = cartRepository.findById(cartId).get();

        Product p = cart.getProductCart();
        if(amountItem>p.getAmount())
        {
            System.out.println("ko đủ số lượng");
        }


        Cart newCart = cart;
        newCart.setCartAmount(amountItem);

        cartRepository.save(newCart);

    }

    @Override
    public void deleteItem(HttpServletRequest request, long cartId) {
        String token = JwtUtil.getToken(request);
        Long userId = jwtTokenProvider.getUserIdFromJWT(token);

        Optional<User> findUser = userRepository.findById(userId);


        cartRepository.deleteById(cartId);
    }

    @Override
    public List<CartResponse> getAllCart(HttpServletRequest request) {
        String token = JwtUtil.getToken(request);
        Long userId = jwtTokenProvider.getUserIdFromJWT(token);

        Optional<User> findUser = userRepository.findById(userId);

        List<Cart> carts = cartRepository.findAllByUserCart(findUser.get());
        List<CartResponse> cartResponses =  carts.stream().map(t->{
            CartResponse cartResponse = new CartResponse();
            cartResponse.setProductId(t.getProductCart().getProductId());
            cartResponse.setCartId(t.getCartId());
            cartResponse.setPrice(t.getProductCart().getPrice());
            cartResponse.setNameProduct(t.getProductCart().getNameProduct());
            cartResponse.setAmountItem(t.getCartAmount());
            cartResponse.setImageProduct(t.getProductCart().getImageFirst());
            cartResponse.setTotalPrice(t.getCartAmount() * t.getProductCart().getPrice());
            cartResponse.setState(t.getState());
            return cartResponse;
        }).collect(Collectors.toList());

        return cartResponses;
    }




    @Override
    public int countItem(HttpServletRequest request) {
        String token = JwtUtil.getToken(request);
        Long userId = jwtTokenProvider.getUserIdFromJWT(token);

        Optional<User> findUser = userRepository.findById(userId);

        int nCountItem = 0;

        nCountItem = cartRepository.findAllByUserCart(findUser.get()).size();
        return nCountItem;
    }

    @Override
    public void checkOut(long cartId) {
        Cart cart = cartRepository.findById(cartId).get();
        cart.setState(1);


        Product p = cart.getProductCart();
        p.setAmount(p.getAmount()-cart.getCartAmount());

        productRepository.save(p);

        cart.setState(1);
//        Optional<Product> p=productService.findById(cart.getProductCart().getProductId());
//        p.set
        cartRepository.save(cart);

    }


}
