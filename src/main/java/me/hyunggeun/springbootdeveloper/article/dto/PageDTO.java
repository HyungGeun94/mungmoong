package me.hyunggeun.springbootdeveloper.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageDTO<T> {
    private List<T> content;       // 실제 데이터 리스트
    private long totalElements;    // 전체 요소 수
}