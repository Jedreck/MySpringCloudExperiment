package com.jedreck.tester2.feign;

import com.jedreck.tester2.entitys.PersonEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(value = "TEST-02")
public interface Tester2Test1Service {
    @PostMapping(value = "test01/changeName")
    Boolean changeName(@RequestBody PersonEntity person);

    @PostMapping(value = "test01/insertPerson")
    public boolean insertPerson(@RequestBody PersonEntity person);
}
