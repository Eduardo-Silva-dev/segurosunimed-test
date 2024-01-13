package com.example.api.repository;

import com.example.api.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.api.domain.Customer;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

	Page<Customer> findAllByOrderByNameAsc(Pageable pageable);

	Optional<Customer> findCustomerByEmail(String email);

	@Query("SELECT c FROM Customer c WHERE " +
			"(:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
			"(:email IS NULL OR LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
			"(:gender IS NULL OR LOWER(c.gender) LIKE LOWER(CONCAT('%', :gender, '%'))) " +
			"ORDER BY c.name ASC")
	Page<Customer> findByCriteriaAndOrderByName(@Param("name") String name, @Param("email") String email,
												@Param("gender") String gender, Pageable pageable);

}
