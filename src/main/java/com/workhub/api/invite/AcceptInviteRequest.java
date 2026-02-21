package com.workhub.api.invite;

import jakarta.validation.constraints.NotBlank;

public record AcceptInviteRequest (
        @NotBlank String token
){

}
