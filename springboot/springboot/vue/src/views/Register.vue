<template>
  <div class="wrapper">
    <div style="height: 60px; line-height: 60px; font-size: 20px; padding-left: 50px; color: white;
      background-color: rgba(0,0,0,0.2)">管理系统
    </div>
    <div
        style="margin: 100px auto; background-color: #fff; width: 350px; height: 480px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>患 者 注 册</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input placeholder="请输入账号" size="medium" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="请输入密码" size="medium"  show-password v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item prop="name">
          <el-input placeholder="请输入姓名" size="medium" v-model="user.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="gender">
          <el-radio v-model="user.gender" label="男" border>男</el-radio>
          <el-radio v-model="user.gender" label="女" border>女</el-radio>
        </el-form-item>
        <el-form-item prop="phone">
          <el-input placeholder="请输入手机号" size="medium" v-model="user.phone"></el-input>
        </el-form-item>
        <el-form-item prop="age">
          <el-input placeholder="请输入年龄" size="medium" v-model="user.age"></el-input>
        </el-form-item>
        <el-form-item style="margin: 5px 0; text-align: right">
          <el-button type="warning" size="small"  autocomplete="off" @click="$router.push('/login')">返回登录</el-button>
          <el-button type="primary" size="small"  autocomplete="off" @click="login">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      user: {},
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        gender: [
          {required: true, message: '请输入性别', trigger: 'blur'},
        ],
        age: [
          {required: true, message: '请输入年龄', trigger: 'blur'},
        ],
        phone: [
          {required: true, message: '请输入手机号', trigger: 'blur'},
        ],
        name: [
          {required: true, message: '请输入姓名', trigger: 'blur'},
        ],
      }
    }
  },
  methods: {
    login() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          this.request.post("/user/register", this.user).then(res => {
            if(res.code === '200') {
              this.$message.success("注册成功")
              this.$router.push('Login')
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      });
    }
  }
}
</script>

<style>
  .wrapper {
    height: 100vh;
    background-image: radial-gradient(circle at 67% 83%, hsla(317, 0%, 96%, 0.05) 0%, hsla(317, 0%, 96%, 0.05) 1%, transparent 1%, transparent 5%, transparent 5%, transparent 100%), radial-gradient(circle at 24% 80%, hsla(317, 0%, 96%, 0.05) 0%, hsla(317, 0%, 96%, 0.05) 27%, transparent 27%, transparent 63%, transparent 63%, transparent 100%), radial-gradient(circle at 23% 5%, hsla(317, 0%, 96%, 0.05) 0%, hsla(317, 0%, 96%, 0.05) 26%, transparent 26%, transparent 82%, transparent 82%, transparent 100%), radial-gradient(circle at 21% 11%, hsla(317, 0%, 96%, 0.05) 0%, hsla(317, 0%, 96%, 0.05) 35%, transparent 35%, transparent 45%, transparent 45%, transparent 100%), radial-gradient(circle at 10% 11%, hsla(317, 0%, 96%, 0.05) 0%, hsla(317, 0%, 96%, 0.05) 21%, transparent 21%, transparent 81%, transparent 81%, transparent 100%), radial-gradient(circle at 19% 61%, hsla(317, 0%, 96%, 0.05) 0%, hsla(317, 0%, 96%, 0.05) 20%, transparent 20%, transparent 61%, transparent 61%, transparent 100%), radial-gradient(circle at 13% 77%, hsla(317, 0%, 96%, 0.05) 0%, hsla(317, 0%, 96%, 0.05) 63%, transparent 63%, transparent 72%, transparent 72%, transparent 100%), radial-gradient(circle at 30% 93%, hsla(317, 0%, 96%, 0.05) 0%, hsla(317, 0%, 96%, 0.05) 33%, transparent 33%, transparent 82%, transparent 82%, transparent 100%), linear-gradient(90deg, rgb(22, 176, 207), rgb(103, 7, 215));
    overflow: hidden;
  }
</style>
