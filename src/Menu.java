import java.util.*;

public class Menu {
    private static Scanner scanner = new Scanner(System.in);
    private static Inventario inventario = new Inventario();

    public static void main(String[] args) {
        inventario.cargarDesdeArchivo("productos.txt");
        while (true) {
            mostrarMenu();
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            switch (opcion) {
                case 1:
                    agregarProducto();
                    break;
                case 2:
                    actualizarProducto();
                    break;
                case 3:
                    eliminarProducto();
                    break;
                case 4:
                    buscarPorProducto();
                    break;
                case 5:
                    generarReporte();
                    break;
                case 6:
                    cantidadPorCategoria();
                    break;
                case 7:
                    productoMasCaro();
                    break;
                case 8:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Menú ---");
        System.out.println("1. Agregar producto");
        System.out.println("2. Actualizar producto");
        System.out.println("3. Eliminar producto");
        System.out.println("4. Buscar por categoría");
        System.out.println("5. Generar reporte");
        System.out.println("6. Cantidad de productos por categoría");
        System.out.println("7. Producto más caro");
        System.out.println("8. Salir");
        System.out.print("Elija una opción: ");
    }

    private static void agregarProducto() {
        System.out.print("Ingrese ID del producto: ");
        String id = scanner.nextLine();
        System.out.print("Ingrese nombre del producto: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese categoría: ");
        String categoria = scanner.nextLine();

        double precio = 0;
        int cantidad = 0;

        // Validación para ingresar un precio válido (numérico)
        boolean precioValido = false;
        while (!precioValido) {
            try {
                System.out.print("Ingrese precio: ");
                precio = Double.parseDouble(scanner.nextLine());
                if (precio < 0) {
                    throw new NumberFormatException("El precio no puede ser negativo.");
                }
                precioValido = true;  // Salir del bucle si el precio es válido
            } catch (NumberFormatException e) {
                System.out.println("Error: El precio debe ser un valor numérico válido. " + e.getMessage());
            }
        }

        // Validación para ingresar una cantidad válida (numérica)
        boolean cantidadValida = false;
        while (!cantidadValida) {
            try {
                System.out.print("Ingrese cantidad: ");
                cantidad = Integer.parseInt(scanner.nextLine());
                if (cantidad < 0) {
                    throw new NumberFormatException("La cantidad no puede ser negativa.");
                }
                cantidadValida = true;  // Salir del bucle si la cantidad es válida
            } catch (NumberFormatException e) {
                System.out.println("Error: La cantidad debe ser un valor numérico entero válido. " + e.getMessage());
            }
        }

        // Crear el producto y agregarlo al inventario
        Producto producto = new Producto(id, nombre, categoria, precio, cantidad);
        inventario.agregarProducto(producto);
        System.out.println("Producto agregado correctamente.");
    }



    private static void actualizarProducto() {
        // Solicitar el ID del producto que se desea actualizar
        System.out.print("Ingrese ID del producto a actualizar: ");
        String id = scanner.nextLine();

        // Buscar el producto por su ID
        Producto producto = inventario.buscarPorId(id);

        if (producto != null) {
            // Si el producto se encuentra, proceder con la actualización
            System.out.println("Producto encontrado: " + producto);

            // Solicitar nuevo nombre para el producto
            System.out.print("Ingrese nuevo nombre del producto: ");
            String nuevoNombre = scanner.nextLine();

            // Solicitar nueva categoría para el producto
            System.out.print("Ingrese nueva categoría del producto: ");
            String nuevaCategoria = scanner.nextLine();

            // Validación para actualizar el precio (numérico)
            boolean precioValido = false;
            double nuevoPrecio = 0;
            while (!precioValido) {
                try {
                    System.out.print("Ingrese nuevo precio: ");
                    nuevoPrecio = Double.parseDouble(scanner.nextLine());
                    if (nuevoPrecio < 0) {
                        throw new NumberFormatException("El precio no puede ser negativo.");
                    }
                    precioValido = true;  // Salir del bucle si el precio es válido
                } catch (NumberFormatException e) {
                    System.out.println("Error: El precio debe ser un valor numérico válido. " + e.getMessage());
                }
            }

            // Validación para actualizar la cantidad (numérica)
            boolean cantidadValida = false;
            int nuevaCantidad = 0;
            while (!cantidadValida) {
                try {
                    System.out.print("Ingrese nueva cantidad: ");
                    nuevaCantidad = Integer.parseInt(scanner.nextLine());
                    if (nuevaCantidad < 0) {
                        throw new NumberFormatException("La cantidad no puede ser negativa.");
                    }
                    cantidadValida = true;  // Salir del bucle si la cantidad es válida
                } catch (NumberFormatException e) {
                    System.out.println("Error: La cantidad debe ser un valor numérico entero válido. " + e.getMessage());
                }
            }

            // Crear un nuevo objeto Producto con los datos actualizados
            Producto nuevoProducto = new Producto(id, nuevoNombre, nuevaCategoria, nuevoPrecio, nuevaCantidad);

            // Actualizar el producto en el inventario
            inventario.actualizarProducto(id, nuevoProducto);

        } else {
            // Si el producto no se encuentra, mostrar un mensaje
            System.out.println("No se encontró un producto con el ID proporcionado.");
        }
    }



    private static void eliminarProducto() {
        System.out.print("Ingrese ID del producto a eliminar: ");
        String id = scanner.nextLine();

        // Buscar el producto por ID
        Producto producto = inventario.buscarPorId(id);

        // Si el producto no existe, lanzar una excepción o manejar el error
        if (producto != null) {
            // Si el producto existe, proceder con la eliminación
            boolean eliminado = inventario.eliminarProducto(id);
            if (eliminado) {
                System.out.println("Producto eliminado exitosamente.");
            } else {
                System.out.println("Error al eliminar el producto.");
            }
        } else {
            // Si el producto no existe, mostrar un mensaje de error
            System.out.println("Error: Producto con ID " + id + " no encontrado.");
        }
    }


    private static void buscarPorProducto() {
        // Mostrar un menú para que el usuario elija el tipo de búsqueda
        System.out.println("Seleccione el tipo de búsqueda:");
        System.out.println("1. Buscar por ID");
        System.out.println("2. Buscar por Categoría");
        System.out.println("3. Buscar por Nombre");
        System.out.print("Opción: ");
        int opcion = Integer.parseInt(scanner.nextLine());

        switch (opcion) {
            case 1:
                buscarPorId();
                break;
            case 2:
                buscarPorCategoria();
                break;
            case 3:
                buscarPorNombre();
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private static void buscarPorId() {
        System.out.print("Ingrese el ID del producto para buscar: ");
        String id = scanner.nextLine();
        Producto producto = inventario.buscarPorId(id);

        if (producto == null) {
            System.out.println("No se encontró un producto con ese ID.");
        } else {
            System.out.println(producto);
        }
    }

    private static void buscarPorCategoria() {
        System.out.print("Ingrese categoría para buscar: ");
        String categoria = scanner.nextLine();
        List<Producto> productos = inventario.buscarPorCategoria(categoria);

        if (productos.isEmpty()) {
            System.out.println("No se encontraron productos en esta categoría.");
        } else {
            productos.forEach(System.out::println);
        }
    }

    private static void buscarPorNombre() {
        System.out.print("Ingrese nombre del producto para buscar: ");
        String nombre = scanner.nextLine();
        List<Producto> productos = (List<Producto>) inventario.buscarPorNombre(nombre);

        if (productos.isEmpty()) {
            System.out.println("No se encontraron productos con ese nombre.");
        } else {
            productos.forEach(System.out::println);
        }
    }


    private static void generarReporte() {
        inventario.generarReporte();
        System.out.println("Reporte generado.");
    }

    private static void cantidadPorCategoria() {
        Map<String, Integer> cantidadPorCategoria = inventario.cantidadPorCategoria();
        cantidadPorCategoria.forEach((categoria, cantidad) ->
                System.out.println("Categoría: " + categoria + " - Total: " + cantidad));
    }

    private static void productoMasCaro() {
        Producto maxProducto = inventario.obtenerProductoMasCaro();
        if (maxProducto != null) {
            System.out.println("El producto más caro es: " + maxProducto);
        } else {
            System.out.println("No hay productos en el inventario.");
        }
    }
}