package com.karfield.automatrix;

import com.github.kklisura.cdt.services.ChromeService;
import com.google.gson.JsonObject;
import com.karfield.automatrix.ipc.Base;

import java.util.HashMap;

public interface AmContext {
    String getTraceId();

    JsonObject getArguments();

    ChromeService getChromeService();

    void log(Base.LogLevel level, String action, String message, HashMap<String, Object> extra);

    void log(Base.LogLevel level, String action, String message, JsonObject extra);

    void querySql(String sql, HashMap<String, Object> args);

    void querySql(String sql, JsonObject args);

    void querySql(String defaultDbCode, String sql, HashMap<String, Object> args);

    void querySql(String defaultDbCode, String sql, JsonObject args);

    void executeSql(String sql, HashMap<String, Object> args);

    void executeSql(String sql, JsonObject args);

    void executeSql(String defaultDbCode, String sql, HashMap<String, Object> args);

    void executeSql(String defaultDbCode, String sql, JsonObject args);

    void executeMultipleSqls(String sql, HashMap<String, Object> args);

    void executeMultipleSqls(String sql, JsonObject args);

    void executeMultipleSqls(String defaultDbCode, String sql, HashMap<String, Object> args);

    void executeMultipleSqls(String defaultDbCode, String sql, JsonObject args);

    String resolveCaptchaImage(byte[] imageData, Integer width, Integer height, String format);
}
