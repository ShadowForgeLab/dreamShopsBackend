package com.project.dreamshops.repo;

import com.project.dreamshops.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<Image,Long> {
}
