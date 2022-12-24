package com.api.rest.controller;


import com.api.rest.model.Empleado;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import  static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmpleadoControllerTestRestTemplate {

        @Autowired
        private TestRestTemplate testRestTemplate;

        @Test
        @Order(1)
        void testGuardarEmpleado(){
            Empleado empleado = Empleado.builder()
                    .id(1L)
                    .nombre("JhonE")
                    .apellido("Vargas")
                    .email("jhon@gmail.com")
                    .build();
            ResponseEntity<Empleado> respuesta = testRestTemplate.postForEntity("http://localhost:8080/api/empleados",empleado,Empleado.class);
            assertEquals(HttpStatus.CREATED,respuesta.getStatusCode());
            assertEquals(MediaType.APPLICATION_JSON,respuesta.getHeaders().getContentType());

            Empleado empleadoCreado = respuesta.getBody();
            assertNotNull(empleadoCreado);

            assertEquals(1L, empleadoCreado.getId());
            assertEquals("JhonE",empleadoCreado.getNombre());
            assertEquals("Vargas",empleadoCreado.getApellido());
            assertEquals("jhon@gmail.com",empleadoCreado.getEmail());
        }

        @Test
        @Order(2)
        void testListarEmpleados(){
            ResponseEntity<Empleado[]> respuesta = testRestTemplate.getForEntity("http://localhost:8080/api/empleados",Empleado[].class);
            List<Empleado> empleados = Arrays.asList(respuesta.getBody());

            assertEquals(HttpStatus.OK,respuesta.getStatusCode());
            assertEquals(MediaType.APPLICATION_JSON,respuesta.getHeaders().getContentType());

            assertEquals(1,empleados.size());
            assertEquals(1L,empleados.get(0).getId());
            assertEquals("JhonE",empleados.get(0).getNombre());
            assertEquals("Vargas",empleados.get(0).getApellido());
            assertEquals("jhon@gmail.com",empleados.get(0).getEmail());

        }

}
