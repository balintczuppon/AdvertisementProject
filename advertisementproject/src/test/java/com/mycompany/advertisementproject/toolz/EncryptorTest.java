package com.mycompany.advertisementproject.toolz;

import java.security.NoSuchAlgorithmException;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EncryptorTest {

    private String plainText;
    private String cipherText;

    private final Encryptor encryptor;

    public EncryptorTest() {
        encryptor = new Encryptor();
    }

    @Before
    public void setUp() {
        plainText = "12345";
    }

    @Test
    public void shouldPassTheEncryptionTest() throws NoSuchAlgorithmException {
            cipherText = encryptor.hashPassword(plainText);
            assertThat(cipherText, not(plainText));
    }
//
//    @Test
//    public void shouldFailTheEncryptionTest() throws NoSuchAlgorithmException {
//            cipherText = encryptor.hashPassword(plainText);
//            assertThat(cipherText, is(plainText));
//    }
}
