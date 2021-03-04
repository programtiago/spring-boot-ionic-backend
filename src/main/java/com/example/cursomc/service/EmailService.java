package com.example.cursomc.service;

import com.example.cursomc.entity.Pedido;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

public interface EmailService {

    void sendOrderConfirmation(Pedido obj);

    void sendEmail(SimpleMailMessage msg);


}
