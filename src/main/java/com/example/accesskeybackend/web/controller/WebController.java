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
        response.put("success", webService.isSiteSupportsIpv6(siteUrl));
        return ResponseEntity.ok(response);
    }
}
