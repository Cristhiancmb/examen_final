import java.io.*;
import java.util.*;

public class Inventario {
    private List<Producto> productos;

    public Inventario() {
        productos = new ArrayList<>();
    }

    public void cargarDesdeArchivo(String archivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\productos.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(",");
                if (datos.length == 5) {
                    String id = datos[0].trim();
                    String nombre = datos[1].trim();
                    String categoria = datos[2].trim();
                    double precio = Double.parseDouble(datos[3].trim());
                    int cantidad = Integer.parseInt(datos[4].trim());

                    Producto producto = new Producto(id, nombre, categoria, precio, cantidad);
                    productos.add(producto);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }


    public void guardarEnArchivo(String archivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\productos.txt" ))) {
            for (Producto producto : productos) {
                writer.write(producto.getId() + "," + producto.getNombre() + "," + producto.getCategoria() + ","
                        + producto.getPrecio() + "," + producto.getCantidad());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }


    public void agregarProducto(Producto producto) {
        productos.add(producto);
        guardarEnArchivo("productos.txt");
    }


    public void actualizarProducto(String id, Producto nuevoProducto) {
        for (Producto producto : productos) {
            if (producto.getId().equals(id)) {
                producto.setNombre(nuevoProducto.getNombre());
                producto.setCategoria(nuevoProducto.getCategoria());
                producto.setPrecio(nuevoProducto.getPrecio());
                producto.setCantidad(nuevoProducto.getCantidad());
                break;
            }
        }
        guardarEnArchivo("productos.txt");
    }


    public boolean eliminarProducto(String id) {
        Iterator<Producto> iterator = productos.iterator();
        while (iterator.hasNext()) {
            Producto p = iterator.next();
            if (p.getId().equals(id)) {
                iterator.remove();
                guardarEnArchivo("productos.txt");
                return true;
            }
        }
        return false;
    }




    public List<Producto> buscarPorCategoria(String categoria) {
        List<Producto> resultado = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getCategoria().equalsIgnoreCase(categoria)) {
                resultado.add(producto);
            }
        }
        return resultado;
    }


    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> resultados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                resultados.add(producto);
            }
        }
        return resultados;  // Devolver siempre una lista de productos
    }

    // Buscar por ID
    public Producto buscarPorId(String id) {
        for (Producto producto : productos) {
            if (producto.getId().equals(id)) {
                return producto;
            }
        }
        return null;
    }

    // Calcular el producto más caro
    public Producto obtenerProductoMasCaro() {
        if (productos.isEmpty()) return null;
        Producto maxProducto = productos.get(0);
        for (Producto producto : productos) {
            if (producto.getPrecio() > maxProducto.getPrecio()) {
                maxProducto = producto;
            }
        }
        return maxProducto;
    }

    // Calcular cantidad de productos por categoría
    public Map<String, Integer> cantidadPorCategoria() {
        Map<String, Integer> cantidadPorCategoria = new HashMap<>();
        for (Producto producto : productos) {
            cantidadPorCategoria.put(producto.getCategoria(),
                    cantidadPorCategoria.getOrDefault(producto.getCategoria(), 0) + producto.getCantidad());
        }
        return cantidadPorCategoria;
    }

    // Generar reporte
    public void generarReporte() {
        double valorTotal = 0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\reporte_inventario.txt"))) {
            for (Producto producto : productos) {
                writer.write(producto.toString());
                writer.newLine();
                valorTotal += producto.getPrecio() * producto.getCantidad();
            }
            writer.write("Valor total del inventario: " + valorTotal);
        } catch (IOException e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
        }
    }
}
