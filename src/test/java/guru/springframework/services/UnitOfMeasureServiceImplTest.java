package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UnitOfMeasureServiceImplTest {

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureConverter;

    UnitOfMeasureServiceImpl serviceUnderTest;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        unitOfMeasureConverter = new UnitOfMeasureToUnitOfMeasureCommand();
        serviceUnderTest = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureConverter);
    }

    @Test
    public void findAllCommands() {
        //given
        Set<UnitOfMeasure> unitOfMeasureSet = new HashSet<>();
        UnitOfMeasure uof1 = new UnitOfMeasure();
        uof1.setId(1L);
        UnitOfMeasure uof2 = new UnitOfMeasure();
        uof1.setId(3L);
        unitOfMeasureSet.add(uof1);
        unitOfMeasureSet.add(uof2);

        //when
        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasureSet);

        Set<UnitOfMeasureCommand> foundCommandSet = serviceUnderTest.findUomCommands();

        //then
        assertEquals(2, foundCommandSet.size());
        verify(unitOfMeasureRepository).findAll();

    }
}