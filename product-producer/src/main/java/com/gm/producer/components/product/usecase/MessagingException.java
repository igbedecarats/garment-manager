package com.gm.producer.components.product.usecase;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MessagingException extends RuntimeException {

    public MessagingException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
