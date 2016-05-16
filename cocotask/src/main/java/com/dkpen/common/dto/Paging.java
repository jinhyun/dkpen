package com.dkpen.common.dto;

public class Paging {
    private Long totalElements;
    private Long totalPages;
    private PagingRequest pagingRequest;

    public Paging(Long totalElements, PagingRequest pagingRequest) {
        if (totalElements == null) {
            throw new IllegalArgumentException("Paging totalElements must not be null.");
        }
        this.totalElements = totalElements;
        this.totalPages = (totalElements / pagingRequest.getPageSize()) + 1;
        this.pagingRequest = pagingRequest;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public PagingRequest getPagingRequest() {
        return pagingRequest;
    }
}
