package com.example.shoplapttop.controller;

import com.example.shoplapttop.model.request.order.OrderRequest;
import com.example.shoplapttop.model.response.ApiResponse;
import com.example.shoplapttop.model.response.cart.CartResponse;
import com.example.shoplapttop.service.CartService;
import com.example.shoplapttop.service.OrderService;
import com.example.shoplapttop.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    private final OrderService orderService;

    @PostMapping("/addItem")
    @PreAuthorize("hasAnyRole('USER') OR hasAnyRole('ADMIN')")
    public ResponseEntity<?> addToCart(HttpServletRequest request, @RequestParam long productId, @RequestParam int amountItem){
        cartService.insertItem(request, productId, amountItem);
        return new ResponseEntity(new ApiResponse(true,"SUCCESS"), HttpStatus.OK);
    }

    @PutMapping("/updateItem")
    @PreAuthorize("hasAnyRole('USER') OR hasAnyRole('ADMIN')")
    public ResponseEntity<?> updateItemCart(HttpServletRequest request, @RequestParam long cartId, @RequestParam int amountItem){
        cartService.updateItem(request, cartId, amountItem);
        return new ResponseEntity(new ApiResponse(true,"SUCCESS"), HttpStatus.OK);
    }

    @DeleteMapping("/deleteItem/{cartId}")
    @PreAuthorize("hasAnyRole('USER') OR hasAnyRole('ADMIN')")
    public ResponseEntity<?> checkOutItem(HttpServletRequest request, @PathVariable long cartId){
        cartService.deleteItem(request, cartId);
        return new ResponseEntity(new ApiResponse(true,"SUCCESS"), HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('USER') OR hasAnyRole('ADMIN')")
    public ResponseEntity<List<CartResponse>> getAllCart(HttpServletRequest request){
        return new ResponseEntity(cartService.getAllCart(request), HttpStatus.OK);
    }

    @GetMapping("/count")
    @PreAuthorize("hasAnyRole('USER') OR hasAnyRole('ADMIN')")
    public int countItemCart(HttpServletRequest request){
        return cartService.countItem(request);
    }

    @PutMapping("/checkout/{cartId}")
    @PreAuthorize("hasAnyRole('USER') OR hasAnyRole('ADMIN')")
    public ResponseEntity<?> checkOutItem(@PathVariable long cartId){
        cartService.checkOut(cartId);
        return new ResponseEntity(new ApiResponse(true,"SUCCESS"), HttpStatus.OK);
    }


        @PostMapping("/checkout1")
    @PreAuthorize("hasAnyRole('USER') OR hasAnyRole('ADMIN')")
    public ResponseEntity<?> check(HttpServletRequest request,@RequestBody OrderRequest order){
        orderService.insertOrder(request,order.getOrderName(),order.getOrderTotal(), order.getOrderInfor());
        System.out.println(order.getOrderInfor());
        return new ResponseEntity(new ApiResponse(true,"Insert success"), HttpStatus.OK);
    }




}
