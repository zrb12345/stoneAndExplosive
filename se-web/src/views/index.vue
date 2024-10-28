<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <div v-for="item in demoArray" :key="item.path" class="sudoku_row">
          <!-- <input type="text" v-model="item.name">
          <el-switch class="exio-switch" v-model="item.status" active-text="开" inactive- text="关"
            active-color="#13ce66"></el-switch>
          <button @click="showInfo(item)">查看</button> -->
          <div class="sudoku_item " :class="curSelect == sudoku.id ? 'opacity' : ''" v-for="(sudoku, index) in sudokus"
            :key="index" @click="startFun(index)">
            <img :src="sudoku.img_src" width="40" height="40">
            {{ sudoku.name }}
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row>
      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
        @pagination="getList" />
    </el-row>
    <el-row>
      <el-form :model="upform" :rules="uprules" style="width:100%;" ref="upform" label-width="100px">
        <el-form-item label="上传附件:">
          <el-button type="primary" @click="upLoad()" size="mini" class="form-btn">点击上传</el-button>
        </el-form-item>
      </el-form>
    </el-row>
  </div>

</template>
<script>
import { listSe } from "@/api/system/se";
import { uploadFile } from "@/api/system/qiniu";
export default {

  name: "Se",
  // dicts: ['sys_normal_disable', 'sys_se_sex'],
  // components: { Treeselect },
  data() {
    return {
      // 用户表格数据
      seList: null,
      loading: true,
      // 总条数
      total: 0,
      queryParams: {
        pageNum: 0,
        pageSize: 10,

      },
      upform: {
        username: "admin",
        password: "admin123",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      uprules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" }
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" }
        ],
        code: [{ required: true, trigger: "change", message: "请输入验证码" }]
      },
      // upform: "http://sl0ntpo8c.hn-bkt.clouddn.com/085bb8645c9743bfbecb49532d5d9445.png",
      demoArray: [],
      sudokus: [
        {
          img_src: "https://edu.lswai.com/穿越时空的思念01.PNGcdc1f698-28f0-44f1-9acd-0ad0258a4950.PNG",
          name: '穿越时空的思念01',
          id: 0
        },
        {
          img_src: "https://edu.lswai.com/一种护理装置及其使用方法图片.pnged5aebc2-58b0-4a26-bd39-207e62ba1c32.png",
          name: '一种护理装置及其使用方法图片',
          id: 1
        },
        {
          img_src: "https://edu.lswai.com/%E9%A1%B9%E7%9B%AE%E7%94%B3%E8%AF%B7.PNG18d46d54-308f-46a4-848a-b11feba79008.PNG",
          name: '新增项目',
          id: 2
        }
      ],
      curSelect: null
    }
  },
  created() {
    this.getList();
    // 生成模拟数据
    for (let i = 0; i < 5; i++) {
      let e = {};
      e.name = "div" + i;
      e.status = true;
      this.demoArray.push(e);
    }
  },
  methods: {
    uploadFileQN() {
      uploadFile()
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
    startFun(e) {
      console.log("点击了按钮")
      var that = this;
      var list = that.sudokus;
      for (var i = 0, len = list.length; i < len; ++i) {
        if (list[i].id == e) {
          that.curSelect = i;
          console.log("======" + that.curSelect);
          that.curSelect = null;
        }
      }
    },
    showInfo(item) {
      console.log(item.name);
      console.log(item.status);
    },
    // 请求-上传附件
    upLoad() {
      const _this = this;
      //   const fileType = [
      //     "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
      //     "application/vnd.ms-excel"
      //   ];
      const fileType = ['xls', 'xlsx', 'et', 'png']
      const inputFile = document.createElement("input")
      inputFile.type = "file"
      inputFile.style.display = "none"
      document.body.appendChild(inputFile)
      inputFile.click()
      inputFile.addEventListener("change", function () {
        const file = inputFile.files[0];
        var testmsg = file.name.substring(file.name.lastIndexOf('.') + 1)
        if (!fileType.includes(testmsg)) {
          _this.$message.warning("上传的文件格式只能是,xls,xlsx,et");
          document.body.removeChild(inputFile);
          return false;
        }
        const formData = new FormData();
        formData.append("file", file);
        uploadFile(formData).then(response => {
          if (response.code == 200) {
            _this.$message.success(response.message || '导入成功')

          } else {
            _this.$message.error(response.message || '导入失败')
          }
          document.body.removeChild(inputFile)
        })
        // window.request({
        //   url: 'xxx/xxx',
        //   data: formData,
        //   success: (res) => {
        //     if (res.code === 200) {
        //       _this.$message.success(res.message || "导入成功");
        //       _this.getTableList();
        //     } else {
        //       _this.$message.success("导入失败");
        //     }

        //   },
        //   finally: () => {
        //     document.body.removeChild(inputFile);
        //   }
        // })
      })
    },
  }
};
</script>
<style lang="scss" scoped>
.sudoku_row {
  display: flex;
  align-items: center;
  width: 100%;
  flex-wrap: wrap;
}

.sudoku_item {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  width: 25%;
  padding-top: 10px;
  padding-bottom: 10px;
}

.opacity {
  opacity: 0.4;
  background: #e5e5e5;
}

.sudoku_item img {
  margin-bottom: 3px;
  display: block;
}
</style>