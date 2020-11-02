package com.pm.ecommerce.order_service.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PagedResponse<T> {
    int totalPages;
    int currentPage;
    int itemsPerPage;
    List<T> data;
    public PagedResponse() {
        data = new ArrayList<>();
    }
    public PagedResponse(int totalPages, int currentPage, int itemsPerPage, List<T> data) {
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.itemsPerPage = itemsPerPage;
        this.data = data;
    }
}
