<template>
    <div class="form1 sign-up">
        <h2 class="form-title">立即注册账户</h2>

        

        <el-form :model="data.form" style="width: 100%" @validate="onValidate" ref="formRef" :rules="data.rules">

            <div class="form-group">
                <el-form-item prop="username" style="width: 100%">
                    <label for="username" class="form-label">用户名</label>
                    <div class="input_rep">
                        <el-input v-model="data.form.username" maxlength="16" class="input2" placeholder="用户名"
                            prefix-icon="User" clearable />
                    </div>
                </el-form-item>
            </div>

        


            <div class="form-group">
                <el-form-item prop="email" style="width: 100%">
                <label for="email" class="form-label">电子邮件</label>
                <div class="input_rep">
                    <el-input v-model="data.form.email" type="email" placeholder="电子邮件地址" class="input2"
                        prefix-icon="Message" clearable />
                </div>
            </el-form-item>
            </div>


            
            
            <div class="form-group">
                <el-form-item prop="password" style="width: 100%">
                <label for="password" class="form-label">密码</label>
                <div class="input_rep">
                    <el-input v-model="data.form.password" maxlength="16" class="input2" type="password"
                        placeholder="密码" prefix-icon="Lock" show-password />
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
                                {{coldTime > 0 ? '请稍后 ' + coldTime + ' 秒' : '获取验证码'}}
                            </el-button>
                        </el-col>
                    </el-row>
                </el-form-item>
            </div>

        </el-form>
        <div class="button1">
            <el-button @click="submit" type="success" plain class="submit">
                立 即 注 册
            </el-button>
        </div>
    </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import router from '@/router'
import { post } from '@/net'

const data = reactive({
  form: {
    username: '',
    password: '',
    email: '',
    code: ''
  },
  rules: {
    username: [
      { validator: validateUsername, trigger: ['blur', 'change'] },
      { min: 6, max: 16, message: '用户名的长度必须在6-16个字符之间', trigger: ['blur', 'change'] }
    ],
    password: [
      { required: true, message: '请输入密码', trigger: 'blur' },
      { min: 6, max: 16, message: '密码的长度必须在6-16个字符之间', trigger: ['blur', 'change'] }
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

function validateUsername(rule, value, callback) {
  if (!value) {
    callback(new Error('请输入用户名'))
  } else if (!/^[a-zA-Z0-9\u4e00-\u9fa5]+$/.test(value)) {
    callback(new Error('用户名不能包含特殊字符，只能是中文/英文'))
  } else {
    callback()
  }
}



const formRef = ref(null)
const isEmailValid = ref(false)
const coldTime = ref(0)
let timerId = null

// 调用校验后更新 isEmailValid
const onValidate = (prop, isValid) => {
  if (prop === 'email') {
    isEmailValid.value = isValid
  }
}

// 点击“获取验证码”
const validateEmail = () => {
  if (!isEmailValid.value) {
    ElMessage.warning('请先输入并校验合法的邮箱地址！')
    return
  }
  coldTime.value = 60
  post('/api/auth/valid-register-email', { email: data.form.email },
    message => {
      ElMessage.success(message)
      timerId = setInterval(() => {
        coldTime.value--
        if (coldTime.value <= 0) {
          clearInterval(timerId)
        }
      }, 1000)
    },
    message => {
      ElMessage.warning(message)
      coldTime.value = 0
    }
  )
}


const submit = () => {
  formRef.value.validate(isValid => {
    if (!isValid) {
      ElMessage.warning('请完整填写注册表单内容！')
      return
    }
    post('/api/auth/register', {
      username: data.form.username,
      password: data.form.password,
      email: data.form.email,
      code: data.form.code
    }, message => {
      ElMessage.success(message)
      router.push('/')
    }, errorMsg => {
      ElMessage.error(errorMsg)
    })
  })
}
</script>

<style>
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

.el-form-item__error {
  color: #f56c6c;   
  font-size: 14px;  
  margin-left: 1.5rem; 
  padding-top: 0.1rem;
  display: block;    
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