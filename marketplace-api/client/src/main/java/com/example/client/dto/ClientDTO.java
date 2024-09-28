package com.example.client.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    @NotNull
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 1, max = 100, message = "Name dolzno bit bolshe 1 i do 100")
    private String name;

    @NotNull
    private LocalDateTime createdAt;
}
