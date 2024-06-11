package com.developerstack.edumanage.util.security;

import org.mindrot.BCrypt;

public class PasswordManager {
    public String encrypt(String rawPassword) { // rowpassword enbazu simple text aahum
        return BCrypt.hashpw(rawPassword,BCrypt.gensalt(10));
        // this is encoding type is 10 (just give a number)
    }
    public boolean checkPassword(String rawPassword, String hashPassword){
        return BCrypt.checkpw(rawPassword, hashPassword);
        // user enter seira password eam hashpw eam check senji booleana return seyyum
    }

}
