import java.io.File
import java.time.LocalDateTime

object Logger {

    fun logError(error: String) {

        val archivo = File("errores_log.txt")

        val mensaje = "[${LocalDateTime.now()}] ERROR: $error\n"

        archivo.appendText(mensaje)
    }
}