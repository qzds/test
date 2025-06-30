<template>
  <div class="bg">
    <div class="dowebok" :class="{ 's--signup': isSignup }">
      <RouterView v-if="!isSignup" />

      <div class="sub-cont">
        <div class="img">
          <div class="img__text m--up">
            <h2>还未注册？</h2>
            <p>立即注册，便捷管理课表！</p>
          </div>
          <div class="img__text m--in">
            <h2>已有帐号？</h2>
            <p>有帐号就登录吧，好久不见了！</p>
          </div>
          <div class="img__btn" @click="toggle">
            <span class="m--up">注 册</span>
            <span class="m--in">登 录</span>
          </div>
        </div>

        <div class="form-container">
          <RouterView v-if="isSignup" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const isSignup = ref(false)
const route = useRoute()
const router = useRouter()

watch(
  () => route.name,
  (name) => {
    isSignup.value = (name === 'welcome-register')
  },
  { immediate: true }
)

const toggle = () => {
  if (isSignup.value) {
    router.push({ name: 'welcome-login' })
  } else {
    router.push({ name: 'welcome-register' })
  }
}
</script>

<style scoped>
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
  font-size: 26px;
  text-align: center;
  padding-bottom: 0.5rem;
}

.form1 .login_box .label2 {
  position: absolute;
  left: 2.25rem;
  font-size: 16px;
  padding-top: 0.3rem;
  color: #999;
  pointer-events: none;
  transition: 0.2s ease all;
  color: #000;
  pointer-events: none;
  transition: all 0.5s;
}

.input1 :deep(.el-input__inner) {
  background-color: #fff;
  height: 50px;
  line-height: 50px;
  font-size: 20px;
  border-radius: 8px;
  border: none;
  box-sizing: border-box;
}

.input1 :deep(.el-input__wrapper.is-focus) {
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

.button1 {
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



:deep(.el-button.submit:hover) {
  background: linear-gradient(45deg, #6296bb 0%, #7593c3 100%);
  transform: translateY(-3px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
  transition: all 0.3s ease;
}

:deep(.el-button.submit:active) {
  transform: translateY(1px);
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.15);
  background: linear-gradient(135deg, var(--blue) 0%, var(--lightblue) 50%, var(--blue) 100%);
  filter: brightness(0.95);
  transition: all 0.1s ease;
}

.sub-cont {
  overflow: hidden;
  position: absolute;
  left: 640px;
  top: 0;
  width: 900px;
  height: 100%;
  padding-left: 260px;
  background-color: transparent;
  transition: -webkit-transform 0.6s ease-in-out;
  transition: transform 0.6s ease-in-out;
  transition: transform 0.6s ease-in-out, -webkit-transform 0.6s ease-in-out;
  transition: transform 0.6s ease-in-out;
}

.dowebok.s--signup .sub-cont {
  -webkit-transform: translate3d(-640px, 0, 0);
  transform: translate3d(-640px, 0, 0);
}


.img {
  overflow: hidden;
  z-index: 2;
  position: absolute;
  left: 0;
  top: 0;
  width: 260px;
  height: 100%;
  padding-top: 360px;
}

.img:before {
  content: '';
  position: absolute;
  right: 0;
  top: 0;
  width: 900px;
  height: 100%;
  background-image: url("@/assets/imgs/bg1.jpg");
  background-size: cover;
  transition: -webkit-transform 0.6s ease-in-out;
  transition: transform 0.6s ease-in-out;
  transition: transform 0.6s ease-in-out, -webkit-transform 0.6s ease-in-out;
  transition: transform 0.6s ease-in-out;
}

.img:after {
  content: '';
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.6);
}

.dowebok.s--signup .img:before {
  -webkit-transform: translate3d(640px, 0, 0);
  transform: translate3d(640px, 0, 0);
}

.img__text {
  z-index: 2;
  position: absolute;
  left: 0;
  top: 50px;
  width: 100%;
  padding: 0 20px;
  text-align: center;
  color: #fff;
  transition: -webkit-transform 0.6s ease-in-out;
  transition: transform 0.6s ease-in-out;
  transition: transform 0.6s ease-in-out, -webkit-transform 0.6s ease-in-out;
}

.img__text h2 {
  margin-bottom: 10px;
  font-weight: normal;
}

.img__text p {
  font-size: 14px;
  line-height: 1.5;
}

.dowebok.s--signup .img__text.m--up {
  -webkit-transform: translateX(520px);
  transform: translateX(520px);
}

.img__text.m--in {
  -webkit-transform: translateX(-520px);
  transform: translateX(-520px);
}

.dowebok.s--signup .img__text.m--in {
  -webkit-transform: translateX(0);
  transform: translateX(0);
}




.img__btn {

  position: relative;
  z-index: 2;
  padding: 0;
  width: 110px;
  height: 45px;
  margin: 0 auto;
  background: transparent;
  color: #fff;
  text-transform: uppercase;
  font-size: 19px;
  font-weight: bold;
  line-height: 45px;
  text-align: center;
  letter-spacing: 1px;
  cursor: pointer;
  overflow: hidden;
  border-radius: 4px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
}


.img__btn:hover {
  transform: scale(1.05);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.4);
}

.img__btn:active {
  transform: scale(0.98);
}

.img__btn:after {
  content: '';
  z-index: 2;
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  border: 2px solid #fff;
  border-radius: 30px;
}

.img__btn span {
  position: absolute;
  left: 0;
  top: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  transition: -webkit-transform 0.6s;
  transition: transform 0.6s;
  transition: transform 0.6s, -webkit-transform 0.6s;
}

.img__btn span.m--in {
  -webkit-transform: translateY(-72px);
  transform: translateY(-72px);
}

.dowebok.s--signup .img__btn span.m--in {
  -webkit-transform: translateY(0);
  transform: translateY(0);
}

.dowebok.s--signup .img__btn span.m--up {
  -webkit-transform: translateY(72px);
  transform: translateY(72px);
}

.fb-btn {
  border: 2px solid #d3dae9;
  color: #8fa1c7;
}

.fb-btn span {
  font-weight: bold;
  color: #455a81;
}

.sign-in {
  transition-timing-function: ease-out;
  padding: 6rem 2rem 3rem 2rem;
  transition: transform 0.6s ease-in-out;
}

.dowebok.s--signup .sign-in {
  transition-timing-function: ease-in-out;
  transition: transform 0.6s ease-in-out;
  -webkit-transform: translate3d(840px, 0, 0);
  transform: translate3d(1140px, 0, 0);
}

.sign-up {
  -webkit-transform: translate3d(-900px, 0, 0);
  transform: translate3d(-900px, 0, 0);
  transition: transform 0.6s ease-in-out;
  padding: 2.5rem 2rem;
}

.dowebok.s--signup .sign-up {
  transition-timing-function: ease-in-out;
  transition: transform 0.6s ease-in-out;
  -webkit-transform: translate3d(0, 0, 0);
  transform: translate3d(0, 0, 0);
}

.form-title {
  width: 100%;
  font-size: 26px;
  text-align: center;
  padding-bottom: 0.5rem;
  margin-bottom: 1rem;
  color: #fffdfd;
}
</style>