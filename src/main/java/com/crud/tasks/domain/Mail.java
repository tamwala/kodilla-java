package com.crud.tasks.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Mail {
    private final String mailTo;
    private final String subject;
    private final String message;
    private final String toCc;

    @Builder
    public Mail(final String mailTo, final String subject, final String message, final String toCc) {
        this.mailTo = mailTo;
        this.subject = subject;
        this.message = message;
        this.toCc = toCc;
    }

    public String getMailTo() {
        return mailTo;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public String getToCc() {
        return toCc;
    }
}
