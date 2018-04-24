package com.everlapp.androidexamples.registeraccount;

public class ProfileDataSource {

    public void createProfile(String name, String password, RegistrationCallback callback) {
        if (name.isEmpty() || password.isEmpty()) {
            callback.onError(new Exception("Registration Error"));
            return;
        }
        callback.onSuccess("User successfully registered!");
    }

}
