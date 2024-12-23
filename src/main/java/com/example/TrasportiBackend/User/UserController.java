package com.example.TrasportiBackend.User;

import com.example.TrasportiBackend.Auth.AuthService;
import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.payloads.entities.AziendaDTO;
import com.example.TrasportiBackend.payloads.entities.AziendaPutDTO;
import com.example.TrasportiBackend.payloads.entities.TrasportatoreDTO;
import com.example.TrasportiBackend.payloads.entities.TrasportatorePutDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public Page<Azienda> getAllAziende(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return userService.getAllAziende(page, size, orderBy);
    }

    @GetMapping("/trasportatore")
    public Page<Trasportatore> getAllTrasportatori(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return userService.getAllTrasportatori(page, size, orderBy);
    }

    @PutMapping("/trasportatore/me")
    @PreAuthorize("hasAuthority('Trasportatore')")
    public Trasportatore putById(@AuthenticationPrincipal Trasportatore currentUser, @RequestBody @Validated TrasportatorePutDTO trasportatoreDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return userService.putTrasportatoreById(currentUser.getId(), trasportatoreDTO);
    }

    @PutMapping("/azienda/me")
    @PreAuthorize("hasAuthority('Azienda')")
    public Azienda putById(@AuthenticationPrincipal Azienda currentUser, @RequestBody @Validated AziendaPutDTO aziendaDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return userService.putAziendaById(currentUser.getId(), aziendaDTO);
    }

    @PutMapping("/trasportatoreAdmin/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public Trasportatore putByIdAdmin(@PathVariable long id, @RequestBody @Validated TrasportatorePutDTO trasportatoreDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return userService.putTrasportatoreById(id, trasportatoreDTO);
    }

    @PutMapping("/aziendaAdmin/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public Azienda putByIdAdmin(@PathVariable long id, @RequestBody @Validated AziendaPutDTO aziendaDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return userService.putAziendaById(id, aziendaDTO);
    }

    @DeleteMapping("/trasportatoreAdmin/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public boolean deleteByIdT(@PathVariable long id) {
        return userService.deleteTrasportatoreById(id);
    }

    @DeleteMapping("/aziendaAdmin/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public boolean deleteByIdA(@PathVariable long id) {
        return userService.deleteAziendaById(id);
    }

    @DeleteMapping("/trasportatore/me")
    @PreAuthorize("hasAuthority('Trasportatore')")
    public boolean deleteByIdT(@AuthenticationPrincipal Trasportatore trasportatore) {
        return userService.deleteTrasportatoreById(trasportatore.getId());
    }

    @DeleteMapping("/azienda/me")
    @PreAuthorize("hasAuthority('Azienda')")
    public boolean deleteByIdA(@AuthenticationPrincipal Azienda azienda) {
        return userService.deleteAziendaById(azienda.getId());
    }

    @GetMapping("/azienda/findByNomeAndCognomeContaining/{nome}/{cognome}")
    public Page<Trasportatore> findByNomeAndCognomeContainingAz(@PathVariable String nome, @PathVariable String cognome, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return userService.findByNomeAndCognomeContaining(nome, cognome, page, size, orderBy);
    }

    @GetMapping("/azienda/findBySettore/{settore}")
    public Page<Azienda> findBySettoreAz(@PathVariable String settore, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return userService.findBySettore(settore, page, size, orderBy);
    }

    @GetMapping("/trasportatore/findByNomeAndCognomeContaining/{nome}/{cognome}")
    public Page<Trasportatore> findByNomeAndCognomeContainingTr(@PathVariable String nome, @PathVariable String cognome, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return userService.findByNomeAndCognomeContaining(nome, cognome, page, size, orderBy);
    }

    @GetMapping("/trasportatore/findByCittaAndNomeAndCognomeContaining/{nome}/{cognome}/{citta}")
    public Page<Trasportatore> findByCittaAndNomeAndCognomeContainingTr(@PathVariable String nome, @PathVariable String cognome, @PathVariable String citta, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return userService.findByCittaAndNomeAndCognome(citta, nome, cognome, page, size, orderBy);
    }

    @GetMapping("/trasportatore/findByCitta/{citta}")
    public Page<Trasportatore> findByCitta(@PathVariable String citta, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return userService.findByCitta(citta, page, size, orderBy);
    }

    @GetMapping("/azienda/trasportatore/findByNomeAndCognomeContaining/{nome}/{cognome}")
    public Page<Trasportatore> azFindByNomeAndCognomeContainingTr(@PathVariable String nome, @PathVariable String cognome, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return userService.findByNomeAndCognomeContaining(nome, cognome, page, size, orderBy);
    }

    @GetMapping("/azienda/trasportatore/findByCittaAndNomeAndCognomeContaining/{nome}/{cognome}/{citta}")
    public Page<Trasportatore> azFindByCittaAndNomeAndCognomeContainingTr(@PathVariable String nome, @PathVariable String cognome, @PathVariable String citta, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return userService.findByCittaAndNomeAndCognome(citta, nome, cognome, page, size, orderBy);
    }

    @GetMapping("/azienda/trasportatore/findByCitta/{citta}")
    public Page<Trasportatore> azFindByCitta(@PathVariable String citta, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return userService.findByCitta(citta, page, size, orderBy);
    }

    @GetMapping("/azienda/trasportatore")
    public Page<Trasportatore> azFindAllT(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return userService.getAllTrasportatori(page, size, orderBy);
    }

    @GetMapping("/trasportatore/trasportatore")
    public Page<Trasportatore> tFindAllT(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return userService.getAllTrasportatori(page, size, orderBy);
    }

    @GetMapping("/trasportatore/azienda")
    public Page<Azienda> tFindAllAz(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return userService.getAllAziende(page, size, orderBy);
    }

    @GetMapping("trasportatore/findBySettore/{settore}")
    public Page<Azienda> findBySettoreTr(@PathVariable String settore, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return userService.findBySettore(settore, page, size, orderBy);
    }

    @GetMapping("/trasportatore/findBySettore/{nomeAzienda}")
    public Page<Azienda> trFindByNomeAzienda(@PathVariable String nomeAzienda, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return userService.findByNomeAzienda(nomeAzienda, page, size, orderBy);
    }

    @GetMapping("/azienda/findBySettore/{nomeAzienda}")
    public Page<Azienda> azFindByNomeAzienda(@PathVariable String nomeAzienda, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) {
        return userService.findByNomeAzienda(nomeAzienda, page, size, orderBy);
    }

    @GetMapping("/azienda/reset/{password}/{oldPassword}/me")
    public boolean resetPasswordAz(@PathVariable String password, @PathVariable String oldPassword, @AuthenticationPrincipal User user) {
        return authService.resetPassword(password, oldPassword, user);
    }

    @GetMapping("/trasportatore/reset/{password}/{oldPassword}/me")
    public boolean resetPassword(@PathVariable String password, @PathVariable String oldPassword, @AuthenticationPrincipal User user) {
        return authService.resetPassword(password, oldPassword, user);
    }

    @GetMapping("/resetAdmin/{password}/{oldPassword}/{id}")
    public boolean resetPassword(@PathVariable String password, @PathVariable String oldPassword, @PathVariable long id) {
        return authService.resetPasswordAdmin(password, oldPassword, id);
    }

    @GetMapping("/azienda/blocca/{id}/{azId}")
    public boolean bloccaTrasportatore(@PathVariable long id, @PathVariable long azId) {
        return userService.bloccaTrasportatore(id, azId);
    }

    @GetMapping("/azienda/sblocca/{id}/{azId}")
    public boolean sbloccaTrasportatore(@PathVariable long id, @PathVariable long azId) {
        return userService.sbloccaTrasportatore(id, azId);
    }

    @GetMapping("/trasportatore/azienda/byParams")
    public Page<Azienda> findAllAz(@RequestParam(defaultValue = "") String nome, @RequestParam(defaultValue = "") String email,
                                              @RequestParam(defaultValue = "") String citta,
                                              @RequestParam(defaultValue = "") String partitaIva,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int size,
                                              @RequestParam(defaultValue = "id") String orderBy,
                                              @RequestParam(defaultValue="DESC") String direction) {
        return userService.getAziendaByParams(nome,citta,email,partitaIva,page,size,orderBy,direction);
    }
}
