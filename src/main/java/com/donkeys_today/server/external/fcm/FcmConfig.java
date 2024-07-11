package com.donkeys_today.server.external.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

@Configuration
public class FcmConfig {

  @Bean
  public FirebaseApp firebaseApp() throws IOException {
    FileInputStream aboutFirebaseFile = new FileInputStream(
        String.valueOf(ResourceUtils.getFile("./secret/firebase-adminsdk.json")));

    FirebaseOptions options = FirebaseOptions
        .builder()
        .setCredentials(GoogleCredentials.fromStream(aboutFirebaseFile))
        .build();
    return FirebaseApp.initializeApp(options);
  }

  @Bean
  public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
    return FirebaseMessaging.getInstance(firebaseApp);
  }
}
