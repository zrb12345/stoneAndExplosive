<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--用户数据
密度 md
强度 qd
平均块度 kd
泊松比 bsb
弹性模量 txml     
大块率       dkl      
炸药单耗     zydh 
孔径  kjin
孔距 kju
-->
      <el-col :span="24" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
          label-width="68px">
          <el-form-item label="密度" prop="md">
            <el-input v-model="queryParams.md" placeholder="请输入密度" clearable style="width: 240px"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="强度" prop="qd">
            <el-input v-model="queryParams.qd" placeholder="请输入强度" clearable style="width: 240px"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="平均块度" prop="kd">
            <el-input v-model="queryParams.kd" placeholder="请输入平均块度" clearable style="width: 240px"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="泊松比" prop="bsb">
            <el-input v-model="queryParams.bsb" placeholder="请输入泊松比" clearable style="width: 240px"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="弹性模量" prop="txml">
            <el-input v-model="queryParams.txml" placeholder="请输入弹性模量" clearable style="width: 240px"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="大块率" prop="dkl">
            <el-input v-model="queryParams.dkl" placeholder="请输入大块率" clearable style="width: 240px"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="炸药单耗" prop="zydh">
            <el-input v-model="queryParams.zydh" placeholder="请输入炸药单耗" clearable style="width: 240px"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="孔径" prop="kjin">
            <el-input v-model="queryParams.kjin" placeholder="请输入孔径" clearable style="width: 240px"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="孔距" prop="kju">
            <el-input v-model="queryParams.kju" placeholder="请输入孔距" clearable style="width: 240px"
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select v-model="queryParams.status" placeholder="状态" clearable style="width: 240px">
              <el-option v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.label"
                :value="dict.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="创建时间">
            <el-date-picker v-model="dateRange" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
              range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
              v-hasPermi="['system:se:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
              v-hasPermi="['system:se:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
              v-hasPermi="['system:se:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="info" plain icon="el-icon-upload2" size="mini" @click="handleImport"
              v-hasPermi="['system:se:import']">导入</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="normal" plain icon="el-icon-upload2" size="mini" @click="trainAndForecastFun"
              v-hasPermi="['system:se:import']">训练</el-button>
          </el-col>

          <el-col :span="1.5">
            <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
              v-hasPermi="['system:se:export']">导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="seList" @selection-change="handleSelectionChange"
          :default-sort="defaultSort" @sort-change="handleSortChange">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="编号" width="50" align="center" key="id" prop="id" v-if="columns[0].visible" />
          <el-table-column label="密度" align="center" key="md" prop="md" v-if="columns[1].visible"
            :show-overflow-tooltip="true" />
          <el-table-column label="强度" align="center" key="qd" prop="qd" v-if="columns[2].visible"
            :show-overflow-tooltip="true" />
          <el-table-column label="块度" align="center" key="kd" prop="kd" v-if="columns[3].visible" />
          <el-table-column label="泊松比" align="center" key="bsb" prop="bsb" v-if="columns[4].visible" />
          <el-table-column label="弹性模量" align="center" key="txml" prop="txml" v-if="columns[5].visible" />
          <el-table-column label="大块率" align="center" key="dkl" prop="dkl" v-if="columns[6].visible" />
          <el-table-column label="炸药单耗" align="center" key="zydh" prop="zydh" v-if="columns[7].visible" />
          <el-table-column label="孔径" align="center" key="kjin" prop="kjin" v-if="columns[8].visible" />
          <el-table-column label="孔距" align="center" key="kju" prop="kju" v-if="columns[9].visible" />
          <el-table-column label="状态" align="center" key="status" v-if="columns[10].visible">
            <template slot-scope="scope">
              <el-switch v-model="scope.row.status" active-value="0" inactive-value="1"
                @change="handleStatusChange(scope.row)"></el-switch>
            </template>
          </el-table-column>
          <!-- <el-table-column prop="type" label="类型" sortable="custom" :sort-orders="['descending', 'ascending']"
            align="center" key="type" v-if="columns[11].visible">
            <template slot-scope="scope">{{ getLabel(getType, scope.row.type, 'dictValue', 'dictLabel') }}</template>
          </el-table-column> -->
          <!-- <el-table-column label="创建时间" align="center" prop="createTime" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column> -->
          <el-table-column label="操作" align="center" width="160" class-name="small-padding fixed-width">
            <template slot-scope="scope" v-if="scope.row.id !== 0">
              <div>
                <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)"
                  v-hasPermi="['system:se:resetPwd', 'system:se:edit']">
                  <el-button size="medium" type="danger" icon="el-icon-d-arrow-right">预测</el-button>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="A" icon="el-icon-circle-check">大块率</el-dropdown-item>
                    <el-dropdown-item command="B" icon="el-icon-circle-check">块度</el-dropdown-item>
                    <el-dropdown-item command="C" icon="el-icon-circle-check">炸药类型</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </div>
              <div>
                <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                  v-hasPermi="['system:se:edit']">修改</el-button>
                <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                  v-hasPermi="['system:se:remove']">删除</el-button>
              </div>

            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize" @pagination="getList" />
      </el-col>
    </el-row>

    <!-- 添加或修改用户配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="密度" prop="md">
              <el-input v-model="form.md" placeholder="请输入密度" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="块度" prop="kd">
              <el-input v-model="form.kd" placeholder="请输入块度" maxlength="30" />
            </el-form-item>
          </el-col>


        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="强度" prop="qd">
              <el-input v-model="form.qd" placeholder="请输入强度" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="泊松比" prop="dkl">
              <el-input v-model="form.bsb" placeholder="请输入泊松比" maxlength="11" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="弹性模量" prop="txml">
              <el-input v-model="form.txml" placeholder="请输入弹性模量" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="大块率" prop="dkl">
              <el-input v-model="form.dkl" placeholder="请输入大块率" maxlength="30" />
            </el-form-item>
          </el-col>

        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="炸药单耗" prop="zydh">
              <el-input v-model="form.zydh" placeholder="请输入炸药单耗" maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="孔径" prop="kjin">
              <el-input v-model="form.kjin" placeholder="请输入孔径" maxlength="30" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="孔距" prop="kju">
              <el-input v-model="form.kju" placeholder="请输入孔距" maxlength="30" />
            </el-form-item>
          </el-col>
          <!-- <el-col :span="12">
            <el-select v-model="form.type" placeholder="请选择炸药类型" clearable>
              <el-option v-for="dict in dict.type.extype" :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select> </el-col> -->
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.value">{{
                  dict.label
                }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>

        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的用户数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;"
            @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getConsumeOfTop5 } from "@/api/system/exStone";
