package com.integrador.grupoA.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "facturacion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Facturacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private double monto;

    @Column
    private LocalDate fecha_inicio;

    @Column
    private LocalDate fecha_fin;
}
