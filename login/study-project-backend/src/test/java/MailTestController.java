import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
public class MailTestController {

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/sendMail")
    public String sendMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("qzds@foxmail.com");
        message.setTo("3332618478@qq.com"); // 替换为您的测试邮箱
        message.setSubject("测试邮件");
        message.setText("配置成功！");

        try {
            mailSender.send(message);
            return "邮件发送成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "邮件发送失败: " + e.getMessage();
        }
    }
}