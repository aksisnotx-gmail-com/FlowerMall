<template>
	<view class="container">
		<view class="top-title">
			<view class="top-title-container" :style="{backgroundColor:process.backgroundColor,background:'url('+process.background+')'}">
				<view class="top-tag">
					<view >
						<image class="image" :src="process.url" mode="scaleToFill"></image>
					</view>
				</view>
				
				<view class="top-title-info">
					<view class="top-title-list" :style="{backgroundColor:process.backgroundColor}">
						<view class="top-title-row b-b">
							<text class="top-title-tit" :style="{color:process.tagColor}">名称：</text>
							<text class="top-title-text" :style="{color:process.contentColor}">{{process.name}}</text>
						</view>
						<view class="top-title-row b-b">
							<text class="top-title-tit" :style="{color:process.tagColor}">区域：</text>
							<text class="top-title-text" :style="{color:process.contentColor}">{{process.areaText}}</text>
						</view>
						<view class="top-title-row b-b">
							<text class="top-title-tit" :style="{color:process.tagColor}">类型：</text>
							<text class="top-title-text" :style="{color:process.contentColor}">{{process.typeText}}</text>
						</view>
						<view class="top-title-row b-b">
							<text class="top-title-tit" :style="{color:process.tagColor}">创建者：</text>
							<text class="top-title-text" :style="{color:process.contentColor}">{{process.createUserName}}</text>
						</view>
						<view class="scoll-item-title-row b-b">
							<text class="scoll-item-title-tit" :style="{color:process.tagColor}">创建时间：</text>
							<text class="top-title-text" :style="{color:process.contentColor}">{{formatDate(process.createTime)}}</text>
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="c-list" :style="{backgroundColor:process.backgroundColor,background:'url('+process.background+')'}">
			<view class="c-row b-b">
				<text class="tit" :style="{color:process.tagColor}">描述</text>
				<view class="con" :style="{backgroundColor:process.backgroundColor}">
					<text type="textarea" :style="{color:process.contentColor}">{{process.description}}</text>
				</view>
			</view>
		</view>
		<view class="introduce-section">
			<text>详情</text>
		</view>
		<view class="scroll-menu-view">
			<scroll-view scroll-x="true">
				<view class="scroll-menu-topic-view" v-for="item in tabs" :key="(item.id)" @click="swichMenu(item.id)">
					<view>
						<text :class="type==item.id ? 'scroll-menu-topic-text' : 'scroll-menu-topic-text1'">{{item.name}}</text>
					</view>
				</view>
			</scroll-view>
		</view>
		<view class="c-list" v-if="formData.length > 0 && formData[type].item.length > 0" :style="{backgroundColor:process.backgroundColor,background:'url('+process.background+')'}">
			<view v-for="(item,index) in formData[type].item">
				<view v-if="item.type === '1'">
					<view class="c-row" v-if="item.tag != ''">
						<text class="tit" :style="{color:process.tagColor}">{{item.tag}}</text>
						<view class="con" :style="{backgroundColor:process.backgroundColor}">
							<text type="textarea" :style="{color:process.contentColor}">{{item.content}}</text>
						</view>
					</view>
					<view class="c-row" v-if="item.tag == ''">
						<text class="tit1" :style="{color:process.tagColor}"></text>
						<view class="con" :style="{backgroundColor:process.backgroundColor}">
							<text type="textarea" :style="{color:process.contentColor}">{{item.content}}</text>
						</view>
					</view>
				</view>
				<view v-if="item.type === '2'">
					<view class="c-row" v-if="item.tag != ''">
						<text class="tit" :style="{color:process.tagColor}">{{item.tag}}</text>
						<view class="con">
							<swiper class="carousel" circular indicator-dots="true">
								<swiper-item v-for="(imgItem, imgIndex) in item.file" :key="imgIndex" class="carousel-item">
									<image class="swiper-img" :src="imgItem.url" />
								</swiper-item>
							</swiper>
						</view>
					</view>
					<view class="c-row" v-if="item.tag == ''">
						<text class="tit1" :style="{color:process.tagColor}"></text>
						<view class="con">
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
		<view class="introduce-section">
			<text>任务列表</text>
		</view>
		
		<view class="c-list" v-if="formData.length > 0 && formData[type].requestList != undefined && formData[type].requestList.length != 0" >
			<view class="c-row" :style="{backgroundColor:process.backgroundColor,background:'url('+process.background+')'}">
				<view v-for="(item,index) in formData[type].requestList" :key="index">
					<view class="con" style="width: 250upx;height: 150upx;">
						<image :src="item.tagUrl" mode="aspectFill" style="width: 95%;height: 150upx;" @click="viewRequest(item.id)"></image>
					</view>
					<view class="con" style="width: 250upx;height: 80upx;">
						<text>{{item.name}}</text>
					</view>
				</view>
			</view>
		</view>
		
		<view class="page-bottom">
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="goBack">返回</button>
			</view>
		</view>
	</view>
</template>

<script>
	import {getProcessDetail} from "@/api/public/process/processView.js"
	export default{
		data() {
			return {
				process: {
					name: "",
					description: "",
					province: "",
					city: "",
					county: "",
					url: "",
					areaText: "请选择",
					typeText: "请选择",
					backgroundColor: "#ffffff",
					tagColor:"",
					contentColor:"",
					background: ""
				},
				tabs: [],
				formData: [],
				imageStyles: {
					width: 30,
					height:30,
					border: {
						"color":"#ff155f",
						"width":"0px",
						"style":"solid",
						radius: '50%'
					}
				},
				backgroundColor: "#ffffff",
				tagColor:"",
				contentColor:"",
				background: "",
				active: 0,
				type: 0
			};
		},
		onLoad(e){
			getProcessDetail(e.id).then(response => {
				this.process = response.data.process;
				this.formData = response.data.formData;
				
				let formDataIndex = 0;
				for(let detail of this.formData){
					let data = {id:formDataIndex,name:detail.title};
					this.tabs.push(data);
					formDataIndex = formDataIndex + 1;
					for(let item of detail.item){
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
			formatDate(dateString) {
				const date = new Date(dateString);
				const year = date.getFullYear();
				const month = String(date.getMonth() + 1).padStart(2, '0');
				const day = String(date.getDate()).padStart(2, '0');
				return `${year}-${month}-${day}`;
			},
			swichMenu(id) {
				this.type = id;
			},
			viewRequest(id){
				uni.navigateTo({
					url: `/pages/public/share/request/requestInfoDetail?id=${id}`
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
	.introduce-section{
		background: $uni-bg-color-main;
		/* padding: 20upx 30upx; */
		display: flex;
		justify-content: center;
		flex-wrap: wrap;
		height: 40px;
		align-content: center;
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
			line-height: 1.5;
			
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
	.scroll-menu-view {
		display: flex;
		white-space: nowrap;
		background-color: $uni-bg-color-main;
		line-height: 50rpx;
		margin-top: 5px;
	
		.scroll-menu-topic-view {
			display: inline-block;
			white-space: nowrap;
			height: 100%;
			position: relative;
	
			.scroll-menu-topic-text {
				font-size: 30rpx;
				color: $scroll-menu-topic-text;
				padding: 10rpx 40rpx;
			}
			.scroll-menu-topic-text1 {
				font-size: 30rpx;
				color: $scroll-menu-topic-text1;
				padding: 10rpx 40rpx;
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
