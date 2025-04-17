package io.github.vishvakalhara.handymanbackend.domains.dtos.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateMyBioRequest {

    @NotNull(message = "Bio is required!")
    @Size(min = 25, max= 224, message = "Bio should have minimum of {min} characters and maximum {max} characters.")
    private String bio;
}
