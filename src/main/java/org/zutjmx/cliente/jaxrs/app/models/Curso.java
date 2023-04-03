package org.zutjmx.cliente.jaxrs.app.models;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Curso {
    private Long id;

    private String nombre;

    private String descripcion;

    private Instructor instructor;

    private Double duracion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Double getDuracion() {
        return duracion;
    }

    public void setDuracion(Double duracion) {
        this.duracion = duracion;
    }

    @Override
    public String toString() {
        return "Curso {" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", instructor='" + instructor + '\'' +
                ", duracion=" + duracion +
                '}';
    }
}
