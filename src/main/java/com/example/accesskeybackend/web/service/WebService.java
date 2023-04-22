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
    private final String IPv4MASK = "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    private final String IPv6MASK = "(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))";

    public Boolean isSiteSupportsIpv6(String siteUrl) {
        if(isReachable(siteUrl) && !isIpAddress(siteUrl)) {
            String siteName = getSiteName(siteUrl);
            try {
                List<InetAddress> ipv6Adresses = Arrays.stream(InetAddress.getAllByName(siteName))
                        .filter(address -> address instanceof Inet6Address)
                        .toList();
                return !ipv6Adresses.isEmpty();
            } catch (UnknownHostException e) {
                log.error("Unknown Host Name: " + e.getMessage());
            }
        } else if (isIpAddress(siteUrl)) {
            return siteUrl.matches(IPv6MASK);
        }
        return false;
    }

    private Boolean isReachable(String siteUrl) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(siteUrl).openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            log.error("URL cannot be reached: " + e.getMessage());
        }
        return false;
    }

    private Boolean isIpAddress(String siteUrl) {
        String siteName = getSiteName(siteUrl);
        return siteName.matches(IPv4MASK) || siteUrl.matches(IPv6MASK);
    }

    private String getSiteName(String siteUrl) {
        String siteName = siteUrl.startsWith("http://") || siteUrl.startsWith("https://") ? siteUrl.split("://")[1] : siteUrl;
        siteName = siteName.split("/")[0];
        return siteName;
    }
}
