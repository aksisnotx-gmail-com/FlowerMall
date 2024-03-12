<template>
  <view class="container">
	  <view class="detail-desc">
	  	<view class="d-header">
	  		<text>需求</text>
	  	</view>
	  </view>
	<view class="cate-section">
	  	<view class="cate-item" @click="navTo('/pages/request/addRequest')">
			<image src="/static/temp/packgood.png"></image>
			<text>创建需求</text>
		</view>
		<view class="cate-item" @click="navTo('/pages/request/requestList')">
			<image src="/static/temp/packagegoods.webp"></image>
			<text>我发布的需求</text>
		</view>
		<view class="cate-item" @click="navTo('/pages/response/responseList')">
			<image src="/static/temp/opengood.png"></image>
			<text>我回复的需求</text>
		</view>
	</view>
	<view class="detail-desc">
		<view class="d-header">
			<text>商品</text>
		</view>
	</view>
	<view class="cate-section">
		<view class="cate-item" @click="navTo('/pages/product/addProduct')">
			<image src="/static/temp/opengood.png"></image>
			<text>商品上新</text>
		</view>
		<view class="cate-item" @click="navTo('/pages/product/productList')">
			<image src="/static/temp/opengood.png"></image>
			<text>我的商品</text>
		</view>
	</view>
	<view class="detail-desc">
		<view class="d-header">
			<text>流程</text>
		</view>
	</view>
	<view class="cate-section">
		<view class="cate-item" @click="navTo('/pages/process/processExplan')">
			<image src="/static/temp/opengood.png"></image>
			<text>流程创建</text>
		</view>
		<view class="cate-item" @click="navTo('/pages/process/processList?type=1')">
			<image src="/static/temp/opengood.png"></image>
			<text>流程列表</text>
		</view>
	</view>
	<view class="detail-desc">
		<view class="d-header">
			<text>商户</text>
		</view>
	</view>
	<view class="cate-section">
		<view class="cate-item" @click="navTo('/pages/merchant/addMerchant')">
			<image src="/static/temp/opengood.png"></image>
			<text>商户创建</text>
		</view>
		<view class="cate-item" @click="navTo('/pages/merchant/merchantList')">
			<image src="/static/temp/opengood.png"></image>
			<text>商户列表</text>
		</view>
		<view class="cate-item" @click="weixinLogin">
			<image src="/static/temp/opengood.png"></image>
			<text>登录</text>
		</view>
	</view>
	<view class="detail-desc">
		<view class="d-header">
			<text>审核</text>
		</view>
	</view>
	<view class="cate-section">
		<view class="cate-item" @click="navTo('/pages/review/requestList')">
			<image src="/static/temp/opengood.png"></image>
			<text>需求审核</text>
		</view>
		<view class="cate-item" @click="navTo('/pages/review/productList')">
			<image src="/static/temp/opengood.png"></image>
			<text>商品列表</text>
		</view>
		<view class="cate-item" @click="navTo('/pages/review/processList')">
			<image src="/static/temp/opengood.png"></image>
			<text>流程列表</text>
		</view>
	</view>
	<view class="cate-section">
		<view class="cate-item" @click="navTo('/pages/review/responseList')">
			<image src="/static/temp/opengood.png"></image>
			<text>回复列表</text>
		</view>
		<view class="cate-item" @click="navTo('/pages/review/merchantList')">
			<image src="/static/temp/opengood.png"></image>
			<text>商户列表</text>
		</view>
	</view>
  </view>
</template>

<script>
  export default {
    methods: {
	  navTo(url){
	  	uni.navigateTo({  
	  		url
	  	})  
	  },
	  weixinLogin(){
		  
	  }
    }
  }
</script>

<style lang="scss">
  page {
    display: flex;
    flex-direction: column;
    box-sizing: border-box;
    background-color: #fff;
    min-height: 100%;
    height: auto;
  }

  .cate-section {
  	display: flex;
  	justify-content: space-around;
  	align-items: center;
  	flex-wrap:wrap;
  	padding: 30upx 22upx; 
  	background: #fff;
  	.cate-item {
  		display: flex;
  		flex-direction: column;
  		align-items: center;
  		font-size: $font-sm + 2upx;
  		color: $font-color-dark;
  	}
  	image {
  		width: 88upx;
  		height: 88upx;
  		margin-bottom: 14upx;
  		border-radius: 50%;
  		opacity: .7;
  		box-shadow: 4upx 4upx 20upx rgba(250, 67, 106, 0.3);
  	}
  }
  .detail-desc{
  	background: #fff;
  	margin-top: 16upx;
  	margin-left: 0%;
  	margin-right: 0%;
  	.d-header{
  		display: flex;
  		justify-content: center;
  		align-items: center;
  		height: 80upx;
  		font-size: $font-base + 2upx;
  		color: $font-color-dark;
  		position: relative;
  			
  		text{
  			padding: 0 20upx;
  			background: #fff;
  			position: relative;
  			z-index: 1;
  		}
  		&:after{
  			position: absolute;
  			left: 50%;
  			top: 50%;
  			transform: translateX(-50%);
  			width: 100%;
  			height: 0;
  			content: '';
  			border-bottom: 1px solid #ccc; 
  		}
  	}
  }
</style>
