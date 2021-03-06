package donglee99.instagram.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("/Users/idonghyeon/Desktop/STUDY/instagram-clonecoding/instagram-clonecoding/instagram/src/main/java/donglee99/instagram")
    private String profileUploadFolder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry.addResourceHandler("/profile.imgs/**")
                .addResourceLocations("file:///" + profileUploadFolder)
                .setCachePeriod(60*10*6)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());

        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///" + profileUploadFolder)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}
