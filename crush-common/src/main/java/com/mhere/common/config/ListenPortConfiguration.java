package com.mhere.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.io.FileWriter;
import java.io.IOException;

@Configuration
public class ListenPortConfiguration {

    @Value("${LISTEN_PORT_FILE:unset}")
    String listenInfoFile;

    @EventListener(WebServerInitializedEvent.class)
    public void onApplicationEvent(WebServerInitializedEvent event) {
        int localPort = event.getWebServer().getPort();
        if (!listenInfoFile.equals("unset")) {
            try (FileWriter writer = new FileWriter(listenInfoFile)) {
                writer.write(String.format("%d", localPort));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
//        System.out.println("Listening port: " + localPort);
    }
}
