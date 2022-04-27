package infrastructure

import java.util.*
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class MailService {
    fun send(recipientEmail: String, messageBody: String, subject: String) {
        val session = Session.getDefaultInstance(setProperties(), null)
        try {
            val mimeMessage = createTextMessage(session, recipientEmail, messageBody, subject)
            send(session, mimeMessage)

        } catch (messagingException: MessagingException) {
            messagingException.printStackTrace()
        }
    }

    private fun send(session: Session, mimeMessage: MimeMessage) {
        val smtpTransport = session.getTransport("smtp")
        smtpTransport.connect()
        smtpTransport.sendMessage(mimeMessage, mimeMessage.allRecipients)
        smtpTransport.close()
    }

    private fun createTextMessage(
        session: Session?,
        recipientEmail: String,
        messageBody: String,
        subject: String
    ): MimeMessage {
        val mimeMessage = MimeMessage(session)
        mimeMessage.setFrom(InternetAddress("me@test.com"))
        mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false))
        mimeMessage.setText(messageBody)
        mimeMessage.subject = subject
        mimeMessage.sentDate = Date()
        return mimeMessage
    }

    private fun setProperties(): Properties {
        val properties = Properties()
        properties.put("mail.smtp.host", "127.0.0.1")
        properties.put("mail.smtp.port", "25")
        return properties
    }

}
