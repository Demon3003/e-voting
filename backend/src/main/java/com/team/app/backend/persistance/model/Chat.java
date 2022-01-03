package com.team.app.backend.persistance.model;
import com.team.app.backend.persistance.model.CompositeKey.ChatKeys;
import com.team.app.backend.persistance.model.CompositeKey.ChatKeysEmbed;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;


@Entity
@IdClass(ChatKeys.class)
public class Chat {
    @Id
    public Long id;
    @Id
    public Long userIdTo;
    @Id
    public Long userIdFrom;
}
//@Entity
//public class Chat {
//    @EmbeddedId
//   ChatKeysEmbed chatKeysEmbed;
//}
