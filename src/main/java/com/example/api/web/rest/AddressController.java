package com.example.api.web.rest;

import com.example.api.domain.Address;
import com.example.api.domain.Customer;
import com.example.api.service.AddressService;
import com.example.api.service.CustomerService;
import com.example.api.web.dto.AddressRequest;
import com.example.api.web.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {

	private final AddressService addressService;
	private final CustomerService customerService;

	private static final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer not found";

	@PostMapping
	public ResponseEntity<Customer> createAddress(@Valid @RequestBody AddressRequest addressRequest) {
		Customer customer = customerService.findById(addressRequest.getIdCustomer())
				.map(existingCustomer -> {
					Address address = addressService.treatAddress(addressRequest);
					return customerService.updateAddress(addressRequest.getIdCustomer(), address);
				})
				.orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE));

		return ResponseEntity.status(HttpStatus.CREATED).body(customer);
	}

	@DeleteMapping("/{idAddress}/customer/{idCustomer}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> deleteAddress(@PathVariable Long idCustomer, @PathVariable Long idAddress) {
		if (customerService.findById(idCustomer).isPresent()) {
			addressService.delete(idAddress);
			return ResponseEntity.noContent().build();
		} else {
			throw new CustomerNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
		}
	}

}