<script>
import { listOrder, delOrder } from "@/api/order/index";

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
        material: '',
        createUser: '',
        orderState: ''
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
      listOrder().then(response => {
        this.infoList = this.flattenOrdersWithProducts(response.data.records);
        console.log(this.infoList, 'list');
        this.total = response.data.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQueryByMate () {
      const val = this.queryParams.material.trim();
      this.infoList = this.infoList.filter(item => item.material.includes(val));
    },
    handleQueryByUser () {
      const val = this.queryParams.createUser.trim();
      this.infoList = this.infoList.filter(item => item.createUser.includes(val));
    },
    handleQueryByState(val) {
      console.log(val, 'val');
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.getList()
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
          if (this.form.id != null) {
            updateInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInfo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const { id } = row.orderInfo
      this.$modal.confirm('是否确认删除商品名称为"' + row.material + '"的数据项？').then(function() {
        return delOrder(id);
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
    flattenOrdersWithProducts(orders) {
      // 创建一个新数组来存放扩展后的商品信息
      const flattenedProducts = [];

      orders.forEach(order => {
        // 遍历每个订单中的商品
        order.products.forEach(product => {
          // 为每个商品创建一个新的对象，包含商品信息和它所属的订单信息
          const productWithOrderInfo = {
            ...product, // 复制原商品信息
            orderInfo: { // 添加一个新的属性来存放订单信息
              id: order.id,
              createUser: order.createUser,
              userId: order.userId,
              deliveryAddress: order.deliveryAddress,
              orderState: order.orderState,
              createTime: order.createTime,
              lumpSum: order.lumpSum
            }
          };
          // 将扩展后的商品对象添加到结果数组中
          flattenedProducts.push(productWithOrderInfo);
        });
      });

      return flattenedProducts;
    },
    mapState (state) {
      const obj = {
        WAITING: '代付款',
        PAID: '已付款',
        CANCELLED: '已取消'
      }
      
      if(!obj[state]) return ''
      return obj[state]
    },
  }
};
</script> 

  <template>
    <div class="app-container">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="商品名称" prop="material">
          <el-input
            v-model="queryParams.material"
            placeholder="请输入商品名称"
            clearable
            @keyup.enter.native="handleQueryByMate"
          />
        </el-form-item>
        <el-form-item label="用户名" prop="createUser">
          <el-input
            v-model="queryParams.createUser"
            placeholder="请输入用户名"
            clearable
            @keyup.enter.native="handleQueryByUser"
          />
        </el-form-item>
        <el-form-item label="订单状态" prop="orderState">
          <el-select v-model="queryParams.orderState" placeholder="请选择">
            <el-option
              v-for="item of [{value: 'WAITING', label: '待付款'}, { value: 'PAID', label: '已付款' }, {value: 'CANCELLED', label: '已取消'}]"
              :key="item.value"
              :label="item.label"
              :value="item.value"
              @change="handleQueryByState"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <!-- <el-button type="primary" icon="el-icon-search" size="mini">搜索</el-button> -->
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
<!--   
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
        <el-col :span="1.5">
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
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row> -->
  
      <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
        <el-table-column type="index" width="55" align="center" />
        <el-table-column label="商品图片" align="center" prop="url">
          <template slot-scope="scope">
            <img :src="scope.row.url.replace(';', '')" style="width: 100px; object-fit: fill;" />
          </template>
        </el-table-column>
        <el-table-column label="商品名称" align="center" prop="material" />
        <el-table-column label="商品价格" align="center">
          <template slot-scope="scope">
            <span>{{ Number(scope.row.price) * Number(scope.row.productNum) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="用户名称" align="center" prop="orderInfo.createUser" />
        <el-table-column label="地址" align="center" prop="orderInfo.deliveryAddress" />
        <el-table-column label="状态" align="center" prop="orderInfo.orderState" >
          <template slot-scope="scope">
            <span>{{ mapState(scope.row.orderInfo.orderState) }}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:info:edit']"
            >修改</el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:info:remove']"
            >删除</el-button>
          </template>
        </el-table-column> -->
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
          <el-form-item label="商品名称" prop="name">
            <el-input v-model="form.name" placeholder="请输入商品名称" />
          </el-form-item>
          <el-form-item label="材料" prop="materialType">
            <el-select v-model="form.materialType">
              <el-option
                v-for="dict in dict.type.sys_fanhua_material"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="用途" prop="useType">
            <el-select v-model="form.useType"  placeholder="请选择用途">
              <el-option
                v-for="dict in dict.type.sys_fanhua_use_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="对象" prop="useToType">
            <el-select v-model="form.useToType"  placeholder="请选择对象">
              <el-option
                v-for="dict in dict.type.sys_fanhua_use_to"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="价格" prop="priceType">
            <el-select v-model="form.priceType"  placeholder="请选择价格">
              <el-option
                v-for="dict in dict.type.sys_fanhua_price"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="类型" prop="priceType">
            <el-select v-model="form.type"  placeholder="请选择类型">
              <el-option
                v-for="dict in dict.type.sys_fanhua_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="材料" prop="material">
            <el-input v-model="form.material" placeholder="请输入材料" />
          </el-form-item>
          <el-form-item label="价格" prop="material">
            <el-input v-model="form.price" placeholder="请输入价格" />
          </el-form-item>
          <el-form-item label="包装" prop="packageInfo">
            <el-input v-model="form.packageInfo" placeholder="请输入包装" />
          </el-form-item>
          <el-form-item label="花语" prop="flowerLanguage">
            <el-input v-model="form.flowerLanguage" placeholder="请输入花语" />
          </el-form-item>
          <el-form-item label="附赠" prop="bundled">
            <el-input v-model="form.bundled" placeholder="请输入附赠" />
          </el-form-item>
          <el-form-item label="配送" prop="delivery">
            <el-input v-model="form.delivery" placeholder="请输入配送" />
          </el-form-item>
          <el-form-item label="说明" prop="description">
            <el-input v-model="form.description" placeholder="请输入说明" />
          </el-form-item>
          <el-form-item label="数量" prop="productNum">
            <el-input v-model="form.productNum" placeholder="请输入数量" />
          </el-form-item>
          <el-form-item label="头像" prop="tagUrl">
            <el-input v-model="form.tagUrl" placeholder="请输入头像" />
          </el-form-item>
          <el-form-item label="附件图片" prop="url">
            <el-input v-model="form.url" type="textarea" placeholder="请输入内容" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </div>
  </template>

