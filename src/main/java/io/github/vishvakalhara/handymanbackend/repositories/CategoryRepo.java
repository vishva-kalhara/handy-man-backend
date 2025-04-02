package io.github.vishvakalhara.handymanbackend.repositories;

import io.github.vishvakalhara.handymanbackend.domains.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepo extends JpaRepository<Category, UUID> {

    boolean existsCategoryByCategoryNameIgnoreCase(String name);
}