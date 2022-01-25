package com.chess.backend.repository;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Firebase {
    private Firestore db;

    /**
     * Add  documents with fields.
     *
     */
    void addDocument(String collection, String docId, Object value)  {
        DocumentReference docRef = db.collection(collection).document(docId);
        try{
            ApiFuture<WriteResult> result = docRef.set(value);
            System.out.println("Update time : " + result.get().getUpdateTime());
        }
        catch (ExecutionException | InterruptedException e){
            System.out.println("ERROR: "+ docId);
            e.fillInStackTrace();
            e.printStackTrace();
            System.out.println(e);
        }

    }
    /**
     * Add  documents with fields.
     *
     * @return
     */
    Map<String, Object> getDocument(String collection, String id) {
        DocumentReference docRef = db.collection(collection).document(id);
        // future.get() blocks on response
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try{
            DocumentSnapshot document = future.get();
            return document.getData();

        }
        catch (ExecutionException | InterruptedException e){
            System.out.println("ERROR: can not get document with id: "+ id);
            System.out.println(e.getMessage());
            return null;
        }


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
        }


    }
}
