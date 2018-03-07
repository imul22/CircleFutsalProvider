package com.kodecircle.circlefutsalprovider.core;
/**
 * This is the source code of CircleFutsal for Android ver.1.x.x.
 * It is licensed under GNU GPL v.3.
 * You should have received a copy of the license in this archive.
 * <p>
 * See license at :
 * https://github.com/ryanbekhen/circle_futsal/blob/master/LICENSE"
 * Copyright KODE CIRCLE, 2018.
 */

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;


import java.util.Collection;

/**
 *  @author ACHMAD IRIANTO EKA PUTRA
 *  @version 1.0.1
 *  @since 11/02/18
 */

public class FirestoreUtil {

    private static FirebaseFirestore getBase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build();
        db.setFirestoreSettings(settings);
        return db;
    }

    public static CollectionReference getUserRef() {
        if(AppInfo.isSandboxMode)
            return getBase().collection("users_sandbox");
        else
            return getBase().collection("users");
    }

    public static class userKey {
        public static final String name = "name";
        public static final String email = "email";
        public static final String phone = "phone";
        public static final String city = "city";
        public static final String playBalance = "play_balance";
        public static final String teamNodeId = "team_node_id";
        public static final String userNodeId = "user_node_id";
        public static final String photoPath = "photo_path";
        public static final String createdAt = "created_at";
        public static final String verified = "verified";
    }


    public static CollectionReference getUserNodeRef() {
        if(AppInfo.isSandboxMode)
            return getBase().collection("user_node_sandbox");
        else
            return getBase().collection("user_node");
    }

    public static class userNodeKey {
        public static final String city = "city";
        public static final String playingTime = "playing_time";
        public static final String totalPlayers = "total_players";
        public static final String createdAt = "created_at";
    }

    public static CollectionReference getFutsalField() {
        if(AppInfo.isSandboxMode)
            return getBase().collection("futsal_field_sandbox");
        else
            return getBase().collection("futsal_field");
    }

    public static class futsalFieldKey {
        public static final String name = "name";
        public static final String description = "description";
        public static final String city = "city";
        public static final String geoLocation = "location";
        public static final String phone = "phone";
        public static final String email = "email";
        public static final String price = "price";
        public static final String verified = "verified";
        public static final String createdAt = "created_at";
        public static final String password = "password";
    }
}
