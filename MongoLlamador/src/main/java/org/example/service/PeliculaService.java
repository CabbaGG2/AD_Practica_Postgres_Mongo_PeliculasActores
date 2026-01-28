package org.example.service;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.model.Actor;
import org.example.model.Pelicula;
import org.example.repository.ActorRepository;
import org.example.repository.PeliculaRepository;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class PeliculaService {

    private final PeliculaRepository peliRepo;
    private final ActorRepository actorRepo;

    public PeliculaService(PeliculaRepository peliRepo, ActorRepository actorRepo) {
        this.peliRepo = peliRepo;
        this.actorRepo = actorRepo;
    }

    public void crearActualizarPeliculaConActores(Pelicula pelicula) {
        peliRepo.save(pelicula);
    }

    public void eliminarPeliculas() {
        peliRepo.deleteAll();
    }

    public void eliminarPeliculaPorId(Long id) {
        peliRepo.deleteById(id);
    }

    public Pelicula obtenerPeliculaPorId(Long id) {
        return peliRepo.findById(id).orElse(null);
    }

    public List<Pelicula> obtenerTodasLasPeliculas() {
        return peliRepo.findAll();
    }

    //Este metodo separa los actores de la pelicula y lo mete en un objeto actor
    public List<Actor> buscarActoresDePelicula(Long peliculaId) {
        Pelicula pelicula = peliRepo.findById(peliculaId).orElse(null);
        if (pelicula != null) {
            return pelicula.getActores();
        }
        return List.of();
    }

    //Este metodo busca un archivo JSON y con peliculas y lo sube a la base de datos Mongo

    public List<Pelicula> guardarPeliculaDesdeJson() {
        Gson gson = new Gson();
        try{
            FileReader reader = new FileReader("src/main/java/org/example/json/peliculas.json");
            Type peliculaListType = new TypeToken<List<Pelicula>>() {}.getType();
            List<Pelicula> peliculas = gson.fromJson(reader, peliculaListType);
            for (Pelicula pelicula : peliculas) {
                peliRepo.save(pelicula);
            }
            return peliculas;
        } catch (Exception e) {
            System.out.println("Error al leer el archivo JSON: " + e.getMessage());
            return List.of();
        }
    }

    //Metodo para escribir en un archivo JSON las peliculas de la base de datos MongoDB

    public void exportarPeliculasAJson() {
        Gson gson = new Gson();
        List<Pelicula> peliculas = obtenerTodasLasPeliculas();
        try{
            FileWriter writer = new FileWriter("src/main/java/org/example/json/peliculas_exportadas.json");
            gson.toJson(peliculas, writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.out.println("Error al escribir el archivo JSON: " + e.getMessage());
        }
    }
}
