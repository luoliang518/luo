package com.luo.auth.user;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ENCTest {
    @Qualifier("jasyptStringEncryptor")
    @Autowired
    private StringEncryptor stringEncryptor;
    @Test
    public void test() {
        String encrypt = stringEncryptor.encrypt("CTXBZCWOHFWAJMRG");
        System.out.println(encrypt);
        String decrypt = stringEncryptor.decrypt(encrypt);
        System.out.println(decrypt);
    }
}
