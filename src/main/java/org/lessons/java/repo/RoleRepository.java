package org.lessons.java.repo;

import org.lessons.java.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findByName(String name);
}
