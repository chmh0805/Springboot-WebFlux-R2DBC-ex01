package com.hyuk.webflux.web;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hyuk.webflux.domain.Customer;
import com.hyuk.webflux.domain.CustomerRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class CustomerController {

	private final CustomerRepository customerRepository;
	
	@PostMapping("/customer")
	public Mono<Customer> save(@RequestBody Customer customer) {
		return customerRepository.save(customer).log();
	}
	
	@GetMapping(value = "/customer", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Customer> findAll() {
		return customerRepository.findAll().delayElements(Duration.ofSeconds(1)).log();
	}
	
	@GetMapping("/customer/{id}")
	public Mono<Customer> findById(@PathVariable long id) {
		return customerRepository.findById(id).log();
	}
	
	@DeleteMapping("/customer/{id}")
	public Mono<Void> deleteById(@PathVariable long id) {
		return customerRepository.deleteById(id).log();
	}
	
	@PutMapping("/customer/{id}")
	public Mono<Customer> updateById(@PathVariable long id, @RequestBody Customer customer) {
		customer.setId(id);
		return customerRepository.save(customer);
	}
}
