package com.api.parkingcontrol.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import javax.validation.Valid;

import com.api.parkingcontrol.DTO.ParkingSpotDto;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotServices;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping  // URI já foi definida a nivel de classe. Sempre será enviado para parking spot.
    //Acionando assim o método saveParkingSpot
    //Utiliza response Entity para para mapear a resposta.
    //Recebe os dados de entrada através do DTO.
    //Colocar o @Valid para que seja efetuada a validação que foi iniciado na camada DTO
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto){
        //Verificações

        //Verifica se o veiculo já está cadastrado através da placa
        if(parkingSpotServices.existsByPlacaVeiculo(parkingSpotDto.getPlacaVeiculo())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro! Veículo já cadastrado!");
        }
        //Verifica se a vaga já está sendo utilizada através do número da vaga
        if(parkingSpotServices.existsByNumeroVaga(parkingSpotDto.getNumeroVaga())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro! vaga já utilizada!");
        }
        //Verifica se já existe uma vaga registrada pra o apartamento. 
        //Cada apartamento tem direito a uma vaga.
        if(parkingSpotServices.existsByApartamento(parkingSpotDto.getApartamento())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Erro!já existe uma vaga registrada para esse Ap/Bloco");
        }
        
        var parkingSpotModel = new ParkingSpotModel();
        //Somente a Model envia as informações para o banco de dados.
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);//Efetua a conversão de DTO para model.
        parkingSpotModel.setDataRegistro(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotServices.save(parkingSpotModel));
    }


    //Solicitando todos os carros e vagas cadastradas no DB
    @GetMapping
    //Retorna uma listagem dos módulos, por isso utilizamos "List".
    //Acionando no Final o método findAll, criado no service.
    public ResponseEntity<List<ParkingSpotModel>> getAllParkingSpots(){
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotServices.findAll());
    }
}
