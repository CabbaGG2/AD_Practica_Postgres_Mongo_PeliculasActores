package org.example;

import org.example.model.Actor;
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
        List<Actor> actores = new ArrayList<>();

        //falta terminar la secuencia
        //
        //ctor act1 = new Actor(null, "Actor Uno", 35, "Comedia");
    }
}
