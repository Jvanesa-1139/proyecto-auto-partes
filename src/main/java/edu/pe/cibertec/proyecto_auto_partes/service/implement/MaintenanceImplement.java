package edu.pe.cibertec.proyecto_auto_partes.service.implement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import edu.pe.cibertec.proyecto_auto_partes.dto.ProductoDetailDto;
import edu.pe.cibertec.proyecto_auto_partes.dto.ProductoDto;
import edu.pe.cibertec.proyecto_auto_partes.dto.UserDto;
import edu.pe.cibertec.proyecto_auto_partes.entity.Producto;
import edu.pe.cibertec.proyecto_auto_partes.entity.User;
import edu.pe.cibertec.proyecto_auto_partes.repository.*;
import edu.pe.cibertec.proyecto_auto_partes.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCreator;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;
import java.util.ArrayList;

@Service
public class MaintenanceImplement implements MaintenanceService {
    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    FamiliaRepository familiaRepository;

    @Autowired
    LineaRepository lineaRepository;

    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<ProductoDto> findAllProducts() {
        List<ProductoDto> products = new ArrayList<>();
        Iterable<Producto> iterable = productoRepository.findAll();
        iterable.forEach(producto -> {
            ProductoDto productoDto = new ProductoDto(
                    producto.getCodigoProducto(),
                    producto.getNombreProducto(),
                    producto.getPrecioUnitario());

            products.add(productoDto);
        });
        return products;
    }

    @Override
    public ProductoDetailDto findDetailById(Integer id) {

        Optional<Producto> optional = productoRepository.findById(id);

        return optional.map(
                producto -> new ProductoDetailDto(
                        producto.getCodigoProducto(),
                        producto.getNombreProducto(),
                        producto.getSerie(),
                        producto.getFamiliaProducto().getIdFamilia(),
                        producto.getLineaProducto().getIdLinea(),
                        producto.getGrupoProducto().getIdGrupo(),
                        producto.getPrecioUnitario()
                )
        ).orElse(null);     }

    @Override
    public Boolean updateProduct(ProductoDetailDto productoDetailDto) {

        Optional<Producto> optional = productoRepository.findById(productoDetailDto.codigoProducto());
        return optional.map(
                producto -> {
                    producto.setNombreProducto(productoDetailDto.nombreProducto());
                    producto.setSerie(productoDetailDto.serie());
                    producto.setFamiliaProducto(familiaRepository.findById(productoDetailDto.idFamilia()).orElse(null));
                    producto.setLineaProducto(lineaRepository.findById(productoDetailDto.idLinea()).orElse(null));
                    producto.setGrupoProducto(grupoRepository.findById(productoDetailDto.idGrupo()).orElse(null));
                    producto.setPrecioUnitario(productoDetailDto.precioUnitario());
                    productoRepository.save(producto);
                    return true;
                }
        ).orElse(false);
    }



    @Override
    public Boolean createProduct(ProductoDetailDto productoDetailDto) {

        Producto producto = new Producto();
        producto.setNombreProducto(productoDetailDto.nombreProducto());
        producto.setSerie(productoDetailDto.serie());
        producto.setFamiliaProducto(familiaRepository.findById(productoDetailDto.idFamilia()).orElse(null));
        producto.setLineaProducto(lineaRepository.findById(productoDetailDto.idLinea()).orElse(null));
        producto.setGrupoProducto(grupoRepository.findById(productoDetailDto.idGrupo()).orElse(null));
        producto.setPrecioUnitario(productoDetailDto.precioUnitario());

        productoRepository.save(producto);
        return true;
    }
    @Override
    public Boolean deleteProductById(Integer id) {

        Optional<Producto> optional = productoRepository.findById(id);
        return optional.map(
                producto -> {
                    productoRepository.delete(producto);
                    return true;
                }
        ).orElse(false);
    }

    @Override
    public Optional<ProductoDetailDto> getProductById(int id) throws Exception {
        Optional<Producto> optional = productoRepository.findById(id);
        return optional.map(producto  -> new ProductoDetailDto(
                producto.getCodigoProducto(),
                producto.getNombreProducto(),
                producto.getSerie(),
                producto.getFamiliaProducto().getIdFamilia(),
                producto.getGrupoProducto().getIdGrupo(),
                producto.getLineaProducto().getIdLinea(),
                producto.getPrecioUnitario()
        ));
    }

    @Override
    public Boolean createUser(UserDto userDto) {
        String hashedPassword = passwordEncoder.encode(userDto.password());
        User user = new User();
        user.setUsername(userDto.username());
        user.setPassword(hashedPassword);
        user.setRole(userDto.role());
        userRepository.save(user);
        return true;
    }

    //Stored Procedure
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Integer crearPedidoConDetalles(Integer clienteId, String productos) throws Exception {
        try {
            // Definir el OUT parameter
            SqlOutParameter outParameter = new SqlOutParameter("p_idPedido", Types.INTEGER);

            // Preparar los parámetros del procedimiento almacenado
            MapSqlParameterSource inputParams = new MapSqlParameterSource()
                    .addValue("p_idCliente", clienteId)
                    .addValue("p_productos", productos);

            // Ejecutar el procedimiento almacenado
            Map<String, Object> result = jdbcTemplate.call(
                    new CallableStatementCreator() {
                        @Override
                        public CallableStatement createCallableStatement(Connection con) throws SQLException {
                            CallableStatement cs = con.prepareCall("{call CrearPedidoConDetalles(?, ?, ?)}");
                            cs.setInt(1, clienteId);
                            cs.setString(2, productos);
                            cs.registerOutParameter(3, Types.INTEGER);
                            return cs;
                        }
                    },
                    new ArrayList<SqlParameter>() {{
                        add(new SqlParameter("p_idCliente", Types.INTEGER));
                        add(new SqlParameter("p_productos", Types.VARCHAR));
                        add(outParameter);
                    }}
            );

            // Obtener y retornar el ID del pedido
            return (Integer) result.get("p_idPedido");

        } catch (DataAccessException e) {
            // Manejar errores de base de datos
            throw new Exception("Error al crear el pedido: " + e.getMessage(), e);
        }
    }
}


