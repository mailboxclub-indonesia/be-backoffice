package org.mailboxclubindonesia.backoffice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties("googleapis.map")
public class GoogleMapConfig {

  private String key;
}
