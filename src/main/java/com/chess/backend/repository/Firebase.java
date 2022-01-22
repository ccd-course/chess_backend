package com.chess.backend.repository;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.WriteResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Firebase {
    private Firestore db;

    /**
     * Add  documents with fields.
     *
     */
    void addDocument(String collection, String docId, Object value)  {
        DocumentReference docRef = db.collection(collection).document(docId);
        docRef.set(value);
//        ApiFuture<WriteResult> result = docRef.set(value);

    }
    public Firebase() {


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
