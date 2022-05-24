package com.api.parkingcontrol.services;

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

    public boolean existsByPlacaVeiculo(String placaVeiculo){
        return parkingSpotRepository.existsByPlacaVeiculo(placaVeiculo);
    }
}
