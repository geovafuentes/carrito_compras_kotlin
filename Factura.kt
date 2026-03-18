import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val valoresCompra = "%28s %9s\n"

class Factura {
    /// se modifica factura para poder enviarla por correo tambien.
    fun generar(carrito: Carrito): String {

        val impuesto = 0.13
        val subtotal = carrito.calcularTotal()
        val iva = subtotal * impuesto
        val total = subtotal + iva

        val contenido = StringBuilder()

        val fechaVoucher = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        val linea = "=".repeat(40)
        val lineaFina = "-".repeat(40)

        contenido.append("$linea\n")
        contenido.append("         FACTURA DE COMPRA\n")
        contenido.append("$linea\n")
        contenido.append("Fecha : $fechaVoucher\n")
        contenido.append("$lineaFina\n")
        //%-20s %6s %10s Utiilizado para formatear y alinear de manera mas ordenada el producto, cantidad y total
        contenido.append("%-20s %6s %10s\n".format("Producto", "Cant.", "Total"))
        contenido.append("$lineaFina\n")

        carrito.obtenerItems().forEach {
            contenido.append("%-20s %6d %9s\n".format(it.producto.nombre, it.cantidad, "$${"%.2f".format(it.total())}"))
        }

        contenido.append("$lineaFina\n")
        //%28s %9s\n Utiilizado para formatear y alinear de manera mas ordenada el subtotal
        contenido.append(valoresCompra.format("Subtotal:", "$${"%.2f".format(subtotal)}"))
        contenido.append(valoresCompra.format("IVA (13%):", "$${"%.2f".format(iva)}"))
        contenido.append("$lineaFina\n")
        contenido.append(valoresCompra.format("TOTAL:", "$${"%.2f".format(total)}"))
        contenido.append("$linea\n")
        contenido.append("    Gracias por tu compra!\n")
        contenido.append("$linea\n")

        println(contenido.toString())

        // Aqui esstoy creando la carpeta de las facturas 
        val carpeta = File("facturas")
        if (!carpeta.exists()) {
            carpeta.mkdir()
        }

        // esta linea me ayuda con la fecha de la factura
        val fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))

        val archivo = File(carpeta, "factura_$fecha.txt")

        archivo.writeText(contenido.toString())

        println("Factura guardada en: ${archivo.absolutePath}")
        return contenido.toString()
    }
}