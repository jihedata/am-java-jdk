package com.karfield.automatrix.impl;

import com.github.kklisura.cdt.launch.config.ChromeLauncherConfiguration;
import com.github.kklisura.cdt.services.ChromeService;
import com.github.kklisura.cdt.services.impl.ChromeServiceImpl;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.protobuf.ByteString;
import com.karfield.automatrix.AmContext;
import com.karfield.automatrix.ipc.*;

import java.util.HashMap;

public class AmContextImpl implements AmContext {
    private final BaseIpcGrpc.BaseIpcBlockingStub baseIpc;
    private final CdpIpcGrpc.CdpIpcBlockingStub cdpIpc;
    private final OcrIpcGrpc.OcrIpcBlockingStub ocrIpc;
    private final SqlIpcGrpc.SqlIpcBlockingStub sqlIpc;
    private final ByteString inputData;
    private final String traceId;
    private final ChromeServiceImpl chromeService;

    public AmContextImpl(BaseIpcGrpc.BaseIpcBlockingStub baseIpcStub, CdpIpcGrpc.CdpIpcBlockingStub cdpIpcStub, OcrIpcGrpc.OcrIpcBlockingStub ocrIpcStub, SqlIpcGrpc.SqlIpcBlockingStub sqlIpcStub, String traceId, ByteString payload) {
        this.baseIpc = baseIpcStub;
        this.cdpIpc = cdpIpcStub;
        this.ocrIpc = ocrIpcStub;
        this.sqlIpc = sqlIpcStub;
        this.traceId = traceId;
        this.inputData = payload;
        if (this.cdpIpc != null) {
            this.chromeService = new ChromeServiceImpl(cdpIpc.getBrowserDebuggerPort(Cdp.GetBrowserDebuggerPortRequest.newBuilder().build()).getPort());
        } else {
            this.chromeService = null;
        }
    }

    @Override
    public String getTraceId() {
        return this.traceId;
    }

    @Override
    public JsonObject getArguments() {
        return JsonParser.parseString(inputData.toString()).getAsJsonObject();
    }

    @Override
    public ChromeService getChromeService() {
        return chromeService;
    }

    @Override
    public void log(Base.LogLevel level, String action, String message, HashMap<String, Object> extra) {
        baseIpc.saveLog(Base.SaveLogRequest.newBuilder().setTraceId(traceId).setLevel(level).setAction(action).setMessage(message).setExtraJson(ByteString.copyFrom(new Gson().toJson(extra).getBytes())).build());
    }

    @Override
    public void log(Base.LogLevel level, String action, String message, JsonObject extra) {
        baseIpc.saveLog(Base.SaveLogRequest.newBuilder().setTraceId(traceId).setLevel(level).setAction(action).setMessage(message).setExtraJson(ByteString.copyFrom(new Gson().toJson(extra).getBytes())).build());
    }

    @Override
    public void querySql(String sql, HashMap<String, Object> args) {
        if (sqlIpc != null) {
            sqlIpc.querySingleSql(Sql.ExecuteSqlRequest.newBuilder()
                    .setTraceId(traceId)
                    .setSql(sql)
                    .setJsonArguments(ByteString.copyFrom(new Gson().toJson(args).getBytes())).build());
        }
    }

    @Override
    public void querySql(String sql, JsonObject args) {
        if (sqlIpc != null) {
            sqlIpc.querySingleSql(Sql.ExecuteSqlRequest.newBuilder()
                    .setTraceId(traceId)
                    .setSql(sql)
                    .setJsonArguments(ByteString.copyFrom(new Gson().toJson(args).getBytes())).build());
        }
    }

    @Override
    public void querySql(String defaultDbCode, String sql, HashMap<String, Object> args) {
        if (sqlIpc != null) {
            sqlIpc.querySingleSql(Sql.ExecuteSqlRequest.newBuilder()
                    .setTraceId(traceId)
                    .setSql(sql)
                    .setDefaultDbCode(defaultDbCode)
                    .setJsonArguments(ByteString.copyFrom(new Gson().toJson(args).getBytes())).build());
        }
    }

