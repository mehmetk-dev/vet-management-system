package com.veterinary.api;

import com.veterinary.business.CustomerService;
import com.veterinary.business.VaccineService;
import com.veterinary.core.config.mapStruct.CustomerMapper;
import com.veterinary.core.result.ResultData;
import com.veterinary.dto.CursorResponse;
import com.veterinary.dto.request.CustomerRequest;
import com.veterinary.dto.response.CustomerResponse;
import com.veterinary.dto.response.VaccineResponse;
import com.veterinary.entities.Customer;
import com.veterinary.utilies.ResultHelper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    private final VaccineService vaccineService;

    public CustomerController(CustomerService customerService, CustomerMapper customerMapper, VaccineService vaccineService) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
        this.vaccineService = vaccineService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomerResponse> save(@Valid @RequestBody CustomerRequest customerRequest){
        Customer customer = customerMapper.toEntity(customerRequest);
        Customer savedCustomer = customerService.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerMapper.toResponse(savedCustomer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable("id") long id){
        return ResponseEntity.status(HttpStatus.OK).body(customerMapper.toResponse(this.customerService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable("id")long id, @RequestBody CustomerRequest customerRequest){
        CustomerResponse response = customerService.update(id, customerRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResultData<CursorResponse<CustomerResponse>> getCustomers(
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "pageSize",defaultValue = "10") int pageSize
    ){
        Page<Customer> customers = customerService.getAllCustomer(page,pageSize);
        Page<CustomerResponse> customerResponses = customers
                .map(customerMapper::toResponse);
        return ResultHelper.cursor(customerResponses);
    }

    @GetMapping(params = "name")
    public ResponseEntity<List<CustomerResponse>> getByName(@RequestParam(name = "name") String name){
        return ResponseEntity.ok(this.customerService.getAllByName(name));
    }


    @GetMapping("/{id}/animals")
    public ResultData<List<String>> getAnimalsByCustomer(@PathVariable("id")long id){
        List<String> animalNameList = this.customerService.getAnimalsByCustomerId(id);
        return new ResultData<>(true,"200",id + " ID'li kullanıcının hayvan listesi",animalNameList);
    }

    @GetMapping("/{id}/animals/vaccines")
    public ResponseEntity<List<VaccineResponse>> getVaccinesByCustomerAnimals(
            @RequestParam(value = "startDate") LocalDate startDate,
            @RequestParam(value = "endDate") LocalDate endDate,
            @PathVariable("id") long id)
    {
        return ResponseEntity.status(HttpStatus.OK).body(vaccineService.getVaccinesExpiringBetween(startDate,endDate,id));
    }
}
