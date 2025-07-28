package com.plazoleta.plazoleta.infrastructure.out.jpa.entity;

import com.plazoleta.plazoleta.domain.model.Categoria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "plato")
@AllArgsConstructor
@NoArgsConstructor
public class PlatoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private float precio;
    private String descripcion;
    private String urlImagen;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    private boolean isActivo;
    private Long idRestaurante;
}
