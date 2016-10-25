package co.barnetapp.barnet;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jggomezt on 09/09/2016.
 */
public class Mensaje {

    private String autor;
    private String fecha;
    private String imagen;
    private String pregunta;

    public Mensaje() {

    }

    public Mensaje(String autor, String fecha, String imagen, String pregunta) {
        this.autor = autor;
        this.fecha = fecha;
        this.imagen = imagen;
        this.pregunta = pregunta;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    @Exclude
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("autor", autor);
        result.put("fecha", fecha);
        result.put("imagen", imagen);
        result.put("pregunta", pregunta);

        return result;
    }
}
