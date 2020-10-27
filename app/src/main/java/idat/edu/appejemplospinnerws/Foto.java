package idat.edu.appejemplospinnerws;

public class Foto {
    private int idFoto;
    private String tituloFoto;

    public Foto(int idFoto, String tituloFoto) {
        this.idFoto = idFoto;
        this.tituloFoto = tituloFoto;
    }

    public int getIdFoto() {
        return idFoto;
    }

    public String getTituloFoto() {
        return tituloFoto;
    }

    public String toString() {
        return tituloFoto;
    }
}
