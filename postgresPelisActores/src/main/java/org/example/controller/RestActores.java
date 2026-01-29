package org.example.controller;


import org.example.model.Actor;
import org.example.model.Pelicula;
import org.example.service.ActorService;
import org.example.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RestActores.MAPPING)
public class RestActores {

    public static final String MAPPING = "/postgres/actores";

    @Autowired
    private ActorService actorService;

    @Autowired
    private PeliculaService peliculaService;

    @GetMapping
    public List<Actor> getAll() {
        return actorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actor> getById(@PathVariable Long id) {
        return actorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Actor> create(@RequestBody Actor actor) {
        //Ejemplo de JSON para crear un actor
        /*{
          "nome": "string",
          "apelidos": "string",
          "nacionalidade": "string",
          "pelicula": {
            "id": 1
          }
        }*/

        //Realizamos esta comprobación para asegurarnos de que la pelicula asociada existe
        // en caso contrario devolvemos un bad request
        // si la pelicula por cualquier casuistica no existiera y no hacemos esta comprobación
        // se lanzaría una excepción al intentar guardar el actor con una pelicula inexistente
        // trataría de hacer la pelicula en cascada y fallaría porque no terminó esta transacción antes
        if (actor.getPelicula() != null && actor.getPelicula().getId() != null) {
            Pelicula peli = peliculaService.findById(actor.getPelicula().getId())
                    .orElse(null);
            if (peli == null) {
                return ResponseEntity.badRequest().build();
            }
            actor.setPelicula(peli);
        }

        Actor guardado = actorService.save(actor);
        return ResponseEntity.ok(guardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actor> update(@PathVariable Long id,
                                        @RequestBody Actor datos) {
        return actorService.findById(id)
                .map(a -> {
                    a.setNome(datos.getNome());
                    a.setApelidos(datos.getApelidos());
                    a.setNacionalidade(datos.getNacionalidade());

                    if (datos.getPelicula() != null && datos.getPelicula().getId() != null) {
                        Pelicula peli = peliculaService.findById(datos.getPelicula().getId())
                                .orElse(null);
                        a.setPelicula(peli);
                    }
                    return ResponseEntity.ok(actorService.save(a));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!actorService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        actorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        actorService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
