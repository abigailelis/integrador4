package com.integrador.grupoA.services;

import com.integrador.grupoA.clients.UsuarioClientRest;
import com.integrador.grupoA.clients.ViajeClientRest;
import com.integrador.grupoA.models.Usuario;
import com.integrador.grupoA.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UsuarioClientRest usuarioClientRest;

    @Autowired
    ViajeClientRest viajeClientRest;

    public Usuario modificarEstadoUsuario(Long id, Map<String, Boolean> estado){
        return usuarioClientRest.modificarEstadoUsuario(id, estado);
    }

    public List<Usuario> obtenerUsuariosFrecuentes(String tipo_usuario, LocalDate fechaInicio, LocalDate fechaFin){
        List<Usuario> usuariosPorTipo = usuarioClientRest.obtenerUsuariosPorTipo(tipo_usuario);
        List<Usuario> usuariosFrecuentes = viajeClientRest.obtenerUsuariosFrecuentes(fechaInicio, fechaFin);
        return usuariosPorTipo.stream()
                .filter(usuariosFrecuentes::contains)
                .collect(Collectors.toList());
    }

}
