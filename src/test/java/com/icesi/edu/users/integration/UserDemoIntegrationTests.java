package com.icesi.edu.users.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icesi.edu.users.dto.UserDTO;
import com.icesi.edu.users.integration.config.InitialDataConfig;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.InputStreamReader;
import java.io.Reader;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = { "spring.datasource.url=jdbc:h2:mem:testdb" }
)
@ActiveProfiles("test")
public class UserDemoIntegrationTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    private ObjectMapper objectMapper;

    private static final String USER_UUID = "5631cbd3-cf53-415f-bd06-4e995ee3c322";


    @BeforeEach
    private void init() {
        objectMapper = new ObjectMapper();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @SneakyThrows
    public void createUserSuccessfully() {
        UserDTO baseUser = baseUser();
        baseUser.setEmail("invalidEmail@invalid");
        String body = objectMapper.writeValueAsString(baseUser);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)).andExpect(status().isOk())
                .andReturn();

        UserDTO userResult = objectMapper.readValue(result.getResponse().getContentAsString(), UserDTO.class);
     //   UserError userError = objectMapper.readValue(result.getResponse().getContentAsString(), UserError.class);
        assertThat(userResult, hasProperty("firstName", is("Juan")));

    }

    @Test
    @SneakyThrows
    public void getUserSuccessfully() {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users/" + USER_UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserDTO userResult = objectMapper.readValue(result.getResponse().getContentAsString(), UserDTO.class);

        assertThat(userResult, hasProperty("firstName", is("Juan")));

    }


    @SneakyThrows
    private UserDTO baseUser(){
        String body = parseResourceToString("createUser.json");
        return objectMapper.readValue(body, UserDTO.class);
    }
    @SneakyThrows
    private String parseResourceToString(String classPath) {
        Resource resource = new ClassPathResource(classPath);
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        }
    }


}
