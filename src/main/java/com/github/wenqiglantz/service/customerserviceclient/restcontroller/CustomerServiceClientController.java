package com.github.wenqiglantz.service.customerserviceclient.restcontroller;

import com.github.wenqiglantz.service.customerserviceclient.customerclient.CustomerServiceClient;
import com.github.wenqiglantz.service.customerserviceclient.data.CustomerVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/customers")
public class CustomerServiceClientController {

    private final CustomerServiceClient customerServiceClient;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CustomerVO> createCustomer(CustomerVO customerVO){
        return customerServiceClient.createCustomer(customerVO);
    }

    @GetMapping("/{customerId}")
    public Mono<CustomerVO> getCustomer(@PathVariable String customerId) {
        return customerServiceClient.getCustomer(customerId);
    }

    @PutMapping(value = "/{customerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CustomerVO> updateCustomer(@PathVariable String customerId, CustomerVO customerVO){
        return customerServiceClient.updateCustomer(customerId, customerVO);
    }

    @DeleteMapping("/{customerId}")
    public Mono<Void> deleteCustomer(@PathVariable String customerId){
        return customerServiceClient.deleteCustomer(customerId);
    }
}
