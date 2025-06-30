<script setup>
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import router from '@/router'
import { post } from '@/net'


const validatePassword = (rule, value, callback) => {
  if (!value) {
    callback(new Error('请再次输入密码'))
  } else if (value !== data.form.password) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const data = reactive({
  form: {
    password: '',
    password_repeat: '',
    email: '',
    code: ''
  },
  rules: {
    password: [
      { required: true, message: '请输入密码', trigger: 'blur' },
      { min: 6, max: 16, message: '密码的长度必须在6-16个字符之间', trigger: ['blur', 'change'] }
    ],
    password_repeat: [
      { validator: validatePassword, trigger: ['blur', 'change'] }
    ],
    email: [
      { required: true, message: '请输入邮件地址', trigger: 'blur' },
      { type: 'email', message: '请输入合法的电子邮件地址', trigger: ['blur', 'change'] }
    ],
    code: [
      { required: true, message: '请输入获取的验证码', trigger: 'blur' }
    ]
  }
})

const active = ref(0)
const formRef = ref()
const isEmailValid = ref(false)
const coldTime = ref(0)


const onValidate = (prop, isValid) => {
  if (prop === 'email') {
    isEmailValid.value = isValid
  }
}


const validateEmail = () => {
  coldTime.value = 60
  post(
    '/api/auth/valid-reset-email',
    { email: data.form.email },
    (message) => {
      ElMessage.success(message)
      const timer = setInterval(() => {
        coldTime.value--
        if (coldTime.value <= 0) {
          clearInterval(timer)
        }
      }, 1000)
    },
    (message) => {
      ElMessage.warning(message)
      coldTime.value = 0
    }
  )
}

const startReset = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      post(
        '/api/auth/start-reset',
        { email: data.form.email, code: data.form.code },
        () => {
          active.value++
        },
        (message) => {
          ElMessage.warning(message)
        }
      )
    } else {
      ElMessage.warning('请填写电子邮件地址和验证码')
    }
  })
}

const doReset = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      post(
        '/api/auth/do-reset',
        { password: data.form.password },
        (message) => {
          ElMessage.success(message)
          router.push('/')
        },
        (message) => {
          ElMessage.warning(message)
        }
      )
    } else {
      ElMessage.warning('请填写新的密码')
    }
  })
}
</script>


<template>
    <div class="bg">
        <div class="dowebok">

            <div style="margin: 30px 20px">
                <el-steps :active="active" finish-status="success" align-center>
                    <el-step title="验证电子邮件" />
                    <el-step title="重新设定密码" />
                </el-steps>
            </div>

            <transition name="custom-fade" mode="out-in">
                <div style="text-align: center;margin: 0 20px;height: 100%" v-if="active === 0">
                    <div style="margin-top: 2rem">
                        <div style="font-size: 30px;color: white;font-weight: bold">重置密码</div>
                        <div style="font-size: 14px;color: grey">请输入需要重置密码的电子邮件地址</div>
                    </div>

                    <div style="margin-top: 1.5rem">
                        <el-form :model="data.form" style="width: 100%" @validate="onValidate" ref="formRef"
                            :rules="data.rules">


                            <div class="form-group">
                                <el-form-item prop="email" style="width: 100%">
                                    <label for="email" class="form-label">电子邮件</label>
                                    <div class="input_rep">
                                        <el-input v-model="data.form.email" type="email" placeholder="电子邮件地址"
                                            class="input2" prefix-icon="Message" clearable />
                                    </div>
                                </el-form-item>
                            </div>

                            <div class="form-group">
                                <el-form-item prop="code" style="width: 100%">
                                    <label for="code" class="form-label">验证码</label>
                                    <el-row :gutter="10" style="width: 100%" type="flex" align="middle">
                                        <el-col :span="17">
                                            <el-input v-model="data.form.code" :maxlength="6" type="text"
                                                placeholder="请输入验证码" prefix-icon="EditPen" class="input2">
                                            </el-input>
                                        </el-col>
                                        <el-col :span="6" style="padding-left: 20px;">
                                            <el-button type="success" @click="validateEmail" class="captcha-btn"
                                                :disabled="!isEmailValid || coldTime > 0">
                                                {{ coldTime > 0 ? '请稍后 ' + coldTime + ' 秒' : '获取验证码' }}
                                            </el-button>
                                        </el-col>
                                    </el-row>
                                </el-form-item>
                            </div>

                        </el-form>

                    </div>
                    <div class="button1">
                        <el-button @click="startReset()" class="submit" type="success" plain>
                            开始重置密码
                        </el-button>
                    </div>
                </div>
            </transition>

            <transition name="el-fade-in-linear" mode="out-in">
                <div style="text-align: center;margin: 0 20px;height: 100%" v-if="active === 1">
                    <div style="margin-top: 2rem">
                        <div style="font-size: 30px;color: white;font-weight: bold">重置密码</div>
                        <div style="font-size: 14px;color: grey">请输入需要重置密码的电子邮件地址</div>
                    </div>
                    <div style="margin-top: 1.5rem">
                        <el-form :model="data.form" style="width: 100%" @validate="onValidate" ref="formRef"
                            :rules="data.rules">


                            <div class="form-group">
                                <el-form-item prop="password" style="width: 100%">
                                    <label for="password" class="form-label">密码</label>
                                    <div class="input_rep">
                                        <el-input v-model="data.form.password" maxlength="16" class="input2"
                                            type="password" placeholder="密码" prefix-icon="Lock" show-password />
                                    </div>
                                </el-form-item>
                            </div>

                            <div class="form-group">
                                <el-form-item prop="password_repeat" style="width: 100%">
                                    <label for="password_repeat" class="form-label">确认密码</label>
                                    <div class="input_rep">
                                        <el-input v-model="data.form.password_repeat" maxlength="16" class="input2"
                                            type="password" placeholder="确认密码" prefix-icon="Lock" show-password />
                                    </div>
                                </el-form-item>
                            </div>


                        </el-form>
                    </div>
                    <div class="button1">
                        <el-button @click="doReset()" class="submit" type="success" plain>
                            立即重置密码
                        </el-button>
                    </div>
                </div>
            </transition>
        </div>
    </div>
