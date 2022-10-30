package com.icesi.edu.users.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icesi.edu.users.dto.LoginDTO;
import com.icesi.edu.users.dto.TokenDTO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@SpringBootTest
public class UsersLoginIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @SneakyThrows
    public void loginSuccessfully(){
        LoginDTO baseLogin = baseLogin();
        String body = objectMapper.writeValueAsString(baseLogin);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login").contentType(MediaType.APPLICATION_JSON).content(body)).andExpect(status().isOk()).andReturn();
        TokenDTO tokenResult = objectMapper.readValue(result.getResponse().getContentAsString(), TokenDTO.class);
        assertThat(tokenResult, notNullValue());
        assertThat(tokenResult, hasProperty("token"));
    }

    private LoginDTO baseLogin() {
        return LoginDTO.builder()
                        .email("admin@icesi.edu.co")
                        .password("@dm1N")
                        .build();
    }

}
