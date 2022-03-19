package com.xaero.camunda.jms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActiveMQListener {

    private final RuntimeService camundaRuntimeService;

    @JmsListener(destination = "test.queue")
    public void readMessage(String message) {
        log.info("read amq message: {}", message);
        log.info("send message to camunda");

        final Map<String, Object> variables = new HashMap<>();
        variables.put("message", message);

        camundaRuntimeService.startProcessInstanceByKey("Hello", variables);
    }

    @JmsListener(destination = "hello.delay")
    public void readHelloDelayMessage(String message) {
        log.info("read hello delay amq message: {}", message);

        final Map<String, Object> variables = new HashMap<>();
        variables.put("message", message);

        camundaRuntimeService.startProcessInstanceByKey("HelloWithDelay", variables);
    }
}
