package com.api.rest.service;

import static org.mockito.BDDMockito.given;
import com.api.rest.repository.EmpleadoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceTests {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoService empleadoService;

    @DisplayName("Test para guardar un empleado")
    @Test
    void testGuardarEmpleado(){

        //given

        //when
        //then

    }

}
