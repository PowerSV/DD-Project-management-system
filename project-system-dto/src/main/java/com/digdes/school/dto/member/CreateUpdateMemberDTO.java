package com.digdes.school.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Объект для создания сотрудника")
public class CreateUpdateMemberDTO {
    @Schema(description = "Идентификатор")
    private Long id;

    @Schema(description = "Имя (обязательное поле)")
    private String firstName;

    @Schema(description = "Фамилия (обязательное поле)")
    private String lastName;

    @Schema(description = "Отчество")
    private String middleName;

    @Schema(description = "Должность сотрудника")
    private String position;

    @Schema(description = "Электронная почта. Уникальное поле")
    private String email;

    @Schema(description = "Учетная запись. Уникальное поле")
    private String account;
}
