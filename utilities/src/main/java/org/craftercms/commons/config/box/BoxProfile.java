package org.craftercms.commons.config.box;

import org.craftercms.commons.config.ConfigurationProfile;

/**
 * Holds the credentials to open a connection using the Box API. All values are provided by the Box Developer Console.
 *
 * @see <a href="https://developer.box.com/docs/authentication">Official Box API documentation</a>
 *
 * @author joseross
 */
public class BoxProfile extends ConfigurationProfile {

    /**
     * Box API Client ID
     */
    protected String clientId;

    /**
     * Box API Client Secret
     */
    protected String clientSecret;

    /**
     * Box API Enterprise ID
     */
    protected String enterpriseId;

    /**
     * RSA Public Key ID, the public key can be uploaded or generated from the Box Developer Console
     */
    protected String publicKeyId;

    /**
     * RSA Private Key, the encrypted private key
     */
    protected String privateKey;

    /**
     * RSA Public Key Password, used to decrypt the private key
     */
    protected String privateKeyPassword;

    /**
     * Folder where the files will be uploaded, it will be created if it doesn't exist
     */
    protected String uploadFolder;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(final String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(final String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getPublicKeyId() {
        return publicKeyId;
    }

    public void setPublicKeyId(final String publicKeyId) {
        this.publicKeyId = publicKeyId;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(final String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPrivateKeyPassword() {
        return privateKeyPassword;
    }

    public void setPrivateKeyPassword(final String privateKeyPassword) {
        this.privateKeyPassword = privateKeyPassword;
    }

    public String getUploadFolder() {
        return uploadFolder;
    }

    public void setUploadFolder(final String uploadFolder) {
        this.uploadFolder = uploadFolder;
    }

}
