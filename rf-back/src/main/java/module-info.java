module rf.back {
	requires rf.api;
	requires rf.domain;

	requires spring.beans;
	requires spring.boot;
	requires spring.boot.autoconfigure;
	requires spring.context;
	requires spring.core;
	requires spring.security.config;
	requires spring.security.crypto;
	requires spring.security.web;
	requires spring.webmvc;

	requires com.fasterxml.jackson.databind;
	requires org.apache.tomcat.embed.core;
	requires org.slf4j;

	exports rf.back to spring.beans, spring.context;
	exports rf.back.api.impl to spring.beans, spring.context;
	exports rf.back.security to spring.beans, spring.context;

	opens rf.back to spring.core;
	opens rf.back.api.impl to spring.core, spring.web;
	opens rf.back.security to spring.core;
	opens rf.back.service.impl to spring.core;
}
