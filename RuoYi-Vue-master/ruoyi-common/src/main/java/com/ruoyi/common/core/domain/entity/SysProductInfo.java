package com.ruoyi.common.core.domain.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商品信息0对象 sys_product_info
 * 
 * @author ruoyi
 * @date 2024-01-09
 */
public class SysProductInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String name;

    /** 材料 */
    @Excel(name = "材料")
    private String material;

    /** 包装 */
    @Excel(name = "包装")
    private String packageInfo;

    /** 花语 */
    @Excel(name = "花语")
    private String flowerLanguage;

    /** 附赠 */
    @Excel(name = "附赠")
    private String bundled;

    /** 配送 */
    @Excel(name = "配送")
    private String delivery;

    /** 说明 */
    @Excel(name = "说明")
    private String description;

    /** 数量 */
    @Excel(name = "数量")
    private Long productNum;

    /** 头像 */
    @Excel(name = "头像")
    private String tagUrl;

    /** 附件图片 */
    @Excel(name = "附件图片")
    private String url;

    /** 状态：0，新建，1，审核通过，2，已下架 */
    @Excel(name = "状态：0，新建，1，审核通过，2，已下架")
    private String status;
    
    private String materialType;
    private String useType;
    private String useToType;
    private String priceType;
    private String type;
    private float price;
    
    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setMaterial(String material) 
    {
        this.material = material;
    }

    public String getMaterial() 
    {
        return material;
    }
    public void setPackageInfo(String packageInfo) 
    {
        this.packageInfo = packageInfo;
    }

    public String getPackageInfo() 
    {
        return packageInfo;
    }
    public void setFlowerLanguage(String flowerLanguage) 
    {
        this.flowerLanguage = flowerLanguage;
    }

    public String getFlowerLanguage() 
    {
        return flowerLanguage;
    }
    public void setBundled(String bundled) 
    {
        this.bundled = bundled;
    }

    public String getBundled() 
    {
        return bundled;
    }
    public void setDelivery(String delivery) 
    {
        this.delivery = delivery;
    }

    public String getDelivery() 
    {
        return delivery;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setProductNum(Long productNum) 
    {
        this.productNum = productNum;
    }

    public Long getProductNum() 
    {
        return productNum;
    }
    public void setTagUrl(String tagUrl) 
    {
        this.tagUrl = tagUrl;
    }

    public String getTagUrl() 
    {
        return tagUrl;
    }
    public void setUrl(String url) 
    {
        this.url = url;
    }

    public String getUrl() 
    {
        return url;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public String getUseToType() {
		return useToType;
	}

	public void setUseToType(String useToType) {
		this.useToType = useToType;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("material", getMaterial())
            .append("packageInfo", getPackageInfo())
            .append("flowerLanguage", getFlowerLanguage())
            .append("bundled", getBundled())
            .append("delivery", getDelivery())
            .append("description", getDescription())
            .append("productNum", getProductNum())
            .append("tagUrl", getTagUrl())
            .append("url", getUrl())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
