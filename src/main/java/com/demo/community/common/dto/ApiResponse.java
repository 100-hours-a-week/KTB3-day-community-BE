package com.demo.community.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ApiResponse<T> {

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

}
