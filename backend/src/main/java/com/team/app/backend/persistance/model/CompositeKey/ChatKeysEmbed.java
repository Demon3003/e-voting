package com.team.app.backend.persistance.model.CompositeKey;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@Embeddable
public class ChatKeysEmbed implements Serializable {
    public Long id;
    public Long userIdTo;
    public Long userIdFrom;
}
