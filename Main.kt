private const val lineaDoble = "================================"

fun main() {
///TODO: Agregar mas productos y colocarles un precio mas normalizado, separar la logica de metodos para que el codigo sea mas legible.
    val productos = mutableListOf(
        Producto("Laptop", 800.0, 5),
        Producto("Mouse", 25.0, 20),
        Producto("Teclado", 50.0, 10)
    )

    val carrito = Carrito()
    val factura = Factura()

    while (true) {

        try {
            println()
            println(lineaDoble)
            println("           TIENDA")
            println(lineaDoble)
            println("  1. Ver productos")
            println("  2. Agregar producto")
            println("  3. Eliminar producto del carrito")
            println("  4. Ver carrito")
            println("  5. Finalizar Compra")
            println("  6. Salir")
            println(lineaDoble)
            print("Selecciona una opcion: ")

            val opcion = readlnOrNull()?.toIntOrNull()

            when (opcion) {

                1 -> {
                    println("\nProductos disponibles")

                    productos.forEachIndexed { index, p ->
                        println("${index + 1}. ${p.nombre} | Precio: ${p.precio} | Stock: ${p.cantidadDisponible}")
                    }
                    println("\nPresiona Enter para continuar...")
                    readlnOrNull()
                }

                2 -> {

                    while (true) {

                        println("\n--- AGREGAR PRODUCTOS ---")

                        productos.forEachIndexed { index, p ->

                            println("${index + 1}. ${p.nombre} | Precio: ${p.precio} | Stock: ${p.cantidadDisponible}")

                        }

                        println("0. Volver al menú")

                        println("Seleccione producto:")
                        val index = readlnOrNull()?.toIntOrNull()

                        if (index == null) {
                            println("Entrada inválida")
                            continue
                        }

                        if (index == 0) {
                            break
                        }

                        if (index < 1 || index > productos.size) {
                            println("Producto inválido")
                            continue
                        }

                        val producto = productos[index - 1]

                        println("Cantidad:")
                        val cantidad = readlnOrNull()?.toIntOrNull()

                        if (cantidad == null || cantidad <= 0) {
                            println("Cantidad inválida")
                            continue
                        }

                        if (cantidad > producto.cantidadDisponible) {
                            println("Stock insuficiente")
                            continue
                        }

                        carrito.agregarProducto(producto, cantidad)

                        println("Producto agregado al carrito")

                        println("\nCARRITO ACTUAL")
                        carrito.mostrarCarrito()

                        println("\nINVENTARIO ACTUALIZADO")
                        productos.forEachIndexed { i, p ->
                            println("${i + 1}. ${p.nombre} | Stock: ${p.cantidadDisponible}")
                        }
                    }
                }

                3 -> {
                    // se modifica eliminacion de producto para hacerlo por index
                    val items = carrito.obtenerItems()

                    if (items.isEmpty()) {
                        println("El carrito está vacío")
                    } else {
                        println("\n--- ELIMINAR PRODUCTO ---")
                        items.forEachIndexed { index, item ->
                            println("${index + 1}. ${item.producto.nombre} | Cantidad: ${item.cantidad}")
                        }
                        println("0. Volver al menú")

                        print("Seleccione producto a eliminar: ")
                        val index = readlnOrNull()?.toIntOrNull()

                        when {
                            index == null -> println("Entrada inválida")
                            index == 0 -> { /* volver */
                            }

                            index < 1 || index > items.size -> println("Opción inválida")
                            else -> {
                                carrito.eliminarProducto(items[index - 1].producto.nombre)
                                println("Producto eliminado del carrito")
                            }
                        }
                    }
                }

                4 -> {
                    val items = carrito.obtenerItems()
                    if (items.isEmpty()) {
                        println("El carrito está vacío")
                    } else {
                        //TODO: Mejorar vista de listado de carritos.
                        carrito.mostrarCarrito()
                    }
                    println("\nPresiona Enter para continuar...")
                    readlnOrNull()
                }

                5 -> {
                    val items = carrito.obtenerItems()
                    if (items.isEmpty()) {
                        println("El carrito está vacío, no puedes finalizar una compra sin productos")
                    } else {
                        val infoFactura = factura.generar(carrito)
                        println("Ingrese correo:")
                        val correo = readlnOrNull() ?: ""

                        EmailService.enviarFactura(correo, infoFactura)

                        carrito.vaciar()
                    }
                }
                6 -> {
                    println("Gracias por usar la tienda")
                    return
                }

                else -> println("Opción inválida")
            }

        } catch (e: Exception) {

            println("Ocurrió un error inesperado")

            Logger.logError(e.message ?: "Error desconocido")
        }
    }
}