
import java.util.*
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

object EmailService {

    private val remitente = "proyectosudb.dsm2026@gmail.com"
    private val password = "jbqu pfey busr efwq"

    fun enviarFactura(correo: String, contenido: String) {
        if (correo == remitente || correo.isBlank() || contenido.isBlank()) {
            println("Cliente no solicito factura")
        }else {
            try {
                val props = Properties().apply {
                    put("mail.smtp.auth", "true")
                    put("mail.smtp.starttls.enable", "true")
                    put("mail.smtp.host", "smtp.gmail.com")
                    put("mail.smtp.port", "587")
                }

                val session = Session.getInstance(props, object : Authenticator() {
                    override fun getPasswordAuthentication() =
                        PasswordAuthentication(remitente, password)
                })

                val mensaje = MimeMessage(session).apply {
                    setFrom(InternetAddress(remitente))
                    setRecipients(Message.RecipientType.TO, InternetAddress.parse(correo))
                    subject = "Tu factura de compra UDB"
                    setText(contenido)
                }

                Transport.send(mensaje)
                println("Correo enviado a $correo")
            } catch (e: Exception) {
                println("Ocurrió un error no hemos podido enviarte el correo con tu factura.")
                Logger.logError(e.message ?: "Error desconocido!!")
            }
        }
    }
}