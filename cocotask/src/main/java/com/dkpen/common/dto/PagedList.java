package com.dkpen.common.dto;

import java.util.List;

public class PagedList<E> {
    private List<E> source;
    private Paging paging;

    public PagedList(List<E> source, Paging paging) {
        this.source = source;
        this.paging = paging;
    }

    public void setSource(List<E> source) {
        this.source = source;
    }

    public List<E> getSource() {
        return source;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public Paging getPaging() {
        return paging;
    }
}
