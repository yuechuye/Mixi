package com.infrastructure.core;

import cn.hutool.core.text.AntPathMatcher;
import com.mixi.common.pojo.ApiInfo;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 描述: 网关管理器，继承自CloudApiConfigManager，并添加网关特有的配置管理功能
 * @author suifeng
 * 日期: 2024/7/11
 */
@Component
@Getter
@Slf4j
public class GateWayManager extends CloudApiConfigManager {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private List<String> supportModules;

    /**
     * 初始化网关支持的模块
     */
    public void initSupportModules(String supportModulesStr) {
        if (supportModulesStr != null && !supportModulesStr.trim().isEmpty()) {
            this.supportModules = Arrays.stream(supportModulesStr.split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
            log.info("Init support modules: {}", this.supportModules);
        } else {
            // 默认值为空列表
            this.supportModules = Collections.emptyList();
            log.info("No support modules specified, default to empty list.");
        }
    }

    /**
     * 过滤支持的模块
     */
    public boolean isModuleSupported(String moduleName) {
        boolean supported = supportModules.isEmpty() || supportModules.contains(moduleName);
        if (supported) {
            log.info("Module '{}' is supported.", moduleName);
        } else {
            log.warn("Module '{}' is not supported.", moduleName);
        }
        return supported;
    }

    /**
     * 根据请求URI获取对应的ApiInfo
     */
    public Optional<ApiInfo> getApiInfo(String requestUri) {
        return getConfig().stream()
                .filter(apiInfo -> pathMatcher.match(apiInfo.getUrl(), requestUri))
                .findFirst();
    }
}
