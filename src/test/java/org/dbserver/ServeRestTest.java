package org.dbserver;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.dbserver.model.Usuario;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServeRestTest {

    private static final String BASE_URL = "https://serverest.dev";

    private ServeRestStub serveRestStub;

    @BeforeAll
    void setup() {
        RestAssured.baseURI = BASE_URL;
        serveRestStub = new ServeRestStub();
    }

    @Test
    @DisplayName("Teste verificando se o get usuarios lista os corretamente")
    @Order(3)
    void testListarUsuarios() {
        Response response = given()
                .when()
                .get("/usuarios");

        assertEquals(200, response.getStatusCode());

        String primeiroNome = response.jsonPath().getString("usuarios[0].nome");
        String primeiroEmail = response.jsonPath().getString("usuarios[0].email");
        String primeiroPassword = response.jsonPath().getString("usuarios[0].password");
        boolean primeiroAdministrador = response.jsonPath().getBoolean("usuarios[0].administrador");

        assertFalse(primeiroNome.isEmpty());
        assertFalse(primeiroEmail.isEmpty());
        assertFalse(primeiroPassword.isEmpty());
        assertTrue(primeiroAdministrador);

        assertTrue(primeiroEmail.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}"));
    }

    @Test
    @DisplayName("Teste que verifica se um novo usuario pode ser criado")
    @Order(1)
    void testCriaUsuario() {
        Usuario usuario = serveRestStub.postUsuario();
        Response response = given()
                .contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("/usuarios");
        assertEquals(201, response.getStatusCode());
        serveRestStub.setIdUsuario(response.jsonPath().getString("_id"));
        System.out.println(serveRestStub.getIdUsuario());
    }

    @Test
    @DisplayName("Teste que verifica se um usuario com email já existente pode ser criado")
    @Order(2)
    void testCriaUsuarioInvalido() {
        Usuario usuario = serveRestStub.postUsuario();
        Response response = given()
                .contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("/usuarios");
        assertEquals(400, response.getStatusCode());
    }

    @Test
    @DisplayName("Teste que verifica se um usuario pode ser buscado")
    @Order(4)
    void testBuscarUsuario() {
        System.out.println(serveRestStub.getIdUsuario());
        Usuario usuario = serveRestStub.postUsuario();
        String idUsuario = serveRestStub.getIdUsuario();
        Response response = given()
                .when()
                .get("/usuarios/" + idUsuario);
        assertEquals(200, response.getStatusCode());

        String nome = response.jsonPath().getString("nome");
        String email = response.jsonPath().getString("email");
        String senha = response.jsonPath().getString("password");
        String admin = response.jsonPath().getString("administrador");
        String id = response.jsonPath().getString("_id");

        assertEquals(usuario.getNome(), nome);
        assertEquals(usuario.getEmail(), email);
        assertEquals(usuario.getPassword(), senha);
        assertEquals(usuario.getAdministrador(), admin);
        assertEquals(idUsuario, id);

    }

    @Test
    @DisplayName("Teste que verifica se um usuario pode ter sua senha editada")
    @Order(5)
    void testEditarUsuario() {
        Usuario usuario = serveRestStub.postUsuario();
        String idUsuario = serveRestStub.getIdUsuario();
        Response response = given()
                .contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .put("/usuarios/" + idUsuario);
        assertEquals(200, response.getStatusCode());

        String mensagem = response.jsonPath().getString("message");
        assertEquals("Registro alterado com sucesso", mensagem);

    }

    @Test
    @DisplayName("Teste que verifica se um usuario pode ser deletado")
    @Order(6)
    void testDeletarUsuario() {
        String idUsuario = serveRestStub.getIdUsuario();
        Response response = given()
                .when()
                .delete("/usuarios/" + idUsuario);
        assertEquals(200, response.getStatusCode());

        String mensagem = response.jsonPath().getString("message");
        assertEquals("Registro excluído com sucesso", mensagem);

    }


}

