package com.cyn.ccds.event.rm.appointment.server;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.IAuthenticationProvider;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.requests.GraphServiceClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/** The main spring boot entry point */
@SpringBootApplication(
    scanBasePackages = {
      "com.cyn.ccds.event.rm.appointment.server",
      "com.cyn.commons",
      "com.cyn.ccds.authentication.filter"
    })
@EnableScheduling
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public IAuthenticationProvider microsoftAuthenticationProvider(
      @Value("${microsoft.graph.client.id}") String clientId,
      @Value("${microsoft.graph.client.secret}") String clientSecret,
      @Value("${microsoft.graph.tenant}") String tenant) {
    ClientSecretCredential clientSecretCredential =
        new ClientSecretCredentialBuilder()
            .clientId(clientId)
            .clientSecret(clientSecret)
            .tenantId(tenant)
            .build();
    return new TokenCredentialAuthProvider(clientSecretCredential);
  }

  @Bean
  @SuppressWarnings("unchecked")
  public GraphServiceClient<Request> microsoftGraphClient(
      IAuthenticationProvider authenticationProvider) {
    return GraphServiceClient.builder()
        .authenticationProvider(authenticationProvider)
        .buildClient();
  }
}
