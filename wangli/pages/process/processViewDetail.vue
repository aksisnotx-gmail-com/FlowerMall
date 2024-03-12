<template>
	<view class="container">
		<uni-section title="" >
			<uni-steps :options="this.detail.formData" :active="active" active-color="#a6e7f1"/>
		</uni-section>
		<view class="c-list">
			<view class="c-row b-b">
				<view class="tit">
					<uni-icons type="left" size="30" color="#a6e7f1" style="margin-left: 30%;" @click="preStep"></uni-icons>
				</view>
				<view class="con">
				</view>
				<view class="con">
				</view>
				<view class="tit">
					<uni-icons type="right" size="30" color="#a6e7f1" style="margin-left: 30%;" @click="nextStep"></uni-icons>
				</view>
			</view>
		</view>
		
		<uni-section title="">
			<view class="content" v-if="this.detail.formData.length > 0">
				<view class="c-list">
					<view v-for="(item,index) in this.detail.formData[this.active].item">
						<view v-if="item.type === '1'">
							<view class="c-row introduce-section" v-if="item.tag != ''">
								<text class="tit">{{item.tag}}</text>
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
				<view class="c-list" v-if="this.detail.formData[this.active].requestList != undefined && this.detail.formData[this.active].requestList.length != 0">
					<view class="introduce-section">
						<text class="con">需求列表</text>
					</view>
					<view class="c-row">
						<view v-for="(item,index) in this.detail.formData[this.active].requestList" :key="index">
							<view class="con" style="width: 250upx;height: 150upx;">
								<image :src="item.tagUrl" mode="aspectFill" style="width: 95%;height: 150upx;" @click="viewRequest(item.id)"></image>
							</view>
							<view class="con" style="width: 250upx;height: 80upx;">
								<text>{{item.name}}</text>
							</view>
						</view>
					</view>
				</view>
			</view>
		</uni-section>
		
		<view class="page-bottom">
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="goBack">返回</button>
			</view>
		</view>
	</view>
</template>

<script>
	import {getProcessDetail} from "@/api/process/processView.js"
	
	export default{
		data() {
			return {
				detail: {
					formData: []
				},
				active: 0
			};
		},
		onLoad(e){
			getProcessDetail(e.id).then(response => {
					this.detail = response.data;
					let formData = response.data.formData;
					console.log(formData)
					for(let detail of formData){
						let items = detail.item;
						for(let item of items){
							if(item.type == '2'){
								let urlArr = item.url.split(',');
								let file = [];
								for(let url of urlArr){
									let fileData = {url: url};
									file.push(fileData);
								}
								item.file = file;
							}
						}
					}
			});
		},
		methods:{
			viewRequest(id){
				uni.navigateTo({
					url: `/pages/request/requestViewDetail?id=${id}`
				})
			},
			nextStep() {
				if (this.active < this.detail.formData.length - 1) {
					this.active += 1
				} else {
					this.active = 0
				}	
			},
			preStep(){
				if (this.active > 0) {
					this.active -= 1
				} else {
					this.active = this.detail.formData.length - 1
				}
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
		.con{
			flex: 1;
			color: $font-color-dark;
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
	@mixin flex {
			/* #ifndef APP-NVUE */
			display: flex;
			/* #endif */
			flex-direction: row;
		}
	.popup-content {
			@include flex;
			align-items: center;
			justify-content: center;
			padding: 15px;
			height: 50px;
			background-color: #fff;
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
</style>
