package com.integrador.grupoA.clients;

import com.integrador.grupoA.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

//Falta modificar el name y port correcto.
@FeignClient(name= "msvc-usuarios", url = "http://localhost:8001")
public interface UsuarioClientRest {

    /**Habilita y deshabilita la cuenta de un usuario por su id.*/
    @RequestMapping(method = RequestMethod.PATCH, value = "/api/usuarios/{id}", consumes = "application/json")
    Usuario modificarEstadoUsuario(@PathVariable Long id, @RequestBody Map<String, Boolean> activo);

    /**
     * Obtener una lista de los usuarios según tipo de usuario.
     * Si el tipo de usuario es null, se obtienen todos los usuarios.
     * Ejemplo --> "api/usuarios/tipo-usuario?tipoUsuario=ADMIN"
     */
    @GetMapping("/api/usuarios/tipo-suario")
    List<Usuario> obtenerUsuariosPorTipo(@RequestParam String tipoUsuario);
}
