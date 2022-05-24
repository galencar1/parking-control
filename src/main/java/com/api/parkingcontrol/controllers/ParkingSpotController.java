package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.services.ParkingSpotServices;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Bean para criação de API REST
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")

public class ParkingSpotController {
    //Cria um ponto de injeção para service
    final ParkingSpotServices parkingSpotServices;
    
    public ParkingSpotController (ParkingSpotServices parkingSpotServices){
        this.parkingSpotServices = parkingSpotServices;
    }
}
