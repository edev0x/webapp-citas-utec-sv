package com.utec.citasutec.repository.factory;

import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Paginated<T> {
    private final List<T> items;
    private final long totalItems;
    private final int currentPage;
    private final int pageSize;
    private final int totalPages;
}
