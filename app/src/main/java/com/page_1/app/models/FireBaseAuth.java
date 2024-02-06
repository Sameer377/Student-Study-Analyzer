package com.page_1.app.models;

import com.google.firebase.auth.FirebaseAuth;

public class FireBaseAuth {
    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public void setFirebaseAuth(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    private  FirebaseAuth firebaseAuth=null;

}
