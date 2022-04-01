package com.snowalker.shardingjdbc.snowalker.demo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author administrator
 * @date 2017-04-20
 * @see <a href="http://springfox.github.io/springfox/docs/current/#getting-started">swagger</a>
 **/
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "swagger",value = {"enable"},havingValue = "true")
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(this.apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.snowalker.shardingjdbc.snowalker"))
            .paths(PathSelectors.any())
            .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("shardingjdbc-云平台分库分表  RestFul Apis")
                .description("云平台分库分表  RestFul Apis")
                .termsOfServiceUrl("https://www.novoyun.com/")
                .contact(new Contact("云数据接口", "https://www.novoyun.com/", "adm@novoyun.com"))
                .version("3.0")
                .build();
    }
}
