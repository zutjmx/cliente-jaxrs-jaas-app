package org.zutjmx.cliente.jaxrs.app.ejecucion;

import com.github.javafaker.Faker;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.client.jaxrs.internal.BasicAuthentication;
import org.zutjmx.cliente.jaxrs.app.models.Curso;
import org.zutjmx.cliente.jaxrs.app.models.Instructor;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        WebTarget rootUri = client
                .target("http://localhost:8080/webapp-jaxrs-jaas/api")
                .path("/cursos");

        //rootUri.register(new BasicAuthentication("jesus","Proxmox5@"));
        rootUri.register(new BasicAuthentication("admin","Proxmox5@"));
        listarCursos(rootUri);

        System.out.println(":: Crear nuevo curso ::");
        Curso cursoACrear = getCursoACrear();
        System.out.println("Curso a crear: " + cursoACrear);

        Entity<Curso> entityHeader = Entity
                .entity(cursoACrear,MediaType.APPLICATION_JSON);

        Curso cursoGuardado = rootUri
                .request(MediaType.APPLICATION_JSON)
                .post(entityHeader,Curso.class);
        System.out.println("Curso creado: " + cursoGuardado);

        System.out.println(":: Busqueda porId ::");

        Response response = rootUri
                .path("/9")
                .request(MediaType.APPLICATION_JSON)
                .get();
        Curso curso = response.readEntity(Curso.class);
        System.out.println(curso);
        System.out.println("Status: " + response.getStatus());
        System.out.println("MadiaType: " + response.getMediaType());

        listarCursos(rootUri);

        System.out.println(":: Editar ::");
        Curso cursoAEditar = cursoGuardado;
        Faker faker = new Faker();

        String nuevaDescripcion = faker.backToTheFuture().quote();
        if(nuevaDescripcion.length()>50) {
            cursoAEditar.setDescripcion(nuevaDescripcion.substring(0,49));
        } else {
            cursoAEditar.setDescripcion(nuevaDescripcion);
        }
        entityHeader = Entity
                .entity(cursoAEditar,MediaType.APPLICATION_JSON);
        cursoGuardado = rootUri
                .path("/"+cursoGuardado.getId())
                .request(MediaType.APPLICATION_JSON)
                .put(entityHeader,Curso.class);
        System.out.println("Curso editado: " + cursoGuardado);

        listarCursos(rootUri);

        /*System.out.println(":: Eliminar último curso creado y editado => " + cursoGuardado + " ::");
        rootUri
                .path("/" + cursoGuardado.getId())
                .request()
                .delete();
        listarCursos(rootUri);*/
    }

    private static void listarCursos(WebTarget rootUri) {
        System.out.println(":: Lista Actualizada ::");
        Response response;
        /*List<Curso> cursos = rootUri
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class)
                .readEntity(new GenericType<List<Curso>>(){});*/
        response = rootUri
                .request(MediaType.APPLICATION_JSON)
                .get();

        List<Curso> cursos = response
                .readEntity(new GenericType<List<Curso>>(){});

        cursos.forEach(System.out::println);
    }

    private static Curso getCursoACrear() {
        Faker faker = new Faker();
        Curso cursoACrear = new Curso();

        String descripcion = faker.dune().quote();
        if(descripcion.length()>50) {
            cursoACrear.setDescripcion(descripcion.substring(0,49));
        } else {
            cursoACrear.setDescripcion(descripcion);
        }

        String nombreCurso = faker.book().title();
        if(nombreCurso.length()>50) {
            cursoACrear.setNombre(nombreCurso.substring(0,49));
        } else {
            cursoACrear.setNombre(nombreCurso);
        }

        /*String instructor = faker.book().author();
        if(instructor.length()>50) {
            cursoACrear.setInstructor(instructor.substring(0,49));
        } else {
            cursoACrear.setInstructor(instructor);
        }*/
        Instructor instructor = new Instructor();
        instructor.setId(5L);
        cursoACrear.setInstructor(instructor);

        Double duracion = faker.number().randomDouble(0,20,100);
        cursoACrear.setDuracion(duracion);
        return cursoACrear;
    }
}
