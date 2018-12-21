package com.lmdsoft.repository.search;

import com.lmdsoft.modules.sys.entity.UserEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * Spring Data Elasticsearch repository for the CoAcciTarget entity.
 */
@Component
public interface UserRepository extends ElasticsearchRepository<UserEntity, String> {
    UserEntity queryById(String id);

}
