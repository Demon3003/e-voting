package com.team.app.backend.persistance.model.CompositeKey;


import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
public class ChatKeys implements Serializable {
    public Long id;
    public Long userIdTo;
    public Long userIdFrom;
}
