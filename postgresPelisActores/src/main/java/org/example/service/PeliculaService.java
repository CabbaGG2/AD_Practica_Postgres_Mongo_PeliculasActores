package org.example.service;

import org.example.model.Pelicula;
import org.example.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {

    private final PeliculaRepository peliRepo;

    @Autowired
    public PeliculaService(PeliculaRepository peliRepo) {
        this.peliRepo = peliRepo;
    }

    public Pelicula save(Pelicula pelicula) {
        return peliRepo.save(pelicula);
    }

    public boolean existe(Long id) {
        return peliRepo.existsById(id);
    }

    public void delete(Long id) {
        peliRepo.deleteById(id);
    }

    public List<Pelicula> obtenerPeliculaTitulo(String titulo) {
        return peliRepo.findByTitulo(titulo);
    }

    public List<Pelicula> obtenerPeliculaXenero(String xenero) {
        return peliRepo.findByXenero(xenero);
    }

    //Optional es un envoltorio que se utiliza para saber si el objeto vino o no vino con una capa extra de información
    //evita o NullPointerException
    //se puede utilizar para devolver un valor que puede que no exista
    //y tratar esos casos de forma elegante
    //en lugar de devolver null, devolvemos un Optional vacío
    public Optional<Pelicula> findById(Long id) {
        return peliRepo.findById(id);
    }

    public List<Pelicula> obtenerTodasPeliculas() {
        return peliRepo.findAll();
    }
}
