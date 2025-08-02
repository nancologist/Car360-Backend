package com.nancologist.car360.playground;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pg")
@RequiredArgsConstructor
public class PgController {

    private final PgService pgService;

    @GetMapping("/users")
    public ResponseEntity<List<PgUser>> getUsers(@RequestParam(required = false) String search) {
        List<PgUser> users = this.pgService.findUsers(search);
        return ResponseEntity.ok(users);
    }
}
