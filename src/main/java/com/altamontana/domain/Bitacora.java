package com.altamontana.domain;

import com.altamontana.common.json.CustomDateTimeDeserializer;
import com.altamontana.common.json.CustomDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "bitacora",
        indexes = {@Index(name = "bitacora_pk", columnList = "bitacora_id", unique = true),
                   @Index(name = "receta_idx", columnList = "receta_id"),
                   @Index(name = "componente_idx", columnList = "componente_id")})
public class Bitacora {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_bitacora_id")
    @SequenceGenerator(name = "sec_bitacora_id", sequenceName = "sec_bitacora", allocationSize = 1, initialValue = 1)
    @Column(name = "bitacora_id")
    private Long bitacoraID;

    @Column(name = "numero_proceso", nullable = false)
    private Integer numeroProceso;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receta_id", nullable = false, foreignKey = @ForeignKey(name = "fk_bitacora_recetas"))
    private Receta receta;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "fecha_hora", nullable = true)
    private Date fechaHora;

    @NotNull
    @Column(name = "orden", nullable = false)
    private Integer orden;

    @NotBlank
    @Column(name = "proceso", length = 100, nullable = false)
    private String proceso;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "componente_id", nullable = false, foreignKey = @ForeignKey(name = "fk_bitacora_componentes"))
    private Componente componente;

    @Column(name = "cantidad_teorica", nullable = false)
    private Double cantidadTeorica;

    @Column(name = "cantidad_real", nullable = true)
    private Double cantidadReal;

    @NotBlank
    @Column(name = "unidad_medida", length = 10, nullable = false)
    private String unidadmedida;

    @Column(name = "observacion", length = 400, nullable = true)
    private String observacion;

    public Long getBitacoraID() {
        return bitacoraID;
    }

    public void setBitacoraID(Long bitacoraID) {
        this.bitacoraID = bitacoraID;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public Componente getComponente() {
        return componente;
    }

    public void setComponente(Componente componente) {
        this.componente = componente;
    }

    public Double getCantidadTeorica() {
        return cantidadTeorica;
    }

    public void setCantidadTeorica(Double cantidadTeorica) {
        this.cantidadTeorica = cantidadTeorica;
    }

    public Double getCantidadReal() {
        return cantidadReal;
    }

    public void setCantidadReal(Double cantidadReal) {
        this.cantidadReal = cantidadReal;
    }

    public String getUnidadmedida() {
        return unidadmedida;
    }

    public void setUnidadmedida(String unidadmedida) {
        this.unidadmedida = unidadmedida;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Integer getNumeroProceso() {
        return numeroProceso;
    }

    public void setNumeroProceso(Integer numeroProceso) {
        this.numeroProceso = numeroProceso;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }
}