    @Override
    public void querySql(String defaultDbCode, String sql, JsonObject args) {
        if (sqlIpc != null) {
            sqlIpc.querySingleSql(Sql.ExecuteSqlRequest.newBuilder()
                    .setTraceId(traceId)
                    .setSql(sql)
                    .setDefaultDbCode(defaultDbCode)
                    .setJsonArguments(ByteString.copyFrom(new Gson().toJson(args).getBytes())).build());
        }
    }

    @Override
    public void executeSql(String sql, HashMap<String, Object> args) {
        if (sqlIpc != null) {
            sqlIpc.executeSingleSql(Sql.ExecuteSqlRequest.newBuilder()
                    .setTraceId(traceId)
                    .setSql(sql)
                    .setJsonArguments(ByteString.copyFrom(new Gson().toJson(args).getBytes())).build());
        }
    }

    @Override
    public void executeSql(String sql, JsonObject args) {
        if (sqlIpc != null) {
            sqlIpc.executeSingleSql(Sql.ExecuteSqlRequest.newBuilder()
                    .setTraceId(traceId)
                    .setSql(sql)
                    .setJsonArguments(ByteString.copyFrom(new Gson().toJson(args).getBytes())).build());
        }
    }

    @Override
    public void executeSql(String defaultDbCode, String sql, HashMap<String, Object> args) {
        if (sqlIpc != null) {
            sqlIpc.executeSingleSql(Sql.ExecuteSqlRequest.newBuilder()
                    .setTraceId(traceId)
                    .setSql(sql)
                    .setDefaultDbCode(defaultDbCode)
                    .setJsonArguments(ByteString.copyFrom(new Gson().toJson(args).getBytes())).build());
        }
    }

    @Override
    public void executeSql(String defaultDbCode, String sql, JsonObject args) {
        if (sqlIpc != null) {
            sqlIpc.executeSingleSql(Sql.ExecuteSqlRequest.newBuilder()
                    .setTraceId(traceId)
                    .setSql(sql)
                    .setDefaultDbCode(defaultDbCode)
                    .setJsonArguments(ByteString.copyFrom(new Gson().toJson(args).getBytes())).build());
        }
    }

    @Override
    public void executeMultipleSqls(String sql, HashMap<String, Object> args) {
        if (sqlIpc != null) {
            sqlIpc.executeMultipleSqls(Sql.ExecuteSqlRequest.newBuilder()
                    .setTraceId(traceId)
                    .setSql(sql)
                    .setJsonArguments(ByteString.copyFrom(new Gson().toJson(args).getBytes())).build());
        }
    }

    @Override
    public void executeMultipleSqls(String sql, JsonObject args) {
        if (sqlIpc != null) {
            sqlIpc.executeMultipleSqls(Sql.ExecuteSqlRequest.newBuilder()
                    .setTraceId(traceId)
                    .setSql(sql)
                    .setJsonArguments(ByteString.copyFrom(new Gson().toJson(args).getBytes())).build());
        }
    }

    @Override
    public void executeMultipleSqls(String defaultDbCode, String sql, HashMap<String, Object> args) {
        if (sqlIpc != null) {
            sqlIpc.executeMultipleSqls(Sql.ExecuteSqlRequest.newBuilder()
                    .setTraceId(traceId)
                    .setSql(sql)
                    .setDefaultDbCode(defaultDbCode)
                    .setJsonArguments(ByteString.copyFrom(new Gson().toJson(args).getBytes())).build());
        }
    }

    @Override
    public void executeMultipleSqls(String defaultDbCode, String sql, JsonObject args) {
        if (sqlIpc != null) {
            sqlIpc.executeMultipleSqls(Sql.ExecuteSqlRequest.newBuilder()
                    .setTraceId(traceId)
                    .setSql(sql)
                    .setDefaultDbCode(defaultDbCode)
                    .setJsonArguments(ByteString.copyFrom(new Gson().toJson(args).getBytes())).build());
        }
    }

    @Override
    public String resolveCaptchaImage(byte[] imageData, Integer width, Integer height, String format) {
        if (ocrIpc != null) {
            Ocr.ResolvedCaptchaAnaswer resp = ocrIpc.resolveCaptchaImage(Ocr.CaptchaImage.newBuilder()
                    .setTraceId(traceId)
                    .setImage(ByteString.copyFrom(imageData))
                    .setWidth(width)
                    .setHeight(height)
                    .setFormat(format).build());
            return resp.getAnswer();
        }
        return null;
    }
}
