open module why.studio.gateway {

    requires spring.beans;
    requires spring.context;
    requires spring.core;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.boot.configuration.processor;
    requires spring.security.config;
    requires spring.security.core;
    requires spring.security.oauth2;
    requires spring.security.jwt;
    requires spring.web;

    requires static lombok;

    requires java.xml.bind;
    requires java.sql;
    requires com.sun.xml.bind;

    requires org.apache.commons.io;

}