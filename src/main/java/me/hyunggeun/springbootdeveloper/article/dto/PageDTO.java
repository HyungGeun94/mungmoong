package me.hyunggeun.springbootdeveloper.article.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PageDTO<T> {
    private List<T> content;       // 실제 데이터 리스트
    private int pageNumber;        // 현재 페이지 번호
    private int pageSize;          // 페이지 크기
    private long totalElements;    // 전체 요소 수

    // 생성자
    public PageDTO(List<T> content, int pageNumber, int pageSize, long totalElements) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
    }

    // Getters
    public List<T> getContent() {
        return content;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }
}