package org.example.repository;

import org.example.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeliculaRepository  extends JpaRepository<Pelicula, Long> {

    List<Pelicula> findByTitulo(String titulo);

    List<Pelicula> findByXenero(String xenero);
}
