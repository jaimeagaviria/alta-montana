package com.altamontana.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "recetas",
        indexes = {@Index(name = "receta_pk", columnList = "receta_id", unique = true),
                   @Index(name = "nombre_idx", columnList = "nombre", unique = true)})
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_receta_id")
    @SequenceGenerator(name = "sec_receta_id", sequenceName = "sec_receta", allocationSize = 1, initialValue = 1)
    @Column(name = "receta_id")
    private Integer recetaID;

    @NotBlank
    @Column(name = "nombre", length = 75, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 500, nullable = true)
    private String descripcion;

    @NotNull
    @Column(name = "densidad_inicial", nullable = false)
    private Integer densidadInicial;

    @NotNull
    @Column(name = "densidad_final", nullable = false)
    private Integer densidadFinal;

    @Column(name = "litros_finales", nullable = true)
    private Integer litrosFinales;

    @JsonIgnore
    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<Componente> componentes;

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

    public Integer getLitrosFinales() {
        return litrosFinales;
    }

    public void setLitrosFinales(Integer litrosFinales) {
        this.litrosFinales = litrosFinales;
    }

    public List<Componente> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<Componente> componentes) {
        this.componentes = componentes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
