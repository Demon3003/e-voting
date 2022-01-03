package com.team.app.backend.persistance.model;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notification extends BaseForm {

    private boolean seen;

    public Notification(Long id, String text, boolean seen, Timestamp date, Long categoryId, Long userId) {
        super(id, text, date, categoryId, userId);
        this.seen = seen;
    }

    public Notification() {
        this.seen = false;
    }
}
