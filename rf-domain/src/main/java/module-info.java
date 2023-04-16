module rf.domain {
	requires transitive spring.security.core;

	exports rf.domain.entity;
	exports rf.domain.entity.enums;
	exports rf.domain.exception;
	exports rf.domain.repo;
	exports rf.domain.service;
}
