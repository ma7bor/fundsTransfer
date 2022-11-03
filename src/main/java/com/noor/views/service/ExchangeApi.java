package com.noor.views.service;

import com.noor.views.dto.ExchangeRatesResponse;
import com.noor.views.exception.ExchangeRatesException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;


@Component
public class ExchangeApi {


    @Async
    public CompletableFuture<ExchangeRatesResponse> getExchangeData(BigDecimal amount, String currencyFrom, String currencyTo) throws IOException{
        ExchangeRatesResponse exchangeRatesResponse = new ExchangeRatesResponse();
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .method("GET", null)
                .url("https://api.apilayer.com/fixer/convert?to="+currencyFrom+"&from="+currencyTo+"&amount="+amount)
                .addHeader("apikey", "V6mT4igLTaE8svYNiv00A8bU8tWVZcfi")
                .build();
        Response response = client.newCall(request).execute();
        try {
            JSONObject jsonObject = new JSONObject(response.body().string());
            System.out.println("json resukt == " + jsonObject.getBigDecimal("result"));
            exchangeRatesResponse.setBalance(jsonObject.getBigDecimal("result"));
            exchangeRatesResponse.setSuccess(jsonObject.getBoolean("success"));
            //String loudScreaming = jsonObject.getJSONObject("query").getString("amount");
            return CompletableFuture.completedFuture(exchangeRatesResponse);
        }catch (JSONException err){
            throw new JSONException("JSON parse exception");
        } catch (Exception ex){
            throw new ExchangeRatesException("External API ERROR");
        }
    }
}
