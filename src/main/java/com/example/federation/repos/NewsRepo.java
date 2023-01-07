package com.example.federation.repos;

import com.example.federation.domain.News;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NewsRepo extends CrudRepository<News, Integer> {
    List<News> findByName(String tag);
    News findById(long Id);
}
