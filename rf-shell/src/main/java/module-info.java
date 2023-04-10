module rf.shell {
	requires rf.api;

	requires spring.beans;
	requires spring.boot;
	requires spring.boot.autoconfigure;
	requires spring.cloud.openfeign.core;
	requires spring.context;

	requires org.slf4j;

	exports rf.shell to spring.beans, spring.context;
}