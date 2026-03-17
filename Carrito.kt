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
        println("\n Carrito de Compras")

        items.forEach {
            println("${it.producto.nombre} | Cantidad: ${it.cantidad} | Precio Unitario: ${it.producto.precio} | Total: ${it.total()}")
        }

        println("TOTAL GENERAL: $${calcularTotal()}")
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