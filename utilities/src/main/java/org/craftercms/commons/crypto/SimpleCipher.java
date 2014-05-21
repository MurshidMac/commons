/*
 * Copyright (C) 2007-2014 Crafter Software Corporation.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.craftercms.commons.crypto;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.craftercms.commons.i10n.I10nLogger;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for simplifying encryption/decryption with the {@link javax.crypto.Cipher} class. By default, the
 * algorithm used is AES.
 *
 * @author Sumer Jabri
 * @author Alfonso Vásquez
 */
public class SimpleCipher {

    private static final I10nLogger logger = new I10nLogger(SimpleCipher.class, "crafter.commons.messages.logging");

    public static final String LOG_KEY_ENC_SUCCESSFUL =        "crypto.cipher.encryptionSuccessful";
    public static final String LOG_KEY_DEC_SUCCESSFUL =        "crypto.cipher.decryptionSuccessful";
    public static final String LOG_KEY_KEY_GEN =               "crypto.cipher.keyGenerated";
    public static final String LOG_KEY_IV_GEN =                "crypto.cipher.ivGenerated";
    public static final String LOG_KEY_DEF_CIPHER_CREATED =    "crypto.cipher.defaultCipherCreated";

    public static final String ERROR_KEY_INVALID_TRANSFORMATION =  "crypto.cipher.invalidCipherTransformation";
    public static final String ERROR_KEY_KEY_NOT_SET =             "crypto.cipher.keyNotSet";
    public static final String ERROR_KEY_IV_NOT_SET =              "crypto.cipher.ivNotSet";
    public static final String ERROR_KEY_ENC_ERROR =               "crypto.cipher.encryptionError";
    public static final String ERROR_KEY_DEC_ERROR =               "crypto.cipher.decryptionError";

	private Key key;
    private byte[] iv;
	private Cipher cipher;

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public String getBase64Key() {
        return key != null? Base64.encodeBase64String(key.getEncoded()) : null;
    }

    public void setBase64Key(String aesKey) {
        this.key = new SecretKeySpec(Base64.decodeBase64(aesKey), CipherUtils.AES_CIPHER_ALGORITHM);
    }

    public void setBase64Key(String key, String algorithm) {
        this.key = new SecretKeySpec(Base64.decodeBase64(key), algorithm);
    }

    public byte[] getIv() {
        return iv;
    }

    public void setIv(byte[] iv) {
        this.iv = iv;
    }

    public String getBase64Iv() {
        return iv != null? Base64.encodeBase64String(iv) : null;
    }

    public void setBase64Iv(String iv) {
        this.iv = Base64.decodeBase64(iv);
    }

    public Cipher getCipher() {
        return cipher;
    }

    public void setCipher(Cipher cipher) {
        this.cipher = cipher;
    }

    public void setCipherTransformation(String transformation) throws CryptoException {
        try {
            cipher = Cipher.getInstance(transformation);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new CryptoException(ERROR_KEY_INVALID_TRANSFORMATION, e, transformation);
        }
    }

    public String encryptBase64(String clear) throws CryptoException {
        return Base64.encodeBase64String(encrypt(StringUtils.getBytesUtf8(clear)));
    }

    public byte[] encrypt(byte[] clear) throws CryptoException {
        if (key == null) {
            key = CipherUtils.generateAesKey();

            logger.debug(LOG_KEY_KEY_GEN);
        }
        if (iv == null) {
            iv = CipherUtils.generateAesIv();

            logger.debug(LOG_KEY_IV_GEN);
        }
        if (cipher == null) {
            String cipherTransformation = CipherUtils.DEFAULT_AES_CIPHER_TRANSFORMATION;

            try {
                cipher = Cipher.getInstance(cipherTransformation);
            } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
                // Should NEVER happen
                throw new RuntimeException(e);
            }

            logger.debug(LOG_KEY_DEF_CIPHER_CREATED, cipherTransformation);
        }

        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));

            return cipher.doFinal(clear);
        } catch (GeneralSecurityException e){
            throw new CryptoException(ERROR_KEY_ENC_ERROR, e);
        } finally {
            logger.debug(LOG_KEY_ENC_SUCCESSFUL);
        }
    }

    public String decryptBase64(String encrypted) throws CryptoException {
        return StringUtils.newStringUtf8(decrypt(Base64.decodeBase64(encrypted)));
    }

    public byte[] decrypt(byte[] encrypted) throws CryptoException {
        if (key == null) {
            throw new CryptoException(ERROR_KEY_KEY_NOT_SET);
        }
        if (iv == null) {
            throw new CryptoException(ERROR_KEY_IV_NOT_SET);
        }
        if (cipher == null) {
            String cipherTransformation = CipherUtils.DEFAULT_AES_CIPHER_TRANSFORMATION;

            try {
                cipher = Cipher.getInstance(CipherUtils.DEFAULT_AES_CIPHER_TRANSFORMATION);
            } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
                // Should NEVER happen
                throw new RuntimeException(e);
            }

            logger.debug(LOG_KEY_DEF_CIPHER_CREATED, cipherTransformation);
        }

        try {
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));

            return cipher.doFinal(encrypted);
        } catch (GeneralSecurityException e) {
            throw new CryptoException(ERROR_KEY_DEC_ERROR, e);
        } finally {
            logger.debug(LOG_KEY_DEC_SUCCESSFUL);
        }
    }

}