package com.api.rest.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import com.api.rest.exception.ResourceNotFoundException;
import com.api.rest.model.Empleado;
import com.api.rest.repository.EmpleadoRepository;
import com.api.rest.service.Imple.EmpleadoServiceImple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.ResourceAccessException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmpleadoServiceTests {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoServiceImple empleadoService;

    private Empleado empleado;

    @BeforeEach
    void setup(){
        empleado = Empleado.builder()
                .id(1L)
                .nombre("JhonE")
                .apellido("Osorio")
                .email("jhon@gmail.com")
                .build();
    }

    @DisplayName("Test para guardar un empleado")
    @Test
    void testGuardarEmpleado(){

        //given
            given(empleadoRepository.findByEmail(empleado.getEmail()))
                    .willReturn(Optional.empty());
            given(empleadoRepository.save(empleado)).willReturn(empleado);

        //when
        Empleado empleadoGuardado = empleadoService.saveEmpleado(empleado);

        //then
        assertThat(empleadoGuardado).isNotNull();
    }

    @DisplayName("Test para guardar un empleado con ThrowException")
    @Test
    void testGuardarEmpleadoConThrowException(){

        //given
        given(empleadoRepository.findByEmail(empleado.getEmail()))
                .willReturn(Optional.of(empleado));

        //when
        assertThrows(ResourceNotFoundException.class,() ->{
            empleadoService.saveEmpleado(empleado);
        });

        //then
        verify(empleadoRepository,never()).save(any(Empleado.class));
    }


    @DisplayName("Test para listar empleados")
    @Test
    void testListarEmpleados(){

        //given
        Empleado empleado1 = Empleado.builder()
                .id(2L)
                .nombre("JhonE")
                .apellido("Osorio")
                .email("jhon@gmail.com")
                .build();
        given(empleadoRepository.findAll()).willReturn(List.of(empleado,empleado1));

        //when
        List<Empleado> empleados = empleadoService.getAllEmpleados();

        //then
        assertThat(empleados).isNotNull();
        assertThat(empleados.size()).isEqualTo(2);
    }

    @DisplayName("Test para listar una lista vacia")
    @Test
    void testListarColeccionEmpleadosVacia(){
        //given
        Empleado empleado1 = Empleado.builder()
                .id(2L)
                .nombre("JhonE")
                .apellido("Osorio")
                .email("jhon@gmail.com")
                .build();
        given(empleadoRepository.findAll()).willReturn(Collections.emptyList());

        //when
        List<Empleado> empleados = empleadoService.getAllEmpleados();

        //then
        assertThat(empleados).isEmpty();
        assertThat(empleados.size()).isEqualTo(0);
    }

    @DisplayName("Test para obtener empleado por ID")
    @Test
    void testObtenerEmpleadoPorID(){
        //given
        given(empleadoRepository.findById(1L)).willReturn(Optional.of(empleado));

        //when
        Empleado empleadoGuardado = empleadoService.getEmpleadoById(empleado.getId()).get();

        //then
        assertThat(empleadoGuardado).isNotNull();
    }

    @DisplayName("Test para actualizar empleado")
    @Test
    void testActualizarEmpleado(){
        //given
        given(empleadoRepository.save(empleado)).willReturn(empleado);
        empleado.setEmail("Osorio@gmail.com");
        empleado.setNombre("Juan J");

        //when
        Empleado empleadoActualizado = empleadoService.updateEmpleado(empleado);

        //then
        assertThat(empleadoActualizado.getEmail()).isEqualTo("Osorio@gmail.com");
        assertThat(empleadoActualizado.getNombre()).isEqualTo("Juan J");
    }

    @DisplayName("Test para eliminar un empleado")
    @Test
    void testEliminarEmpleado(){
        //given
        long empleadoId = 1L;
        willDoNothing().given(empleadoRepository).deleteById(empleadoId);

        //when
        empleadoService.deleteEmpleado(empleadoId);

        //then
        verify(empleadoRepository,times(1)).deleteById(empleadoId);
    }

}
