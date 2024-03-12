package com.ruoyi.web.controller.system;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysProductInfo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ISysProductInfoService;

/**
 * 商品信息0Controller
 * 
 * @author ruoyi
 * @date 2024-01-09
 */
@RestController
@RequestMapping("/system/product")
public class SysProductInfoController extends BaseController
{
    @Autowired
    private ISysProductInfoService sysProductInfoService;

    /**
     * 查询商品信息0列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysProductInfo sysProductInfo)
    {
        startPage();
        List<SysProductInfo> list = sysProductInfoService.selectSysProductInfoList(sysProductInfo);
        return getDataTable(list);
    }

    @GetMapping("/getProduct")
    public AjaxResult getProduct(SysProductInfo sysProductInfo)
    {
    	
        return success(sysProductInfoService.selectSysProductInfoList(sysProductInfo));
    }
    
    @GetMapping("/getProductList")
    public AjaxResult getProductList(SysProductInfo sysProductInfo)
    {
    	AjaxResult aj = AjaxResult.success();
    	
    	SysProductInfo hotProduct = new SysProductInfo();
    	hotProduct.setType(Constants.SYS_PRODUCT_TYPE_HOT);
    	
    	aj.put("hotProduct", sysProductInfoService.selectSysProductInfoList(hotProduct));
    	
    	
    	SysProductInfo netHotProduct = new SysProductInfo();
    	netHotProduct.setType(Constants.SYS_PRODUCT_TYPE_NET_HOT);
    	aj.put("netHotProduct", sysProductInfoService.selectSysProductInfoList(netHotProduct));
    	
    	
    	SysProductInfo specialProduct = new SysProductInfo();
    	specialProduct.setType(Constants.SYS_PRODUCT_TYPE_SPECIAL);
    	aj.put("specialProduct", sysProductInfoService.selectSysProductInfoList(specialProduct));
    	
    	SysProductInfo lifeProduct = new SysProductInfo();
    	lifeProduct.setType(Constants.SYS_PRODUCT_TYPE_LIFE);
    	aj.put("lifeProduct", sysProductInfoService.selectSysProductInfoList(lifeProduct));
    	
    	SysProductInfo foreverProduct = new SysProductInfo();
    	foreverProduct.setType(Constants.SYS_PRODUCT_TYPE_HOT_FOREVER);
    	aj.put("foreverProduct", sysProductInfoService.selectSysProductInfoList(foreverProduct));
    	
    	SysProductInfo newProduct = new SysProductInfo();
    	newProduct.setType(Constants.SYS_PRODUCT_TYPE_NEW);
    	aj.put("newProduct", sysProductInfoService.selectSysProductInfoList(newProduct));
    	
        return aj;
    }
    /**
     * 导出商品信息0列表
     */
    @Log(title = "商品信息0", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysProductInfo sysProductInfo)
    {
        List<SysProductInfo> list = sysProductInfoService.selectSysProductInfoList(sysProductInfo);
        ExcelUtil<SysProductInfo> util = new ExcelUtil<SysProductInfo>(SysProductInfo.class);
        util.exportExcel(response, list, "商品信息0数据");
    }

    /**
     * 获取商品信息0详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(sysProductInfoService.selectSysProductInfoById(id));
    }

    /**
     * 新增商品信息0
     */
    @Log(title = "商品信息0", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysProductInfo sysProductInfo)
    {
    	sysProductInfo.setCreateBy(getUsername());
        return toAjax(sysProductInfoService.insertSysProductInfo(sysProductInfo));
    }

    /**
     * 修改商品信息0
     */
    @Log(title = "商品信息0", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysProductInfo sysProductInfo)
    {
        return toAjax(sysProductInfoService.updateSysProductInfo(sysProductInfo));
    }

    /**
     * 删除商品信息0
     */
    @Log(title = "商品信息0", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(sysProductInfoService.deleteSysProductInfoByIds(ids));
    }
}
