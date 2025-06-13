package com.integrador.grupoA.clients;

import com.integrador.grupoA.entities.Facturacion;
import com.integrador.grupoA.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.util.List;

//Falta corregir el name y port correctos
@FeignClient(name = "msvc-viajes", url = "http://localhost:8002/api/viaje")
public interface ViajeClientRest {

    //4- Consulta el total facturado en un rango de meses de cierto año
    @GetMapping("/facturacion")
    Facturacion getRevenueByMonthRange(@RequestParam LocalDate fechaInicio, @RequestParam LocalDate fechaFin);

    //5- Obtiene una lista de los usuarios frecuentes en un periodo de tiempo dado.
    @GetMapping("/usuarios-frecuentes")
    List<Usuario> getFrequentUsers(@RequestParam LocalDate fechaInicio, @RequestParam LocalDate fechaFin);

}
