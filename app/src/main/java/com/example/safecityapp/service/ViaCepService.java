package com.example.safecityapp.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ViaCepService {
    @GET("{cep}/json/")
    Call<CepResponse> getCepInfo(@Path("cep") String cep);
}