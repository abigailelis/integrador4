package com.integrador.grupoA.clients;

import com.integrador.grupoA.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

//Falta modificar el name y port correcto.
@FeignClient(name= "msvc-usuarios", url = "http://localhost:8002/api/usuario")
public interface UsuarioClientRest {

    // Habilita y deshabilita la cuenta de un usuario por su id.
    @RequestMapping(method = RequestMethod.PATCH, value = "/estado/{id}", consumes = "application/json")
    Usuario updateUserStatus (@PathVariable Long id, @RequestBody Map<String, Boolean> estado);

    // Obtener una lista de los usuarios según tipo de usuario.
    @GetMapping("/tipo-suario")
    List<Usuario> getUsersByType (@RequestParam String tipoUsuario);

    // Obtiene un usuario por su id.
    @GetMapping("/{id}")
    Usuario getUserById (@PathVariable Long id);
}
