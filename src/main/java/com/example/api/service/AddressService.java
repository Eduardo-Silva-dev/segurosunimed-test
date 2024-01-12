package com.example.api.service;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.integration.FindAddressByCepIntegration;
import com.example.api.integration.response.AddressResponse;
import com.example.api.repository.AddressRepository;
import com.example.api.web.dto.AddressRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final FindAddressByCepIntegration findAddressByCepIntegration;

    private final ModelMapper modelMapper;

    private final AddressRepository addressRepository;

    public Address treatAddress(AddressRequest addressRequest) {
        if(!addressRequest.getStreetAddress().isEmpty()){
            return modelMapper.map(addressRequest, Address.class);
        } else {
            return findAddressByCep(addressRequest.getZipCode());
        }
    }

    @Transactional
    public void delete(Long idAddress) {
        this.addressRepository.deleteById(idAddress);
    }

    public List<Address> findAddressByListCeps(List<String> ceps, Customer customer) {
        List<Address> addressList = new ArrayList<>();
        for (String cep : ceps) {
            Address address = this.findAddressByCep(cep);
            address.setCustomer(customer);
            addressList.add(address);
        }

        return addressList;
    }

    public Address findAddressByCep(String cep) {
        AddressResponse addressResponse = findAddressByCepIntegration.execute(cep);
        return addressResponse.mapToEntity();
    }

}