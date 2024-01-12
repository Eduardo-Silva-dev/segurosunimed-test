
package com.example.api.web.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
public class AddressRequest {

    @NotNull
    private Long idCustomer;

    @NotEmpty
    private String zipCode;

    private String streetAddress;

    private String complement;

    private String neighborhood;

    private String locality;

    private String uf;

    private String ddd;

}