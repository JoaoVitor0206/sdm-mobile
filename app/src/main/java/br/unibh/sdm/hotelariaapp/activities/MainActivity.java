package br.unibh.sdm.hotelariaapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import br.unibh.sdm.hotelariaapp.R;
import br.unibh.sdm.hotelariaapp.api.ClienteService;
import br.unibh.sdm.hotelariaapp.api.RestServiceGenerator;
import br.unibh.sdm.hotelariaapp.entidades.Cliente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ClienteService service = null;
    final private MainActivity mainActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        service = RestServiceGenerator.createService(ClienteService.class);
        buscaClientes();
    }

    public void buscaClientes(){
        ClienteService service = RestServiceGenerator.createService(ClienteService.class);
        Call<List<Cliente>> call = service.getClientes();
        call.enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                if (response.isSuccessful()) {
                    Log.i("ClienteDAO", "Retornou " + response.body().size() + " Clientes!");
                    List<String> lista2 = new ArrayList<String>();
                    for (Cliente item : response.body()) {
                        lista2.add(item.getNome());
                    }
                    Log.i("MainActivity", lista2.toArray().toString());
                    ListView listView = findViewById(R.id.listViewListaClientes);
                    listView.setAdapter(new ArrayAdapter<String>(mainActivity,
                            android.R.layout.simple_list_item_1,
                            lista2));
                } else {
                    Log.e("CriptomoedaDAO", "" + response.message());
                    Toast.makeText(getApplicationContext(), "Erro: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {
                Log.e("Error", "" + t.getMessage());
            }
        });
    }

}