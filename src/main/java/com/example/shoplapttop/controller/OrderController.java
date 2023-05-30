package com.example.shoplapttop.controller;

import com.example.shoplapttop.entity.Order;
import com.example.shoplapttop.model.response.ApiResponse;
import com.example.shoplapttop.model.response.cart.CartResponse;
import com.example.shoplapttop.model.response.order.Or;
import com.example.shoplapttop.model.response.order.OrderResponse;
import com.example.shoplapttop.model.response.order.Ores;

import com.example.shoplapttop.service.CartService;
import com.example.shoplapttop.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class OrderController {


    private final OrderService orderService;
    private final CartService cartService;

    @GetMapping("/api/public/order/all")
//    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderResponse> getAllOrder(){

        return orderService.getAllOrder();
    }


    @GetMapping("/api/public/order/getorder")
    public List<OrderResponse> getOrder(HttpServletRequest request){

        return orderService.getOrder(request);
    }

    @GetMapping("/api/public/order/all1")
    public ResponseEntity<Page<OrderResponse>> getAllUser(@RequestParam(required = false) int offset, @RequestParam(required = false) int limit,
                                                     @RequestParam(required = false) String keyWord, @RequestParam(required = false) Integer sort,
                                                     @RequestParam(required = false) Long Id){
        System.out.println(keyWord);
        return new ResponseEntity(orderService.getAllOrder1(offset,limit,keyWord,sort), HttpStatus.OK);
    }


    @GetMapping("/api/public/order/sales")
//    @PreAuthorize("hasRole('ADMIN')")
    public List<Ores> get(){

        List<Ores> alo = new ArrayList<Ores>();

        Ores a = new Ores();
        a.setAmount(2000);
        a.setColor("green");
        a.setYear(2018);

        Ores b = new Ores();
        b.setAmount(1000);
        b.setColor("blue");
        b.setYear(2017);

        Ores c = new Ores();
        c.setAmount(3000);
        c.setColor("Black");
        c.setYear(2020);

        alo.add(0,a);
        alo.add(1,b);
        alo.add(2,c);
        return alo;
    }


    @GetMapping("/api/public/order/sale")
    public List<Or> get1(){
        PageRequest pageRequest = PageRequest.of(1,3);

        List<Or> out = new ArrayList<>();
        List<Object[]> list = orderService.find1();
        if (list != null && !list.isEmpty()) {
            Or mor =null;
            for (Object[] object : list) {
                mor = new Or();

                String a=object[0].toString()+"/"+object[1].toString();

                mor.setYear(a);
                mor.setAmount(Integer.parseInt(object[2].toString()));

                out.add(mor);
            }
        }
        return out;
    }
    @GetMapping("/api/public/order/salesearch")
    public List<Or> get2(@RequestParam(required = false) String FromDate, @RequestParam(required = false) String ToDate){


        String fdate=check(FromDate);
        String tdate=check(ToDate);
        List<Or> out = new ArrayList<>();
        List<Object[]> list = orderService.find(fdate,tdate);
        if (list != null && !list.isEmpty()) {
            Or mor =null;
            for (Object[] object : list) {
                mor = new Or();

                String a=object[0].toString()+"/"+object[1].toString();

                mor.setYear(a);
                mor.setAmount(Integer.parseInt(object[2].toString()));

                out.add(mor);
            }
        }
        return out;
    }
    public String check(String Date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(Date, formatter);

        String m="";
        String d="";
        int month= localDate.getMonth().getValue();
        int day =localDate.getDayOfMonth();
        int year =localDate.getYear();
        String y=String.valueOf(year);

        if(day<10)
        {
            d="0"+String.valueOf(day);
        }
        else {
            d=String.valueOf(day);
        }
        if(month<10)
        {
            m="0"+String.valueOf(month);
        }
        else {
            m=String.valueOf(month);
        }
        String b=y+"/"+m+"/"+d;
        return b;

    }

    @DeleteMapping("/api/public/order/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteComment(@PathVariable long id){
        orderService.deleteOrder(id);
        System.out.println("alo"+id);
        return new ResponseEntity(new ApiResponse(true,"Delete success"),HttpStatus.OK);
    }

}
