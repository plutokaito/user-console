package io.kaitoshy.projects.user.repository;

import io.kaitoshy.projects.user.domain.User;

import java.util.Collection;

/**
 *  用户存储仓库
 */
public interface UserRepository {

    boolean create(User user);

    boolean save(User user);

    boolean deleteById(Long userId);

    boolean update(User user);

    User getById(Long userId);

    User getByNameAndPassword(String userName, String password);

    Collection<User> getAll();
}