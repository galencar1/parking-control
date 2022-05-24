package com.api.parkingcontrol.repositories;

import java.util.UUID;

import com.api.parkingcontrol.models.ParkingSpotModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


//JPA REPOSITORY - EFETUA AS TRANSAÇÕES COM O BANCO DE DADOS. FACILITA POIS NÃO PRECISAMOS
//CRIAR AS QUERYS NA MÃO
@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotModel, UUID>{
    boolean existsByPlacaVeiculo(String placaVeiculo);
    boolean existsByNumeroVaga (String numeroVaga);
}
