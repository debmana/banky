package com.deborasroka.banky.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deborasroka.banky.model.Role;
import com.deborasroka.banky.repo.RoleRepository;

@Service
@Transactional
public class RoleService {

	@Autowired
	private RoleRepository roleRepo;

	public List<Role> findAllRoles(){


		return roleRepo.findAll();

	}

}
