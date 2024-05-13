package com.arch.mfc.infra.utils;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
@Component
public class TrustMgr implements X509TrustManager {

    @Autowired
    private Logger logger;

    @Override
    public void checkClientTrusted(final X509Certificate[] x509Certificates, final String s)
            throws CertificateException {

    }

    @Override
    public void checkServerTrusted(final X509Certificate[] x509Certificates, final String s)
            throws CertificateException {
            int x = 0;
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

    /**
     * @return SSLSocketFactory
     */
    public SSLSocketFactory getSocketFactory() {
        TrustManager[] trustManager = new TrustManager[]{new TrustMgr()};

        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManager, new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            logger.error("Error al inicializar contexto SSL: " + e.getMessage());
            return null;
        }
        return sslContext.getSocketFactory();
    }
}
