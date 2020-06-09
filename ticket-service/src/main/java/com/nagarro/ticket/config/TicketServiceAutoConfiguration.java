package com.nagarro.ticket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import com.nagarro.ticket.service.*;

@Configuration
@ConditionalOnClass(TicketServiceImpl.class)
public class TicketServiceAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public TicketService TicketService(){
        return new TicketServiceImpl();
    }
}