package com.api.parkingcontrol.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import javax.validation.Valid;

import com.api.parkingcontrol.DTO.ParkingSpotDto;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.services.ParkingSpotServices;
import org.springframework.data.domain.Sort;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<Page<ParkingSpotModel>> getAllParkingSpots(@PageableDefault(page=0, size=10,sort="id",direction=Sort.Direction.ASC)Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotServices.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id")Long id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotServices.findById(id);
        if(!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga não encontrada!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id")Long id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotServices.findById(id);
        if(!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga não encontrada!");
        }
        parkingSpotServices.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Vaga deletada com Sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id")Long id,
                                                    @RequestBody @Valid ParkingSpotDto parkingSpotDto){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotServices.findById(id);
        if(!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga não encontrada!");
        }
        // Precisamos converter DTO para MODEL, por isso instanciamos objeto PArking Spot Model.
        //Precisamos setar todos os campos que serão alterados.
        //Lembrando que o ID nunca é alterado.
        //Data de registro também não pode ser alterada.

        //Primeida forma de fazer abaixo
        /*var parkingSpotModel = parkingSpotModelOptional.get();
        parkingSpotModel.setNumeroVaga(parkingSpotDto.getNumeroVaga());
        parkingSpotModel.setPlacaVeiculo(parkingSpotDto.getPlacaVeiculo());
        parkingSpotModel.setMarcaCarro(parkingSpotDto.getMarcaCarro());
        parkingSpotModel.setModeloCarro(parkingSpotDto.getModeloCarro());
        parkingSpotModel.setCorCarro(parkingSpotDto.getCorCarro());
        parkingSpotModel.setNomeResponsavel(parkingSpotDto.getNomeResponsavel());
        parkingSpotModel.setApartamento(parkingSpotDto.getApartamento());
        parkingSpotModel.setBloco(parkingSpotDto.getBloco());*/

        //Segunda forma de fazer - mais fácil
        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setId(parkingSpotModelOptional.get().getId());
        parkingSpotModel.setDataRegistro(parkingSpotModelOptional.get().getDataRegistro());
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotServices.save(parkingSpotModel));
    }
}
