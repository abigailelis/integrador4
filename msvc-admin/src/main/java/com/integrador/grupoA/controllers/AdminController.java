package com.integrador.grupoA.controllers;

import com.integrador.grupoA.models.Usuario;
import com.integrador.grupoA.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PatchMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> modificarEstadoUsuario(@PathVariable Long id, @RequestBody Map<String, Boolean> estado){
        return ResponseEntity.ok(adminService.modificarEstadoUsuario(id, estado));
    }

    @GetMapping("/usuarios/frecuentes")
    public ResponseEntity<List<Usuario>> obtenerUsuariosFrecuentes(@RequestParam String tipo_usuario, @RequestParam String fechaInicio, @RequestParam String fechaFin){
            LocalDate inicio = LocalDate.parse(fechaInicio);
            LocalDate fin = LocalDate.parse(fechaFin);

        if (inicio.isAfter(fin))
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin.");

        return ResponseEntity.ok(adminService.obtenerUsuariosFrecuentes(tipo_usuario, inicio, fin));
    }
}
