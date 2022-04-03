package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureConverter;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
                                    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureConverter) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureConverter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Override
    public Set<UnitOfMeasureCommand> findUomCommands() {

        Set<UnitOfMeasureCommand> unitOfMeasureCommands = new HashSet<>();
        for (UnitOfMeasure unitOfMeasure : unitOfMeasureRepository.findAll()) {
            unitOfMeasureCommands.add(unitOfMeasureConverter.convert(unitOfMeasure));
        }

        return unitOfMeasureCommands;
    }
}
