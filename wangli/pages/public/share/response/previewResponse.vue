<template>
	<view class="container" style="display: grid;">
		<view class="top-title">
			<view class="top-title-container">
				<view class="top-tag">
					<view>
						<image class="image" :src="response.tagUrl" mode="scaleToFill"></image>
					</view>
				</view>
				
				<view class="top-title-info">
					<view class="top-title-list">
						<view class="top-title-row b-b">
							<text class="top-title-tit">名称：</text>
							<text class="top-title-text">{{response.name}}</text>
						</view>
						<view class="top-title-row b-b">
							<text class="top-title-tit">创建者：</text>
							<text class="top-title-text">{{user.nickName}}</text>
						</view>
						<view class="top-title-row b-b">
							<text class="top-title-tit">创建时间：</text>
							<text class="top-title-text">{{formatDate()}}</text>
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="c-list">
			<view class="introduce-section">
				<text class="con">回复内容</text>
			</view>
		</view>
		<view class="c-list">
			<view class="c-row b-b">
				<text class="tit">回复</text>
				<view class="con">
					<text class="tit">{{response.responseDesc}}</text>
				</view>
			</view>
			
			<view class="c-list">
				<view class="introduce-section">
					<text class="con">其他</text>
				</view>
			</view>
			<view class="content">
				<view class="c-list" v-if="formData.length > 0">
					<view v-for="(item,index) in formData">
						<view v-if="item.type === '1'">
							<view class="c-row b-b" v-if="item.tag != ''">
								<text class="tit">{{item.tag}}</text>
								<view class="con">
									<text type="textarea">{{item.content}}</text>
								</view>
							</view>
							<view class="c-row b-b" v-if="item.tag == ''">
								<text class="tit1"></text>
								<view class="con">
									<text type="textarea">{{item.content}}</text>
								</view>
							</view>
						</view>
						<view v-if="item.type === '2'">
							<view class="c-row b-b" v-if="item.tag != ''">
								<text class="tit">{{item.tag}}</text>
								<view class="con" v-if="item.file.length > 0" >
									<swiper class="carousel" circular indicator-dots="true">
										<swiper-item v-for="(subItem, subIndex) in item.file" :key="subIndex" class="carousel-item">
											<image  class="swiper-img" :src="subItem.url" @load="subItem.loaded = true"/>
										</swiper-item>
									</swiper>
								</view>
							</view>
							<view class="c-row b-b" v-if="item.tag == ''">
								<text class="tit1"></text>
								<view class="con" v-if="item.file.length > 0">
									<swiper class="carousel" circular indicator-dots="true">
										<swiper-item v-for="(subItem1, subIndex1) in item.file" :key="subIndex1" class="carousel-item">
											<image class="swiper-img" :src="subItem1.url" @load="subItem1.loaded = true"/>
										</swiper-item>
									</swiper>
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>
		<!-- 	<view class="c-list">
				<view class="introduce-section">
					<text class="con">回复商品</text>
				</view>
			</view>
			<view class="c-row">
				<view v-for="(productItem,productIndex) in product" :key="productIndex">
					<view class="con" style="width: 230upx;height: 150upx;">
						<image :src="productItem.tagUrl" mode="aspectFill" style="width: 95%;height: 150upx;" @click="viewProduct(productItem.id)"></image>
					</view>
					<view class="con" style="width: 230upx;height: 80upx;">
						<text>{{productItem.name}}</text>
					</view>
				</view>
			</view> -->
		</view>
		<!-- 底部操作菜单 -->
		<view class="page-bottom">
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border add-cart-btn" @click="goBack">返回</button>
			</view>
		</view>
	</view>
</template>

<script>
	export default{
		data() {
			return {
				response:{
					name: "",
					responseDesc: "",
					requestId: "",
					responseProductId: "",
					url: "",
					tagUrl: "",
				},
				user: undefined,
				formData: [],
				product: [],
				publishing: false,
				openToRequestFlat: false,
				openToPublicFlag: false,
				responseFIles: [],
			};
		},
		async onLoad(){
			let pages = getCurrentPages();
			let prePage = pages[pages.length - 2];
			this.response = prePage.$vm.response
			this.formData = prePage.$vm.formData;
			this.product = prePage.$vm.product
			
			if(prePage.$vm.$refs.tagFile.getFiles().length > 0){
				this.response.tagUrl = prePage.$vm.$refs.tagFile.getFiles()[0].url;
			}
			
			this.$store.dispatch('GetInfo').then(res => {
				this.user = res.user;
			})
		},
		methods:{
			formatDate() {
				const date = new Date();
				const year = date.getFullYear();
				const month = String(date.getMonth() + 1).padStart(2, '0');
				const day = String(date.getDate()).padStart(2, '0');
				return `${year}-${month}-${day}`;
			},
			goProcessList(id){
				uni.navigateTo({
					url: `/pages/process/processList?requestId=${id}`
				})
			},
			goRequestDetail(id){
				uni.navigateTo({
					url: `/pages/request/requestViewDetail?id=${id}`
				})
			},
			viewProduct(id){
				uni.navigateTo({
					url: `/pages/public/product/productViewDetail?id=${id}`
				})
			},
			goBack(){
				uni.navigateBack();
			}
		},
	}
