package com.ruoyi.web.controller.flower.tutorial;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonView;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.FlowerTutorial;
import com.ruoyi.system.service.impl.FlowerTutorialServiceImpl;
import com.ruoyi.system.domain.common.Entity;
import com.ruoyi.system.domain.param.FlowerTutorialParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 鲜花教程接口
 *
 * @author xxl
 * @since 2024/3/13
 */
@Api(tags = "鲜花教程接口")
@RestController
@RequestMapping("/flower/tutorial")
@Validated
@RequiredArgsConstructor
public class FlowerTutorialController {

    private final FlowerTutorialServiceImpl flowerTutorialService;

    @PostMapping("publish")
    @ApiOperation("发布教程")
    public R<Boolean> saveTutorial(@RequestBody @Validated(Entity.INSERT.class) @JsonView(Entity.INSERT.class) FlowerTutorialParam param) {
        return R.ok(flowerTutorialService.saveTutorial(param, SecurityUtils.getLoginUser().getUser().getStrUserId()));
    }

    @PostMapping("modify")
    @ApiOperation("修改教程")
    public R<Boolean> modifyTutorial(@RequestBody @Validated(Entity.UPDATE.class) @JsonView(Entity.UPDATE.class) FlowerTutorialParam param) {
        return R.ok(flowerTutorialService.modifyTutorial(param, SecurityUtils.getLoginUser().getUser().getStrUserId()));
    }

    @GetMapping("/admin/delete/{tutorialId}")
    @PreAuthorize("@ss.hasRole('admin')")
    @ApiOperation("管理员删除教程")
    public R<Boolean> adminDeleteTutorial(@PathVariable("tutorialId") String tutorialId) {
        return R.ok(flowerTutorialService.adminDeleteTutorial(tutorialId));
    }

    @GetMapping("/delete/{tutorialId}")
    @ApiOperation("删除个人教程")
    public R<Boolean> deleteTutorial(@PathVariable("tutorialId")String tutorialId) {
        return R.ok(flowerTutorialService.deleteTutorial(tutorialId, SecurityUtils.getLoginUser().getUser().getStrUserId()));
    }

    @GetMapping("/get")
    @ApiOperation("分页获取个人教程")
    public R<Page<FlowerTutorial>> get() {
        return R.ok(this.flowerTutorialService.pageOwner(SecurityUtils.getLoginUser().getUser().getStrUserId()));
    }

    @GetMapping("/getAll")
    @ApiOperation("分页获取所有教程")
    @PreAuthorize("@ss.hasRole('admin')")
    public R<Page<FlowerTutorial>> getAll() {
        return R.ok(this.flowerTutorialService.pageAll());
    }
}
