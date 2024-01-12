package com.example.api.integration.response;


import com.example.api.domain.Address;
import lombok.Data;

@Data
public class AddressResponse {

    private String cep;

    private String logradouro;

    private String complemento;

    private String bairro;

    private String localidade;

    private String uf;

    private String ibge;

    private String gia;

    private String ddd;

    private String siafi;

    public Address mapToEntity() {
        Address address = new Address();
        address.setZipCode(this.getCep());
        address.setStreetAddress(this.getLogradouro());
        address.setComplement(this.getComplemento());
        address.setNeighborhood(this.getBairro());
        address.setLocality(this.getLocalidade());
        address.setUf(this.getUf());
        address.setDdd(this.getDdd());
        return address;
    }
}