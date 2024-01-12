package com.example.api.integration;

import com.example.api.integration.response.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "FindAddressByCepClient", url = "${viacep.url}")
public interface FindAddressByCepIntegration {
    @GetMapping("/{cep}/json/")
    AddressResponse execute(@PathVariable("cep") String cep);

}