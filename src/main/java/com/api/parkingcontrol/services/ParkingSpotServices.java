package com.api.parkingcontrol.services;

import java.util.List;

import javax.transaction.Transactional;

import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import org.springframework.stereotype.Service;

//import org.springframework.beans.factory.annotation.Autowired;



//Service será a camada intermediária entre o controller e o Model.
//Pode ser feito uma interface do service e uma classe implementando a interface.

@Service

public class ParkingSpotServices {
    //Ocorrerá a comunicação entre o service e o repository através da injeção de dependencia.
    //@Autowired // Bean do Spring que efetua a Injeção de dependencia automaticamente.
    final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotServices(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }


    @Transactional // Anotamos como transactional para que nas transações, caso ocorra erro efetua
    //automaticamente o callback
    public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
        //Utilizando métodos do JPA
        return parkingSpotRepository.save(parkingSpotModel);
    }


    //Faz a instancia dos métodos para efetuar as verificações
    //Após efetuada a instancia, colocar as verificações no camada controller!
    public boolean existsByPlacaVeiculo(String placaVeiculo){
        return parkingSpotRepository.existsByPlacaVeiculo(placaVeiculo);
    }

    public boolean existsByNumeroVaga(String numeroVaga){
        return parkingSpotRepository.existsByNumeroVaga(numeroVaga);
    }
    
    public boolean existsByApartamento(String apartamento){
        return parkingSpotRepository.existsByApartamento(apartamento);
    }


    //Método que retorna a lista e está  sendo chamado ao controller.
    public List<ParkingSpotModel> findAll() {
        return parkingSpotRepository.findAll();
    }
}
