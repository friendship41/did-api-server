package com.friendship41.apiserver.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingFilterConfig {
  @Bean
  public CommonsRequestLoggingFilter commonsRequestLoggingFilter() {
    CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
    filter.setIncludeQueryString(true);
    filter.setIncludePayload(true);
    filter.setMaxPayloadLength(100000);
    filter.setIncludeHeaders(true);
    filter.setIncludeClientInfo(false);
    filter.setBeforeMessagePrefix("Start ");
    filter.setAfterMessagePrefix("End ");
    return filter;
  }
}
