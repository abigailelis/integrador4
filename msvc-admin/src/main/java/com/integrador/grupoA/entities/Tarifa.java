package com.integrador.grupoA.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tarifa")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, unique = true)
    @NotEmpty(message = "El campo tipo tarifa no puede estar vacío")
    private String tipo_tarifa;

    @Column (nullable = false)
    @NotEmpty(message = "El campo monto no puede estar vacío")
    private double monto;
}