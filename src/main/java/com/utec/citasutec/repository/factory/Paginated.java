package com.utec.citasutec.repository.factory;

import java.util.List;

public record Paginated<T>(List<T> items, long totalItems, int currentPage, int pageSize, int totalPages) {
}
