package com.revature.nova.repositories;

import com.revature.nova.models.Logger;
import com.revature.nova.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

}
