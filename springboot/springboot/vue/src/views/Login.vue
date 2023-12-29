<template>
  <div class="wrapper">
    <div style="height: 60px; line-height: 60px; font-size: 20px; padding-left: 50px; color: white;
      background-color: rgba(0,0,0,0.2)">管理系统
    </div>
    <div
        style="margin: 150px auto; background-color: #fff; width: 350px; height: 300px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>登 录</b></div>
      <el-form @submit.prevent="login" :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input size="medium" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right">
          <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/register')">前往注册</el-button>
          <el-button type="primary" size="small" autocomplete="off" @click="login">登录</el-button>

        </el-form-item>

      </el-form>
    </div>


  </div>
</template>

<script>
import {resetRouter, setRoutes} from "@/router";

export default {
  name: "Login",
  data() {
    return {
      user: {
        username: "wang",
        password: "123"
      },
      pass: {},
      dialogFormVisible: false,
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'} // trigger是校验的时机，blur是失去焦点时校验
        ],
      }
    }
  },
  components: {},
  mounted() {  // 渲染成html后调用
    // 重置路由
    resetRouter()
    document.addEventListener('keydown', this.handleKeyPress)
  },
  beforeDestroy() {  // 离开页面时
    document.removeEventListener('keydown', this.handleKeyPress)
  },
  methods: {
    login() {
      this.$refs['userForm'].validate((valid) => {  // 当表单通过rules时valid为true
        if (valid) {  // 表单校验合法
          this.request.post("/user/login", this.user).then(res => {
            if (res.code === '200') {
              localStorage.setItem("user", JSON.stringify(res.data))  // 存储用户信息到浏览器
              localStorage.setItem("menus", JSON.stringify(res.data.menus))  // 存储用户菜单到浏览器

              // 动态设置当前用户的路由
              setRoutes()
              this.$router.push("/")
              this.$message.success("登录成功")
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      });
    },
    handleKeyPress(event) {
      if (event.key === 'Enter') {
        this.login()
      }
    }
  }
}
</script>

<style>
.wrapper {
  height: 100vh;
  background-image: radial-gradient(circle at 67% 83%, hsla(317, 0%, 96%, 0.05) 0%, hsla(317, 0%, 96%, 0.05) 1%, transparent 1%, transparent 5%, transparent 5%, transparent 100%),
                    radial-gradient(circle at 24% 80%, hsla(317, 0%, 96%, 0.05) 0%, hsla(317, 0%, 96%, 0.05) 27%, transparent 27%, transparent 63%, transparent 63%, transparent 100%),
                    radial-gradient(circle at 23% 5%, hsla(317, 0%, 96%, 0.05) 0%, hsla(317, 0%, 96%, 0.05) 26%, transparent 26%, transparent 82%, transparent 82%, transparent 100%),
                    radial-gradient(circle at 21% 11%, hsla(317, 0%, 96%, 0.05) 0%, hsla(317, 0%, 96%, 0.05) 35%, transparent 35%, transparent 45%, transparent 45%, transparent 100%),
                    radial-gradient(circle at 10% 11%, hsla(317, 0%, 96%, 0.05) 0%, hsla(317, 0%, 96%, 0.05) 21%, transparent 21%, transparent 81%, transparent 81%, transparent 100%),
                    radial-gradient(circle at 19% 61%, hsla(317, 0%, 96%, 0.05) 0%, hsla(317, 0%, 96%, 0.05) 20%, transparent 20%, transparent 61%, transparent 61%, transparent 100%),
                    radial-gradient(circle at 13% 77%, hsla(317, 0%, 96%, 0.05) 0%, hsla(317, 0%, 96%, 0.05) 63%, transparent 63%, transparent 72%, transparent 72%, transparent 100%),
                    radial-gradient(circle at 30% 93%, hsla(317, 0%, 96%, 0.05) 0%, hsla(317, 0%, 96%, 0.05) 33%, transparent 33%, transparent 82%, transparent 82%, transparent 100%),
                    linear-gradient(90deg, rgb(22, 176, 207), rgb(103, 7, 215));
  overflow: hidden;
}
</style>
