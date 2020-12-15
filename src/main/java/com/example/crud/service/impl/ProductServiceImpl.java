package com.example.crud.service.impl;

import com.example.crud.constants.InputParam;
import com.example.crud.entity.Category;
import com.example.crud.entity.Product;
import com.example.crud.predicate.PredicateProductFilter;
import com.example.crud.repository.CategoryRepository;
import com.example.crud.repository.ProductRepository;
import com.example.crud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.apache.commons.collections4.ListUtils;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository= productRepository;
        this.categoryRepository= categoryRepository;
    }


    @Override
    public List<Product> findAllProduct() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product findById(Long productId) {
        Optional<Product> optionalProduct= productRepository.findById(productId);
        return optionalProduct.get();
    }
//
    @Override
    public List<Product> findByCategoryID(Long categoryId) {
        return productRepository.findProductByCategoryId(categoryId);
    }

//    @Override
//    public List<Product> findProductByPrice(double priceMin, double priceMax) {
//        return productRepository.findProductByPrice(priceMin, priceMax);
//    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void remove(Product product) {
        productRepository.delete(product);
    }

    @Override
    public Product update(Product product) {
        long productId= product.getId();
        Product oldProduct= findById(productId);
        oldProduct.setCategory(product.getCategory()== null? oldProduct.getCategory() : product.getCategory());
        oldProduct.setName(product.getName()== null? oldProduct.getName() : product.getName());
        oldProduct.setDescription(product.getDescription()== null? oldProduct.getDescription() : product.getDescription());
        oldProduct.setPrice(product.getPrice()== 0? oldProduct.getPrice(): product.getPrice());
        oldProduct.setImage(product.getImage()== null? oldProduct.getImage(): product.getImage());
        oldProduct.setPreview(product.getPreview()== null? oldProduct.getPreview(): product.getPreview());
        oldProduct.setDateAdd(new Date().getTime());
        save(oldProduct);
        return oldProduct;
    }

    @Override
    public List<Product> filterProduct(Map<String, Object> filter){
        double priceMin= (double) filter.get(InputParam.PRICE_MIN);
        double priceMax= (double) filter.get(InputParam.PRICE_MAX);
        long timeStart= (long) filter.get(InputParam.TIME_START);
        long timeEnd= (long) filter.get(InputParam.TIME_END);
        long categoryId= (long) filter.get(InputParam.CATEGORY_ID);
        String keyword= (String) filter.get(InputParam.KEY_WORD);
        String sortBy= (String) filter.get(InputParam.SORT_BY);

        Predicate<Product> predicate= null;
        PredicateProductFilter productFilter = PredicateProductFilter.getInstance();
        Predicate<Product> checkPrice = productFilter.checkDateAdd(timeStart, timeEnd);
        Predicate<Product> checkDateAdd = productFilter.checkPrice(priceMin, priceMax);
        Predicate<Product> checkKeyword = productFilter.checkKeyword(keyword);
        Predicate<Product> checkCategory= productFilter.checkCategory(categoryId);
        predicate = checkPrice.and(checkDateAdd).and(checkKeyword).and(checkCategory);
        List<Product> productList= filterProduct(findAllProduct(), predicate);
        productList= sortByDateAdd(productList, sortBy);
        return productList;
    }

    public List<Product> sortByDateAdd(List<Product> products, String sortBy){
        if(sortBy.equals(InputParam.INCREASE)){
            Collections.sort(products, new Comparator<Product>() {
                public int compare(Product o1, Product o2) {
                    return o1.getDate() > o2.getDate() ? 1 : (o1 == o2 ? 0 : -1);
                }
            });
        }
        if (sortBy.equals(InputParam.DECREASE)){
            Collections.sort(products, new Comparator<Product>() {
                public int compare(Product o1, Product o2) {
                    return o1.getDate() < o2.getDate() ? 1 : (o1 == o2 ? 0 : -1);
                }
            });
        }
        return products;
    }
    public static List<Product> filterProduct (List<Product> products,
                                                 Predicate<Product> predicate)
    {
        return products.stream()
                .filter( predicate )
                .collect(Collectors.<Product>toList());
    }


    public List<Product> filterByPrice(List<Product> input, double priceMin, double priceMax){
        List<Product> productFilter= new ArrayList<>();
        for(Product product: input){
            if(priceMin <= product.getPrice() && product.getPrice()<= priceMax){
                productFilter.add(product);
            }
        }
        input=  ListUtils.intersection(input, productFilter);
        return input;
    }

    public List<Product> filterByCategoryId(List<Product> input, long categoryId){
        List<Product> productFilter= new ArrayList<>();
        Optional<Category> category= categoryRepository.findById(categoryId);
        if(category.isEmpty()){
            return null;
        }
        productFilter= productRepository.findProductByCategoryId(categoryId);
        if(productFilter!= null && productFilter.size()!=0){
            input= ListUtils.intersection(input, productFilter);
            return input;
        }
        return null;
    }

    public List<Product> filterByKeyword(String keyword){
////        List<Product> productList= (List<Product>) productRepository.findAll();
////        List<Product> productFilter= new ArrayList<>();
////        for(Product product: productList){
////            if (product.getName().toLowerCase().contains(keyword) ||product.getDescription().toLowerCase().contains(keyword) || product.getCategory().getName().toLowerCase().contains(keyword)) {
////                productFilter.add(product);
////            }
////        }
//        ;
        return null;
    }

    public List<Product> filterByDateAdd(int limit, String sortBy){
        Sort sort= null;
        if(sortBy.equals(InputParam.INCREASE)){
            sort= Sort.by("price").ascending();
        }
        else if(sortBy.equals(InputParam.DECREASE)){
            sort= Sort.by("price").descending();
        }
        else if(sortBy.equals(InputParam.BEST_SELLER)){
            sort= Sort.by("price").ascending();
        }
        else if(sortBy.equals(InputParam.NEWEST)){
            sort= Sort.by("dateAdd").descending();
        }
//        Pageable pageable = PageRequest.of(1, limit, sort);
        List<Product> productList= (List<Product>) productRepository.findAll(sort);
        return productList;
    }
}
