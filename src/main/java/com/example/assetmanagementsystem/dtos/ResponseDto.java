package com.example.assetmanagementsystem.dtos;

import com.example.assetmanagementsystem.exception.ErrorDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {
    private Integer status;
    private List<T> data;
    private Long count;
    private Long total;
    private String message;
    private List<ErrorDto> errors;

    @Override
    public String toString() {
        return "ResponseDto{" +
                "status=" + status +
                ", data=" + data +
                ", count=" + count +
                ", total=" + total +
                ", message=" + message +
                ", errors=" + errors +
                '}';
    }
}

