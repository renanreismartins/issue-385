package com.metrics.demo;

import io.micrometer.core.instrument.Counter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    private final Counter personCounter;
    private final ApplicationInfoBean infoBean;

    public PersonController(Counter personCounter, ApplicationInfoBean infoBean) {
        this.personCounter = personCounter;
        this.infoBean = infoBean;
    }

    @GetMapping("/person")
    public String person() {
        personCounter.increment();
        return "renan " + infoBean.getAmiId() + " " + infoBean.getInstanceType();
    }

}