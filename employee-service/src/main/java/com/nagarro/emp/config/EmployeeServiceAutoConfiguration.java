package com.nagarro.emp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import com.nagarro.emp.service.*;

@Configuration
@ConditionalOnClass(EmployeeServiceImpl.class)
public class EmployeeServiceAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public EmployeeService EmployeeService(){
        return new EmployeeServiceImpl();
    }
}