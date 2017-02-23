package org.kalashnyk.homebudget.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Sergii on 11.02.2017.
 */
@Configuration
@ComponentScan(basePackages = {"org.kalashnyk.**.service"})
public class RootConfig {
}
