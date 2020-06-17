package com.github.hclincode.shared.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
public class ConfigureProps {
    @Value("${hashLiveSeconds}")
    private int hashLiveSeconds;

    @Value("${hashLength}")
    private int hashLength;

    public int getHashLiveSeconds() {
        return this.hashLiveSeconds;
    }

    public void setHashLiveSeconds(int hashLiveSeconds) {
        this.hashLiveSeconds = hashLiveSeconds;
    }

    public int getHashLength() {
        return this.hashLength;
    }

    public void setHashLength(int hashLength) {
        this.hashLength = hashLength;
    }

}