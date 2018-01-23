package com.altamontana.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class AbstractClass {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final boolean debug;
    protected final boolean info;
    protected final boolean trace;

    @Value("${spring.profiles.active}")
    protected String profile;

    public AbstractClass() {
        this.debug = this.logger.isDebugEnabled();
        this.info = this.logger.isInfoEnabled();
        this.trace = this.logger.isTraceEnabled();
    }

    protected boolean isProfileDevEnabled() {
        if (this.profile != null) {
            return this.profile.equalsIgnoreCase("dev");
        } else {
            this.logger.error("No se ha configurado la propiedad spring.profiles.active en el archivo app.properties.");
            return false;
        }
    }

    protected boolean isProfileTestEnabled() {
        if (this.profile != null) {
            return this.profile.equalsIgnoreCase("test");
        } else {
            this.logger.error("No se ha configurado la propiedad spring.profiles.active en el archivo app.properties.");
            return false;
        }
    }

    protected boolean isProfileProdEnabled() {
        if (this.profile != null) {
            return this.profile.equalsIgnoreCase("prod");
        } else {
            this.logger.error("No se ha configurado la propiedad spring.profiles.active en el archivo app.properties.");
            return false;
        }
    }
}
