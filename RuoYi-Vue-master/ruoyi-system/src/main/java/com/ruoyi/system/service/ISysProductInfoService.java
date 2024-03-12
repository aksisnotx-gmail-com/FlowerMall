package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysProductInfo;

/**
 * 商品信息0Service接口
 * 
 * @author ruoyi
 * @date 2024-01-09
 */
public interface ISysProductInfoService 
{
    /**
     * 查询商品信息0
     * 
     * @param id 商品信息0主键
     * @return 商品信息0
     */
    public SysProductInfo selectSysProductInfoById(String id);

    /**
     * 查询商品信息0列表
     * 
     * @param sysProductInfo 商品信息0
     * @return 商品信息0集合
     */
    public List<SysProductInfo> selectSysProductInfoList(SysProductInfo sysProductInfo);

    /**
     * 新增商品信息0
     * 
     * @param sysProductInfo 商品信息0
     * @return 结果
     */
    public int insertSysProductInfo(SysProductInfo sysProductInfo);

    /**
     * 修改商品信息0
     * 
     * @param sysProductInfo 商品信息0
     * @return 结果
     */
    public int updateSysProductInfo(SysProductInfo sysProductInfo);

    /**
     * 批量删除商品信息0
     * 
     * @param ids 需要删除的商品信息0主键集合
     * @return 结果
     */
    public int deleteSysProductInfoByIds(String[] ids);

    /**
     * 删除商品信息0信息
     * 
     * @param id 商品信息0主键
     * @return 结果
     */
    public int deleteSysProductInfoById(String id);
}
