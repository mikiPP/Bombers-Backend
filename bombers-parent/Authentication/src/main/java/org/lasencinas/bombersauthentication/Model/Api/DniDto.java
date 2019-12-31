package org.lasencinas.bombersauthentication.Model.Api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
public class DniDto {

    @ApiModelProperty(example = "51543266-Z ", value = "Dni number")
    @NotNull
    @NotEmpty
    private String dni;


    @ApiModelProperty(value = "Dni owner ID")
    private Long userId;

}