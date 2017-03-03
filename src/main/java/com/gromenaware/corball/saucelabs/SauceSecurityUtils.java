package com.gromenaware.corball.saucelabs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class SauceSecurityUtils {

    private static final Logger LOGGER = LogManager.getLogger(SauceSecurityUtils.class);

    public SauceSecurityUtils() {
    }

    public static String hmacEncode(String algorithm, String input, String privateKey)
            throws IllegalArgumentException {
        try {
            byte[] keyBytes = privateKey.getBytes();
            Key key = new SecretKeySpec(keyBytes, 0, keyBytes.length, algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(key);
            return byteArrayToHex(mac.doFinal(input.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.info(ex);
            throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
        } catch (InvalidKeyException ex) {
            LOGGER.info(ex);
            throw new IllegalArgumentException("Illegal key: " + privateKey);
        }
    }

    protected static String byteArrayToHex(byte[] bytes) {
        int hn, ln, cx;
        String hexDigitChars = "0123456789abcdef";
        StringBuffer buf = new StringBuffer(bytes.length * 2);
        for (cx = 0; cx < bytes.length; cx++) {
            hn = ((int) (bytes[cx]) & 0x00ff) / 16;
            ln = ((int) (bytes[cx]) & 0x000f);
            buf.append(hexDigitChars.charAt(hn));
            buf.append(hexDigitChars.charAt(ln));
        }
        return buf.toString();
    }
}
