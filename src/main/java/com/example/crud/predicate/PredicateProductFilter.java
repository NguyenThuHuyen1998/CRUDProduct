package com.example.crud.predicate;

import com.example.crud.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Predicate;

/*
    created by HuyenNgTn on 12/12/2020
*/
public class PredicateProductFilter {
    public static final Logger logger = LoggerFactory.getLogger(PredicateProductFilter.class);

    private static final PredicateProductFilter predicateFilter= new PredicateProductFilter();

    private PredicateProductFilter(){

    }

    public static PredicateProductFilter getInstance(){
        return predicateFilter;
    }

    public Predicate<Product> checkDateAdd(long timeStart, long timeEnd){
        return (product) ->{
            try{
                if(timeStart== -1){
                    if(timeEnd ==-1){
                        return true;
                    }
                    if(product.getDate() <= timeEnd){
                        return true;
                    }
                }
                else if(timeEnd== -1){
                    if (product.getDate() >= timeStart){
                        return true;
                    }
                }
                else if(product.getDate() >= timeStart && product.getDate() <= timeEnd){
                    return true;
                }
                return false;
            }
            catch (Exception e){
                e.printStackTrace();
                return false;
            }
        };
    }

    public Predicate<Product> checkPrice(double minPrice, double maxPrice){
        return (product) ->{
            try{
                if(minPrice== -1){
                    if(maxPrice ==-1){
                        return true;
                    }
                    if(product.getPrice() <= maxPrice){
                        return true;
                    }
                }
                else if(maxPrice== -1){
                    if (product.getPrice() >= minPrice){
                        return true;
                    }
                }
                else if(product.getPrice() >= minPrice && product.getPrice() <= maxPrice){
                    return true;
                }
                return false;
            }
            catch (Exception e){
                e.printStackTrace();
                return false;
            }
        };
    }

    public Predicate<Product> checkKeyword(String keyword){
        return (product) ->{
            if(product.getName().contains(keyword) || product.getDescription().contains(keyword)  || product.getPreview().contains(keyword)){
                return true;
            }
            return false;
        };
    }

    public Predicate<Product> checkCategory(long categoryId){
        return product -> {
            if(categoryId==0){
                return true;
            }
            if(product.getCategory().getId()== categoryId){
                return true;
            }
            return false;
        };
    }
}
