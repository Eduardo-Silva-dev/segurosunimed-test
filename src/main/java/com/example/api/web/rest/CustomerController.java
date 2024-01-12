package com.example.api.web.rest;

import com.example.api.web.dto.CustomerRequest;
import com.example.api.web.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.api.domain.Customer;
import com.example.api.service.CustomerService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

	private final CustomerService service;

	private static final String CUSTOMER_NOT_FOUND = "Customer not found";

	@GetMapping
	public ResponseEntity<Page<Customer>> findAll(@PageableDefault(size = 5, page = 0, sort = "name") Pageable pageable) {
		Page<Customer> customers = service.findAll(pageable);
		return ResponseEntity.ok(customers);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Customer> findById(@PathVariable Long id) {
		Customer customer = service.findById(id).orElseThrow(() ->
				new CustomerNotFoundException(CUSTOMER_NOT_FOUND));
		return ResponseEntity.ok(customer);
	}

	@GetMapping("/search")
	public ResponseEntity<Page<Customer>> searchCustomers(
			@RequestParam(required = false) String name, @RequestParam(required = false) String email,
			@RequestParam(required = false) String gender, @PageableDefault(size = 5, page = 0, sort = "name") Pageable pageable) {
		Page<Customer> customers = service.findByFilter(name, email, gender, pageable);
		return ResponseEntity.ok(customers);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Customer> createCustomer(@RequestBody @Valid CustomerRequest customer) {
		Customer createdCustomer = service.save(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerRequest customer) {
		Customer updatedCustomer = service.findById(id)
				.map(existingCustomer -> service.update(id, customer))
				.orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND));
		return ResponseEntity.ok(updatedCustomer);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
		service.findById(id).ifPresentOrElse(service::delete, () -> {
			throw new CustomerNotFoundException(CUSTOMER_NOT_FOUND);
		});
		return ResponseEntity.noContent().build();
	}

}