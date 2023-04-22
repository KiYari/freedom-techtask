package com.example.accesskeybackend.web.controller;

import com.example.accesskeybackend.web.service.WebService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/web")
@AllArgsConstructor
public class WebController {
    private final WebService webService;

    @GetMapping("/checkIpv6Support")
    public ResponseEntity<?> checkIpv6Support(UriComponentsBuilder uriBuilder) {
        Map<String, Object> response = new HashMap<>();
        String siteUrl = uriBuilder.toUriString();
        System.out.println(siteUrl);
        System.out.println(webService.isSiteSupportsIpv6(siteUrl));
        response.put("success", webService.isSiteSupportsIpv6(siteUrl));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/checkIpv6Support2")
    public ResponseEntity<?> checkIpv6Support2(@RequestBody String siteUrl) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", webService.isSiteSupportsIpv6(siteUrl));
        return response.get("success") ? ResponseEntity.ok(response) : ResponseEntity.status(400).body(response);
    }
}