import { listSe, trainAndForecast, onlyForecast1, onlyForecast2, onlyForecast3, onlyForecast4, onlyForecast5, addse, getSe, delSe, updateSe, resetSePwd, changeSeStatus, deptTreeSelect } from "@/api/system/se";
import { getToken } from "@/utils/auth";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  name: "Se",
  dicts: ['sys_normal_disable', 'extype'],//, 'sys_se_sex'
  components: { Treeselect },
  data() {
    return {
      // 默认排序
      defaultSort: { prop: 'operTime', order: 'descending' },
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
      // 用户表格数据
      seList: null,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      overexcavationThickness: undefined,
      // 默认密码
      initPassword: undefined,
      // 日期范围
      dateRange: [],
      // 岗位选项
      postOptions: [],
      // 角色选项
      roleOptions: [],
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/se/importData"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        kd: undefined,
        dkl: undefined,
        status: undefined,
        deptId: undefined
      },
      // 列信息
      columns: [
        { key: 0, label: `编号`, visible: true },
        { key: 1, label: `密度`, visible: true },
        { key: 2, label: `强度`, visible: true },
        { key: 3, label: `平均块度`, visible: true },
        { key: 4, label: `大块率`, visible: true },
        { key: 5, label: `泊松比`, visible: true },
        { key: 6, label: `弹性模量`, visible: true },
        { key: 7, label: `炸药单耗`, visible: true },
        { key: 8, label: `孔径`, visible: true },
        { key: 9, label: `孔距`, visible: true },
        { key: 10, label: `状态`, visible: true },
        { key: 11, label: `创建时间`, visible: true }
      ],
      // 表单校验
      rules: {
      },
      // 模拟数据字典数据，类型数据
      getType: [
        { dictValue: 1, dictLabel: '二级岩石乳化炸药' },
        { dictValue: 2, dictLabel: '岩石膨化硝铵炸药' },
        { dictValue: 3, dictLabel: '铵梯炸药' },
        { dictValue: 4, dictLabel: '浆状炸药' },
        { dictValue: 5, dictLabel: '粉状铵油炸药' },
        { dictValue: 6, dictLabel: '多孔粒状铵油炸药' },
        { dictValue: 7, dictLabel: '重铵油炸药' },
        { dictValue: 8, dictLabel: '1#混装乳化炸药' },
        { dictValue: 9, dictLabel: '2#混装乳化炸药' },
        { dictValue: 10, dictLabel: '3#混装乳化炸药' },
        { dictValue: 11, dictLabel: '4#混装乳化炸药' },
        { dictValue: 12, dictLabel: '5#混装乳化炸药' },
        { dictValue: 13, dictLabel: '6#混装乳化炸药' }
      ],
    };
  },
  watch: {
    // 根据名称筛选部门/花萼宽度树
    overexcavationThickness(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getList();
    // this.getDeptTree();
    this.getConfigKey("sys.se.initPassword").then(response => {
      this.initPassword = response.msg;
    });
  },
  methods: {
    /** 排序触发事件 */
    handleSortChange(column, prop, order) {
      this.queryParams.orderByColumn = column.prop;
      this.queryParams.isAsc = column.order;
      this.getList();
    },
    /** 查询用户列表 */
    getList() {
      this.loading = true;
      listSe(this.addDateRangeWYR(this.queryParams, this.dateRange)).then(response => {
        this.seList = response.rows;
        this.total = response.total;
        this.loading = false;
      }
      );
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.handleQuery();
    },
    // 状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '""' + row.kd + '"数据吗？').then(function () {
        return changeSeStatus(row.id, row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function () {
        row.status = row.status === "0" ? "1" : "0";
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
        id: undefined,
        deptId: undefined,
        kd: undefined,
        zydh: undefined,
        password: undefined,
        dkl: undefined,
        email: undefined,
        sex: undefined,
        status: "0",
        remark: undefined,
        postIds: [],
        roleIds: []
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.queryParams.deptId = undefined;
      this.$refs.tree.setCurrentKey(null);
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "A":
          this.onlyForecastFun1(row);
          break;
        case "B":
          this.onlyForecastFun2(row);
          break;
        case "C":
          this.onlyForecastFun3(row);
          break;
        default:
          break;
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      // this.postOptions = response.posts;
      // this.roleOptions = response.roles;
      this.open = true;
      this.title = "添加数据";
      // this.form.password = this.initPassword;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getSe(id).then(response => {
        this.form = response.data;
        // this.postOptions = response.posts;
        // this.roleOptions = response.roles;
        // this.$set(this.form, "postIds", response.postIds);
        // this.$set(this.form, "roleIds", response.roleIds);
        this.open = true;
        this.title = "修改数据";
        this.form.password = "";
      });
    },

    /** 分配角色操作 */
    handleAuthRole: function (row) {
      const id = row.id;
      this.$router.push("/system/se-auth/role/" + id);
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateSe(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            let list1 = new Array();
            list1.push(this.form);
            addse(list1).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    onlyForecastFun1(row) {
      onlyForecast1(row).then(response => {
        window.alert(response.msg);
      }
      );
    },
    onlyForecastFun2(row) {
      onlyForecast2(row).then(response => {
        window.alert(response.msg);
      }
      );
    },
    getConsumeOfTop5Asend(params) {
      getConsumeOfTop5(params).then(response2 => {
        window.alert(response2.msg);
      })
    },
    onlyForecastFun3(row) {
      onlyForecast3(row).then(response => {
        let params = response.data
        params.push(row.type)
        this.getConsumeOfTop5Asend(params)
      })
    },
    onlyForecastFun4(row) {
      onlyForecast4(row).then(response => {
        window.alert(response.msg);
      }
      );
    },
    onlyForecastFun5(row) {
      onlyForecast5(row).then(response => {
        window.alert(response.msg);
      }
      );
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除编号为"' + ids + '"的数据项？').then(function () {
        return delSe(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {


      // this.download('system/se/export', {
      // ...this.queryParams
      // }, `se_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "用户导入";
      this.upload.open = true;
    },



    trainAndForecastFun() {
      trainAndForecast().then(response => {
        window.alert(response.msg);
      }
      );
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('system/se/importTemplate', {
      }, `se_template_${new Date().getTime()}.xlsx`)
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }
  }
};
</script>