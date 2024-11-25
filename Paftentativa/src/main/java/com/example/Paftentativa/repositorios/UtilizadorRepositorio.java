package com.example.Paftentativa.repositorios;

import com.example.Paftentativa.models.UtilizadorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UtilizadorRepositorio extends JpaRepository<UtilizadorModel, UUID> {
    Optional<UtilizadorModel> findByEmail(String email);
}
