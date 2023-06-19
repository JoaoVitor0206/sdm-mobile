package br.unibh.sdm.hotelariaapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;


import br.unibh.sdm.hotelariaapp.R;
import br.unibh.sdm.hotelariaapp.api.ClienteService;
import br.unibh.sdm.hotelariaapp.api.RestServiceGenerator;
import br.unibh.sdm.hotelariaapp.entidades.Cliente;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormularioClienteActivity extends AppCompatActivity {
    private ClienteService service = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_cliente);
        setTitle("Edição de Cliente");
        service = RestServiceGenerator.createService(ClienteService.class);
        configuraBotaoSalvar();
        inicializaObjeto();
    }

    private void inicializaObjeto() {
        Intent intent = getIntent();
        if (intent.getSerializableExtra("objeto") != null) {
            Cliente objeto = (Cliente) intent.getSerializableExtra("objeto");
            EditText nome= findViewById(R.id.editTextNome);
            EditText telefone = findViewById(R.id.editTextTelefone);
            EditText email = findViewById(R.id.editTextEmail);
            nome.setText(objeto.getNome());
            telefone.setText(objeto.getTelefone());
            email.setText(objeto.getEmail());
            nome.setEnabled(false);
            Button botaoSalvar = findViewById(R.id.buttonSalvar);
            botaoSalvar.setText("Atualizar");
        }
    }


    private void configuraBotaoSalvar() {
        Button botaoSalvar = findViewById(R.id.buttonSalvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("FormularioCliente","Clicou em Salvar");
                Cliente cliente = recuperaInformacoesFormulario();
                Intent intent = getIntent();
                if (intent.getSerializableExtra("objeto") != null) {
                    Cliente objeto = (Cliente) intent.getSerializableExtra("objeto");
                    cliente.setUserId(objeto.getUserId());
                    if (validaFormulario(cliente)) {
                        atualizaCliente(cliente);
                    }
                } else {
                    if (validaFormulario(cliente)) {
                        salvaCliente(cliente);
                    }
                }
            }
        });
    }

    private boolean validaFormulario(Cliente cliente){
        boolean valido = true;
        EditText nome = findViewById(R.id.editTextNome);
        EditText telefone = findViewById(R.id.editTextTelefone);
        EditText email = findViewById(R.id.editTextEmail);
        Log.i("Cliente: ", cliente.toString());
        if (cliente.getNome() == null || cliente.getNome().trim().length() == 0){
            nome.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            valido = false;
        } else {
            nome.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_blue_dark), PorterDuff.Mode.SRC_ATOP);
        }
        if (cliente.getTelefone() == null || cliente.getTelefone().trim().length() == 0){
            telefone.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            valido = false;
        } else {
            telefone.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_blue_dark), PorterDuff.Mode.SRC_ATOP);
        }
        if (cliente.getEmail() == null || cliente.getEmail().trim().length() == 0){
           email.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            valido = false;
        } else {
            email.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_blue_dark), PorterDuff.Mode.SRC_ATOP);
        }
        if (!valido){
            Log.e("FormularioCripto", "Favor verificar os campos destacados");
            Toast.makeText(getApplicationContext(), "Favor verificar os campos destacados", Toast.LENGTH_LONG).show();
        }
        return valido;
    }


    private void salvaCliente(Cliente cliente) {
        Call<Cliente> call = service.criaCliente(cliente);
        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (response.isSuccessful()) {
                    Log.i("FormularioCliente", "Salvou o Cliente"+ cliente.getNome());
                    Toast.makeText(getApplicationContext(), "Salvou o Cliente"+ cliente.getNome(), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Log.e("FormularioCliente", "Erro (" + response.code()+"): Verifique novamente os valores");
                    Toast.makeText(getApplicationContext(), "Erro (" + response.code()+"): Verifique novamente os valores", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                Log.e("FormularioCliente", "Erro: " + t.getMessage());
            }
        });
    }

    private void atualizaCliente(Cliente cliente) {
        Log.i("FormularioCliente","Vai atualizar cliente "+cliente.getNome());
        Call<Cliente> call = service.atualizaCliente(cliente.getNome(), cliente);
        call.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (response.isSuccessful()) {
                    Log.i("FormularioCliente", "Atualizou o Cliente " + cliente.getNome());
                    Toast.makeText(getApplicationContext(), "Atualizou o Cliente" + cliente.getNome(), Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Log.e("FormularioCliente", "Erro (" + response.code()+"): Verifique novamente os valores");
                    Toast.makeText(getApplicationContext(), "Erro (" + response.code()+"): Verifique novamente os valores", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                Log.e("FormularioCliente", "Erro: " + t.getMessage());
            }
        });
    }

    @NotNull
    private Cliente recuperaInformacoesFormulario() {
        EditText nome = findViewById(R.id.editTextNome);
        EditText telefone = findViewById(R.id.editTextTelefone);
        EditText email = findViewById(R.id.editTextEmail);
        Cliente cliente = new Cliente();
        cliente.setNome(nome.getText().toString());
        cliente.setTelefone(telefone.getText().toString());
        cliente.setEmail(email.getText().toString());
        return cliente;
    }
}