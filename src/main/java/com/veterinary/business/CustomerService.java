package com.veterinary.business;

import com.veterinary.core.config.exception.EntityAlreadyExistsException;
import com.veterinary.core.config.exception.NotFoundException;
import com.veterinary.core.config.mapStruct.CustomerMapper;
import com.veterinary.dao.CustomerRepo;
import com.veterinary.dto.request.CustomerRequest;
import com.veterinary.dto.response.CustomerResponse;
import com.veterinary.entities.Animal;
import com.veterinary.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepo customerRepo, CustomerMapper customerMapper) {
        this.customerRepo = customerRepo;
        this.customerMapper = customerMapper;
    }

    public Customer save(Customer customer) {
        if (customerRepo.existsByEmail(customer.getEmail()) || customerRepo.existsByPhone(customer.getPhone())) {
            throw new EntityAlreadyExistsException("Bu email veya numara zaten kayıtlı!", 409);
        }
        return this.customerRepo.save(customer);
    }

    public Customer getById(long id) {
        return this.customerRepo.findById(id).orElseThrow(() -> new NotFoundException("Kayıt Bulunamadı"));
    }


    public CustomerResponse update(long id, CustomerRequest customerRequest) {

        this.getById(id);

        Customer updated = customerMapper.toEntity(customerRequest);
        updated.setId(id);

        Customer saved = customerRepo.save(updated);
        return customerMapper.toResponse(saved);
    }

    public void delete(Long id) {
        customerRepo.delete(this.getById(id));
    }

    public Page<Customer> getAllCustomer(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return customerRepo.findAll(pageable);
    }
}
