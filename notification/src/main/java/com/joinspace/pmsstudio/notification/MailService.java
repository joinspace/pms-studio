package com.joinspace.pmsstudio.notification;

import com.joinspace.pmsstudio.domain.User;

public interface MailService {
    void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml);

    void sendEmailFromTemplate(User user, String templateName, String titleKey);

    void sendActivationEmail(User user);

    void sendCreationEmail(User user);

    void sendPasswordResetMail(User user);
}
