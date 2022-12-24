package com.api.rest.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.api.rest.model.Empleado;
import com.api.rest.service.EmpleadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest
public class EmpleadoControllerTests {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private EmpleadoService empleadoService;

        @Autowired
        private ObjectMapper objectMapper;

        @Test
        void testGuardarEmpleado() throws Exception {
                //given
                Empleado empleado = Empleado.builder()
                        .id(1L)
                        .nombre("JhonE")
                        .apellido("Vargas")
                        .email("jhon@gmail.com")
                        .build();
                given(empleadoService.saveEmpleado(any(Empleado.class)))
                        .willAnswer((invocation) -> invocation.getArgument(0));

                //when
                ResultActions response = mockMvc.perform(post("/api/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(empleado)));

                //then
                response.andDo(print())
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.nombre",is(empleado.getNombre())))
                        .andExpect(jsonPath("$.apellido",is(empleado.getApellido())))
                        .andExpect(jsonPath("$.email",is(empleado.getEmail())));

        }

        @Test
        void testListarEmpleados(){
                //given
                //when
                //then
        }



}
