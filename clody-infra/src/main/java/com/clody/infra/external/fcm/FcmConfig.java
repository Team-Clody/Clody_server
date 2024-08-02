package com.clody.infra.external.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FcmConfig {

  @Bean
  public FirebaseApp firebaseApp() throws IOException {
    ClassPathResource resource = new ClassPathResource("firebase-adminsdk.json");

    try (InputStream aboutFirebaseFile = resource.getInputStream()) {
      FirebaseOptions options = FirebaseOptions
          .builder()
          .setCredentials(GoogleCredentials.fromStream(aboutFirebaseFile))
          .build();
      return FirebaseApp.initializeApp(options);
    }
  }

  @Bean
  public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
    return FirebaseMessaging.getInstance(firebaseApp);
  }
}
