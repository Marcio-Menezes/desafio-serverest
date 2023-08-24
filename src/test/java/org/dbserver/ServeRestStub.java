package org.dbserver;


import org.dbserver.model.Usuario;

public class ServeRestStub {

    private String idUsuario;

    public Usuario postUsuario() {
        Usuario N = new Usuario(
                "Natural Harmonia Gropius",
                "n@teamplasma.com",
                "ReshiramMelhorGalinhaDeFogo123",
                "true");
        return N;
    }

    public Usuario putUsuario() {
        Usuario N = new Usuario(
                "Natural Harmonia Gropius",
                "nchampeon@teamplasma.com",
                "ReshiramMelhorGalinhaDeFogo123",
                "true");
        return N;
    }

    public String setIdUsuario(String idUsuario) {
        return this.idUsuario = idUsuario;
    }

    public String getIdUsuario() {
        return this.idUsuario;
    }


}
