package com.example.accesskeybackend.web.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class WebService {
    public boolean isSiteSupportsIpv6(String siteUrl) {
        if(isReachable(siteUrl)) {
            String siteName = siteUrl.startsWith("http://") || siteUrl.startsWith("https://") ? siteUrl.split("://")[1] : siteUrl;
            siteName = siteName.split("/")[0];
            try {
                List<InetAddress> ipv6Adresses = Arrays.stream(InetAddress.getAllByName(siteName))
                        .filter(address -> address instanceof Inet6Address)
                        .toList();
                return !ipv6Adresses.isEmpty();
            } catch (UnknownHostException e) {
                log.error("Incorrect Host Name: " + e.getMessage());
            }
        }
        return false;
    }

    private boolean isReachable(String siteUrl) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(siteUrl).openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            log.error("Incorrect URL input: " + e.getMessage());
        }
        return false;
    }
}
