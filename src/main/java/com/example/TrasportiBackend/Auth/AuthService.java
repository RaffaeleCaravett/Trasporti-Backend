package com.example.TrasportiBackend.Auth;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.TrasportiBackend.Security.JWTTools;
import com.example.TrasportiBackend.User.*;
import com.example.TrasportiBackend.enums.Role;
import com.example.TrasportiBackend.enums.Settore;
import com.example.TrasportiBackend.exceptions.AccessTokenInvalidException;
import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.exceptions.PasswordMismatchException;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.payloads.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.stylesheets.LinkStyle;

import java.io.IOException;
import java.util.*;

@Service
public class AuthService {
    @Autowired
    PasswordEncoder bcrypt;
    @Autowired
    Cloudinary cloudinary;
    @Autowired
    TrasporatoreRepository trasporatoreRepository;
    @Autowired
    AziendaRepository aziendaRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    JWTTools jwtTools;

    public TrasportatoreLoginSuccess trasportatoreLogin(UserLogin userLogin){
        Optional<Trasportatore> optionalTrasportatore =trasporatoreRepository.findByEmail(userLogin.email());
        if(!optionalTrasportatore.isPresent()){
            throw new UserNotFoundException("Trasportatore con email " + userLogin.email() + " non trovato in db");
        }
        if(!bcrypt.matches(userLogin.password(),optionalTrasportatore.get().getPassword())){
            throw new PasswordMismatchException("La password che hai inserito non coincide con la password che abbiamo in database");
        }

        TrasportatoreLoginSuccess trasportatoreLoginSuccess = new TrasportatoreLoginSuccess();
        Trasportatore trasportatore = optionalTrasportatore.get();
        Tokens tokens = generateTokens(trasportatore);
        trasportatoreLoginSuccess.setTokens(tokens);
        trasportatoreLoginSuccess.setTrasportatore(trasportatore);
        return trasportatoreLoginSuccess;
    }

    public AziendaLoginSuccess aziendaLogin(UserLogin userLogin){
        Optional<Azienda> optionalAzienda = aziendaRepository.findByEmail(userLogin.email());
        if(!optionalAzienda.isPresent()){
            throw new UserNotFoundException("Azienda con email " + userLogin.email() + " non trovato in db");
        }
        if(!bcrypt.matches(userLogin.password(),optionalAzienda.get().getPassword())){
            throw new PasswordMismatchException("La password che hai inserito non coincide con la password che abbiamo in database");
        }

        AziendaLoginSuccess aziendaLoginSuccess = new AziendaLoginSuccess();
        Azienda azienda = optionalAzienda.get();
        Tokens tokens = generateTokens(azienda);
        aziendaLoginSuccess.setTokens(tokens);
        aziendaLoginSuccess.setAzienda(azienda);
        return aziendaLoginSuccess;
    }
    public AdminLoginSuccess adminLogin(UserLogin userLogin){
        Optional<Admin> optionalAdmin = adminRepository.findByEmail(userLogin.email());
        if(!optionalAdmin.isPresent()){
            throw new UserNotFoundException("Admin con email " + userLogin.email() + " non trovato in db");
        }
        if(!bcrypt.matches(userLogin.password(),optionalAdmin.get().getPassword())){
            throw new PasswordMismatchException("La password che hai inserito non coincide con la password che abbiamo in database");
        }

        AdminLoginSuccess adminLoginSuccess = new AdminLoginSuccess();
        Admin admin = optionalAdmin.get();
        Tokens tokens = generateTokens(admin);
        adminLoginSuccess.setTokens(tokens);
        adminLoginSuccess.setAdmin(admin);
        return adminLoginSuccess;
    }
    public Tokens generateTokens(User u){
        return jwtTools.createTokens(u);
    }
    public Azienda verifyAziendaAccessToken(String token){
        try{
            return jwtTools.verifyAziendaToken(token);
        }catch (Exception e){
            throw new AccessTokenInvalidException("Il token non è valido.");
        }
    }

    public Trasportatore verifyTrasportatoreAccessToken(String token){
        try{
            return jwtTools.verifyTrasportatoreToken(token);
        }catch (Exception e){
            throw new AccessTokenInvalidException("Il token non è valido.");
        }
    }

    public Admin verifyAdminAccessToken(String token){
        try{
            return jwtTools.verifyAdminToken(token);
        }catch (Exception e){
            throw new AccessTokenInvalidException("Il token non è valido.");
        }
    }
    public Tokens verifyAziendaRefreshToken(String token){
        try{
            return jwtTools.verifyAziendaRefreshToken(token);
        }catch (Exception e){
            throw new AccessTokenInvalidException("Il refresh token non è valido.");
        }
    }

    public Tokens verifyTrasportatoreRefreshToken(String token){
        try{
            return jwtTools.verifyTrasportatoreRefreshToken(token);
        }catch (Exception e){
            throw new AccessTokenInvalidException("Il refresh token non è valido.");
        }
    }

    public Tokens verifyAdminRefreshToken(String token){
        try{
            return jwtTools.verifyAdminRefreshToken(token);
        }catch (Exception e){
            throw new AccessTokenInvalidException("Il refresh token non è valido.");
        }
    }

