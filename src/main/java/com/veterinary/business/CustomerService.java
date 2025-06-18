package com.veterinary.business;

import com.veterinary.core.config.exception.EntityAlreadyExistsException;
import com.veterinary.core.config.exception.ExceptionMessages;
import com.veterinary.core.config.exception.NotFoundException;
import com.veterinary.core.config.mapStruct.CustomerMapper;
import com.veterinary.dao.CustomerRepo;
import com.veterinary.dto.request.CustomerRequest;
import com.veterinary.dto.response.CustomerResponse;
import com.veterinary.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepo customerRepo, CustomerMapper customerMapper) {
        this.customerRepo = customerRepo;
        this.customerMapper = customerMapper;
    }

    public Customer save(Customer customer) {
        if (customerRepo.existsByEmail(customer.getEmail())) {
            throw new EntityAlreadyExistsException(String.format(ExceptionMessages.EMAIL_EXISTS,customer.getEmail()));
        }

        if (customerRepo.existsByPhone(customer.getPhone())) {
            throw new EntityAlreadyExistsException(String.format(ExceptionMessages.PHONE_EXISTS,customer.getPhone()));
        }
        return this.customerRepo.save(customer);
    }

    public Customer getById(long id) {
        return this.customerRepo.findById(id).orElseThrow(
                () -> new NotFoundException(String.format(ExceptionMessages.CUSTOMER_NOT_FOUND,id)));
    }


    public CustomerResponse update(long id, CustomerRequest customerRequest) {

        Customer customer = this.getById(id);
        if (customerRepo.existsByEmail(customerRequest.getEmail())
                && !customerRequest.getEmail().equalsIgnoreCase(customer.getEmail())){
            throw new EntityAlreadyExistsException(String.format(ExceptionMessages.EMAIL_EXISTS,customerRequest.getEmail()));
        }

        if (customerRepo.existsByPhone(customerRequest.getPhone())
                && !customerRequest.getPhone().equalsIgnoreCase(customer.getPhone())){
            throw new EntityAlreadyExistsException(String.format(ExceptionMessages.PHONE_EXISTS,customerRequest.getPhone()));
        }

        this.customerMapper.updateEntityFromRequest(customer,customerRequest);
        customer.setUpdatedAt(LocalDateTime.now());

        return customerMapper.toResponse(customerRepo.save(customer));
    }

    public void delete(Long id) {
        customerRepo.delete(this.getById(id));
    }

    public Page<Customer> getAllCustomer(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return customerRepo.findAll(pageable);
    }

    public List<CustomerResponse> getAllByName(String name) {
        List<CustomerResponse> responseList = new ArrayList<>();
        List<Customer> customerList = this.customerRepo.findByNameContainingIgnoreCase(name);
        if (!customerList.isEmpty()) {
            for (Customer customer : customerList) {
                responseList.add(customerMapper.toResponse(customer));
            }
        } else {
            throw new NotFoundException(String.format(ExceptionMessages.CUSTOMER_NAME_NOT_FOUND,name));
        }
        return responseList;
    }

    public List<String> getAnimalsByCustomerId(long id) {
        getById(id); //id kontrolü
        List<String> animalNameList = this.customerRepo.findAnimalsNameByCustomerId(id);
        if (animalNameList.isEmpty()) {
            throw new NotFoundException(String.format(ExceptionMessages.CUSTOMER_ANIMALS_NOT_FOUND,id));
        }
        return animalNameList;
    }
}

