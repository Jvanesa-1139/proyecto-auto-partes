package edu.pe.cibertec.proyecto_auto_partes.controller;

import edu.pe.cibertec.proyecto_auto_partes.dto.ProductoDetailDto;
import edu.pe.cibertec.proyecto_auto_partes.dto.ProductoDto;
import edu.pe.cibertec.proyecto_auto_partes.dto.UserDto;
import edu.pe.cibertec.proyecto_auto_partes.entity.FamiliaProducto;
import edu.pe.cibertec.proyecto_auto_partes.entity.GrupoProducto;
import edu.pe.cibertec.proyecto_auto_partes.entity.LineaProducto;
import edu.pe.cibertec.proyecto_auto_partes.repository.FamiliaRepository;
import edu.pe.cibertec.proyecto_auto_partes.repository.GrupoRepository;
import edu.pe.cibertec.proyecto_auto_partes.repository.LineaRepository;
import edu.pe.cibertec.proyecto_auto_partes.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceController {

    @Autowired
    MaintenanceService maintenanceService;

    @Autowired
    FamiliaRepository familiaRepository;

    @Autowired
    LineaRepository lineaRepository;

    @Autowired
    GrupoRepository grupoRepository;

    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }

    @GetMapping("/restricted")
    public String restricted(Model model){
        return "restricted";
    }


    @GetMapping("/start")
    public String start(Model model) {
        List<ProductoDto> productos = maintenanceService.findAllProducts();
        model.addAttribute("productos", productos);
        return "maintenance";
    }



    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {

        ProductoDetailDto productoDetailDto = maintenanceService.findDetailById(id);
        String descripcionFamilia = familiaRepository.findById(productoDetailDto.idFamilia())
                .map(FamiliaProducto::getDescripcion)
                .orElse("N/A");
        String descripcionGrupo = grupoRepository.findById(productoDetailDto.idGrupo())
                .map(GrupoProducto::getDescripcion)
                .orElse("N/A");
        String descripcionLinea = lineaRepository.findById(productoDetailDto.idLinea())
                .map(LineaProducto::getDescripcion)
                .orElse("N/A");

        model.addAttribute("producto", productoDetailDto);
        model.addAttribute("descripcionFamilia", descripcionFamilia);
        model.addAttribute("descripcionGrupo", descripcionGrupo);
        model.addAttribute("descripcionLinea", descripcionLinea);

        return "maintenance-detail";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        ProductoDetailDto productoDetailDto = maintenanceService.findDetailById(id);
        model.addAttribute("producto", productoDetailDto);
        model.addAttribute("familiaProducto", familiaRepository.findAll());
        model.addAttribute("lineaProducto", lineaRepository.findAll());
        model.addAttribute("grupoProducto", grupoRepository.findAll());
        return "maintenance-edit";
    }

    @PostMapping("/edit-confirm")
    public String edit(@ModelAttribute ProductoDetailDto film, Model model) {
        maintenanceService.updateProduct(film);
        return "redirect:/maintenance/start";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        boolean isDeleted = maintenanceService.deleteProductById(id);
        if (isDeleted) {
            model.addAttribute("message", "Se elimin0 correctamente.");
        } else {
            model.addAttribute("error", "No se pudo eliminar .");
        }
        return "redirect:/maintenance/start";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("productoCreateDto", new ProductoDetailDto(null, "", "",
                null, null, null, null));
        model.addAttribute("familias", familiaRepository.findAll());
        model.addAttribute("familiaProducto", familiaRepository.findAll());
        model.addAttribute("lineaProducto", lineaRepository.findAll());
        model.addAttribute("grupoProducto", grupoRepository.findAll());

        return "maintenance-create";
    }

    @PostMapping("/create-confirm")
    public String createConfirm(@ModelAttribute ProductoDetailDto productoDetailDto, Model model) {
        boolean isCreated = maintenanceService.createProduct(productoDetailDto);
        if (isCreated) {
            model.addAttribute("message", "Producto creada .");
        } else {
            model.addAttribute("error", "No se pudo crear.");
        }
        return "redirect:/maintenance/start";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("userAddDto", new UserDto(null, "", "",
                ""));
        return "maintenance-add";
    }

    @PostMapping("/add-confirm")
    public String addConfirm(@ModelAttribute UserDto userDto, Model model) {
        boolean isCreated = maintenanceService.createUser(userDto);
        if (isCreated) {
            model.addAttribute("message", "usuario creado .");
        } else {
            model.addAttribute("error", "No se pudo crear.");
        }
        return "redirect:/maintenance/start";
    }

    //Stored Procedure

    @PostMapping("/crear")
    public ResponseEntity<?> crearPedido(
            @RequestParam Integer clienteId,
            @RequestParam String productos
    ) {
        try {
            // productos en formato "1:2,3:1,5:3"
            // Significa: Producto 1 (cantidad 2), Producto 3 (cantidad 1), Producto 5 (cantidad 3)
            Integer pedidoId = maintenanceService.crearPedidoConDetalles(clienteId, productos);
            return ResponseEntity.ok().body(Map.of("pedidoId", pedidoId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}