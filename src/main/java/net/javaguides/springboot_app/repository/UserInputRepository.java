package net.javaguides.springboot_app.repository;

import net.javaguides.springboot_app.model.UserInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInputRepository extends JpaRepository<UserInput, Long> {
}
