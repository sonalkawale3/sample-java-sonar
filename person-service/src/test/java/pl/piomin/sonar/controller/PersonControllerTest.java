package pl.piomin.sonar.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import pl.piomin.sonar.model.Gender;
import pl.piomin.sonar.model.Person;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
public class PersonControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders createHeaders(String auth) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", auth);
        return headers;
    }

    @Test
    public void testFindAll() {
        HttpEntity<String> entity = new HttpEntity<>(createHeaders("YWRtaW46YWRtaW4="));
        ResponseEntity<Set> response = restTemplate.exchange(
                "/",
            HttpMethod.GET,
            entity,
            Set.class
        );
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().size() > 0);
    }

    @Test
    public void testFindById() {
        HttpEntity<String> entity = new HttpEntity<>(createHeaders("YWRtaW46YWRtaW4="));
        ResponseEntity<Person> response = restTemplate.exchange(
            "/person/1",
            HttpMethod.GET,
            entity,
            Person.class
        );
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
    }

    @Test
    public void testFindByName() {
        HttpEntity<String> entity = new HttpEntity<>(createHeaders("YWRtaW46YWRtaW4="));
        ResponseEntity<Set> response = restTemplate.exchange(
            "/person/name/Kalinowski/Piotr",
            HttpMethod.GET,
            entity,
            Set.class
        );
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().size() > 0);
    }

    @Test
    public void testFindByLastName() {
        HttpEntity<String> entity = new HttpEntity<>(createHeaders("YWRtaW46YWRtaW4="));
        ResponseEntity<Set> response = restTemplate.exchange(
            "/person/lastName/Kalinowski",
            HttpMethod.GET,
            entity,
            Set.class
        );
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().size() > 0);
    }

    @Test
    public void testAdd() {
        Person newPerson = new Person(null, "X", "X", new Date(), Gender.MALE);
        HttpEntity<Person> entity = new HttpEntity<>(newPerson, createHeaders("YWRtaW46YWRtaW4="));
        ResponseEntity<Person> response = restTemplate.exchange(
            "/person",
            HttpMethod.POST,
            entity,
            Person.class
        );
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody().getId());
    }

    @Test
    public void testUpdate() {
        Person updatePerson = new Person(1, "X", "X", new Date(), Gender.MALE);
        HttpEntity<Person> entity = new HttpEntity<>(updatePerson, createHeaders("YWRtaW46YWRtaW4="));
        ResponseEntity<Person> response = restTemplate.exchange(
            "/person",
            HttpMethod.PUT,
            entity,
            Person.class
        );
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void testRemove() {
        Person removePerson = new Person(2, null, null, null, null);
        HttpEntity<Person> entity = new HttpEntity<>(removePerson, createHeaders("YWRtaW46YWRtaW4="));
        ResponseEntity<Void> response = restTemplate.exchange(
            "/person",
            HttpMethod.DELETE,
            entity,
            Void.class
        );
        assertEquals(200, response.getStatusCode().value());
    }

}
