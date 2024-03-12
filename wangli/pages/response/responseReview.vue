<template>
	<view class="container">
		<view class="top-title">
			<view class="top-title-container">
				<view class="top-tag">
					<view >
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
							<text class="top-title-tit">状态：</text>
							<text class="con primary" v-if="response.status == '1' ">待审核</text>
							<text class="con success" v-if="response.status == '2' ">需求发起方审核成功</text>
							<text class="con warning" v-if="response.status == '3' ">需求发起方审核失败</text>
							<text class="con success" v-if="response.status == '4' ">平台审核成功</text>
							<text class="con warning" v-if="response.status == '5' ">平台审核失败</text>
						</view>
						<view class="top-title-row b-b">
							<text class="top-title-tit">回复时间：</text>
							<text class="top-title-text">{{response.createTime}}</text>
						</view>
						<view class="top-title-row b-b">
							<text class="top-title-tit">审核时间：</text>
							<text class="top-title-text">{{response.updateTime}}</text>
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="c-list">
			<view class="introduce-section">
				<text>详情</text>
			</view>
			<view class="c-row b-b">
				<text class="tit">描述：</text>
				<view class="con">
					<text class="tit">{{response.responseDesc}}</text>
				</view>
			</view>
			<view class="introduce-section">
				<text>其他</text>
			</view>
			<uni-section title="">
				<view class="content">
					<view class="c-list" v-if="this.formData.length > 0">
						<view v-for="(item,index) in this.formData">
							<view v-if="item.type === '1'">
								<view class="c-row introduce-section" v-if="item.tag != ''">
									<text class="secondTitle">{{item.tag}}</text>
								</view>
								<view class="c-row" v-if="item.content != ''">
									<text class="con" type="textarea">{{item.content}}</text>
								</view>
							</view>
							<view v-if="item.type === '2'">
								<view class="c-row introduce-section" v-if="item.tag != ''">
									<text class="secondTitle">{{item.tag}}</text>
								</view>
								<view class="c-row" v-if="item.file.length > 0">
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
				<button type="primary" class="action-btn no-border buy-now-btn" @click="goBack">返回</button>
			</view>
			<view class="action-btn-group" v-if="response.status == 1">
				<button type="primary" class="action-btn no-border buy-now-btn" @click="reviewSuccess">通过</button>
			</view>
			<view class="action-btn-group" v-if="response.status == 1">
				<button type="primary" class="action-btn no-border buy-now-btn" @click="reviewFail">不通过</button>
			</view>
		</view>
	</view>
</template>

<script>
	import config from '@/config'
	import {getResponseInfo,reviewSuccess,reviewFail} from "@/api/response/responseReview.js"
	
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
					createTime:"",
					updateTime:""
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
			reviewFail(){
				let data = {'id': this.response.id};
				reviewSuccess(data).then(response => {
					this.$modal.msgSuccess("审核完成");
					setTimeout(function() {
						uni.navigateBack();
					}, 1000);
				})
			},
			reviewSuccess(){
				let data = {'id': this.response.id}
				reviewFail(data).then(response => {
					this.$modal.msgSuccess("审核完成");
					setTimeout(function() {
						uni.navigateBack();
					}, 1000);
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
			margin-left: 30%;
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
			height: 180upx;
			/* margin: 30upx; */
			margin-left: 30upx;
			margin-top: 30upx;
			margin-bottom: 30upx;
			font-size: $font-sm+2upx;
			line-height: 1.8;
			background: $scroll-item-background;
			.image{
				width: 180upx;
				height: 180upx;
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
</style>
