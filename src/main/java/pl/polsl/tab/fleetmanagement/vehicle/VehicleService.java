package pl.polsl.tab.fleetmanagement.vehicle;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.tab.fleetmanagement.brandmodel.BrandModelEntity;
import pl.polsl.tab.fleetmanagement.brandmodel.BrandModelRepository;
import pl.polsl.tab.fleetmanagement.exceptions.IdNotFoundInDatabaseException;
import pl.polsl.tab.fleetmanagement.exceptions.ItemExistsInDatabaseException;
import pl.polsl.tab.fleetmanagement.keeping.KeepingDTO;
import pl.polsl.tab.fleetmanagement.keeping.KeepingEntity;
import pl.polsl.tab.fleetmanagement.keeping.KeepingRepository;
import pl.polsl.tab.fleetmanagement.person.PersonEntity;
import pl.polsl.tab.fleetmanagement.person.PersonRepository;
import pl.polsl.tab.fleetmanagement.vehiclepurpose.PurposeEntity;
import pl.polsl.tab.fleetmanagement.vehiclepurpose.PurposeRepository;
import pl.polsl.tab.fleetmanagement.vehicletype.TypeEntity;
import pl.polsl.tab.fleetmanagement.vehicletype.TypeRepository;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;
    private TypeRepository typeRepository;
    private PurposeRepository purposeRepository;
    private BrandModelRepository brandModelRepository;
    private PersonRepository personRepository;
    private KeepingRepository keepingRepository;

    public VehicleService(VehicleRepository vehicleRepository, TypeRepository typeRepository,
                          PurposeRepository purposeRepository, BrandModelRepository brandModelRepository,
                          PersonRepository personRepository, KeepingRepository keepingRepository) {
        this.vehicleRepository = vehicleRepository;
        this.typeRepository = typeRepository;
        this.purposeRepository = purposeRepository;
        this.brandModelRepository = brandModelRepository;
        this.personRepository = personRepository;
        this.keepingRepository = keepingRepository;
    }

    public List<VehicleDTO> getAllVehicles() {
        List<VehicleEntity> vehicleEntities = new ArrayList<>();
        List<VehicleDTO> vehicleDTOs = new ArrayList<>();

        vehicleRepository.findAll().forEach(vehicleEntities::add);

        for (VehicleEntity vehicleEntity : vehicleEntities) {
            vehicleDTOs.add(new VehicleDTO(vehicleEntity.getId(), vehicleEntity.getVin(), vehicleEntity.getPlates(), vehicleEntity.getEquipmentLevel(),
                    vehicleEntity.getMileage(), vehicleEntity.getAvgFuelConsumption(), vehicleEntity.getTypesByTypesId(),
                    vehicleEntity.getPurposesByPurposesId(), vehicleEntity.getBrandsModelsByBrandsModelsId()));
        }

        return vehicleDTOs;
    }

    public VehicleDTO getVehicle(Long id) {
        VehicleEntity vehicleEntity = vehicleRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Vehicle of id " + id + " not found"));

        return new VehicleDTO(vehicleEntity);
    }

    public VehicleDTO addVehicle(VehicleDTORequest vehicleDTO) {
        TypeEntity typeEntity = typeRepository.findById(vehicleDTO.getTypeId())
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Vehicle type of id " + vehicleDTO.getTypeId() + " not found"));

        PurposeEntity purposeEntity = purposeRepository.findById(vehicleDTO.getPurposeId())
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Vehicle purpose of id " + vehicleDTO.getPurposeId() + " not found"));

        BrandModelEntity brandModelEntity = brandModelRepository.findById(vehicleDTO.getBrandModelId())
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Vehicle brand model of id " + vehicleDTO.getBrandModelId() + " not found"));

        try {
            VehicleEntity vehicleEntity = vehicleRepository.save(new VehicleEntity(vehicleDTO.getVin(), vehicleDTO.getPlates(),
                    vehicleDTO.getEquipmentLevel(), vehicleDTO.getMileage(), vehicleDTO.getAvgFuelConsumption(), brandModelEntity,
                    typeEntity, purposeEntity));

            return new VehicleDTO(vehicleEntity);
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new ItemExistsInDatabaseException("Vehicle VIN ( " + vehicleDTO.getVin() + ") or vehicle plates (" +
                             vehicleDTO.getPlates() + ") exists in DB");
                }
            }
            throw new RuntimeException(e);
        }
    }

    public VehicleDTO updateVehicle(Long id, VehicleDTORequest vehicleDTO) {
        Optional<VehicleEntity> vehicleEntity = vehicleRepository.findById(id);

        if(vehicleEntity.isEmpty())
            throw new IdNotFoundInDatabaseException("Vehicle of id " + id + " not found");

        TypeEntity typeEntity = typeRepository.findById(vehicleDTO.getTypeId())
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Vehicle type of id " + vehicleDTO.getTypeId() + " not found"));

        PurposeEntity purposeEntity = purposeRepository.findById(vehicleDTO.getPurposeId())
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Vehicle purpose of id " + vehicleDTO.getPurposeId() + " not found"));

        BrandModelEntity brandModelEntity = brandModelRepository.findById(vehicleDTO.getBrandModelId())
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Vehicle brand model of id " + vehicleDTO.getBrandModelId() + " not found"));

        try {
            vehicleEntity.get().setVin(vehicleDTO.getVin());
            vehicleEntity.get().setEquipmentLevel(vehicleDTO.getEquipmentLevel());
            vehicleEntity.get().setMileage(vehicleDTO.getMileage());
            vehicleEntity.get().setAvgFuelConsumption(vehicleDTO.getAvgFuelConsumption());
            vehicleEntity.get().setBrandsModelsId(vehicleDTO.getBrandModelId());
            vehicleEntity.get().setBrandsModelsByBrandsModelsId(brandModelEntity);
            vehicleEntity.get().setTypesId(vehicleDTO.getTypeId());
            vehicleEntity.get().setTypesByTypesId(typeEntity);
            vehicleEntity.get().setPurposesId(vehicleDTO.getPurposeId());
            vehicleEntity.get().setPurposesByPurposesId(purposeEntity);
            return new VehicleDTO(vehicleRepository.save(vehicleEntity.get()));
        } catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    throw new ItemExistsInDatabaseException("Vehicle VIN ( " + vehicleDTO.getVin() + ") or vehicle plates (" +
                            vehicleDTO.getPlates() + ") exists in DB");
                }
            }
            throw new RuntimeException(e);
        }
    }

    public void deleteVehicle(Long id) {
        try {
            vehicleRepository.deleteById(id);
        } catch (IdNotFoundInDatabaseException e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    public Iterable<KeepingDTO> getVehiclesKeepings(Long id) {
        VehicleEntity vehicleEntity = vehicleRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Vehicle of id " + id + " not found"));

        List<KeepingDTO> keepingDTOs = new ArrayList<>();
        List<KeepingEntity> keepingEntities = new ArrayList<>();
        vehicleEntity.getKeepingsById().forEach(keepingEntities::add);

        for (KeepingEntity keepingEntity : keepingEntities) {
            keepingDTOs.add(new KeepingDTO(keepingEntity));
        }

        return keepingDTOs;
    }

    public void changeVehiclesKeeper(Long id, Long personId) {
        PersonEntity personEntity = personRepository.findById(personId)
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Person of id " + personId + " not found"));

        VehicleEntity vehicleEntity = vehicleRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundInDatabaseException("Vehicle of id " + id + " not found"));

        for (KeepingEntity keepingEntity : vehicleEntity.getKeepingsById()) {
            if (keepingEntity.getEnddate() == null) {
                keepingEntity.setEnddate(new Date(System.currentTimeMillis()));
            }
        }

        KeepingEntity keepingEntity = new KeepingEntity(new Date(System.currentTimeMillis()), null, personId,
                personEntity, id, vehicleEntity);

        keepingRepository.save(keepingEntity);
    }
}
