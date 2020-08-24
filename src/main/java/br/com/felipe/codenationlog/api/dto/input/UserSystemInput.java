package br.com.felipe.codenationlog.api.dto.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSystemInput {

  @NotNull
  @NotBlank
  @ApiParam(value = "Full name of user api", example = "My User Application")
  private String name;

  @NotNull
  @NotBlank
  @ApiParam(value = "Access username, for authentication", example = "user")
  private String username;

  @NotNull
  @NotBlank
  @ApiParam(value = "Password of username", example = "my_password")
  private String password;
}