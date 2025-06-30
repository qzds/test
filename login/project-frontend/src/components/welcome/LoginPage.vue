<template>
  <div class="form1 sign-in">
    <el-form :model="data.form"  ref="formRef"  :rules="data.rules" style="width: 100%">
      <h2>欢迎使用</h2>

      <el-form-item style="width: 100%" prop="username">
        <div class="login_box" :class="{ 'has-value': data.form.username }">
          <el-input
            v-model="data.form.username"
            placeholder=" "
            class="input1"
            prefix-icon="User"
            clearable
          />
          <label class="label2">用户名</label>
        </div>
      </el-form-item>

      <el-form-item style="width: 100%" prop="password">
        <div class="login_box" :class="{ 'has-value': data.form.password }">
          <el-input
            v-model="data.form.password"
            placeholder=" "
            class="input1"
            prefix-icon="Lock"
            show-password
          />
          <label class="label2">密码</label>
        </div>
      </el-form-item>

      <el-row style="margin-top: 5px">
        <el-col :span="8" :offset="3" style="text-align: left">
          <el-checkbox v-model="data.form.remember" label="记住我" />
        </el-col>
        <el-col :span="8" :offset="2" style="text-align: right">
          <el-link @click="router.push('/forget')">忘记密码？</el-link>
        </el-col>
      </el-row>

      <div class="button1">
        <el-button @click="submit" type="success" plain class="submit">
          立 即 登 录
        </el-button>
      </div>
    </el-form>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { get, post } from '@/net'
import router from '@/router'
import { useStore } from '@/stores'

const store = useStore()
const formRef = ref(null)

const data = reactive({
  form: {
    username: '',
    password: '',
    remember: false
  },
  rules: {
    username: [
      { required: true, message: '请输入用户名' },
      { min: 6, max: 16, message: '长度在 6 到 16 个字符' }
    ],
    password: [
      { required: true, message: '请输入密码' },
      { min: 6, max: 16, message: '长度在 6 到 16 个字符' }
    ]
  }
})

const submit = () => {
  formRef.value.validate(valid => {
    if (!valid) {
      return
    }

    post(
      '/api/auth/login',
      {
        username: data.form.username,
        password: data.form.password,
        remember: data.form.remember
      },
      message => {
        ElMessage.success(message)
        get(
          '/api/user/me',
          userInfo => {
            store.auth.user = userInfo
            router.push('/index')
          },
          () => {
            store.auth.user = null
          }
        )
      }
    )
  })
}
</script>

<style>

.el-form-item__error {
  color: #f56c6c;   
  font-size: 14px;  
  margin-left: 6rem; 
  padding-top: 0;
  display: block;    
}

.form1 {
 
    display: flex;
    flex-direction: column;
    align-items: center; 
    width: 640px;
    height: 100%;
    transition: -webkit-transform 0.6s ease-in-out;
    transition: transform 0.6s ease-in-out;
    transition: transform 0.6s ease-in-out, -webkit-transform 0.6s ease-in-out;
}
.form1 .login_box {
    display: flex;
    align-items: center; 
    position: relative;
	width: 80%;
    margin: 1rem auto;
}

h2 {
    width: 100%;
    font-size: 30px;
    text-align: center;
    padding-bottom: 1rem;
}

.form1 .login_box .label2 {
    position: absolute;
    left: 2.5rem;
    font-size: 20px;
    padding-top: 0.3rem;
    color: #999;
    pointer-events: none;
    transition: 0.2s ease all; 
    color: #000;       
    pointer-events: none;  
    transition: all 0.5s;
}

.sign-in {
    transition-timing-function: ease-out;
    padding: 5rem 2rem  3rem 2rem;
    transition: transform 0.6s ease-in-out;
}

.input1 .el-input__inner {
  background-color: #fff;
  height: 50px;
  line-height: 50px;
  font-size: 20px;
  border-radius: 8px;
  border: none;
  box-sizing: border-box;
}

.input1 .el-input__wrapper.is-focus {
  border: 2px solid #4f8ac6;
  box-shadow: 0 0 6px rgba(64, 158, 255, 0.5);
  border-radius: 8px;
}

.login_box:focus-within .label2,
.login_box.has-value .label2 {
  top: -2rem;
  color: #644c4c;
  font-size: 17px;
}

.button1{
    display: flex;
  justify-content: center;
 color: #cad2d8;
}

.el-button.submit {
  margin-top: 2rem;
  background-color: var(--blue);
  background-image: linear-gradient(90deg, var(--blue) 0%, var(--lightblue) 74%);
  text-transform: uppercase;
  display: block;
  width: 260px;
  height: 3.5rem;
  border-radius: 30px;
  padding: 0;
  border: none;
  overflow: hidden;
  color: #fff;
  font-size: 25px;
  transition: all 0.5s ease;
  position: relative;
  box-shadow: none;
}


.el-button.submit::v-deep(.el-button__inner),
.el-button.submit::v-deep(button) {
  width: 100%;
  height: 100%;
  padding: 0;
  border: none;
  outline: none;
  box-shadow: none;
  cursor: pointer;
}
.button1 ::v-deep(.el-button.submit:hover) {
  background: linear-gradient(45deg,
    #6296bb 0%,
    #7593c3 100%
  );
  transform: translateY(-3px);
  box-shadow: 0 8px 20px rgba(0,0,0,0.2);
}

.button1 ::v-deep(.el-button.submit:active) {
  transform: translateY(1px);
  box-shadow: 0 2px 5px rgba(0,0,0,0.15);
  background: linear-gradient(135deg, var(--blue) 0%, var(--lightblue) 50%, var(--blue) 100%);
  filter: brightness(0.95);
  transition: all 0.1s ease;
}


</style>