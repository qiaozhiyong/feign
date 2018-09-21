package com.example.feign;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@RestController
public class FeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeignApplication.class, args);
    }
    //服务名不区分大小写
    @FeignClient(value = "helloService" ,fallback = HelloError.class)
    public interface HelloService {
        @RequestMapping(value = "/hello", method = RequestMethod.GET)
        public String hello(@RequestParam("name") String name);
    }

    @Autowired
    private HelloService helloService;

    @RequestMapping("/hello")
    public String hello(@RequestParam("name")String name){
        return helloService.hello(name);
    }

    @Component
    public class HelloError implements HelloService {
        @Override
        public String hello(String name){
            return "hello  ,"+name+"!  sorry ,error !";
        }
    }




}
