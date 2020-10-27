package idat.edu.appejemplospinnerws;

public class Album {
    private int idAlbum;
    private String tituloAlbum;

    public Album(int idAlbum, String tituloAlbum) {
        this.idAlbum = idAlbum;
        this.tituloAlbum = tituloAlbum;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public String getTituloAlbum() {
        return tituloAlbum;
    }

    public String toString() {
        return tituloAlbum;
    }
}

