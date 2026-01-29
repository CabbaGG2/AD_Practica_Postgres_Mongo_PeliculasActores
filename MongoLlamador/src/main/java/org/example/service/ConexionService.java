package org.example.service;

import org.example.model.Actor;
import org.example.model.Pelicula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class ConexionService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String POSTGRES_BASE_URL_ACTORES = "http://localhost:8081/postgres/actores";
    private static final String POSTGRES_BASE_URL_PELICULAS = "http://localhost:8081/postgres/peliculas";

    // ========== ACTORES ==========

    public List<Actor> getAllActores() {
        try {
            String url = POSTGRES_BASE_URL_ACTORES;
            ResponseEntity<List<Actor>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Actor>>() {
                    }
            );
            return response.getBody() != null ? response.getBody() : Collections.emptyList();
        } catch (
                HttpClientErrorException e) {
            System.out.println("Error al obtener actores: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public Actor getActorById(Long id) {
        try {
            String url = POSTGRES_BASE_URL_ACTORES + "/" + id;
            ResponseEntity<Actor> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, Actor.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.out.println("Error al obtener actor " + id + ": " + e.getMessage());
            return null;
        }
    }

    public Actor crearActor(Actor actor) {
        try {
            String url = POSTGRES_BASE_URL_ACTORES;
            HttpHeaders cabecera = new HttpHeaders();
            cabecera.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Actor> request = new HttpEntity<>(actor, cabecera);

            ResponseEntity<Actor> response = restTemplate.exchange(
                    url, HttpMethod.POST, request, Actor.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.out.println("Error al crear actor: " + e.getMessage());
            return null;
        }
    }

    public Actor actualizarActor(Long id, Actor datos) {
        try {
            String url = POSTGRES_BASE_URL_ACTORES + "/" + id;
            HttpHeaders cabecera = new HttpHeaders();
            cabecera.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Actor> request = new HttpEntity<>(datos, cabecera);

            ResponseEntity<Actor> response = restTemplate.exchange(
                    url, HttpMethod.PUT, request, Actor.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.out.println("Error al actualizar actor " + id + ": " + e.getMessage());
            return null;
        }
    }

    public boolean eliminarActor(Long id) {
        try {
            String url = POSTGRES_BASE_URL_ACTORES + "/" + id;
            restTemplate.exchange(
                    url, HttpMethod.DELETE, null, Void.class);
            return true;
        } catch (HttpClientErrorException e) {
            System.out.println("Error al eliminar actor " + id + ": " + e.getMessage());
            return false;
        }
    }

    // ========== PELICULAS ==========

    public List<Pelicula> getAllPeliculas() {
        try {
            String url = POSTGRES_BASE_URL_PELICULAS;
            ResponseEntity<List<Pelicula>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null,
                    new ParameterizedTypeReference<List<Pelicula>>() {
                    }
            );
            return response.getBody() != null ? response.getBody() : Collections.emptyList();
        } catch (
                HttpClientErrorException e) {
            System.out.println("Error al obtener peliculas: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public Pelicula getPeliculaById(Long id) {
        try {
            String url = POSTGRES_BASE_URL_PELICULAS + "/" + id;
            ResponseEntity<Pelicula> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, Pelicula.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.out.println("Error al obtener pelicula " + id + ": " + e.getMessage());
            return null;
        }
    }

    public List<Pelicula> getPeliculaByTitulo(String titulo) {
        try {
            String url = POSTGRES_BASE_URL_PELICULAS + "/titulo/" + titulo;
            ResponseEntity<List<Pelicula>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Pelicula>>() {}
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.out.println("Error al obtener pelicula con titulo " + titulo + ": " + e.getMessage());
            return null;
        }
    }

    public Pelicula crearPelicula(Pelicula pelicula) {
        try {
            String url = POSTGRES_BASE_URL_PELICULAS;
            HttpHeaders cabecera = new HttpHeaders();
            cabecera.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Pelicula> request = new HttpEntity<>(pelicula, cabecera);

            ResponseEntity<Pelicula> response = restTemplate.exchange(
                    url, HttpMethod.POST, request, Pelicula.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.out.println("Error al crear pelicula: " + e.getMessage());
            return null;
        }
    }

    public Pelicula actualizarPelicula(Long id, Pelicula pelicula) {
        try{
            String url = POSTGRES_BASE_URL_PELICULAS + "/" + id;
            HttpHeaders cabecera = new HttpHeaders();
            cabecera.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Pelicula> request = new HttpEntity<>(pelicula, cabecera);

            ResponseEntity<Pelicula> response = restTemplate.exchange(
                    url, HttpMethod.PUT, request, Pelicula.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.out.println("Error al actualizar pelicula " + id + ": " + e.getMessage());
            return null;
        }
    }

    public boolean eliminarPelicula(Long id) {
        try {
            String url = POSTGRES_BASE_URL_PELICULAS + "/" + id;
            restTemplate.exchange(
                    url, HttpMethod.DELETE, null, Void.class);
            return true;
        } catch (HttpClientErrorException e) {
            System.out.println("Error al eliminar pelicula " + id + ": " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarTodasLasPeliculas() {
        try {
            String url = POSTGRES_BASE_URL_PELICULAS;
            restTemplate.exchange(
                    url, HttpMethod.DELETE, null, Void.class);
            return true;
        } catch (HttpClientErrorException e) {
            System.out.println("Error al eliminar todas las peliculas: " + e.getMessage());
            return false;
        }
    }

}

