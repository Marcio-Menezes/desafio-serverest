package org.dbserver;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.dbserver.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class ServeRestTest {

    private static final String BASE_URL = "https://serverest.dev";

    private ServeRestStub serveRestStub = new ServeRestStub();

    private String idUsuario;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @DisplayName("Teste verificando se o get usuarios lista os corretamente")
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
    void testCriaUsuario(){
        Usuario usuario = serveRestStub.postUsuario();
        Response response = given()
                .contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("/usuarios");
        assertEquals(201, response.getStatusCode());
        this.idUsuario = response.jsonPath().getString("_id");
        System.out.println(this.idUsuario);
    }

    @Test
    @DisplayName("Teste que verifica se um usuario com email já existente pode ser criado")
    void testCriaUsuarioInvalido(){
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
    void testBuscarUsuario(){
        Usuario usuario = serveRestStub.postUsuario();
        Response response = given()
                .when()
                .get("/usuarios/NHLvGXO9I2dLl8WT");
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
        assertEquals("NHLvGXO9I2dLl8WT", id);

    }

    @Test
    @DisplayName("Teste que verifica se um usuario pode ter sua senha editada")
    void testEditarUsuario(){
        Usuario usuario = serveRestStub.postUsuario();
        Response response = given()
                .when()
                .body(usuario)
                .put("/usuarios/NHLvGXO9I2dLl8WT");
        assertEquals(200, response.getStatusCode());

        String nome = response.jsonPath().getString("nome");
        String email = response.jsonPath().getString("email");
        String senha = response.jsonPath().getString("password");
        String admin = response.jsonPath().getString("administrador");

        assertEquals(usuario.getNome(), nome);
        assertEquals(usuario.getEmail(), email);
        assertEquals(usuario.getPassword(), senha);
        assertEquals(usuario.getAdministrador(), admin);

    }

    @Test
    @DisplayName("Teste que verifica se um usuario pode ser deletado")
    void testDeletarUsuario(){
        Response response = given()
                .when()
                .delete("/usuarios/NHLvGXO9I2dLl8WT");
        assertEquals(200, response.getStatusCode());

    }



}

