<template>
	<view class="container">
		<view class="top-title">
			<view class="top-title-container" :style="{backgroundColor:product.backgroundColor,background:'url('+product.background+')'}">
				<view class="top-tag">
					<view >
						<image class="image" :src="product.tagUrl" mode="scaleToFill"></image>
					</view>
				</view>
				
				<view class="top-title-info">
					<view class="top-title-list" :style="{backgroundColor:product.backgroundColor}">
						<view class="top-title-row b-b">
							<text class="top-title-tit" :style="{color:product.tagColor}">名称：</text>
							<text class="top-title-text" :style="{color:product.contentColor}">{{product.name}}</text>
						</view>
						<view class="top-title-row b-b">
							<text class="top-title-tit" :style="{color:product.tagColor}">区域：</text>
							<text class="top-title-text" :style="{color:product.contentColor}">{{product.areaTxt}}</text>
						</view>
						<view class="top-title-row b-b">
							<text class="top-title-tit" :style="{color:product.tagColor}">类型：</text>
							<text class="top-title-text" :style="{color:product.contentColor}">{{product.typeTxt}}</text>
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="c-list" :style="{backgroundColor:product.backgroundColor,background:'url('+product.background+')'}">
			<view class="c-row b-b">
				<text class="tit" :style="{color:product.tagColor}">描述</text>
				<view class="con" :style="{backgroundColor:product.backgroundColor}">
					<text type="textarea" :style="{color:product.contentColor}">{{product.description}}</text>
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit" :style="{color:product.tagColor}">讲价：</text>
				<view class="con">
					<text  class="tit desc-text":style="{color:product.contentColor}">{{bargainText}}</text>
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit" :style="{color:product.tagColor}">自动上架：</text>
				<view class="con">
					<text  class="tit desc-text":style="{color:product.contentColor}">{{publishText}}</text>
				</view>
			</view>
			<view class="introduce-section">
				<text>其他</text>
			</view>
			<view class="content">
				<view class="c-list" v-if="formData.length > 0" :style="{backgroundColor:product.backgroundColor,background:'url('+product.background+')'}">
					<view v-for="(item,index) in formData">
						<view v-if="item.type === '1'">
							<view class="c-row" v-if="item.tag != ''">
								<text class="tit" :style="{color:product.tagColor}">{{item.tag}}</text>
								<view class="con" :style="{backgroundColor:product.backgroundColor}">
									<text type="textarea" :style="{color:product.contentColor}">{{item.content}}</text>
								</view>
							</view>
							<view class="c-row" v-if="item.tag == ''">
								<text class="tit1" :style="{color:product.tagColor}">{{item.tag}}</text>
								<view class="con" :style="{backgroundColor:product.backgroundColor}">
									<text type="textarea" :style="{color:product.contentColor}">{{item.content}}</text>
								</view>
							</view>
						</view>
						<view v-if="item.type === '2'">
							<view class="c-row" v-if="item.tag != ''">
								<text class="tit" :style="{color:product.tagColor}">{{item.tag}}</text>
								<view class="con" v-if="item.file.length > 0">
									<swiper class="carousel" circular indicator-dots="true">
										<swiper-item v-for="(imgItem, imgIndex) in item.file" :key="imgIndex" class="carousel-item">
											<image class="swiper-img" :src="imgItem.url" />
										</swiper-item>
									</swiper>
								</view>
							</view>
							<view class="c-row" v-if="item.tag == ''">
								<text class="tit1" :style="{color:product.tagColor}"></text>
								<view class="con" v-if="item.file.length > 0">
									<swiper class="carousel" circular indicator-dots="true">
										<swiper-item v-for="(imgItem, imgIndex) in item.file" :key="imgIndex" class="carousel-item">
											<image class="swiper-img" :src="imgItem.url" />
										</swiper-item>
									</swiper>
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>
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
	import config from '@/config'
	import {getProductInfo} from "@/api/product/productViewDetail.js"
	
	export default{
		data() {
			return {
				product: {
					id: "",
					name: "",
					description: "",
					unit: "",
					price: "",
					province: "",
					city: "",
					county: "",
					firstLevel: "",
					secondLevel: "",
					bargainFlag: "",
					tagUrl: "",
					url: "",
					publish: "",
					areaTxt: '请选择',
					typeTxt: '请选择',
					backgroundColor: "#ffffff",
					tagColor:"",
					contentColor:"",
					background: ""
				},
				formData: [],
				bargainText: "",
				publishText: ""
			};
		},
		async onLoad(options){
			await getProductInfo(options.id).then(response => {
				console.log(response.data.product)
					this.product = response.data.product;
					let formData = response.data.formData;
					for(let detail of formData){
						if(detail.type == '2'){
							let urlArr = detail.url.split(',');
							let file = [];
							for(let url of urlArr){
								let fileData = {url: url};
								file.push(fileData);
							}
							detail.file = file;
						}
						this.formData.push(detail);
					}
					console.log(this.product)
					if(this.product.bargainFlag == '1'){
						this.bargainText = "不可讲价"
					}else{
						this.bargainText = "可讲价"
					}
					if(this.product.publish == '1'){
						this.publishText = "审核通过后手动发布"
					}else{
						this.publishText = "审核通过后自动发布"
					}
			})
		},
		methods:{
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
	}
	.c-list{
		font-size: $font-sm + 2upx;
		color: $font-color-base;
		background: #fff;
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
		.con{
			flex: 1;
			color: $font-color-dark;
			margin-right: 30upx;
			.selected-text{
				margin-right: 10upx;
			}
			.desc-text{
				color:$uni-color-primary;
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
	.detail-desc{
		background: #fff;
		margin-top: 16upx;
		margin-right: 50upx;
		margin-left: 50upx;
	}
	.introduce-section{
		background: $uni-bg-color-main;
		padding: 20upx 30upx;
		
		.title{
			font-size: 32upx;
			color: $font-color-dark;
			height: 50upx;
			line-height: 50upx;
			margin-left: 20%;
		}
		
		.secondTitle{
			font-size: 32upx;
			color: $font-color-dark;
			height: 50upx;
			line-height: 50upx;
			margin-left: 10%;
		}
	}
	.top-title{
		margin-top: 3px;
		margin-bottom: 3upx;
		background: $head-title-background;
		.top-title-tool{
			display:flex;
			margin-bottom: 3upx;
			align-items: center;
			justify-content: space-evenly;
			background: $tool-background;
		}
		.top-title-container{
			display:flex;
			margin-top: 3px;
			align-items: flex-start;
		}
		.top-tag{
			width: 180upx;
			height: 140upx;
			/* margin: 30upx; */
			margin-left: 30upx;
			margin-top: 30upx;
			margin-bottom: 30upx;
			font-size: $font-sm+2upx;
			line-height: 1.8;
			background: $scroll-item-background;
			.image{
				width: 180upx;
				height: 140upx;
				border-radius: 6upx;
			}
		}
		.top-title-info{
			width: 100%;
			margin-left: 30upx;
			margin-top: 30upx;
			margin-bottom: 30upx;
			line-height: 1.8;
			
			.top-title-list{
				font-size: $font-sm + 2upx;
				margin-right: 30upx;
				background: $card-info-background;
				.top-title-row{
					align-items:center;
					position:relative;
				}
				.top-title-tit{
					width: 100upx;
					margin-left: 10upx;
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
	.swiper-img{
		max-width: 100%;
		max-height: 100%;
		display:inline-block;
		position:relative;
	}
</style>
