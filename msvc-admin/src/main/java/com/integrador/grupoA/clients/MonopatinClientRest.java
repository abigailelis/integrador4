package com.integrador.grupoA.clients;

import com.integrador.grupoA.models.Monopatin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

//Falta corregir el name y port correctos
@FeignClient(name = "msvc-monopatines", url = "http://localhost:8003/api/monopatine")
public interface MonopatinClientRest {

    //1- Genera un reporte de uso de monopatines por kilometros y establece cuáles necesitan mantenimiento.
    @GetMapping("/reporte")
    List<Monopatin> generateScooterReport(boolean verPausa);

    //3- Obtiene una lista de monopatines con más de X viajes en un cierto año
    @GetMapping("/masViajes")
    List<Monopatin> getScootersWithMostTrips(int cantidad, int anio);

}
