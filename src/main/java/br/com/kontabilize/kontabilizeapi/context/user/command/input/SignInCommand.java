package br.com.kontabilize.kontabilizeapi.context.user.command.input;

import lombok.Data;

@Data
public class SignInCommand {

    private String email;
    private String password;
}
