<template>
	<view class="container" style="display: grid;">
		<view class="detail-desc">
			<image
				:src="request.tagUrl" 
				mode="aspectFill"
			></image>
		</view>
		<view class="introduce-section">
			<text class="title">{{request.name}}</text>
		</view>
		<view class="c-list">
			<view class="c-row b-b">
				<text class="tit">描述</text>
				<view class="con">
					<text>{{request.description}}</text>
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">要求</text>
				<view class="con">
					<text>{{request.requirement}}</text>
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">奖励</text>
				<view class="con">
					<text>{{request.reward}}</text>
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">开始时间</text>
				<view class="con">
					<text class="tit">{{request.startTime}}</text>
				</view>
				<text class="tit">结束时间</text>
				<view class="con">
					<text class="tit">{{request.endTime}}</text>
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">回复</text>
				<view class="con">
					<uni-easyinput type="textarea" v-model="response.responseDesc" placeholder="请输入回复内容" />
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit" style="width: 300upx;">
					对发起方显示你的信息
				</text>
				<view class="con">
					<switch :checked="openToRequestFlat" @change="switchChangetoRequest"></switch>
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit" style="width: 300upx;">
					对大众公开你的回复
				</text>
				<view class="con">
					<switch :checked="openToPublicFlag" @change="switchChangetoPublic"></switch>
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">
					头像
				</text>
				<view class="con">
					<uni-file-picker mode="grid" v-model="responseTag" file-mediatype="image" return-type="array" limit="1" ref="tagFile"  file-extname="png,jpg,jpeg" :auto-upload="false"></uni-file-picker>
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">
					附件(最多选择6张图片)
				</text>
				<view class="con">
					<uni-file-picker mode="grid" v-model="responseFIles" file-mediatype="image" return-type="array" limit="6" ref="file"  file-extname="png,jpg,jpeg" :auto-upload="false"></uni-file-picker>
				</view>
			</view>
		</view>
		<!-- 底部操作菜单 -->
		<view class="page-bottom">
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="saveResponse" :disabled="publishing">确定</button>
			</view>
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border add-cart-btn" @click="goBack">返回</button>
			</view>
		</view>
	</view>
</template>

<script>
	import config from '@/config'
	import {getRequestInfo,getResponseInfo,uploadFile,saveResponseInfo} from "@/api/response/responseUpdateDetail.js"
	
	const baseUrl = config.baseUrl
	
	export default{
		data() {
			return {
				request: {
					id:"",
					name: "",
					description: "",
					requirement: "",
					reward: "",
					responseDesc: "",
					url: "",
					publish: false,
					tagUrl: "",
					startTime: "",
					endTime: "",
				},
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
				publishing: false,
				openToRequestFlat: false,
				openToPublicFlag: false,
				responseFIles: [],
				responseTag: [],
			};
		},
		async onLoad(options){
			await getRequestInfo(options.requestId).then(response => {
					this.request = response.data;
			})
			await getResponseInfo(options.id).then(response => {
					this.response = response.data;
					let responseImg = this.response.url.split(',');
					for(let img of responseImg){
						let data = {url:img};
						this.responseFIles.push(data);
					}
					this.responseTag.push( {url:this.response.tagUrl});
			})
		},
		methods:{
			switchChangetoRequest(e){
				if(e.detail.value){
					this.openToRequestFlat = true;
				}else{
					this.openToRequestFlat = false;
				}
			},
			switchChangetoPublic(e){
				if(e.detail.value){
					this.openToPublicFlag = true;
				}else{
					this.openToPublicFlag = false;
				}
			},
			async saveResponse(){
				this.publishing = true;
				
				let imgs = this.$refs.file.getFiles();
				
				for(let img of imgs){
					let data = {name: "file", filePath: img.url};
					await uploadFile(data).then(response => {
					this.responseFIles.push(response.imgUrl)
					});
				}
				let responseUrl = this.responseFIles.join();
				
				let data = {'requestId':this.detail.id,'responseDesc':this.detail.responseDesc,'url':responseUrl,'openToRequestFlat':this.openToRequestFlat,'openToPublicFlag':this.openToPublicFlag};
				console.log("-------------------",data);
				saveResponseInfo(data).then(response => {
					this.$modal.msgSuccess("回复成功")
					this.publishing = false;
					uni.redirectTo({
						url: '/pages/response/responseList'
					})
				}).catch(() => {
					this.publishing = false;
				});
				this.publishing = false;
			},
			goBack(){
				uni.redirectTo({
					url: '/pages/response/responseList'
				})
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
		left: 30upx;
		bottom:30upx;
		z-index: 95;
		display: flex;
		justify-content: center;
		align-items: center;
		width: 690upx;
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
		background: #fff;
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
