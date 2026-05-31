package es.upm.cdelaof26.pmmicro.endpoints;

import es.upm.cdelaof26.pmmicro.model.Provider;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

/**
 * Provider endpoint tests
 * @author cristopher
 */
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProviderEndpointTest {
    @LocalServerPort
    private int port;

    private Response providerPost(Object b) {
        return RestAssured.given().baseUri("http://localhost").port(port)
            .contentType("application/json").body(b)
            .when()
                .post("/api/v1/providers");
    }
    
    @Test
    @DisplayName("I. Crea un proveedor valido")
    void createValidProvider() {
        Provider p = new Provider(null, "Juan", "Madrid Cars", "email0@provider.com", "0192384765", "Madrid", "EUR");
        
        providerPost(p).then().statusCode(201);
    }
    
    @Test
    @DisplayName("II. Crea un proveedor valido e intenta registrar otro con mismo email")
    void createProviderWithTakenEmail() {
        Provider p = new Provider(null, "Juan", "Madrid Cars", "email1@provider.com", "1192384765", "Madrid", "EUR");
        
        providerPost(p).then().statusCode(201);
        
        Provider p1 = new Provider(null, "Juan", "Madrid Cars", "email1@provider.com", "2192384765", "Madrid", "EUR");
        
        providerPost(p1).then().statusCode(409);
    }
    
    @Test
    @DisplayName("III. Crea un proveedor con nombre en null")
    void createProviderWithNullName() {
        Provider p = new Provider(null, null, "Madrid Cars", "email2@provider.com", "3192384765", "Madrid", "EUR");
        
        providerPost(p).then().statusCode(400);
    }
    
    @Test
    @DisplayName("IV. Crea un proveedor valido e intenta registrar otro con mismo número telefónico")
    void createProviderWithTakenPhoneNumber() {
        Provider p = new Provider(null, "Juan", "Madrid Cars", "email3@provider.com", "4192384765", "Madrid", "EUR");
        
        providerPost(p).then().statusCode(201);
        
        Provider p1 = new Provider(null, "Juan", "Madrid Cars", "email5@provider.com", "4192384765", "Madrid", "EUR");
        
        providerPost(p1).then().statusCode(409);
    }
}
