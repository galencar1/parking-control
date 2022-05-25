package com.api.parkingcontrol.models;

//import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//Inserindo anotações para criar o mapeamento com o JPA
@Entity
@Table (name="TB_PARKING_SPOT1")  // Anotação para criar a tabela no banco de dados

public class ParkingSpotModel  {
    //private static final long serialVersionUID = 1L; // Trata das conversões do Java para o DB.

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //  Id será gerado de forma automática
    private Long id;

    //Nullable - Define se o campo pode ser nulo ou não. Unique - Define se o campo será unico ou não.
    @Column(nullable = false, unique = true, length = 10)
    private String numeroVaga;

    @Column(nullable = false, unique = true, length = 7)
    private String placaVeiculo;

    @Column(nullable = false, length = 70)
    private String marcaCarro;

    @Column(nullable = false, length = 70)
    private String modeloCarro;

    @Column(nullable = false, length = 70)
    private String corCarro;

    @Column (nullable = false)
    private LocalDateTime dataRegistro;

    @Column(nullable = false, length = 130)
    private String nomeResponsavel;

    @Column(nullable = false, length = 30)
    private String apartamento;

    @Column(nullable = false,length = 30)
    private String bloco;

    //Gerando Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNumeroVaga() {
        return numeroVaga;
    }

    public void setNumeroVaga(String numeroVaga) {
        this.numeroVaga = numeroVaga;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public String getMarcaCarro() {
        return marcaCarro;
    }

    public void setMarcaCarro(String marcaCarro) {
        this.marcaCarro = marcaCarro;
    }

    public String getModeloCarro() {
        return modeloCarro;
    }

    public void setModeloCarro(String modeloCarro) {
        this.modeloCarro = modeloCarro;
    }

    public String getCorCarro() {
        return corCarro;
    }

    public void setCorCarro(String corCarro) {
        this.corCarro = corCarro;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public String getApartamento() {
        return apartamento;
    }

    public void setApartamento(String apartamento) {
        this.apartamento = apartamento;
    }

    public String getBloco() {
        return bloco;
    }

    public void setBloco(String bloco) {
        this.bloco = bloco;
    }
    
}
