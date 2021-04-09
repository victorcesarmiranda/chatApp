/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.victor.chatapp.coneccaoservidor;

/**
 * @author old_adam
 */
public class Usuario {
    private static final Usuario instance = new Usuario();

    private String currentContact;

    private String userId;

    public static Usuario getInstance() {
        return instance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrentContact() {
        return currentContact;
    }

    public void setCurrentContact(String currentContact) {
        this.currentContact = currentContact;
    }
}
