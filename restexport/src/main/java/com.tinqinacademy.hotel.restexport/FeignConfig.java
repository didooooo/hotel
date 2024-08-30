package com.tinqinacademy.hotel.restexport;

import feign.Contract;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public Contract feignContract() {
        return new SpringMvcContract();
    }
    @Bean
    public Contract useFeignAnnotations() {
        return new Contract.Default();
    }
}
