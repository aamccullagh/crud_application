package com.aaron.app.crud_app.repository;

import com.aaron.app.crud_app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
}
