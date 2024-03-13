package com.ruoyi.common.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.exception.GlobalException;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 分页参数获取器
 * @author xxl
 * @since 2023/11/23
 */
@Slf4j
public class CommonPageRequestUtils {
    private static final String PAGE_SIZE_PARAM_NAME = "size";

    private static final String PAGE_PARAM_NAME = "current";

    private static final Integer PAGE_SIZE_MAX_VALUE = 100;

    public static <T> Page<T> defaultPage() throws GlobalException {
        return defaultPage(null);
    }

    public static <T> Page<T> defaultPage(List<OrderItem> orderItemList) throws GlobalException {

        int size = 20;

        int page = 1;

        //每页条数
        String pageSizeString = getParamFromRequest(PAGE_SIZE_PARAM_NAME);
        if (ObjectUtil.isNotEmpty(pageSizeString)) {
            try {
                size = Convert.toInt(pageSizeString);
                if(size > PAGE_SIZE_MAX_VALUE) {
                    size = PAGE_SIZE_MAX_VALUE;
                }
            } catch (Exception e) {
                log.error(">>> 分页条数转换异常：", e);
                size = 20;
            }
        }

        //第几页
        String pageString = getParamFromRequest(PAGE_PARAM_NAME);
        if (ObjectUtil.isNotEmpty(pageString)) {
            try {
                page = Convert.toInt(pageString); ///order/admin/getAll?current=2
            } catch (Exception e) {
                log.error(">>> 分页页数转换异常：", e);
                page = 1;
            }
        }
        Page<T> objectPage = new Page<>(page, size);
        if (ObjectUtil.isNotEmpty(orderItemList)) {
            objectPage.setOrders(orderItemList);
        }
        return objectPage;
    }

    /**
     * 从请求中中获取参数
     *
     * @author xuyuxiang
     * @date 2021/10/14 10:44
     **/
    public static String getParamFromRequest(String paramName) throws GlobalException {
        HttpServletRequest request = getRequest();

        // 1. 尝试从请求体里面读取
        String paramValue = request.getParameter(paramName);

        // 2. 尝试从header里读取
        if (ObjectUtil.isEmpty(paramValue)) {
            paramValue = request.getHeader(paramName);
        }
        // 3. 尝试从cookie里读取
        if (ObjectUtil.isEmpty(paramValue)) {
            Cookie[] cookies = request.getCookies();
            if(ObjectUtil.isNotEmpty(cookies)) {
                for (Cookie cookie : cookies) {
                    String cookieName = cookie.getName();
                    if (cookieName.equals(paramName)) {
                        return cookie.getValue();
                    }
                }
            }
        }
        // 4. 返回
        return paramValue;
    }

    public static HttpServletRequest getRequest() throws GlobalException {
        ServletRequestAttributes servletRequestAttributes;
        try {
            servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        } catch (Exception e) {
            log.error(">>> 非Web上下文无法获取Request：", e);
            throw new GlobalException("非Web上下文无法获取Request");
        }
        if (servletRequestAttributes == null) {
            throw new GlobalException("非Web上下文无法获取Request");
        } else {
            return servletRequestAttributes.getRequest();
        }
    }

    public static HttpServletResponse getResponse() throws GlobalException {
        ServletRequestAttributes servletRequestAttributes;
        try {
            servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        } catch (Exception e) {
            log.error(">>> 非Web上下文无法获取Response：", e);
            throw new GlobalException("非Web上下文无法获取Response");
        }
        if (servletRequestAttributes == null) {
            throw new GlobalException("非Web上下文无法获取Response");
        } else {
            return servletRequestAttributes.getResponse();
        }
    }

    public static boolean isWeb() {
        return RequestContextHolder.getRequestAttributes() != null;
    }

    /**
     * 因为mybatis-plus的page不能使用于是封装一个自己的
     */
    public static <M extends BaseMapper<T>,T> PageVO<T> getPageVO(ServiceImpl<M,T> service, LambdaQueryWrapper<T> wrapper) {
        Page<Object> page = defaultPage();
        long  total, current = page.getCurrent(), size = page.getSize(),offset = (current - 1) * size;
        List<T> records;
        if (Objects.isNull(wrapper)) {
            total = service.count();
            LambdaQueryWrapper<T> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.last(String.format("LIMIT %d, %d",offset,size));
            records = service.list(queryWrapper);
        }else {
            total = service.count(wrapper);
            wrapper.last(String.format("LIMIT %d, %d",offset,size));
            records = service.list(wrapper);
        }
        service.count();

        return new PageVO<T>().
                setTotal(total).
                setCurrent(current).
                setSize(size).
                setRecords(records);
    }

    /**
     * 分页工具类
     * @author xxl
     * @since 2023/6/10
     */
    @Data
    @Accessors(chain = true)
    public static class PageVO<T> implements Serializable {

        private static final long serialVersionUID = 5633361189305883916L;

        /**
         * 页面数据
         */
        private List<T> records = Collections.emptyList();

        /**
         * 总数
         */
        private long  total = 0;

        /**
         * 当前
         */
        private long current = 1;

        /**
         * 每页显示多少
         */
        private long size = 10;
    }
}
