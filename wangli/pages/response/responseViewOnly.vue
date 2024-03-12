<template>
	<view class="container" style="display: grid;">
		<view class="c-list">
			<view class="detail-desc">
				<image
					:src="response.tagUrl" 
					mode="aspectFill"
				></image>
			</view>
			<view class="c-row b-b">
				<text class="tit">回复名称</text>
				<view class="con">
					<text>{{response.name}}</text>
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">回复</text>
				<view class="con">
					<text class="tit">{{response.responseDesc}}</text>
				</view>
			</view>
			<uni-section title="">
				<view class="content">
					<view class="c-list" v-if="this.formData.length > 0">
						<view v-for="(item,index) in this.formData">
							<view class="c-row b-b" v-if="item.type === '1'">
								<view class="tit">
									<text>{{item.tag}}</text>
								</view>
								<view class="con">
									<text>{{item.content}}</text>
								</view>
							</view>
							<view class="c-row b-b" v-if="item.type === '2'">
								<view class="tit">
									<text>{{item.tag}}</text>
								</view>
								<view class="con">
									<uni-file-picker :value="item.file" readonly></uni-file-picker>
								</view>
							</view>
						</view>
					</view>
				</view>
			</uni-section>
			<view class="c-list">
				<view class="introduce-section">
					<text class="con">回复商品</text>
				</view>
			</view>
			<view class="c-row">
				<view v-for="(item,index) in this.product" :key="index">
					<view class="con" style="width: 230upx;height: 150upx;">
						<image :src="item.tagUrl" mode="aspectFill" style="width: 95%;height: 150upx;" @click="viewProduct(item.id)"></image>
					</view>
					<view class="con" style="width: 230upx;height: 80upx;">
						<text>{{item.name}}</text>
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
	import {getRequestInfo,getResponseInfo,uploadFile,saveResponseInfo} from "@/api/response/responseViewDetail.js"
	
	const baseUrl = config.baseUrl
	
	export default{
		data() {
			return {
				response:{
					id: undefined,
					responseProductId: "",
					responseRequirement: "",
					responseDesc: "",
					url: "",
					tagUrl: "",
					agreeTimes: "",
					openToRequestFlat: "",
					openToPublicFlag: "",
					requestOpenFlag: "",
					agreeFlag: "",
					answer: "",
					status: "",
				},
				formData: [],
				product: [],
				publishing: false,
				openToRequestFlat: false,
				openToPublicFlag: false,
				responseFIles: [],
			};
		},
		async onLoad(options){
			await getResponseInfo(options.id).then(response => {
					this.response = response.data.response;
					this.product = response.data.product;
					
					let formData = response.data.item;
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
			})
		},
		methods:{
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
					url: `/pages/product/productViewDetailOnly?id=${id}`
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
</style>
