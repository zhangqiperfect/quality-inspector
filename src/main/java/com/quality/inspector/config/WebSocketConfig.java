package com.quality.inspector.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author hs
 * @data: 2019/11/11 16:22
 * @param:
 * @description:
 */
@Configuration
public class WebSocketConfig {
    /*  @Bean
      public ServerEndpointExporter serverEndpointExporter() {
          return new ServerEndpointExporter();
      }*/
//    @Profile({"dev", "test"})
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
