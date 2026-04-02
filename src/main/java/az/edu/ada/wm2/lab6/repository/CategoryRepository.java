package az.edu.ada.wm2.lab6.repository;

import az.edu.ada.wm2.lab6.model.Category;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, UUID> {}

