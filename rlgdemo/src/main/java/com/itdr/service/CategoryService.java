package com.itdr.service;

import com.itdr.common.ServerResponse;
import org.springframework.stereotype.Service;

public interface CategoryService {
    ServerResponse getDeepCategpry(Integer categoryId);
}
