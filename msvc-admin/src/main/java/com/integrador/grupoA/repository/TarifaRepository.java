package com.integrador.grupoA.repository;

import com.integrador.grupoA.entities.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifaRepository extends JpaRepository <Tarifa, Long> {

    @Modifying
    @Query("UPDATE Tarifa t SET t.monto = :price WHERE t.id = :id")
    void adjustPrices(Long id, double price);

    @Query("SELECT t FROM Tarifa t WHERE t.tipo_tarifa = :tipoTarifa")
    Tarifa getTarifaByType(String tipoTarifa);
}
