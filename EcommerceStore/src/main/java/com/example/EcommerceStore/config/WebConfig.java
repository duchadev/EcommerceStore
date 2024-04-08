package com.example.EcommerceStore.config;

import java.nio.file.Path;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {
@Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer)
{
  configurer.defaultContentType(MediaType.TEXT_HTML);
}
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/asset/default/script/**")
        .addResourceLocations("classpath:/static/asset/default/script/")
        .setCachePeriod(3600)
        .resourceChain(true)
        .addResolver(new PathResourceResolver() {

          protected MediaType getMediaType(Path path) {
            if (path != null) {
              String filename = path.getFileName().toString();
              if (filename.endsWith(".css")) {
                return MediaType.valueOf("text/css");
              }
            }
            return null;
          }
        });
  }
}
