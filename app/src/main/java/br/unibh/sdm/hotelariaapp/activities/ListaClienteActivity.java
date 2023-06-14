package br.unibh.sdm.hotelariaapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.unibh.sdm.hotelariaapp.R;
import br.unibh.sdm.hotelariaapp.api.ClienteService;
import br.unibh.sdm.hotelariaapp.api.RestServiceGenerator;
import br.unibh.sdm.hotelariaapp.entidades.Cliente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaClienteActivity extends AppCompatActivity {

    private ClienteService service = null;
    final private ListaClienteActivity listaClienteActivity = this;
    private final Context context;

    public ListaClienteActivity() {
        context = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Lista de Clientes");
        setContentView(R.layout.lista_cliente_activity);
        service = RestServiceGenerator.createService(ClienteService.class);
        buscaClientes();
        criacaoBotaoFlutuante();
        criacaoCliqueLongo();
    }

    private void criacaoCliqueLongo() {
        ListView listView = findViewById(R.id.listViewListaClientes);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ListaClienteActivity","Clicou em clique longo na posicao "+position);
                final Cliente objetoSelecionado = (Cliente) parent.getAdapter().getItem(position);
                Log.i("ListaClienteActivity", "Selecionou o Cliente "+objetoSelecionado.getNome());
                new AlertDialog.Builder(parent.getContext()).setTitle("Removendo Cliente")
                        .setMessage("Tem certeza que quer remover o cliente "+objetoSelecionado.getNome()+"?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                removeCliente(objetoSelecionado);
                            }
                        }).setNegativeButton("Não", null).show();
                return true;
            }
        });
    }
    private void removeCliente(Cliente cliente) {
        Log.i("Cliente: ", cliente.toString());
        Log.i("ListaClienteActivity","Vai remover cliente "+cliente.getNome());
        Call<Boolean> call = this.service.excluiCliente(cliente.getUserId());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    Log.i("ListaClienteActivity", "Removeu o Cliente " + cliente.getNome());
                    Toast.makeText(getApplicationContext(), "Removeu o Cliente" + cliente.getNome(), Toast.LENGTH_LONG).show();
                    onResume();
                } else {
                    Log.e("ListaClienteActivity", "Erro (" + response.code()+"): Verifique novamente os valores");
                    Toast.makeText(getApplicationContext(), "Erro (" + response.code()+"): Verifique novamente os valores", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("ListaClienteActivity", "Erro: " + t.getMessage());
            }
        });
    }

    private void criacaoBotaoFlutuante() {
        FloatingActionButton botaoNovo = findViewById(R.id.floatingActionButtonCriar);
        botaoNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MainActivity","Clicou no botão para adicionar Novo Cliente");
                startActivity(new Intent(ListaClienteActivity.this,
                        FormularioClienteActivity.class));
            }
        });
    }

    public void buscaClientes(){
        ClienteService service = RestServiceGenerator.createService(ClienteService.class);
        Call<List<Cliente>> call = service.getClientes();
        call.enqueue(new Callback<List<Cliente>>() {
            @Override
            public void onResponse(Call<List<Cliente>> call, Response<List<Cliente>> response) {
                if (response.isSuccessful()) {
                    Log.i("ListaClienteActivity", "Retornou " + response.body().size() + " Clientes!");
                    ListView listView = findViewById(R.id.listViewListaClientes);
                    listView.setAdapter(new ListaClienteAdapter(context,response.body()));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.i("ListaClienteActivity", "Selecionou o objeto de posicao "+position);
                            Cliente objetoSelecionado = (Cliente) parent.getAdapter().getItem(position);
                            Log.i("ListaClienteActivity", "Selecionou o cliente"+objetoSelecionado.getNome());
                            Intent intent = new Intent(ListaClienteActivity.this, FormularioClienteActivity.class);
                            intent.putExtra("objeto", objetoSelecionado);
                            startActivity(intent);
                        }
                    });
                } else {
                    Log.e("ClienteDAO", "" + response.message());
                    Toast.makeText(getApplicationContext(), "Erro: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Cliente>> call, Throwable t) {
                Log.e("Error", "" + t.getMessage());
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        buscaClientes();
    }
}