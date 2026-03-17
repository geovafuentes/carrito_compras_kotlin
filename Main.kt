fun main() {

    val productos = mutableListOf(
        Producto("Laptop", 800.0, 5),
        Producto("Mouse", 25.0, 20),
        Producto("Teclado", 50.0, 10)
    )

    val carrito = Carrito()
    val factura = Factura()

    while (true) {

        try {

            println("\n==== TIENDA ====")
            println("1. Ver productos")
            println("2. Agregar producto")
            println("3. Eliminar producto del carrito")
            println("4. Ver carrito")
            println("5. Comprar")
            println("6. Salir")

            val opcion = readLine()?.toIntOrNull()

            when (opcion) {

                1 -> {
                    println("\nProductos disponibles")

                    productos.forEachIndexed { index, p ->
                        println("${index + 1}. ${p.nombre} | Precio: ${p.precio} | Stock: ${p.cantidadDisponible}")
                    }
                }

            2 -> {

                while (true) {

                    println("\n--- AGREGAR PRODUCTOS ---")

                    productos.forEachIndexed { index, p ->

                        println("${index + 1}. ${p.nombre} | Precio: ${p.precio} | Stock: ${p.cantidadDisponible}")

                    }

                    println("0. Volver al menú")

                    println("Seleccione producto:")
                    val index = readLine()?.toIntOrNull()

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
                    val cantidad = readLine()?.toIntOrNull()

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

                    println("Nombre del producto a eliminar:")
                    val nombre = readLine() ?: ""

                    carrito.eliminarProducto(nombre)
                }

                4 -> carrito.mostrarCarrito()

                5 -> {

                    factura.generar(carrito)

                    println("Ingrese correo:")
                    val correo = readLine() ?: ""

                    EmailService.enviarFactura(correo)

                    carrito.vaciar()
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