package org.example.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.model.Actor;
import org.example.repository.ActorRepository;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class ActorService {

    private final ActorRepository actorRepo;

    public ActorService(ActorRepository actorRepo) {
        this.actorRepo = actorRepo;
    }

    public void crearActualizarActor(Actor actor) {
        actorRepo.save(actor);
    }

    public void eliminarActores(Long id) {
        actorRepo.deleteById(id);
    }

    public void eliminarTodosActores() {
        actorRepo.deleteAll();
    }

    public Actor buscarActorPorId(Long id) {
        return actorRepo.findById(id).orElse(null);
    }

    public List<Actor> buscarTodosActores() {
        return actorRepo.findAll();
    }

    public List<Actor> subirActoresDesdeJson() {
        Gson gson = new Gson();
        try {
            FileReader reader = new FileReader("actors.json");
            Type actorTipoLista = new TypeToken<List<Actor>>() {}.getType();
            List<Actor> actores = gson.fromJson(reader, actorTipoLista);

            for (Actor actor : actores) {
                actorRepo.save(actor);
            }
            return actores;
        } catch (FileNotFoundException e) {
            System.out.println("Fichero no encontrado: " + e.getMessage());
            return null;
        }
    }
}
