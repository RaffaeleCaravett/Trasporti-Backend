package com.example.TrasportiBackend.Recensioni;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trasportatore/recensione")
public class RecensioneTController {
    @Autowired
    RecensioneService recensioneService;
}
