package com.example.api.service;


import java.util.List;
import java.util.Optional;

import com.example.api.domain.Address;
import com.example.api.web.dto.CustomerRequest;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.example.api.domain.Customer;
import com.example.api.repository.CustomerRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final ModelMapper modelMapper;

	private final CustomerRepository repository;

	private final AddressService addressService;

	public Page<Customer> findAll(Pageable pageable) {
		return repository.findAllByOrderByNameAsc(pageable);
	}

	public Optional<Customer> findById(Long id) {
		return repository.findById(id);
	}

	public Page<Customer> findByFilter(String name, String email, String gender, Pageable pageable) {
		return repository.findByCriteriaAndOrderByName(name,email, gender, pageable);
	}

	@Transactional
	public Customer save(CustomerRequest customerRequest) {
		Customer customer = modelMapper.map(customerRequest, Customer.class);
		List<Address> addressList = this.addressService.findAddressByListCeps(customerRequest.getZipCode(), customer);
		customer.getAddresses().addAll(addressList);
		return repository.save(customer);
	}

	@Transactional
	public Customer update(Long id, CustomerRequest customerRequest) {
		Customer customer = this.findById(id).get();
		customer.setName(customerRequest.getName());
		customer.setEmail(customerRequest.getEmail());
		customer.setGender(customerRequest.getGender());
		if(!customerRequest.getZipCode().isEmpty()){
			List<Address> addressList = this.addressService.findAddressByListCeps(customerRequest.getZipCode(), customer);
			customer.getAddresses().addAll(addressList);
		}
		return repository.save(customer);
	}

	@Transactional
	public Customer updateAddress(Long id, Address address) {
		Customer customer = this.findById(id).get();
		address.setCustomer(customer);
		customer.getAddresses().add(address);
		return repository.save(customer);
	}

	@Transactional
	public void delete(Customer customer) {
		repository.deleteById(customer.getId());
	}


}