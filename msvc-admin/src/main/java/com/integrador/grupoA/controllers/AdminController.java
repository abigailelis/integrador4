package com.integrador.grupoA.controllers;

import com.integrador.grupoA.entities.Facturacion;
import com.integrador.grupoA.models.Monopatin;
import com.integrador.grupoA.entities.Tarifa;
import com.integrador.grupoA.models.Usuario;
import com.integrador.grupoA.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    //1- Genera un reporte de uso de monopatines por kilometros y establece cuáles necesitan mantenimiento.
    @GetMapping("/monopatines/reporte")
    public ResponseEntity<List<Monopatin>> generateScooterReport(@RequestParam boolean seePause){
        List<Monopatin> scooterReport = adminService.generateScooterReport(seePause);

        if(scooterReport.isEmpty())
                return ResponseEntity.noContent().build();
        return ResponseEntity.ok(scooterReport);
    }

    //2- Habilita/Deshabilita el estado (activo) de la cuenta de un usuario.
    @PatchMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> updateUserStatus(@PathVariable Long id, @RequestBody Map<String, Boolean> status){
        if(adminService.getUserById(id) == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(adminService.updateUserStatus(id, status));
    }

    //3- Obtiene una lista de monopatines con más de X viajes en un cierto año
    @GetMapping("/monopatines/masViajes")
    public ResponseEntity<List<Monopatin>> getScootersWithMostTrips(@RequestParam int tripCount, @RequestParam String date){
        int year = LocalDate.parse(date).getYear();
        List<Monopatin> scooterReport = adminService.getScootersWithMostTrips(tripCount, year);

        if(scooterReport.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(scooterReport);
    }

    //4- Consulta el total facturado en un rango de meses de cierto año
    @GetMapping("/viajes/facturacion")
    public ResponseEntity<Facturacion> getRevenueByMonthRange(@RequestParam String startDate, @RequestParam String endDate){
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        if(start.getYear() != end.getYear() || start.isAfter(end))
            throw new IllegalArgumentException("Las fechas ingresadas no son válidas.");

        Facturacion revenueByMonthRange = adminService.getRevenueByMonthRange(start, end);
        if(revenueByMonthRange == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(revenueByMonthRange);
    }

    //5- Obtiene una lista de usuarios frecuentes en un periodo de tiempo según tipo de usuario.
    @GetMapping("/usuarios/frecuentes")
    public ResponseEntity<List<Usuario>> getFrequentUsers(@RequestParam String type, @RequestParam String startDate, @RequestParam String endDate){
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);

        if (start.isAfter(end))
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin.");

        List<Usuario> frequentUsers = adminService.getFrequentUsers(type, start, end);
        if(frequentUsers.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(frequentUsers);
    }

    //6- Ajusta los precios de las tarifas y los habilita a partir de cierta fecha.
    @PutMapping("")
    public ResponseEntity<Optional<Tarifa>> adjustPrices(@RequestBody Tarifa tarifa, @RequestBody LocalDate modificationDate){
        if(!modificationDate.isEqual(LocalDate.now()) || modificationDate.isBefore(LocalDate.now()))
            return null;

        Tarifa tarifaById = adminService.getTarifaById(tarifa.getId());

        if(tarifaById == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(adminService.adjustPrices(tarifa.getId(), tarifa.getMonto()));
    }

    // 7- Da de alta una nueva tarifa
    @PostMapping("")
    public ResponseEntity<Tarifa> addNewTarifa(@RequestBody Tarifa tarifa){
        Tarifa tarifaByType = adminService.getTarifaByType(tarifa.getTipo_tarifa());
        if(tarifaByType != null)
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .header("Error-Message", "Ya existe una tarifa con el tipo: " + tarifa.getTipo_tarifa())
                    .build();
        return ResponseEntity.ok(adminService.addNewTarifa(tarifa));
    }

    // 8- Elimina una tarifa
    @DeleteMapping("/{id}")
    public ResponseEntity<Optional<Tarifa>> deleteTarifa(@PathVariable Long id){
        Tarifa tarifaById = adminService.getTarifaById(id);
        if(tarifaById == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(adminService.deleteTarifa(id));
    }

    //9- Modifica una tarifa
    @PutMapping("/{id}")
    public ResponseEntity<Optional<Tarifa>> updateTarifa(@PathVariable Long id, @RequestBody Tarifa tarifa){
        Tarifa tarifaById = adminService.getTarifaById(id);
        if(tarifaById == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(adminService.updateTarifa(id, tarifa));
    }
}
