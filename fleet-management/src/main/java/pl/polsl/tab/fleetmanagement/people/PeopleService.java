package pl.polsl.tab.fleetmanagement.people;

import org.springframework.stereotype.Service;
import pl.polsl.tab.fleetmanagement.vehicle.VehicleDTO;
import pl.polsl.tab.fleetmanagement.vehicle.VehiclesEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class PeopleService {

    private PeopleRepository peopleRepository;

    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<PeopleDTO> getAllPeople() {
        List<PeopleEntity> peopleEntities = new ArrayList<>();
        List<PeopleDTO> peopleDTOs = new ArrayList<>();

        peopleRepository.findAll().forEach(peopleEntities::add);

        for (PeopleEntity peopleEntity : peopleEntities) {
            peopleDTOs.add(new PeopleDTO(peopleEntity.getId(), peopleEntity.getFirstname(), peopleEntity.getLastname(),
                    peopleEntity.getPhonenumber(), peopleEntity.getFunctionsByFunctionsId(), peopleEntity.getKeepingsById()));
        }

        return peopleDTOs;
    }
}
