package com.shah.springjwttutorials.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author NORUL
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest {

  @NotEmpty
  @Email
  @Schema(example = "admin@gmail.com")
  private String email;
  @NotBlank
  @Schema(example = "1234")
  private String password;
}
