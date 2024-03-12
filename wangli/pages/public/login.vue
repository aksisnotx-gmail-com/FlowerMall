<template>
	<view class="container">
		<view class="wrapper">
			<view class="user-info-box">
				<view class="portrait-box">
					<image class="portrait" :src="'/static/logo/logo-108.png'"></image>
				</view>
				<view class="info-box">
					<text class="username"></text>
				</view>
			</view>
			<view class="welcome">
				欢迎回来！
			</view>
			<!-- <view class="input-content">
				<view class="input-item">
					<text class="tit">手机号码</text>
					<input 
						type="number" 
						:value="mobile" 
						placeholder="请输入手机号码"
						maxlength="11"
						data-key="mobile"
						@input="inputChange"
					/>
				</view>
				<view class="input-item">
					<text class="tit">密码</text>
					<input 
						type="password" 
						:value="password" 
						placeholder="8-18位不含特殊字符的数字、字母组合"
						placeholder-class="input-empty"
						maxlength="20"
						password 
						data-key="password"
						@input="inputChange"
						@confirm="toLogin"
					/>
				</view>
			</view> -->
			<!-- <button class="confirm-btn" @click="toLogin" :disabled="logining">登录</button> -->
			<view class="forget-section">
				<!-- <text>其他登录方式：</text> -->
				<button class="confirm-btn" @click="handleWeixinLogin" :disabled="logining">微信登录</button>
				<!-- <uni-icons type="weixin" size="100" color="#0af02c" @click="handleWeixinLogin"></uni-icons> -->
			</view>
			<!-- <view class="forget-section">
				忘记密码?
			</view> -->
		</view>
		<!-- <view class="register-section">
			还没有账号?
			<text @click="toRegist">马上注册</text>
		</view> -->
	</view>
</template>

<script>
	import {wxLogin} from "@/api/login.js"
	import {toast} from '@/utils/common'
	import {  
        mapMutations  
    } from 'vuex';
	
	export default{
		data(){
			return {
				mobile: '17383417207',
				password: '!QAZxsw2',
				logining: false,
				type: "",
				id:"",
				idTmp: "",
				channel:""
			}
		},
		onLoad(option){
		},
		methods: {
			...mapMutations(['login','WxLogin']),
			handleWeixinLogin(){
				console.log(getCurrentPages());
				let that = this;
				wx.login({
				  success (res) {
				    if (res.code) {
						let userInfo = {'code':res.code}
						that.$store.dispatch('WxLogin', userInfo).then((res) => {
							that.loginSuccess();
				      }).catch((e) => {
				      })
				    } else {
						that.$modal.msgError("获取登录信息失败，请稍后再试")
				    }
				  }
				})
			},
			inputChange(e){
				const key = e.currentTarget.dataset.key;
				this[key] = e.detail.value;
			},
			navBack(){
				uni.navigateBack();
			},
			toRegist(){
				this.$router.push("/pages/public/registUser");
			},
			async toLogin(){
				this.logining = true;
				const {mobile, password} = this;
				if (mobile === "") {
				  this.$modal.msgError("请输入您的手机号")
				  this.logining = false;
				  return;
				} else if (password === "") {
				  this.$modal.msgError("请输入您的密码")
				  this.logining = false;
				  return;
				} else {
				  this.$modal.loading("登录中，请耐心等待...")
				}
				const sendData = {
					mobile,
					password
				};
				this.$store.dispatch('Login', sendData).then(() => {
					this.$modal.closeLoading()
					this.loginSuccess()
				}).catch(() => {
				 this.logining = false;
				})
				this.logining = false;
			},
			loginSuccess() {
				this.$store.dispatch('GetInfo').then(res => {
					const pages = getCurrentPages();
					const pageStackLength = pages.length;
					if (pageStackLength === 1) {
						this.$tab.reLaunch('/pages/public/request/index')
					} else {
						uni.navigateBack();
					}
				})
			}
		},

	}
</script>

<style lang='scss'>
	page {
		background: url("@/static/logo/background.png");
	}
	.container{
		padding-top: 115px;
		position:relative;
		width: 100vw;
		height: 100vh;
		overflow: hidden;
		background: #fff;
	}
	.wrapper{
		position:relative;
		z-index: 90;
		background: #fff;
		padding-bottom: 40upx;
	}
	.back-btn{
		position:absolute;
		left: 40upx;
		z-index: 9999;
		padding-top: var(--status-bar-height);
		top: 40upx;
		font-size: 40upx;
		color: $font-color-dark;
	}
	.left-top-sign{
		font-size: 120upx;
		/* color: $page-color-base; */
		position:relative;
		left: 200upx;
	}
	.right-top-sign{
		position:absolute;
		top: 80upx;
		right: -30upx;
		z-index: 95;
		&:before, &:after{
			display:block;
			content:"";
			width: 400upx;
			height: 80upx;
			background: #24f3e6;
		}
		&:before{
			transform: translate(50deg);
			border-radius: 0 50px 0 0;
		}
		&:after{
			position: absolute;
			right: -198upx;
			top: 0;
			transform: rotate(-50deg);
			border-radius: 50px 0 0 0;
			/* background: pink; */
		}
	}
	.left-bottom-sign{
		position:absolute;
		left: -270upx;
		bottom: -320upx;
		border: 100upx solid #fd5613;
		border-radius: 50%;
		padding: 180upx;
	}
	.welcome{
		position:relative;
		left: 50upx;
		/* top: -90upx; */
		font-size: 46upx;
		color: #555;
		text-shadow: 1px 0px 1px rgba(0,0,0,.3);
	}
	.input-content{
		padding: 0 60upx;
	}
	.input-item{
		display:flex;
		flex-direction: column;
		align-items:flex-start;
		justify-content: center;
		padding: 0 30upx;
		background:$page-color-light;
		height: 120upx;
		border-radius: 4px;
		margin-bottom: 50upx;
		&:last-child{
			margin-bottom: 0;
		}
		.tit{
			height: 50upx;
			line-height: 56upx;
			font-size: $font-sm+2upx;
			color: $font-color-base;
		}
		input{
			height: 60upx;
			font-size: $font-base + 2upx;
			color: $font-color-dark;
			width: 100%;
		}	
	}

	.confirm-btn{
		width: 630upx;
		height: 76upx;
		line-height: 76upx;
		border-radius: 50px;
		margin-top: 70upx;
		background:$buttom-background;
		color: $buttom-background-color;
		font-size: $font-lg;
		&:after{
			border-radius: 100px;
		}
	}
	.forget-section{
		font-size: $font-sm+2upx;
		color: $font-color-spec;
		text-align: center;
		margin-top: 240upx;
	}
	.register-section{
		position:absolute;
		left: 0;
		bottom: 50upx;
		width: 100%;
		font-size: $font-sm+2upx;
		color: $font-color-base;
		text-align: center;
		text{
			color: $font-color-spec;
			margin-left: 10upx;
		}
	}
	.user-info-box{
		height: 180upx;
		display:flex;
		align-items:center;
		position:relative;
		justify-content: center;
		z-index: 1;
		.portrait{
			width: 130upx;
			height: 130upx;
			border:5upx solid #fff;
			border-radius: 50%;
		}
		.username{
			font-size: $font-lg + 6upx;
			color: $font-color-dark;
			margin-left: 20upx;
		}
	}
</style>
