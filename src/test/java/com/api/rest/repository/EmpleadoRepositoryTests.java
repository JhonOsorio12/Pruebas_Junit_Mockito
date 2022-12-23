package com.api.rest.repository;

import com.api.rest.model.Empleado;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmpleadoRepositoryTests {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    private Empleado empleado;

    @BeforeEach
    void setup(){
        empleado = Empleado.builder()
                .nombre("Jhon")
                .apellido("Osorio")
                .email("jhon@gmail.com")
                .build();
    }

    @DisplayName("Test para guardar un empleado")
    @Test
    void testGuardarEmpleado(){
        //given - dado o condicion previo o confoguración
        Empleado empleado1 = Empleado.builder()
                .nombre("Pepe")
                .apellido("Lopez")
                .email("p12@gmail.com")
                .build();

        //when - cuando - acción o el comportamiento que vamos a probar
        Empleado empleadoGuardado = empleadoRepository.save(empleado1);

        //then - entonces - verificar la salida
        assertThat(empleadoGuardado).isNotNull();
        assertThat(empleadoGuardado.getId()).isGreaterThan(0);
    }

    @DisplayName("Test para listar los empleados")
    @Test
    void testListarEmpleados(){
        //given
        Empleado empleado1 = Empleado.builder()
                .nombre("Julen")
                .apellido("Oliva")
                .email("j2@gmail.com")
                .build();

        empleadoRepository.save(empleado1);
        empleadoRepository.save(empleado);

        //when
        List<Empleado> listaEmpleados = empleadoRepository.findAll();

        //then
        assertThat(listaEmpleados).isNotNull();
        assertThat(listaEmpleados.size()).isEqualTo(2);
    }

    @DisplayName("Test para obtener un empleado por ID")
    @Test
    void testObtenerEmpleadoPorId(){
        empleadoRepository.save(empleado);

        //when - comportamiento o acción que vaya a probar
        Empleado empleadoBD = empleadoRepository.findById(empleado.getId()).get();

        //then
        assertThat(empleadoBD).isNotNull();

    }
    @DisplayName("Test para actualizar un empleado")
    @Test
    void actualizarEmpleado(){
        empleadoRepository.save(empleado);

        //when
        Empleado empleadoGuardado = empleadoRepository.findById(empleado.getId()).get();
        empleadoGuardado.setEmail("osorio@gmail.com");
        empleadoGuardado.setNombre("Edison");
        empleadoGuardado.setApellido("Vargas");
        Empleado empleadoActualizado = empleadoRepository.save(empleadoGuardado);

        //then
        assertThat(empleadoActualizado.getEmail()).isEqualTo("osorio@gmail.com");
        assertThat(empleadoActualizado.getNombre()).isEqualTo("Edison");
        assertThat(empleadoActualizado.getApellido()).isEqualTo("Vargas");

    }

    @DisplayName("Test para eliminar un empleado")
    @Test
    void testEliminarEmpleado(){
        empleadoRepository.save(empleado);

        //when
        empleadoRepository.deleteById(empleado.getId());
        Optional<Empleado> empleadoOptional = empleadoRepository.findById(empleado.getId());

        //then
        assertThat(empleadoOptional).isEmpty();
    }

}
