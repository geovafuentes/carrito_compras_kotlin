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
        val anchoTotal = 40
        val linea = "=".repeat(anchoTotal)
        val lineaFina = "-".repeat(anchoTotal)

        contenido.append("$linea\n")
        contenido.append("         FACTURA GUANACOTECH\n")
        contenido.append("$linea\n")
        contenido.append("Fecha : $fechaVoucher\n")
        contenido.append("$lineaFina\n")

        // Encabezado de la tabla (20 chars para producto, 6 para cantidad, 12 para total)
        contenido.append("%-20s %6s %12s\n".format("Producto", "Cant.", "Total"))
        contenido.append("$lineaFina\n")

        carrito.obtenerItems().forEach {
            // EL TRUCO: Cortamos el nombre a un máximo de 19 caracteres.
            // Así aseguramos que nunca sobrepase el límite de 20 definido en el format.
            val nombreCorto = it.producto.nombre.take(19)
            val totalFormateado = "$${"%.2f".format(it.total())}"

            contenido.append("%-20s %6d %12s\n".format(nombreCorto, it.cantidad, totalFormateado))
        }

        contenido.append("$lineaFina\n")

        val valoresCompra = "%26s %13s\n"

        contenido.append(valoresCompra.format("Subtotal:", "$${"%.2f".format(subtotal)}"))
        contenido.append(valoresCompra.format("IVA (13%):", "$${"%.2f".format(iva)}"))
        contenido.append("$lineaFina\n")
        contenido.append(valoresCompra.format("TOTAL:", "$${"%.2f".format(total)}"))

        contenido.append("$linea\n")
        contenido.append("        ¡Gracias por tu compra!\n")
        contenido.append("$linea\n")

        println(contenido.toString())

        // Creación de la carpeta de las facturas
        val carpeta = File("facturas")
        if (!carpeta.exists()) {
            carpeta.mkdir()
        }

        // Fecha para el nombre del archivo
        val fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
        val archivo = File(carpeta, "factura_$fecha.txt")

        // Guardado del archivo
        archivo.writeText(contenido.toString())

        println("Factura guardada exitosamente en: ${archivo.absolutePath}")

        return contenido.toString()
    }
}