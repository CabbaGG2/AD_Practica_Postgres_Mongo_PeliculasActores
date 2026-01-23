package org.example.service;

import org.example.model.Actor;
import org.example.repository.ActorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService {

    //o pones constructor o el decorador @Autowired que se ocupa de inyectar dependencias y construir o obxecto
    //como el constructor que creamos abajo pero de forma automática
    private final ActorRepository actorRepo;

    public ActorService(ActorRepository actorRepo) {
        this.actorRepo = actorRepo;
    }

    public List<Actor> findAll() {
        return actorRepo.findAll();
    }

    //el envoltorio Optional se utiliza para evitar NullPointerException
    //y tratar los casos en los que el objeto puede no existir de forma elegante
    public Optional<Actor> findById(Long id) {
        return actorRepo.findById(id);
    }

    public Actor save(Actor actor) {
        return actorRepo.save(actor);
    }

    public boolean existsById(Long id) {
        return actorRepo.existsById(id);
    }

    public void deleteById(Long id) {
        actorRepo.deleteById(id);
    }

}