</script>

<style lang='scss'>
	page{
		background: $page-color-base;
		padding-bottom: 160upx;
		display: grid;
	}
	.c-list{
		font-size: $font-sm + 2upx;
		color: $font-color-base;
		background: #fff;
		display:grid;
		align-items: center;
		.c-row{
			display:flex;
			align-items:center;
			padding: 20upx 30upx;
			position:relative;
		}
		.tit{
			width: 140upx;
		}
		.tit1{
			width: 70upx;
		}
		.name{
			justify-items: center;
		}
		.con{
			flex: 1;
			color: $font-color-dark;
			.selected-text{
				margin-right: 10upx;
			}
		}
		.bz-list{
			height: 40upx;
			font-size: $font-sm+2upx;
			color: $font-color-dark;
			text{
				display: inline-block;
				margin-right: 30upx;
			}
		}
		.con-list{
			flex: 1;
			display:flex;
			flex-direction: column;
			color: $font-color-dark;
			line-height: 40upx;
		}
		.red{
			color: $uni-color-primary;
		}
	}
	
	/* 底部操作菜单 */
	.page-bottom{
		position:fixed;
		left: 0upx;
		bottom:0upx;
		z-index: 95;
		display: flex;
		justify-content: center;
		align-items: center;
		width: 100%;
		height: 100upx;
		background: rgba(255,255,255,.9);
		box-shadow: 0 0 20upx 0 rgba(0,0,0,.5);
		border-radius: 16upx;
		
		.p-b-btn{
			display:flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
			font-size: $font-sm;
			color: $font-color-base;
			width: 96upx;
			height: 80upx;
			.yticon{
				font-size: 40upx;
				line-height: 48upx;
				color: $font-color-light;
			}
			&.active, &.active .yticon{
				color: $uni-color-primary;
			}
			.icon-fenxiang2{
				font-size: 42upx;
				transform: translateY(-2upx);
			}
			.icon-shoucang{
				font-size: 46upx;
			}
		}
		.action-btn-group{
			display: flex;
			height: 76upx;
			border-radius: 100px;
			overflow: hidden;
			box-shadow: 0 20upx 40upx -16upx #fa436a;
			box-shadow: 1px 2px 5px rgba(219, 63, 96, 0.4);
			background: linear-gradient(to right, #ffac30,#fa436a,#F56C6C);
			margin-left: 20upx;
			position:relative;
			&:after{
				content: '';
				position:absolute;
				top: 50%;
				right: 50%;
				transform: translateY(-50%);
				height: 28upx;
				width: 0;
				border-right: 1px solid rgba(255,255,255,.5);
			}
			.action-btn{
				display:flex;
				align-items: center;
				justify-content: center;
				width: 180upx;
				height: 100%;
				font-size: $font-base ;
				padding: 0;
				border-radius: 0;
				background: transparent;
				background:$buttom-background;
				color: $buttom-background-color;
			}
		}
	}
	.a-t{
		display: flex;
		image{
			width: 170upx;
			height: 170upx;
			flex-shrink: 0;
			margin-top: -40upx;
			border-radius: 8upx;;
		}
		.right{
			display: flex;
			flex-direction: column;
			padding-left: 24upx;
			font-size: $font-sm + 2upx;
			color: $font-color-base;
			line-height: 42upx;
			.price{
				font-size: $font-lg;
				color: $uni-color-primary;
				margin-bottom: 10upx;
			}
			.selected-text{
				margin-right: 10upx;
			}
		}
	}
	.detail-desc{
		background: #fff;
		margin-top: 16upx;
		margin-left: 10%;
		margin-right: 10%;
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
				width: 300upx;
				height: 0;
				content: '';
				border-bottom: 1px solid #ccc; 
			}
		}
	}
	.introduce-section{
		background: #a6e7f1;
		padding: 20upx 30upx;
		
		.title{
			font-size: 32upx;
			color: $font-color-dark;
			height: 50upx;
			line-height: 50upx;
			margin-left: 30%;
		}
	}
	.top-title{
		margin-top: 5px;
		background: $uni-bg-color-main;
		.top-title-container{
			display:flex;
			padding: 20upx 30upx;
			align-items: flex-start;
			background: $scroll-background-color;
		}
		.top-tag{
			width: 180upx;
			margin: 10upx;
			font-size: $font-sm+2upx;
			line-height: 1;
			background: $scroll-item-background;
			.image{
				width: 180upx;
				height: 180upx;
				border-radius: 6upx;
			}
		}
		.top-title-info{
			width: 100%;
			margin: 10upx;
			line-height: 2.5;
			
			.top-title-list{
				font-size: $font-sm + 2upx;
				background: $card-info-background;
				.top-title-row{
					align-items:center;
					position:relative;
				}
				.top-title-tit{
					width: 100upx;
					margin-right: 30upx;
				}
				.top-title-con{
					flex: 1;
					color: $font-color-dark;
					margin-right: 30upx;
				}
			}
		}
	}
	.red-color {
	  color: #ff0000; /* 红色 */
	}
	
	.white-color {
	  color: #ffffff; /* 白色 */
	}
</style>
