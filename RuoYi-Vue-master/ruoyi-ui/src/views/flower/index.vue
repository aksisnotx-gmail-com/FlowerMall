<script>
import { listTutorial, delTutorial, uploadFileApi, addTutorialpi } from "@/api/flower/index";

export default {
  name: "Info",
  dicts: ['sys_fanhua_use_type', 'sys_fanhua_material', 'sys_fanhua_use_to', 'sys_fanhua_price','sys_fanhua_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 商品信息0表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        createUser: '',
        textDesc: '',
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询商品信息0列表 */
    getList() {
      this.loading = true;
      listTutorial().then(response => {
        this.infoList = response.data.records;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    getVideo() {
        
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: '',
        fileId: '',
        textDesc: ''
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      const val = this.queryParams.createUser.trim();
      this.infoList = this.infoList.filter(item => item.createUser.includes(val));
    },
    handleQueryByText () {
      const val = this.queryParams.textDesc.trim();
      this.infoList = this.infoList.filter(item => item.textDesc.includes(val));
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.getList();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加商品信息0";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.open = true;
      const id = row.id || this.ids
      getInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改商品信息0";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (!this.form.id) {
            const { fileId, textDesc } = this.form;
            addTutorialpi({
              fileId,
              textDesc
            }).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });

          } else {
            updateInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$modal.confirm('是否确认删除用户名称为"' + row.createUser + '"的数据项？').then(function() {
        return delTutorial(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/info/export', {
        ...this.queryParams
      }, `info_${new Date().getTime()}.xlsx`)
    },
    convertDateTimeFormat(dateTimeStr) {
        // 解析ISO格式的时间字符串
        const date = new Date(dateTimeStr);
        // 使用Intl.DateTimeFormat来格式化日期
        const options = { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit', hour12: false };
        const formatter = new Intl.DateTimeFormat('en-GB', options);
    
        // 格式化日期，然后替换日期中的"/"为"-"，以匹配所需的格式
        const formattedDate = formatter.format(date).replace(/\//g, '-');
        
        // 将格式化的日期分割并重组，以满足"YYYY-MM-DD hh:mm:ss"的格式要求
        const [dd, mm, yyyy, hh, ii, ss] = formattedDate.match(/\d+/g);
        return `${yyyy}-${mm}-${dd} ${hh}:${ii}:${ss}`;
    },
    uploadFile (param) {
      const self = this
      const formData = new FormData()
      formData.append('file', param.file)
      uploadFileApi(formData).then(res => {
        const { code, data } = res
        if(code === 200) {
            self.form.fileId = data
        }
      })
    },
  }
};
</script>

<template>
    <div class="app-container">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="用户名称" prop="createUser">
          <el-input
            v-model="queryParams.createUser"
            placeholder="请输入用户名称"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="教程内容" prop="textDesc">
          <el-input
            v-model="queryParams.textDesc"
            placeholder="请输入教程内容"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
  
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['system:info:add']"
          >新增</el-button>
        </el-col>
        <!-- <el-col :span="1.5">
          <el-button
            type="success"
            plain
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['system:info:edit']"
          >修改</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['system:info:remove']"
          >删除</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['system:info:export']"
          >导出</el-button>
        </el-col> -->
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
  
      <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
        <el-table-column type="index" width="55" align="center" />
        <el-table-column label="用户名称" align="center" prop="createUser" />
        <el-table-column label="上传时间" align="center" prop="createTime">
            <template slot-scope="scope">
                <span>{{ convertDateTimeFormat(scope.row.createTime) }}</span>
            </template>
        </el-table-column>
        <el-table-column label="教程内容" align="center" prop="textDesc" />
        <el-table-column label="教程视频" align="center" prop="fileId">
            <template slot-scope="scope">                  
                <video width="200" height="200" controls>
                  <source :src="scope.row.file.path" type="video/mp4"/>
              </video>
            </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <!-- <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:info:edit']"
            >修改</el-button> -->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:info:remove']"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
  
      <!-- 添加或修改商品信息0对话框 -->
      <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="视频" autocomplete="off">
            <el-upload
              action=""
              :http-request="uploadFile"
              :limit="1"
            >
              <template #trigger>
                <el-button type="primary">选择视频</el-button>
              </template>
              <template #tip>
                <div class="el-upload__tip text-red">
                  limit 1 file, new file will cover the old file
                </div>
              </template>
            </el-upload>
          </el-form-item>
          <el-form-item label="教程内容" prop="textDesc">
            <el-input
                v-model="form.textDesc"
                style="width: 240px"
                autosize
                type="textarea"
                placeholder="Please input"
              />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </div>
  </template>
