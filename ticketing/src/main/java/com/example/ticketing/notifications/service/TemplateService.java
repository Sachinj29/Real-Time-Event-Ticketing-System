package com.example.ticketing.notifications.service;

import org.springframework.stereotype.Service;

@Service
public class TemplateService {
    public String ticketIssuedHtml(String name, String ticketCode) {
        return "<h1>Ticket Issued</h1><p>Hello " + name + ", your ticket code is <b>" + ticketCode + "</b></p>";
    }
}
