package com.emptytomb.dbmanager.rest;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LifecycleManager implements ServletContextListener {
	private static final Logger logger = LoggerFactory.getLogger(LifecycleManager.class);
	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		logger.info("Empty Tomb Database Manager REST Service Initialized");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		logger.info("Empty Tomb Database Manager REST Service Shutting Down!");
	}
}