module rf.api {
	requires transitive java.validation;
	requires transitive spring.security.core;
	requires transitive spring.web;

	exports rf.api;
	exports rf.api.dto;
	exports rf.api.dto.tasks;
	exports rf.api.dto.users;
}