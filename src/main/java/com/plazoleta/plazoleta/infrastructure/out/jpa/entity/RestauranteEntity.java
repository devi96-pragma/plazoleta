package com.plazoleta.plazoleta.infrastructure.out.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "restaurante")
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Long nit;
    private String direccion;
    private String telefono;
    private String urlLogo;
    private Long usuarioId;
}
