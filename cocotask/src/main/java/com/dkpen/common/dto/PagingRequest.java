package com.dkpen.common.dto;

public class PagingRequest {
    public static final Long PAGE_DEFAULT_SIZE = 3L;
    public static final Long PAGE_DEFAULT_NUMBER = 1L;

    private Long pageSize;  // 최대결과 개수 (queryDsl: limit 과 동일)
    private Long pageNumber;
    private Long offset;    // 삭제하는 개수 (queryDsl: 결과의 시작행)

    public PagingRequest() {
        this.pageSize = PAGE_DEFAULT_SIZE;
        this.pageNumber = PAGE_DEFAULT_NUMBER;
        setOffset();
    }

    public PagingRequest(Long pageNumber) {
        if (pageNumber == null) {
            this.pageNumber = 1L;
        } else {
            this.pageNumber = pageNumber;
        }
        this.pageSize = PAGE_DEFAULT_SIZE;
        setOffset();
    }

    public PagingRequest(Long pageNumber, Long pageSize) {
        if (pageNumber == null) {
            this.pageNumber = 1L;
        } else {
            this.pageNumber = pageNumber;
        }

        if (pageSize <= 0) {
            throw new IllegalArgumentException("PageSize must be greater than 0.");
        }
        this.pageSize = pageSize;
        setOffset();
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
        setOffset();
    }

    public Long getPageSize() {
        return this.pageSize;
    }

    public void setPageNumber(Long pageNumber) {
        this.pageNumber = pageNumber;
        setOffset();
    }

    public Long getPageNumber() {
        return this.pageNumber;
    }

    private void setOffset() {
        this.offset = (this.pageNumber * this.pageSize) - this.pageSize;
    }

    public Long getOffset() {
        return this.offset;
    }
}