</template>


<style>

.custom-fade-enter-active,
.custom-fade-leave-active {
  transition: opacity 0.3s cubic-bezier(0.645, 0.045, 0.355, 1); 
}
.custom-fade-enter-from,
.custom-fade-leave-to {
  opacity: 0;
}
.custom-fade-enter-to,
.custom-fade-leave-from {
  opacity: 1;
}


.el-form-item__error {
  color: #f56c6c;   
  font-size: 14px;  
  margin-left: 1.5rem; 
  padding-top: 0;
  display: block;    
}

.bg {
    font-size: 21px;
    font-family: 'Open Sans', Helvetica, Arial, sans-serif;
     background-image: url("@/assets/imgs/bg1.jpg");
    background-attachment: fixed;
	background-position: center;
	background-repeat: no-repeat;
	background-size: cover;
    display: grid;
    height: 100vh;
    place-items: center;
}
.dowebok {
	border-radius: 0.7rem;
	box-shadow: 0 0.9rem 1.7rem rgba(0, 0, 0, 0.25),
		0 0.7rem 0.7rem rgba(0, 0, 0, 0.22);
	overflow: hidden;
    width: 100%;
    position: absolute;
    left: 50%;
    top: 50%;
    width: 900px;
    height: 600px;
    margin: -300px 0 0 -450px;
    background-color: transparent;
    transition: transform 0.6s ease-in-out; 
}

.captcha-btn {
  width: 100%;
  height: 40px;
  line-height: 40px;        
  border-radius: 4px;       
  font-size: 14px;          
  font-weight: 500;
  background: linear-gradient(90deg, #5ec6db 0%, #99caf5 100%);
  color: #fff;
  transition: background 0.3s ease;
}
.el-button.is-disabled.captcha-btn {
  background: #af9c9c; 
  color: #ffffff; 
  border: none;
}

.form-group {
  width: 80%;
  margin: 0 auto;
  position: relative;
}
.el-form-item {
    width: 100%;
    margin-bottom:0.6rem;
}

.form-label {
  display: block;
  font-size: 17px;
  font-weight: 500;
  color: #fffdfd;
  margin-left: 1rem;
  transition: all 0.3s ease;
}

.form-title {
  width: 100%;
  font-size: 30px;
  text-align: center;
  padding-bottom: 1rem;
  color: #fffdfd;
}

.input_rep {
  width: 100%;
  margin: 0 auto;
}

.input2 .el-input__inner {
  width: 100%;
  background-color: #fff;
  height: 50px;
  line-height: 50px;
  font-size: 20px;
  border-radius: 8px;
  border: none;
  box-sizing: border-box;
}

.input2 .el-input__wrapper {
  position: relative;
  margin: 0rem auto;
}

.input2 .el-input__wrapper.is-focus {
  border: 2px solid #4f8ac6;
  box-shadow: 0 0 6px rgba(64, 158, 255, 0.5);
  border-radius: 8px;
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


.button1 .el-button.submit .el-button__inner {
  padding: 0;
  line-height: 45px; 
}

.button1 .el-button.submit:hover {
  background: linear-gradient(45deg, #6296bb 0%, #7593c3 100%);
  transform: translateY(-3px);
  box-shadow: 0 8px 20px rgba(0,0,0,0.2);
}

.button1 .el-button.submit:active {
  transform: translateY(1px);
  box-shadow: 0 2px 5px rgba(0,0,0,0.15);
  background: linear-gradient(135deg, var(--blue) 0%, var(--lightblue) 50%, var(--blue) 100%);
  filter: brightness(0.95);
  transition: all 0.1s ease;
}


</style>