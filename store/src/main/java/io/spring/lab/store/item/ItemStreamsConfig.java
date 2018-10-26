package io.spring.lab.store.item;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(ItemStreamsBinding.class)
public class ItemStreamsConfig {

}
