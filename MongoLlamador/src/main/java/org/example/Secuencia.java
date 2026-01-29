package org.example;

import org.example.model.Actor;
import org.example.model.Pelicula;
import org.example.service.ConexionService;
import org.example.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Secuencia {

    private final ConexionService conexionSQL;

    private final PeliculaService peliculaService;

    @Autowired
    public Secuencia(ConexionService conexionSQL, PeliculaService peliculaService) {
        this.conexionSQL = conexionSQL;
        this.peliculaService = peliculaService;
    }

    public void executar() {

        // 1. LIMPIEZA INICIAL: Borra lo viejo para no duplicar
        //conexionSQL.eliminarTodasLasPeliculas();

        List<Actor> actores = new ArrayList<>();

        //falta terminar la secuencia
        //
        Actor act1 = new Actor();
        act1.setNome("Matthew");
        act1.setApelidos("McConaughey");
        act1.setNacionalidade("USA");

        Actor act2 = new Actor();
        act2.setNome("Anne");
        act2.setApelidos("Hathaway");
        act2.setNacionalidade("USA");

        Actor act3 = new Actor();
        act3.setNome("Jessica");
        act3.setApelidos("Chastain");
        act3.setNacionalidade("USA");

        actores.add(act1);
        actores.add(act2);
        actores.add(act3);

        Pelicula pelicula1 = new Pelicula();

        pelicula1.setTitulo("Interstellar");
        pelicula1.setXenero("Ciencia Ficción");
        pelicula1.setAno(2014);
        pelicula1.setActores(actores);

        List<Actor> actores2 = new ArrayList<>();

        Actor act4 = new Actor();
        act4.setNome("Keira");
        act4.setApelidos("Knightley");
        act4.setNacionalidade("UK");

        Actor act5 = new Actor();
        act5.setNome("Matthew");
        act5.setApelidos("Macfadyen");
        act5.setNacionalidade("UK");

        Actor act6 = new Actor();
        act6.setNome("Rosamund");
        act6.setApelidos("Pike");
        act6.setNacionalidade("UK");

        actores2.add(act4);
        actores2.add(act5);
        actores2.add(act6);

        Pelicula pelicula2 = new Pelicula();

        pelicula2.setTitulo("OrgulloyPrejuicio");
        pelicula2.setXenero("Romance");
        pelicula2.setAno(2005);
        pelicula2.setActores(actores2);

        System.out.println(">>> Guardando películas en PostgreSQL...");

        // ¡IMPORTANTE! Asignar el resultado a la variable para capturar el ID
        pelicula1 = conexionSQL.crearPelicula(pelicula1);
        System.out.println(">>> Película 1 creada con ID: " + (pelicula1 != null ? pelicula1.getId() : "NULL"));

        pelicula2 = conexionSQL.crearPelicula(pelicula2);
        System.out.println(">>> Película 2 creada con ID: " + (pelicula2 != null ? pelicula2.getId() : "NULL"));

        // 4. RECUPERAR DE SQL
        Pelicula p1Recuperada = conexionSQL.getPeliculaById(pelicula1.getId());
        System.out.println("Pelicula recuperada: " + p1Recuperada.getTitulo());

        // Recuperar por título
        List<Pelicula> p2Recuperada = conexionSQL.getPeliculaByTitulo("OrgulloyPrejuicio");
        System.out.println("Pelicula recuperada: " + p2Recuperada.get(0).getTitulo());


        // 5. GUARDAR EN MONGO
        System.out.println(">>> Guardando en MongoDB...");
        peliculaService.crearActualizarPeliculaConActores(p1Recuperada);
        peliculaService.crearActualizarPeliculaConActores(p2Recuperada.get(0));

        // 6. GUARDAR PELÍCULAS A UN JSON
        System.out.println(">>> Guardando películas desde JSON en MongoDB...");
        peliculaService.exportarPeliculasAJson();

    }
}
