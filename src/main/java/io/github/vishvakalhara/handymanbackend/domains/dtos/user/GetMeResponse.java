package io.github.vishvakalhara.handymanbackend.domains.dtos.user;

import io.github.vishvakalhara.handymanbackend.domains.entities.Task;

import java.util.ArrayList;
import java.util.List;

public class GetMeResponse extends GetUserResponse {
    private String email;
    private List<Task> tasks = new ArrayList<>();

}
