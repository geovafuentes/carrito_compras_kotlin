import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
class Factura {

    fun generar(carrito: Carrito) {

        val impuesto = 0.13
        val subtotal = carrito.calcularTotal()
        val iva = subtotal * impuesto
        val total = subtotal + iva

        val contenido = StringBuilder()

        contenido.append("========= FACTURA =========\n")
        contenido.append("Fecha: ${LocalDateTime.now()}\n")
        carrito.obtenerItems().forEach {
            contenido.append("${it.producto.nombre} | Cantidad: ${it.cantidad} | Precio: ${it.producto.precio} | Total: ${it.total()}\n")
        }

        contenido.append("----------------------------\n")
        contenido.append("Subtotal: $subtotal\n")
        contenido.append("IVA (13%): $iva\n")
        contenido.append("TOTAL: $total\n")

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

        println("Factura guardada en: ${archivo.absolutePath}, 📄")
    }
}