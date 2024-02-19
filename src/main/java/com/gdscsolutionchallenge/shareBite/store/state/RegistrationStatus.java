package com.gdscsolutionchallenge.shareBite.store.state;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.gdscsolutionchallenge.shareBite.exception.code.ErrorCode;
import com.gdscsolutionchallenge.shareBite.exception.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RegistrationStatus {
    PENDING_APPROVAL,
    APPROVAL,
    REJECTION;

    @JsonCreator
    public static RegistrationStatus findRegistrationStatus(String targetStatus) {
        for(RegistrationStatus registrationStatus : values()) {
            if(registrationStatus.name().equals(targetStatus)) return registrationStatus;
        }

        throw new ResourceNotFoundException(ErrorCode.NOT_FOUND_ROLE);
    }
}
