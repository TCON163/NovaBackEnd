package com.revature.nova.services;

import com.revature.nova.models.Product;
import com.revature.nova.repositories.ProductRepo;
import javassist.NotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This service bean is used to talk to its designated repository and handle data retrieval for 'Product'
 *
 * @author Chris Oh, Michael Reece, Brittany Lowell
 * @date 11/22/21
 */
@Service
@Transactional
@Getter
@Setter
public class ProductService {

    private final ProductRepo repo;
    private List<Product> productList;
    private boolean filterOn;

    @Autowired
    public ProductService(ProductRepo repo) {
        this.repo = repo;
    }

    //unit testing: want to know it's returning a list of products, look at individual products within the list
    public List<Product> getProductsContainingTitle(String search)
    {
        return repo.findByTitleContaining(search);
    }

    /**
     * This method gets all products.
     *
     * @return Returns a list containing all products
     */
    public List<Product> displayAllProducts(){
        setFilterOn(false);
        return repo.findAll();
    }

    /**
     * This method returns a filtered list of products using the given filter type and filter value.
     * The filter type and filter value need to match what is in the database exactly.
     *
     * @param type This inputs determines how the products will be filtered. The valid filter types are:
     *             genre, platform, and rating.
     * @param value This input determines the value the products will be filtered by. Valid filter values include:
     *              For genre: RPG, Action, Horror, etc.
     *              For platform: PlayStation 4, Switch, etc.
     *              For rating: Mature, E10+, Teen, etc.
     * @return Returns a list of filtered products.
     */
    public List<Product> filterProducts(String type, String value) {
        switch (type) {
            case "genre":
                setProductList(repo.findByGenre(value));
                setFilterOn(true);
                break;
            case "platform":
                setProductList(repo.findByPlatform(value));
                setFilterOn(true);
                break;
            case "rating":
                setProductList(repo.findByRating(value));
                setFilterOn(true);
                break;
        }
        return getProductList();
    }

    /**
     *This method sorts the product list. If there has not been a call to the filter method, then this method
     * filters all products in the database
     *
     * @param sortingDirection
     * @return
     */
    public List<Product> sortedProductList(String sortingDirection){
        if(isFilterOn()){
            /*See https://www.geeksforgeeks.org/how-to-sort-an-arraylist-of-objects-by-property-in-java/ for example on
    How to Sort an ArrayList of Objects by Property in Java
     */
            if (sortingDirection.equals("lowest")) {
                getProductList().sort(Comparator.comparing(Product::getPrice));
            }else if (sortingDirection.equals("highest")) {
                getProductList().sort((o1, o2) -> o2.getPrice().compareTo(o1.getPrice()));
            }
        } else {
            if (sortingDirection.equals("lowest")) {
                setProductList(repo.findAllByOrderByPriceAsc());
            } else if (sortingDirection.equals("highest")) {
                setProductList(repo.findAllByOrderByPriceDesc());
            }
        }
        return getProductList();
    }
}
