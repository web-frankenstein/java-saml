package com.onelogin.saml2.model;

import com.azure.core.http.HttpClient;
import com.azure.core.http.netty.NettyAsyncHttpClientBuilder;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.security.keyvault.keys.cryptography.CryptographyClient;
import com.azure.security.keyvault.keys.cryptography.CryptographyClientBuilder;

public class HSM {

    private String clientId;
    private String clientCredentials;
    private String tenantId;
    private String keyVaultId;

    /**
     * Constructor to initialises an HSM object.
     *
     * @param clientId The HSM client ID.
     * @param clientCredentials The HSM client credentials.
     * @param tenantId The HSM tenant ID.
     * @param keyVaultId The HSM KeyVault ID.
     *
     * @return HSM
     */
    public HSM(String clientId, String clientCredentials, String tenantId, String keyVaultId) {
        this.clientId = clientId;
        this.clientCredentials = clientCredentials;
        this.tenantId = tenantId;
        this.keyVaultId = keyVaultId;
    }

    /**
     * Initialises a connection to the HSM and returns the HSM client.
     *
     * @return CryptographyClient HSM client.
     */
    public CryptographyClient getHSMClient() {
        ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .clientSecret(clientCredentials)
                .tenantId(tenantId)
                .build();

        HttpClient httpClient = new NettyAsyncHttpClientBuilder().build();

        return new CryptographyClientBuilder()
                .httpClient(httpClient)
                .credential(clientSecretCredential)
                .keyIdentifier(keyVaultId)
                .buildClient();
    }
}