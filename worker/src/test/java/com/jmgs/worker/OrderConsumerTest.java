package com.jmgs.worker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = { "order" })
@TestPropertySource(properties = {
    "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}"
})
class OrderConsumerTest {

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  @Test
  void shouldProcessOrder() throws Exception {

    String json = """
        {
          "orderId": "order-test-123",
          "customerId": "69b0df7ccfb808f94a8563b1",
          "products": [
            "69b0df7ccfb808f94a8563b4",
            "69b0df7ccfb808f94a8563b4"
          ]
        }
        """;

    kafkaTemplate.send("order", json);

    Thread.sleep(10000);
  }
}