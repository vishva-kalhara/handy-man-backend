package io.github.vishvakalhara.handymanbackend.domains.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class OnlyUserId {
    private UUID id;
}
