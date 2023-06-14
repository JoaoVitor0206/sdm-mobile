package br.unibh.sdm.hotelariaapp.api;

import java.util.List;

import br.unibh.sdm.hotelariaapp.entidades.Cliente;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClienteService {
    @Headers({
            "Accept: application/json",
            "User-Agent: HotelariaApp"
    })
    @GET("cliente")
    Call<List<Cliente>> getClientes();
    @GET("cliente/{id}")
    Call<Cliente> getCliente(@Path("id") String nome);
    @POST("cliente")
    Call<Cliente> criaCliente(@Body Cliente cliente);
    @PUT("cliente/{id}")
    Call<Cliente> atualizaCliente(@Path("id") String nome, @Body Cliente cliente);
    @DELETE("cliente/{id}")
    Call<Boolean> excluiCliente(@Path("id") String nome);
}
