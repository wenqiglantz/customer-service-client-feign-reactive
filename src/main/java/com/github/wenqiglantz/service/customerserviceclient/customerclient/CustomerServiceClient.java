package com.github.wenqiglantz.service.customerserviceclient.customerclient;

import com.github.wenqiglantz.service.customerserviceclient.data.CustomerVO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(
        name = "customer-service",
        url = "${customer-service.urls.base-url}",
        configuration = CustomerServiceClientConfig.class
)
public interface CustomerServiceClient {

    @PostMapping(value = "${customer-service.urls.create-customer-url}", headers = {"Content-Type=application/json"})
    Mono<CustomerVO> createCustomer(@RequestBody CustomerVO customerVO);

    @PutMapping(value = "${customer-service.urls.update-customer-url}", headers = {"Content-Type=application/json"})
    Mono<CustomerVO> updateCustomer(@PathVariable("customerId") String customerId,
                                    @RequestBody CustomerVO customerVO);

    @GetMapping(value = "${customer-service.urls.get-customer-url}", headers = {"Content-Type=application/json"})
    Mono<CustomerVO> getCustomer(@PathVariable("customerId") String customerId);

    @DeleteMapping(value = "${customer-service.urls.delete-customer-url}")
    Mono<Void> deleteCustomer(@PathVariable("customerId") String customerId);

}
