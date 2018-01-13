package com.altamontana.domain;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "receta",
        indexes = {@Index(name = "receta_pk", columnList = "receta_id", unique = true),
                   @Index(name = "nombre", columnList = "nombre", unique = true)})
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_receta_id")
    @SequenceGenerator(name = "sec_agencia_id", sequenceName = "sec_receta", allocationSize = 1, initialValue = 1)
    @Column(name = "receta_id")
    private Integer recetaID;

    @NotBlank
    @Column(name = "nombre", length = 75, nullable = false)
    private String nombre;

    @NotBlank
    @Column(name = "densidad_inicial", nullable = false)
    private Integer densidadInicial;

    @NotBlank
    @Column(name = "densidad_final", nullable = false)
    private Integer densidadFinal;

    public Integer getRecetaID() {
        return recetaID;
    }

    public void setRecetaID(Integer recetaID) {
        this.recetaID = recetaID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getDensidadInicial() {
        return densidadInicial;
    }

    public void setDensidadInicial(Integer densidadInicial) {
        this.densidadInicial = densidadInicial;
    }

    public Integer getDensidadFinal() {
        return densidadFinal;
    }

    public void setDensidadFinal(Integer densidadFinal) {
        this.densidadFinal = densidadFinal;
    }
}
