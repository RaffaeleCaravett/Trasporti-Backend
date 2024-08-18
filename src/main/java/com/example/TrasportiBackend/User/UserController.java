package com.example.TrasportiBackend.User;

import com.example.TrasportiBackend.Auth.AuthService;
import com.example.TrasportiBackend.enums.Settore;
import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.payloads.entities.AdminDTO;
import com.example.TrasportiBackend.payloads.entities.AziendaDTO;
import com.example.TrasportiBackend.payloads.entities.PatchAziendaDTO;
import com.example.TrasportiBackend.payloads.entities.TrasportatoreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;
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
    @PatchMapping("/azienda/me")
    @PreAuthorize("hasAuthority('Azienda')")
    public Azienda patchById(@AuthenticationPrincipal Azienda currentUser, @RequestBody @Validated PatchAziendaDTO aziendaDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return userService.patchAziendaById(currentUser.getId(),aziendaDTO);
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
    @GetMapping("/findByNomeAndCognomeContaining/{nome}/{cognome}")
    public Page<Trasportatore>findByNomeAndCognomeContaining(@PathVariable String nome,@PathVariable String cognome,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String orderBy){
        return userService.findByNomeAndCognomeContaining(nome,cognome,page,size,orderBy);
    }
    @GetMapping("/findBySettore/{settore}")
    public Page<Azienda>findBySettore(@PathVariable String settore,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String orderBy){
        return userService.findBySettore(settore,page,size,orderBy);
    }
    @GetMapping("/findBySettore/{nomeAzienda}")
    public Page<Azienda>findByNomeAzienda(@PathVariable String nomeAzienda,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String orderBy){
        return userService.findByNomeAzienda(nomeAzienda,page,size,orderBy);
    }

    @GetMapping("/reset/{password}/{oldPassword}/me")
    public boolean resetPassword(@PathVariable String password,@PathVariable String oldPassword,@AuthenticationPrincipal User user){
        return authService.resetPassword(password,oldPassword,user);
    }
    @GetMapping("/resetAdmin/{password}/{oldPassword}/{id}")
    public boolean resetPassword(@PathVariable String password,@PathVariable String oldPassword,@PathVariable long id){
        return authService.resetPasswordAdmin(password,oldPassword,id);
    }
}
