package com.integrador.grupoA.services;

import com.integrador.grupoA.clients.MonopatinClientRest;
import com.integrador.grupoA.clients.UsuarioClientRest;
import com.integrador.grupoA.clients.ViajeClientRest;
import com.integrador.grupoA.entities.Facturacion;
import com.integrador.grupoA.models.Monopatin;
import com.integrador.grupoA.entities.Tarifa;
import com.integrador.grupoA.models.Usuario;
import com.integrador.grupoA.repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    TarifaRepository tarifaRepository;

    @Autowired
    UsuarioClientRest usuarioClientRest;

    @Autowired
    ViajeClientRest viajeClientRest;

    @Autowired
    MonopatinClientRest monopatinClientRest;

    //1- Genera un reporte de uso de monopatines por kilometros y establece cuáles necesitan mantenimiento.
    // Se comunica con el microservicio de monopatines.
    public List<Monopatin> generateScooterReport(boolean seePause){
        return monopatinClientRest.generateScooterReport(seePause);
    }

    //2- Habilita/Deshabilita el estado (activo) de la cuenta de un usuario.
    // Se comunica con el microservicio de usuarios.
    public Usuario updateUserStatus(Long id, Map<String, Boolean> status){
        return usuarioClientRest.updateUserStatus(id, status);
    }

    //3- Obtiene una lista de monopatines con más de X viajes en un cierto año
    // Se comunica con el microservicio de monopatines.
    public List<Monopatin> getScootersWithMostTrips(int tripCount, int year){
        return monopatinClientRest.getScootersWithMostTrips(tripCount, year);
    }

    //4- Consulta el total facturado en un rango de meses de cierto año
    // Se comunica con el microservicio de viajes.
    public Facturacion getRevenueByMonthRange(LocalDate startDate, LocalDate endDate){
        return viajeClientRest.getRevenueByMonthRange(startDate, endDate);
    }

    //5- Obtiene una lista de usuarios frecuentes en un periodo de tiempo según tipo de usuario.
    // Se comunica con los microservicios de usuarios y viajes
    public List<Usuario> getFrequentUsers(String type, LocalDate startDate, LocalDate endDate){
        List<Usuario> usersByType = usuarioClientRest.getUsersByType(type);
        List<Usuario> FrequentUsers = viajeClientRest.getFrequentUsers(startDate, endDate);
        return usersByType.stream()
                .filter(FrequentUsers::contains)
                .collect(Collectors.toList());
    }

    //6- Ajusta los precios de las tarifas y los habilita a partir de cierta fecha.
    @Transactional
    public Optional<Tarifa> adjustPrices(Long id, double price){
        Tarifa tarifaEncontrada = tarifaRepository.findById(id).orElse(null);
        if(tarifaEncontrada != null){
            tarifaEncontrada.setMonto(price);
            tarifaRepository.adjustPrices(id, price);
            return Optional.of(tarifaRepository.save(tarifaEncontrada));
        }
        return Optional.empty();
    }

    // Obtiene un usuario por su id.
    // Se comunica con el microservicio de usuarios.
    public Usuario getUserById(Long id){
        return usuarioClientRest.getUserById(id);
    }

    // Obtiene una tarifa por su id
    @Transactional(readOnly = true)
    public Tarifa getTarifaById(Long id){
        return tarifaRepository.findById(id).orElse(null);
    }

    // Obtiene una tarifa por su tipo
    @Transactional(readOnly = true)
    public Tarifa getTarifaByType(String type){
        return tarifaRepository.getTarifaByType(type);
    }

    // Agrega una nueva tarifa
    @Transactional
    public Tarifa addNewTarifa(Tarifa tarifa){
        return tarifaRepository.save(tarifa);
    }

    // Elimina una tarifa por su id
    @Transactional
    public Optional<Tarifa> deleteTarifa(Long id){
        Optional<Tarifa> tarifa = tarifaRepository.findById(id);
        tarifaRepository.deleteById(id);
        return tarifa;
    }

    // Modifica una tarifa por su id
    @Transactional
    public Optional<Tarifa> updateTarifa(Long id, Tarifa tarifa){
        Tarifa tarifaEncontrada = tarifaRepository.findById(id).orElse(null);
        if(tarifaEncontrada != null){
            tarifaEncontrada.setTipo_tarifa(tarifa.getTipo_tarifa());
            tarifaEncontrada.setMonto(tarifa.getMonto());
            return Optional.of(tarifaRepository.save(tarifaEncontrada));
        }
        return Optional.empty();
    }
}
