package com.rentitnow.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mail {

    private String mailTo;
    private String subject;
    private String message;
    private String toCc;
}
