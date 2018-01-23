package com.altamontana.domain;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "componentes",
        indexes = {@Index(name = "componente_pk", columnList = "componente_id", unique = true),
                   @Index(name = "nombre_componente_idx", columnList = "nombre_componente")})
public class Componente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_componente_id")
    @SequenceGenerator(name = "sec_componente_id", sequenceName = "sec_componente", allocationSize = 1, initialValue = 1)
    @Column(name = "componente_id")
    private Integer componenteID;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receta_id", nullable = false, foreignKey = @ForeignKey(name = "fk_componentes_recetas"))
    private Receta receta;

    @NotBlank
    @Column(name = "orden", nullable = false)
    private Integer orden;

    @NotBlank
    @Column(name = "proceso", length = 100, nullable = false)
    private String proceso;

    @NotBlank
    @Column(name = "nombre_componente", length = 100, nullable = false)
    private String nombreComponente;

    @NotBlank
    @Column(name = "cantidad_teorica", nullable = false)
    private Integer cantidadTeorica;

    @NotBlank
    @Column(name = "unidad_medida", length = 10, nullable = false)
    private String unidadmedida;

    public Integer getComponenteID() {
        return componenteID;
    }

    public void setComponenteID(Integer componenteID) {
        this.componenteID = componenteID;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getNombreComponente() {
        return nombreComponente;
    }

    public void setNombreComponente(String nombreComponente) {
        this.nombreComponente = nombreComponente;
    }

    public Integer getCantidadTeorica() {
        return cantidadTeorica;
    }

    public void setCantidadTeorica(Integer cantidadTeorica) {
        this.cantidadTeorica = cantidadTeorica;
    }

    public String getUnidadmedida() {
        return unidadmedida;
    }

    public void setUnidadmedida(String unidadmedida) {
        this.unidadmedida = unidadmedida;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }
}
