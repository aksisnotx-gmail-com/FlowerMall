<template>
	<view class="container">
		<view class="container">
			<view class="top-title">
				<view class="top-title-container" :style="{backgroundColor:request.backgroundColor,background:'url('+request.background+')'}">
					<view class="top-tag">
						<view >
							<image class="image" :src="request.tagUrl" mode="scaleToFill"></image>
						</view>
					</view>
					
					<view class="top-title-info">
						<view class="top-title-list" :style="{backgroundColor:request.backgroundColor}">
							<view class="top-title-row b-b">
								<text class="top-title-tit" :style="{color:request.tagColor}">名称：</text>
								<text class="top-title-text" :style="{color:request.contentColor}">{{request.name}}</text>
							</view>
							<view class="top-title-row b-b">
								<text class="top-title-tit" :style="{color:request.tagColor}">区域：</text>
								<text class="top-title-text" :style="{color:request.contentColor}">{{request.areaTxt}}</text>
							</view>
							<view class="top-title-row b-b">
								<text class="top-title-tit" :style="{color:request.tagColor}">类型：</text>
								<text class="top-title-text" :style="{color:request.contentColor}">{{request.typeTxt}}</text>
							</view>
							<view class="top-title-row b-b">
								<text class="top-title-tit" :style="{color:request.tagColor}">时间：</text>
								<text class="top-title-text" :style="{color:request.contentColor}" v-if="request.startTime !='' && request.endTime != ''">{{request.startTime}}----{{request.endTime}}</text>
							</view>
						</view>
					</view>
				</view>
			</view>
			<!-- -->
			<!-- -->
			
			<view class="c-list" :style="{backgroundColor:request.backgroundColor,background:'url('+request.background+')'}">
				<view class="c-row b-b">
					<text class="tit" :style="{color:request.tagColor}">描述</text>
					<view class="con" :style="{backgroundColor:request.backgroundColor}">
						<text type="textarea" :style="{color:request.contentColor}">{{request.description}}</text>
					</view>
				</view>
				<view class="c-row b-b">
					<text class="tit" :style="{color:request.tagColor}">要求</text>
					<view class="con" :style="{backgroundColor:request.backgroundColor}">
						<text type="textarea" :style="{color:request.contentColor}">{{request.requirement}}</text>
					</view>
				</view>
				<view class="introduce-section">
					<text>其他</text>
				</view>
				<view class="content">
					<view class="c-list" v-if="formData.length > 0" :style="{backgroundColor:request.backgroundColor,background:'url('+request.background+')'}">
						<view v-for="(item,index) in formData">
							<view v-if="item.type === '1'">
								<view class="c-row b-b" v-if="item.tag != ''">
									<text class="tit" :style="{color:request.tagColor}">{{item.tag}}</text>
									<view class="con" :style="{backgroundColor:request.backgroundColor}">
										<text type="textarea" :style="{color:request.contentColor}">{{item.content}}</text>
									</view>
								</view>
								<view class="c-row b-b" v-if="item.tag == ''">
									<text class="tit1" :style="{color:request.tagColor}">{{item.tag}}</text>
									<view class="con" :style="{backgroundColor:request.backgroundColor}">
										<text type="textarea" :style="{color:request.contentColor}">{{item.content}}</text>
									</view>
								</view>
							</view>
							<view v-if="item.type === '2'">
								<view class="c-row" v-if="item.tag != ''">
									<text class="tit" :style="{color:request.tagColor}">{{item.tag}}</text>
									<view class="con" v-if="item.file.length > 0">
										<swiper class="carousel" circular indicator-dots="true">
											<swiper-item v-for="(imgItem, imgIndex) in item.file" :key="imgIndex" class="carousel-item">
												<image class="swiper-img" :src="imgItem.url" />
											</swiper-item>
										</swiper>
									</view>
								</view>
								<view class="c-row" v-if="item.tag == ''">
									<text class="tit1" :style="{color:request.tagColor}"></text>
									<view class="con" v-if="item.file.length > 0">
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
	import {getRequestInfo} from "@/api/request/requestViewDetail.js"
	
	export default{
		data() {
			return {
				request: {
					id:"",
					name: "",
					description: "",
					requirement: "",
					url: "",
					publish: false,
					tagUrl: "",
					province: "",
					city: "",
					county: "",
					firstLevel: "",
					secondLevel: "",
					startTime: "",
					endTime: "",
					areaTxt: '请选择',
					typeTxt: '请选择',
					backgroundColor: "#ffffff",
					tagColor:"",
					contentColor:"",
					background: "",
				},
				formData: [],
				responseData: []
			};
		},
		async onLoad(options){
			await getRequestInfo(options.id).then(response => {
					this.request = response.data.request;
					this.formData = response.data.formData;
					
					let formData = response.data.formData;
					console.log(this.request)
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
					}
			})
		},
		methods:{
			viewResponse(id){
				uni.navigateTo({
					url: `/pages/response/responseViewDetail?id=${id}&requestId=${this.request.id}`
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
	.textWarning{
		font-size: 32upx;
		color: $uni-text-color-warning;
		height: 50upx;
		line-height: 50upx;
		margin-left: 20%;
	}
	.seckill-section{
		padding: 4upx 30upx 24upx;
		background: #fff;
		.s-header{
			display:flex;
			align-items:center;
			height: 92upx;
			line-height: 1;
			.s-img{
				width: 140upx;
				height: 30upx;
			}
			.tip{
				font-size: $font-base;
				color: $font-color-light;
				margin: 0 20upx 0 40upx;
			}
			.timer{
				display:inline-block;
				width: 40upx;
				height: 36upx;
				text-align:center;
				line-height: 36upx;
				margin-right: 14upx;
				font-size: $font-sm+2upx;
				color: #fff;
				border-radius: 2px;
				background: rgba(0,0,0,.8);
			}
			.icon-you{
				font-size: $font-lg;
				color: $font-color-light;
				flex: 1;
				text-align: right;
			}
		}
		.floor-list{
			white-space: nowrap;
		}
		.scoll-wrapper{
			display:flex;
			align-items: flex-start;
		}
		.floor-item{
			width: 150upx;
			margin-right: 20upx;
			font-size: $font-sm+2upx;
			color: $font-color-dark;
			line-height: 1.5;
			image{
				width: 150upx;
				height: 150upx;
				border-radius: 6upx;
			}
			.price{
				color: $uni-color-primary;
			}
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
			line-height: 0.5;
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
			line-height: 1.8;
			
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
	.swiper-img{
		max-width: 100%;
		max-height: 100%;
		display:inline-block;
		position:relative;
	}
</style>
