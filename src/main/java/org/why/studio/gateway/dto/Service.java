package org.why.studio.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Service {

    protected int id;
    @NotBlank
    protected String name;
    @Min(1)
    protected int duration;
    @Min(300)
    protected int price;

}
