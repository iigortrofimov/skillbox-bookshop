package com.bookshop.mybookshop.dao;

import com.bookshop.mybookshop.domain.user.UserDataEdition;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataEditionRepository extends JpaRepository<UserDataEdition, UUID> {
}
