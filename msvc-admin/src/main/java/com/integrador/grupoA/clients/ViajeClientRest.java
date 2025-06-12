package com.integrador.grupoA.clients;

import com.integrador.grupoA.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.util.List;

//Falta corregir el name y port correctos
@FeignClient(name = "msvc-viajes", url = "http://localhost:8002")
public interface ViajeClientRest {

    //Obtiene una lista de los usuarios frecuentes en un periodo de tiempo dado.
    @GetMapping("/api/viajes/usuarios-frecuentes")
    List<Usuario> obtenerUsuariosFrecuentes(@RequestParam LocalDate fechaInicio, @RequestParam LocalDate fechaFin);

}
