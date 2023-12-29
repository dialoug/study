<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除这些数据吗？"
          @confirm="delBatch">
        <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>
    </div>

    <el-table :data="tableData" border stripe max-height="680" :header-cell-class-name="'headerBg'"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="username" label="用户名" width="140"></el-table-column>
      <el-table-column prop="name" label="姓名" width="120"></el-table-column>
      <el-table-column prop="gender" label="性别">
        <template v-slot="scope">
          <el-tag>{{ scope.row.gender }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="title" label="职位">
        <template v-slot="scope">
          <el-tag>{{ scope.row.title }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="departmentId" label="科室">
        <template v-slot="scope">
<!--          <el-tag v-if="scope.row.departmentId">{{departmentChildren.find(v => v.id === scope.row.departmentId).name}}</el-tag>-->
          <el-tag v-if="scope.row.departmentId">{{departmentChildren.find(v => v.id === scope.row.departmentId) ? departmentChildren.find(v => v.id === scope.row.departmentId).name : '' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="idCard" label="身份证"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column prop="phone" label="电话"></el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template v-slot="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="del(scope.row.id)">
            <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2, 5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="医生信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" size="small" :model="form" :rules="rules" ref="userForm">
        <el-form-item prop="username" label="用户名">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item v-if="form.password" label="密码">
          <el-input v-model="form.password" :disabled="true" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="name" label="姓名">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="gender" label="性别">
          <el-radio v-model="form.gender" label="男" border>男</el-radio>
          <el-radio v-model="form.gender" label="女" border>女</el-radio>
        </el-form-item>
        <el-form-item prop="title" label="职称">
          <el-select v-model="form.title" style="width: 100%;">
            <el-option value="主治医生">主治医生</el-option>
            <el-option value="副主任医生">副主任医生</el-option>
            <el-option value="主任医生">主任医生</el-option>
          </el-select>
        </el-form-item>
        <el-form-item  prop="title" label="科室">
          <el-select v-model="form.departmentId" style="width: 100%;">
            <el-option-group
                v-for="item in departments"
                :key="item.id"
                :label="item.name">
              <el-option
                  v-for="department in item.children"
                  :key="department.id"
                  :label="department.name"
                  :value="department.id">
              </el-option>
            </el-option-group>
          </el-select>
        </el-form-item>
        <el-form-item prop="idCard" label="身份证">
          <el-input v-model="form.idCard" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="email" label="邮箱">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="phone" label="电话">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "User",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      name: "",
      form: {},
      departments: [],
      departmentChildren: [],
      dialogFormVisible: false,
      multipleSelection: [],
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        name: [
          {required: true, message: '请输入姓名', trigger: 'blur'},
        ],
        title: [
          {required: true, message: '请输入职位', trigger: 'blur'},
        ],
        gender: [
          {required: true, message: '请输入性别', trigger: 'blur'},
        ],
        email: [
          {required: true, message: '请输入邮箱', trigger: 'blur'},
        ],
        idCard: [
          {required: true, message: '请输入身份证', trigger: 'blur'},
        ],
        phone: [
          {required: true, message: '请输入电话', trigger: 'blur'},
        ],
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/user/doctorPage", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
      this.request.get("/department/list").then(res => {
        this.departments = res.data
        this.departmentChildren = this.departments.filter(v => v.children).flatMap(v => v.children)
        console.log(this.departments)
        console.log(JSON.parse(JSON.stringify(this.departmentChildren)))
      })
    },
    save() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {  // 验证通过
          this.request.post("/user", this.form).then(res => {
            if (res.code === '200') {
              this.$message.success("保存成功")
              this.dialogFormVisible = false
              this.load()
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    handleAdd() {
      this.dialogFormVisible = true
      if (this.$refs['userForm'] !== undefined) {
        this.$refs['userForm'].resetFields();
      }
      this.form = {
        password: "123",
        role: '2'
      }
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      if (this.$refs['userForm'] !== undefined) {
        this.$refs['userForm'].resetFields();
      }
        this.form.password = null
      this.dialogFormVisible = true
    },
    del(id) {
      this.request.delete("/user/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id)
      this.request.post("/user/del/batch", ids).then(res => {
        if (res.code === '200') {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    reset() {
      this.name = ""
      this.load()
    },
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
  }
}
</script>


<style>
.headerBg {
  background: #eee !important;
}
</style>
