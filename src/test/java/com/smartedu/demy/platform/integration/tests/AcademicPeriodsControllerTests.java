package com.smartedu.demy.platform.integration.tests;

import com.smartedu.demy.platform.enrollment.interfaces.rest.resources.AcademicPeriodResource;
import com.smartedu.demy.platform.enrollment.interfaces.rest.resources.CreateAcademicPeriodResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AcademicPeriodsControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAllAcademicPeriods_WhenPeriodsExist_ShouldReturnListOfPeriods() {
        // Arrange
        String baseUrl = "http://localhost:" + port + "/api/v1/academic-periods";

        // Crear algunos academic periods de prueba
        CreateAcademicPeriodResource period1 = new CreateAcademicPeriodResource(
                "VERANO",
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 7, 31),
                true
        );

        CreateAcademicPeriodResource period2 = new CreateAcademicPeriodResource(
                "INVIERNO",
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2024, 12, 31),
                true
        );

        // Crear los academic periods
        restTemplate.postForEntity(baseUrl, period1, AcademicPeriodResource.class);
        restTemplate.postForEntity(baseUrl, period2, AcademicPeriodResource.class);

        // Act
        ResponseEntity<List<AcademicPeriodResource>> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AcademicPeriodResource>>() {}
        );


        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(2);

        List<AcademicPeriodResource> periods = response.getBody();
        assertThat(periods.get(0).periodName()).isEqualTo("VERANO");
        assertThat(periods.get(1).periodName()).isEqualTo("INVIERNO");
    }
}