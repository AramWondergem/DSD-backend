package com.example.demo.repository;

import com.example.demo.entities.Lease;
import com.example.demo.entities.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaseRepository extends JpaRepository<Lease, Long> {
    List<Lease> findAllByUser_Username(String userUsername);
}
