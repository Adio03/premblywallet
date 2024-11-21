package org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.controller.identity;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springcorepractice.walletapplication.application.input.identity.CreateUserUseCase;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.model.identity.UserIdentity;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.request.UserIdentityRequest;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.ApiResponse;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.mapper.IdentityMapper;
import org.springcorepractice.walletapplication.infrastructure.enums.constants.ControllerConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/userIdentity")
@RequiredArgsConstructor
@Slf4j
public class IdentityManagerController {
    private final CreateUserUseCase createUserUseCase;
    private final IdentityMapper identityMapper;

    @PostMapping("/createUser")
    public ResponseEntity<ApiResponse<?>> createUserIdentity(@RequestBody UserIdentityRequest userIdentityRequest) throws IdentityManagerException {
        log.info("USERIDENTITY -----> {}", userIdentityRequest);
        UserIdentity userIdentityResponse = identityMapper.mapUserIdentityRequestToUserIdentity(userIdentityRequest);
       UserIdentity response = createUserUseCase.signup(userIdentityResponse);
        return ResponseEntity.ok(ApiResponse.<UserIdentity>builder().
                data(response).
                message(ControllerConstant.RESPONSE_IS_SUCCESSFUL.getMessage()).
                statusCode(HttpStatus.CREATED.name()).build());

    }
}
