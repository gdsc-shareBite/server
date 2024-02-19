package com.gdscsolutionchallenge.shareBite.store.dto;

import com.gdscsolutionchallenge.shareBite.store.state.RegistrationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStoreRegistrationStatusDto {
    @NotNull
    private RegistrationStatus registrationStatus;
}
