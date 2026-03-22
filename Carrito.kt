class Carrito {

    private val items = mutableListOf<ItemCarrito>()

    fun agregarProducto(producto: Producto, cantidad: Int) {
        val existente = items.find { it.producto.nombre == producto.nombre }

        if (existente != null) {
            existente.cantidad += cantidad
        } else {
            items.add(ItemCarrito(producto, cantidad))
        }

        producto.cantidadDisponible -= cantidad
    }

    fun eliminarProducto(nombre: String) {
        val item = items.find { it.producto.nombre == nombre }

        if (item != null) {
            item.producto.cantidadDisponible += item.cantidad
            items.remove(item)
        }
    }

    fun mostrarCarrito() {
        // 1. Un encabezado llamativo
        println("\n" + "=".repeat(65))
        println(" ".repeat(22) + "CARRITO DE COMPRAS")
        println("=".repeat(65))

        // 2. Nombres de las columnas alineados
        // padEnd alinea a la izquierda (ideal para texto)
        // padStart alinea a la derecha (ideal para números/dinero)
        println(
            "Producto".padEnd(25) +
                    "Cantidad".padStart(10) +
                    "Precio U.".padStart(15) +
                    "Total".padStart(15)
        )
        println("-".repeat(65))

        // 3. Iterar los items manteniendo el mismo ancho de columnas
        items.forEach {
            // take(23) asegura que si un nombre es súper largo, no rompa la tabla
            val nombre = it.producto.nombre.take(23).padEnd(25)
            val cantidad = it.cantidad.toString().padStart(10)
            val precioU = "$${it.producto.precio}".padStart(15)
            val totalItem = "$${it.total()}".padStart(15)

            println(nombre + cantidad + precioU + totalItem)
        }

        // 4. Pie de la tabla con el total
        println("-".repeat(65))
        val textoTotal = "TOTAL GENERAL:"
        val valorTotal = "$${calcularTotal()}"

        // Sumamos el ancho de las primeras 3 columnas (25 + 10 + 15 = 50) para alinear el total
        println(textoTotal.padEnd(50) + valorTotal.padStart(15))
        println("=".repeat(65) + "\n")
    }

    fun calcularTotal(): Double {
        return items.sumOf { it.total() }
    }

    fun obtenerItems(): List<ItemCarrito> {
        return items
    }

    fun vaciar() {
        items.clear()
    }
}