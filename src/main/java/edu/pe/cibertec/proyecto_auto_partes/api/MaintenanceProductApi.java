package edu.pe.cibertec.proyecto_auto_partes.api;

import edu.pe.cibertec.proyecto_auto_partes.dto.ProductoDetailDto;
import edu.pe.cibertec.proyecto_auto_partes.dto.ProductoDto;
import edu.pe.cibertec.proyecto_auto_partes.response.*;
import edu.pe.cibertec.proyecto_auto_partes.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/maintenance-producto")
    public class MaintenanceProductApi {


        @Autowired
        MaintenanceService maintenanceService;

        @GetMapping("/all")
        public FindProductsResponse findProducs() {

            try {
                List<ProductoDto> productos = maintenanceService.findAllProducts();
                if (!productos.isEmpty())
                    return new FindProductsResponse("01", null, productos);
                else
                    return new FindProductsResponse("02", "Users not found", null);

            } catch (Exception e) {
                e.printStackTrace();
                return new FindProductsResponse("99", "An error ocurred, please try again", null);

            }

        }

        @GetMapping("/detail")
        public FindProductResponse findProduct(@RequestParam(value = "id", defaultValue = "0") String id) {

            try {
                Optional<ProductoDetailDto> optional = maintenanceService.getProductById(Integer.parseInt(id));
                return optional.map(car ->
                        new FindProductResponse("01", null, car)
                ).orElse(
                        new FindProductResponse("02", "User not found", null)
                );

            } catch (Exception e) {
                e.printStackTrace();
                return new FindProductResponse("99", "An error ocurred, please try again", null);

            }

        }

        @PutMapping("/update")
        public UpdateProductResponse updateCar(@RequestBody ProductoDetailDto productoDetailDtoo) {

            try {
                if (maintenanceService.updateProduct(productoDetailDtoo))
                    return new UpdateProductResponse("01", null);
                else
                    return new UpdateProductResponse("02", "User not found");

            } catch (Exception e) {
                e.printStackTrace();
                return new UpdateProductResponse("99", "An error ocurred, please try again");

            }

        }

        @DeleteMapping("/delete/{id}")
        public DeleteProductResponse deleteProduct(@PathVariable String id) {

            try {
                if (maintenanceService.deleteProductById(Integer.parseInt(id)))
                    return new DeleteProductResponse("01", null);
                else
                    return new DeleteProductResponse("02", "User not found");

            } catch (Exception e) {
                e.printStackTrace();
                return new DeleteProductResponse("99", "An error ocurred, please try again");

            }

        }
        @PostMapping("/create")
        public CreateProductResponse createCar(@RequestBody ProductoDetailDto productoDetailDto) {

            try {
                if (maintenanceService.createProduct(productoDetailDto))
                    return new CreateProductResponse("01", null);
                else
                    return new CreateProductResponse("02", "User already exists");

            } catch (Exception e) {
                e.printStackTrace();
                return new CreateProductResponse("99", "An error ocurred, please try again");

            }

        }

        //Stored Procedure

        @PostMapping("/crear-pedido")
        public CrearPedidoResponse crearPedido(
                @RequestParam Integer clienteId,
                @RequestParam String productos
        ) {
            try {
                // productos en formato "1:2,3:1,5:3"
                // Significa: Producto 1 (cantidad 2), Producto 3 (cantidad 1), Producto 5 (cantidad 3)
                Integer pedidoId = maintenanceService.crearPedidoConDetalles(clienteId, productos);

                if (pedidoId != null) {
                    return new CrearPedidoResponse("01", null, pedidoId);
                } else {
                    return new CrearPedidoResponse("02", "No se pudo crear el pedido", null);
                }

            } catch (Exception e) {
                e.printStackTrace();
                return new CrearPedidoResponse("99", "Ocurrió un error, por favor intente nuevamente", null);
            }
        }

    }


