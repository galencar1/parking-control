package com.api.parkingcontrol.services;

import com.api.parkingcontrol.repositories.ParkingSpotRepository;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
