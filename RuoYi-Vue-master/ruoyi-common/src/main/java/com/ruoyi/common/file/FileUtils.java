package com.ruoyi.common.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.ruoyi.common.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;


/**
 * 通用文件工具类
 * @author xxl
 * @since 2023/11/23
 */
@Slf4j
public class FileUtils {

    /**
     * 上传
     *
     * @param file     文件
     * @param savePath 保存路径
     * @return 字符串
     * @throws GlobalException 异常
     */
    public static String upload(InputStream file, String savePath) throws GlobalException {
        if (ObjectUtil.isNull(file) || StrUtil.isBlank(savePath)) {
            return  null;
        }
        //路径不存就创建
        File touch = FileUtil.touch(new File(savePath));
        try (BufferedOutputStream writer = new BufferedOutputStream(Files.newOutputStream(touch.toPath()))){
            // 使用Hutool的IoUtil进行数据传输
            IoUtil.copy(file, writer);
        } catch (IOException e) {
            log.error("FileUtils.upload文件写出失败",e);
            throw new GlobalException("FileUtils：文件写出失败");
        }finally {
            // Hutool的IoUtil更安全的关闭流
            IoUtil.close(file);
        }
        return touch.getPath();
    }

    /**
     * 下载
     *
     * @param filePath 文件路径
     * @return 字节[]
     * @throws GlobalException 异常
     */
    public static byte[] download(String filePath) throws GlobalException {
        //路径不存就创建
        File touch = FileUtil.touch(new File(filePath));
        if (!touch.exists()) {
            throw new GlobalException(String.format("%s不存在",filePath));
        }

        try {
            return FileUtil.readBytes(touch);
        } catch (IORuntimeException e) {
            log.error("FileUtils.download文件读入失败",e);
            throw new GlobalException("FileUtils：文件读入失败");
        }
    }

    /**
     * 网页下载
     *
     * @param filePath 文件路径
     * @param response 响应
     * @param fileName 文件名
     * @throws GlobalException 异常
     */
    public static void webDownload(String filePath, HttpServletResponse response, String fileName) throws GlobalException {
        try {
            byte[] download = download(filePath);
            response.setHeader("Content-Disposition", "attachment;filename=" + URLUtil.encode(fileName));
            response.addHeader("Content-Length", "" + download.length);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setContentType("application/octet-stream;charset=UTF-8");
            IoUtil.write(response.getOutputStream(), true, download);
        } catch (GlobalException | IOException e) {
            log.error("FileUtils.webDownload下载失败", e);
            throw new GlobalException("web下载失败");
        }
    }
    public static void webTransport(String filePath, HttpServletResponse response, String fileName) throws GlobalException {
        try {
            byte[] download = download(filePath);
            response.addHeader("Content-Length", "" + download.length);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setContentType("application/octet-stream;charset=UTF-8");
            IoUtil.write(response.getOutputStream(), true, download);
        } catch (GlobalException | IOException e) {
            log.error("FileUtils.webDownload下载失败", e);
            throw new GlobalException("web下载失败");
        }
    }


    /**
     * 获取文件后缀
     *
     * @param path 路径
     * @return 字符串
     */
    public static String getFileSuffix(String path){
        final String dot = ".";
        if (StrUtil.isBlank(path) || !path.contains(dot)) {
            return null;
        }
        return path.substring(path.lastIndexOf(dot) + 1);
    }

    /**
     * 获取文件名
     *
     * @param path 路径
     * @return 字符串
     */
    public static String getFileName(String path) {
        if (StrUtil.isBlank(path)) {
            return null;
        }
        return path.substring(path.lastIndexOf("\\") + 1);
    }
}
