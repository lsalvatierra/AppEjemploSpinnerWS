package idat.edu.appejemplospinnerws;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {

    private RequestQueue colapeticiones;
    private Spinner spAlbum;
    private Spinner spFoto;
    ArrayList<Album> lstAlbum;
    ArrayList<Foto> lstFotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spAlbum = findViewById(R.id.spalbum);
        spFoto = findViewById(R.id.spfoto);
        spAlbum.setOnItemSelectedListener(this);
        lstAlbum = new ArrayList<>();
        lstFotos = new ArrayList<>();
        //Instanciar la cola de peticiones.
        colapeticiones = Volley.newRequestQueue(this);
        wsListarAlbum();
    }

    private void wsListarAlbum() {
        String url = "https://jsonplaceholder.typicode.com/albums";
        //Instanciar el objeto request (JsonArrayRequest o JsonObjectRequest)
        JsonArrayRequest peticion = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            lstAlbum.add(new Album(0, "Seleccione"));
                            for(int i = 0; i < response.length(); i++){
                                JSONObject objjson = response.getJSONObject(i);
                                lstAlbum.add(new Album(
                                        objjson.getInt("id"),
                                        objjson.getString("title")
                                ));
                            }
                            spAlbum.setAdapter(new ArrayAdapter<Album>(MainActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item, lstAlbum));

                        }catch (JSONException ex){
                            Log.e("ErrorVolley", ex.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ErrorRequest", error.getMessage());
            }
        });
        //Agregar la petición a la cola de peticiones.
        colapeticiones.add(peticion);
    }
    private void wsListarFotos(int idAlbum) {
        lstFotos.clear();
        String url = "https://jsonplaceholder.typicode.com/albums/"+idAlbum+"/photos";
        //Instanciar el objeto request (JsonArrayRequest o JsonObjectRequest)
        JsonArrayRequest peticion = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int i = 0; i < response.length(); i++){
                                JSONObject objjson = response.getJSONObject(i);
                                lstFotos.add(new Foto(
                                        objjson.getInt("id"),
                                        objjson.getString("title")
                                ));
                            }
                            spFoto.setAdapter(new ArrayAdapter<Foto>(MainActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item, lstFotos));

                        }catch (JSONException ex){
                            Log.e("ErrorVolley", ex.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ErrorRequest", error.getMessage());
            }
        });
        //Agregar la petición a la cola de peticiones.
        colapeticiones.add(peticion);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(this, "ID / nombre flor: " + position, Toast.LENGTH_SHORT).show();
        if(position > 0){
            wsListarFotos(lstAlbum.get(position).getIdAlbum());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