    public Trasportatore registerTrasportatore(TrasportatoreDTO trasportatoreDTO, MultipartFile multipartFile){
        if(trasporatoreRepository.findByEmail(trasportatoreDTO.email()).isPresent()||aziendaRepository.findByEmail(trasportatoreDTO.email()).isPresent()){
            throw new BadRequestException("Trasportatore con email " + trasportatoreDTO.email() + " già presente in db.");
        }
        Trasportatore trasportatore = new Trasportatore();
        trasportatore.setNome(trasportatoreDTO.nome());
        trasportatore.setCognome(trasportatoreDTO.cognome());
        trasportatore.setEta(trasportatoreDTO.eta());
        trasportatore.setCodiceFiscale(trasportatoreDTO.codiceFiscale());
        trasportatore.setCap(trasportatoreDTO.cap());
        trasportatore.setFlottaMezzi(trasportatoreDTO.flottaMezzi());
        trasportatore.setPartitaIva(trasportatoreDTO.partitaIva());
        trasportatore.setCitta(trasportatoreDTO.citta());
        trasportatore.setRegione(trasportatoreDTO.regione());
        trasportatore.setIndirizzo(trasportatoreDTO.indirizzo());
        trasportatore.setEmail(trasportatoreDTO.email());
        trasportatore.setPassword(bcrypt.encode(trasportatoreDTO.password()));
        trasportatore.setRole(Role.Trasportatore);
        trasportatore.setIsActive(1);

        try {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");

            trasportatore.setProfileImage(imageUrl);
            return trasporatoreRepository.save(trasportatore);
        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare l'immagine", e);
        }

    }
    public Azienda registerAzienda(AziendaDTO aziendaDTO,MultipartFile multipartFile){
        if(aziendaRepository.findByEmail(aziendaDTO.email()).isPresent()||trasporatoreRepository.findByEmail(aziendaDTO.email()).isPresent()){
            throw new BadRequestException("Azienda con email " + aziendaDTO.email() + " presente in db.");
        }
        Azienda azienda = new Azienda();
        azienda.setCap(aziendaDTO.cap());
        azienda.setCitta(aziendaDTO.citta());
        azienda.setRegione(aziendaDTO.regione());
        azienda.setIndirizzo(aziendaDTO.indirizzo());
        azienda.setEmail(aziendaDTO.email());
        azienda.setNomeAzienda(aziendaDTO.nomeAzienda());
        azienda.setFatturatoMedio(aziendaDTO.fatturatoMedio());
        azienda.setNumeroDipendenti(aziendaDTO.numeroDipendenti());
        azienda.setSettore(Settore.valueOf(aziendaDTO.settore()));
        azienda.setPartitaIva(aziendaDTO.partitaIva());
        azienda.setPassword(bcrypt.encode(aziendaDTO.password()));
        azienda.setRole(Role.Azienda);
        azienda.setIsActive(1);

        try {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");

            azienda.setProfileImage(imageUrl);
            return aziendaRepository.save(azienda);
        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare l'immagine", e);
        }
    }
    public Admin registerAdmin(AdminDTO adminDTO){
        if(adminRepository.findByEmail(adminDTO.email()).isPresent()){
            throw new BadRequestException("Admin con email " + adminDTO.email() + " già presente in db.");
        }
        if(!adminDTO.email().equals("raffaelecaravetta13@gmail.com")){
            throw new BadRequestException("Mi dispiace, non puoi registrarti qui.");
        }
        Admin admin = new Admin();
        admin.setCap(adminDTO.cap());
        admin.setCitta(adminDTO.citta());
        admin.setRegione(adminDTO.regione());
        admin.setIndirizzo(adminDTO.indirizzo());
        admin.setEmail(adminDTO.email());
        admin.setPassword(bcrypt.encode(adminDTO.password()));
        admin.setRole(Role.Admin);
        return adminRepository.save(admin);
    }
    public boolean resetPassword(String password,String oldPassword, User user){
        if(!bcrypt.matches(oldPassword, user.getPassword())){
            throw new PasswordMismatchException("La vecchia password non coincide con quella che abbiamo noi in database");
        }
        try {
            user.setPassword(bcrypt.encode(password));
            userRepository.save(user);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean resetPasswordAdmin(String password,String oldPassword, long id){
        User user = userRepository.findById(id).orElseThrow(()->new BadRequestException("User con id " +  id + " non trovato in database."));
        if(!bcrypt.matches(oldPassword, user.getPassword())){
            throw new PasswordMismatchException("La vecchia password non coincide con quella che abbiamo noi in database");
        }
        try {
            user.setPassword(password);
            userRepository.save(user);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public List<String> getSettori(){
        List<String> settori = new ArrayList<>();

        settori.add("MODA");
        settori.add("ALIMENTARI");
        settori.add("SUPERMARKET");
        settori.add("ELETTRONICA");
        settori.add("BRICOLAGE");
        settori.add("ALTRO");

        return settori;

    }

    public List<String> getCitta(){
        List<String> cities = new ArrayList<>();

        cities.add("a");
        cities.add("b");
        cities.add("c");
        cities.add("d");
        cities.add("e");
        cities.add("f");
        cities.add("g");
        cities.add("h");
        cities.add("i");

        return cities;

    }

    public String getRegioneByCity(String city){
        String region = "regione";
        return region;
    }
}
