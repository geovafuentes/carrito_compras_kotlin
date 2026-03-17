data class ItemCarrito(
    val producto: Producto,
    var cantidad: Int
) {
    fun total(): Double {
        return producto.precio * cantidad
    }
}