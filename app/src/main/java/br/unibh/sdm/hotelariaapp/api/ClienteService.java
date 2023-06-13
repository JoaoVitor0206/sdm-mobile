package br.unibh.sdm.hotelariaapp.api;

import java.util.List;

import br.unibh.sdm.hotelariaapp.entidades.Cliente;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ClienteService {
    @Headers({
            "Accept: application/json",
            "User-Agent: HotelariaApp"
    })
    @GET("cliente")
    Call<List<Cliente>> getClientes();
}
