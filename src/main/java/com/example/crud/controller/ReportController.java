package com.example.crud.controller;

import com.example.crud.constants.InputParam;
import com.example.crud.entity.Order;
import com.example.crud.helper.TimeHelper;
import com.example.crud.service.JwtService;
import com.example.crud.service.OrderService;
import com.example.crud.service.ReportService;
import com.example.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
//import java.time.DayOfWeek;
//import java.time.LocalDate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;

@RestController
public class ReportController {

    private OrderService orderService;
    private UserService userService;
    private ReportService reportService;
    private JwtService jwtService;

    @Autowired
    public ReportController(OrderService orderService, UserService userService, ReportService reportService, JwtService jwtService){
        this.orderService= orderService;
        this.userService= userService;
        this.reportService= reportService;
        this.jwtService= jwtService;
    }

    @GetMapping
    public ResponseEntity<Order> reportProduct(@RequestParam(value = "time", defaultValue = InputParam.TODAY) String time,
                                               @RequestParam(value = "dateStart", defaultValue = "-1") String dateStart,
                                               @RequestParam(value = "dateEnd", defaultValue = "-1") String dateEnd,
                                               HttpServletRequest request){
        if(jwtService.isAdmin(request)){
            try{
                String start="";
                String end= "";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy");
                TimeHelper timeHelper= new TimeHelper();
                switch (time){
                    case InputParam.TODAY:{
                        start= LocalDate.now().format(formatter);
                        end= LocalDate.now().format(formatter);
                        break;
                    }
                    case InputParam.THIS_WEEK:{
                        start= timeHelper.getFirstDayInWeek();
                        end= timeHelper.getLastDayInWeek();
                        break;
                    }
                    case InputParam.THIS_MONTH:{
                        start= timeHelper.getFirstInMonth();
                        end= timeHelper.getLastDayInMonth();
                        break;
                    }
                    case InputParam.THIS_YEAR:{
                        start= timeHelper.getFirstDayOfYear();
                        end= timeHelper.getLastDayOfYear();
                        break;
                    }
                    case InputParam.OPTION:{
                        start= dateStart;
                        end= dateEnd;
                        break;
                    }

                }
                Map<String, Object> filter= new HashMap<>();
                filter.put(InputParam.USER_ID, -1);
                filter.put(InputParam.STATUS, "");
                filter.put(InputParam.TIME_START, start);
                filter.put(InputParam.TIME_END, end);
                filter.put(InputParam.SORT_BY, "");
                filter.put(InputParam.PRICE_MIN, -1);
                filter.put(InputParam.PRICE_MAX, -1);
                List<Order> orderList= reportService.filterOrder(filter);
            }
            catch (Exception e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity("You isn't admin", HttpStatus.METHOD_NOT_ALLOWED);
    }

    public static void main(String[] args) {
        Calendar dateStart= Calendar.getInstance();
        DateFormat dateFormat= new SimpleDateFormat("dd/mm/yyyy");
//        for (int i=0; i<6; i++){
//            dateStart.add(Calendar.DATE, 1);
//        }
        System.out.println(dateFormat.format(dateStart.getTime()));
    }
}
