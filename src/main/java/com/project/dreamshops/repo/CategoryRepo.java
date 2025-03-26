package com.project.dreamshops.repo;

import com.project.dreamshops.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    Category findByName(String name);

    boolean existsByName(String name);
}
