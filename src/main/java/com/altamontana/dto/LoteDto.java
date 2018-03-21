package com.altamontana.dto;

public class LoteDto {

    private Integer numeroProceso;
    private String nombre;

    public LoteDto(Integer numeroProceso, String nombre) {
        this.numeroProceso = numeroProceso;
        this.nombre = nombre;
    }

    public Integer getNumeroProceso() {
        return numeroProceso;
    }

    public void setNumeroProceso(Integer numeroProceso) {
        this.numeroProceso = numeroProceso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
