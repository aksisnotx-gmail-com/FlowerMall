package com.ruoyi.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysProductInfo;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.mapper.SysProductInfoMapper;
import com.ruoyi.system.service.ISysProductInfoService;

/**
 * 商品信息0Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-01-09
 */
@Service
public class SysProductInfoServiceImpl implements ISysProductInfoService 
{
    @Autowired
    private SysProductInfoMapper sysProductInfoMapper;

    /**
     * 查询商品信息0
     * 
     * @param id 商品信息0主键
     * @return 商品信息0
     */
    @Override
    public SysProductInfo selectSysProductInfoById(String id)
    {
        return sysProductInfoMapper.selectSysProductInfoById(id);
    }

    /**
     * 查询商品信息0列表
     * 
     * @param sysProductInfo 商品信息0
     * @return 商品信息0
     */
    @Override
    public List<SysProductInfo> selectSysProductInfoList(SysProductInfo sysProductInfo)
    {
        return sysProductInfoMapper.selectSysProductInfoList(sysProductInfo);
    }

    /**
     * 新增商品信息0
     * 
     * @param sysProductInfo 商品信息0
     * @return 结果
     */
    @Override
    public int insertSysProductInfo(SysProductInfo sysProductInfo)
    {
    	sysProductInfo.setStatus(Constants.SYS_PRODUCT_STATUS_INIT);
    	sysProductInfo.setId(IdUtils.fastSimpleUUID());
        sysProductInfo.setCreateTime(DateUtils.getNowDate());
        return sysProductInfoMapper.insertSysProductInfo(sysProductInfo);
    }

    /**
     * 修改商品信息0
     * 
     * @param sysProductInfo 商品信息0
     * @return 结果
     */
    @Override
    public int updateSysProductInfo(SysProductInfo sysProductInfo)
    {
        sysProductInfo.setUpdateTime(DateUtils.getNowDate());
        return sysProductInfoMapper.updateSysProductInfo(sysProductInfo);
    }

    /**
     * 批量删除商品信息0
     * 
     * @param ids 需要删除的商品信息0主键
     * @return 结果
     */
    @Override
    public int deleteSysProductInfoByIds(String[] ids)
    {
        return sysProductInfoMapper.deleteSysProductInfoByIds(ids);
    }

    /**
     * 删除商品信息0信息
     * 
     * @param id 商品信息0主键
     * @return 结果
     */
    @Override
    public int deleteSysProductInfoById(String id)
    {
        return sysProductInfoMapper.deleteSysProductInfoById(id);
    }
}
