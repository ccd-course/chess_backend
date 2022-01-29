package com.chess.backend.repository;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class FirebaseConnector {
    Firestore db;

    @PostConstruct
    void init(){
        try{

            String firebaseJsonKeys = System.getenv("FIREBASE_JSON_KEYS");
//                System.out.println("firebaseJsonKeys: "+ firebaseJsonKeys);
            InputStream is = new ByteArrayInputStream(firebaseJsonKeys.getBytes());

            FirestoreOptions firestoreOptions =
                    FirestoreOptions.getDefaultInstance().toBuilder()
                            .setCredentials(GoogleCredentials.fromStream(is))
                            .build();
            db = firestoreOptions.getService();


        }
        catch(IOException e) {
            System.out.println("ERROR: invalid service account credentials. See README.");
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
