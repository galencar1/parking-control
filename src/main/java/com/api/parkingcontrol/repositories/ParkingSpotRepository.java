package com.api.parkingcontrol.repositories;



import com.api.parkingcontrol.models.ParkingSpotModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


//JPA REPOSITORY - EFETUA AS TRANSAÇÕES COM O BANCO DE DADOS. FACILITA POIS NÃO PRECISAMOS
//CRIAR AS QUERYS NA MÃO
@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotModel, Long>{
    //Declara os métodos para verificação.
    //Após isso instaciar os métodos no service
    boolean existsByPlacaVeiculo(String placaVeiculo);
    boolean existsByNumeroVaga (String numeroVaga);
    boolean existsByApartamento (String apartamento);
}
