package com.example.TrasportiBackend.User;

import com.example.TrasportiBackend.Auth.AuthService;
import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.payloads.entities.AziendaDTO;
import com.example.TrasportiBackend.payloads.entities.TrasportatoreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;
    @PostMapping("/trasportatore")
    public Trasportatore save(@RequestBody @Validated TrasportatoreDTO trasportatoreDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return authService.registerTrasportatore(trasportatoreDTO);
    }
    @PostMapping("/azienda")
    public Azienda save(@RequestBody @Validated AziendaDTO aziendaDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return authService.registerAzienda(aziendaDTO);
    }

    @GetMapping("/azienda")
    public Page<Azienda> getAllAziende(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String orderBy){
        return userService.getAllAziende(page,size,orderBy);
    }
    @GetMapping("/trasportatore")
    public Page<Trasportatore> getAllTrasportatori(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String orderBy){
        return userService.getAllTrasportatori(page,size,orderBy);
    }

    @PutMapping("/trasportatore/me")
    @PreAuthorize("hasAuthority('Trasportatore')")
    public Trasportatore putById(@AuthenticationPrincipal Trasportatore currentUser, @RequestBody @Validated TrasportatoreDTO trasportatoreDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return userService.putTrasportatoreById(currentUser.getId(), trasportatoreDTO);
    }
    @PutMapping("/azienda/me")
    @PreAuthorize("hasAuthority('Azienda')")
    public Azienda putById(@AuthenticationPrincipal Azienda currentUser,@RequestBody @Validated AziendaDTO aziendaDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return userService.putAziendaById(currentUser.getId(),aziendaDTO);
    }
    @PutMapping("/trasportatoreAdmin/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public Trasportatore putByIdAdmin(@PathVariable long id,@RequestBody @Validated TrasportatoreDTO trasportatoreDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return userService.putTrasportatoreById(id,trasportatoreDTO);
    }
    @PutMapping("/aziendaAdmin/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public Azienda putByIdAdmin(@PathVariable long id,@RequestBody @Validated AziendaDTO aziendaDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return userService.putAziendaById(id,aziendaDTO);
    }

    @DeleteMapping("/trasportatoreAdmin/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public boolean deleteByIdT(@PathVariable long id){
        return userService.deleteTrasportatoreById(id);
    }
    @DeleteMapping("/aziendaAdmin/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public boolean deleteByIdA(@PathVariable long id){
        return userService.deleteAziendaById(id);
    }
    @DeleteMapping("/trasportatore/me")
    @PreAuthorize("hasAuthority('Trasportatore')")
    public boolean deleteByIdT(@AuthenticationPrincipal Trasportatore trasportatore){
        return userService.deleteTrasportatoreById(trasportatore.getId());
    }
    @DeleteMapping("/azienda/me")
    @PreAuthorize("hasAuthority('Azienda')")
    public boolean deleteByIdA(@AuthenticationPrincipal Azienda azienda){
        return userService.deleteAziendaById(azienda.getId());
    }
}
