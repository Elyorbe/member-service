package me.elyor.memberservice;

import me.elyor.memberservice.member.MemberDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.annotation.PostConstruct;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberServiceApplicationIntegrationTests {

    @LocalServerPort
    private int port;

    private String baseUrl;

    private String healthPath = "/actuator/health";

    @PostConstruct
    public void initialize() {
        this.baseUrl = "http://localhost:" + port;
    }

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    void whenRequestHealthEndpointThenReturn200() {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = testRestTemplate
                .getForEntity(baseUrl + healthPath, Map.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void whenSignupAndRetrieveByIdThenExpectCorrectResonse() {
        MemberDto.SignupRequest request = MemberDto.SignupRequest.builder()
                .username("elyorbek").password("VNeTkd!#24")
                .email("elyor@example.com").phoneNumber("01044335566")
                .build();

        ResponseEntity<Map> entity = testRestTemplate
                .postForEntity(baseUrl + "/api/v1/members/signup", request, Map.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isNotNull();

        Integer id =(Integer)entity.getBody().get("id");
        then(id).isNotNull();

        //Check returned response is the same as creation
        entity = testRestTemplate.getForEntity(baseUrl + "/api/v1/members/" + id, Map.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Map body = entity.getBody();
        then(body).isNotNull();
        String username = (String) body.get("username");
        String email = (String) body.get("email");
        String phoneNumber = (String) body.get("phoneNumber");
        then(username).isEqualTo(request.username);
        then(email).isEqualTo(request.email);
        then(phoneNumber).isEqualTo(request.phoneNumber);

    }

}
