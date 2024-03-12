<template>
	<view class="container">
		<view class="top-title">
			<view class="top-title-container" :style="{backgroundColor:request.backgroundColor,background:'url('+request.background+')'}">
				<view class="top-tag">
					<view>
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
							<text class="top-title-tit" :style="{color:request.tagColor}">创建者：</text>
							<text class="top-title-text" :style="{color:request.contentColor}">{{request.createUserName}}</text>
						</view>
						<view class="top-title-row b-b">
							<text class="top-title-tit" :style="{color:request.tagColor}">时间：</text>
							<text class="top-title-text" :style="{color:request.contentColor}" v-if="request.startTime !='' && request.endTime != ''">{{request.startTime}}----{{request.endTime}}</text>
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="scroll-menu-view">
			<scroll-view scroll-x="true">
				<view class="scroll-menu-topic-view" v-for="switchItem in tabs" :key="(switchItem.id)" @click="swichMenu(switchItem.id)">
					<view>
						<text :class="type==switchItem.id ? 'scroll-menu-topic-text' : 'scroll-menu-topic-text1'">{{switchItem.name}}</text>
					</view>
				</view>
			</scroll-view>
		</view>
		
		<view class="c-list" :style="{backgroundColor:request.backgroundColor,background:'url('+request.background+')'}" v-if="type == '0'">
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
			<view class="content" style="display: flex;align-items:center;justify-content:center;">
				<view class="c-list" v-if="formData != undefined && formData.length > 0">
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
							<view class="c-row b-b" v-if="item.tag != ''">
								<text class="tit" :style="{color:request.tagColor}">{{item.tag}}</text>
								<view class="con" v-if="item.file.length > 0" >
									<swiper class="carousel" circular indicator-dots="true">
										<swiper-item v-for="(subItem, subIndex) in item.file" :key="subIndex" class="carousel-item">
											<image  class="swiper-img" :src="subItem.url" @load="subItem.loaded = true"/>
										</swiper-item>
									</swiper>
								</view>
							</view>
							<view class="c-row b-b" v-if="item.tag == ''">
								<text class="tit1" :style="{color:request.tagColor}"></text>
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
		</view>
		
		<view v-if="type == '1'">
			<view v-if="responseData.length > 0">
				<view class="scoll-item-title" v-for="(responseItem, responseIndex) in responseData" :key="responseItem.id" :style="{backgroundColor:request.backgroundColor,background:'url('+request.background+')'}" v-if="type == '1'">
					<view class="scoll-item-container" @click="goResponseDetail(responseItem.id)">
						<view class="scoll-item-tag">
							<view>
								<image class="scoll-item-image" :src="responseItem.tagUrl" mode="scaleToFill"></image>
							</view>
						</view>
						<view class="scoll-item-title-info">
							<view class="scoll-item-title-list" :style="{backgroundColor:request.backgroundColor}">
								<view class="scoll-item-title-row b-b">
									<text class="scoll-item-title-tit" :style="{color:request.tagColor}">名称：</text>
									<text class="top-title-text" :style="{color:request.contentColor}">{{responseItem.name}}</text>
								</view>
								<view class="scoll-item-title-row b-b">
									<text class="top-title-tit" :style="{color:request.tagColor}">创建者：</text>
									<text class="top-title-text" :style="{color:request.contentColor}">{{responseItem.userName}}</text>
								</view>
								<view class="scoll-item-title-row b-b">
									<view class="flex-container">
										<view>
											<uni-icons type="hand-up"  color="#a6e7f1"></uni-icons>
											<text class="top-title-text" :style="{color:request.contentColor}">：{{responseItem.agreeCount}}</text>
										</view>
										<view>
											<uni-icons type="eye-filled"  color="#a6e7f1"></uni-icons>
											<text class="top-title-text" :style="{color:request.contentColor}">：{{responseItem.viewCount}}</text>
										</view>
									</view>
								</view>
							</view>
						</view>	
					</view>
				</view>
			</view>
			<view v-if="responseData.length == 0" :style="{backgroundColor:request.backgroundColor,background:'url('+request.background+')'}">
				<text :style="{color:request.tagColor}">暂无</text>
			</view>
		</view>
		
		<view v-if="type == '2'">
			<view v-if="message.length > 0">
				<view style="height: 100%; max-height: calc(100% - 100upx);">
					<scroll-view scroll-y class="scoll-wrapper" ref="scollWrapper" style="height: 100%; max-height: calc(100% - 100upx);">
						<view v-for="(msgItem,msgIndex) in message" :key="msgIndex">
							<ul class="message-list">
								<view :class="{'user-info-box': msgItem.type === '0', 'owner-info-box': msgItem.type === '1'}">
									<view class="portrait-box">
										<image class="portrait" :src="msgItem.tagUrl || '/static/logo/logo-108.png'"></image>
									</view>
									<view class="top-title-info" v-if="msgItem.type === '0'">
										<view class="top-title-list">
											<view class="top-title-row b-b">
												<text class="top-title-tit">{{msgItem.userName || '游客'}}</text>
											</view>
											<view class="top-title-row b-b">
												<text class="top-title-tit">{{msgItem.content}}</text>
											</view>
										</view>
									</view>
									<view class="top-title-info" v-if="msgItem.type === '1'">
										<view class="owner-top-title-list">
											<view class="owner-top-title-row b-b">
												<text class="owner-top-title-tit">{{msgItem.userName || '游客'}}</text>
											</view>
											<view class="owner-top-title-row b-b">
												<text class="owner-top-title-tit">{{msgItem.content}}</text>
											</view>
										</view>
									</view>
								</view>  
							</ul>
						</view>
					</scroll-view>
				</view>
			</view>
			<view v-if="message.length == 0" :style="{backgroundColor:request.backgroundColor,background:'url('+request.background+')'}">
				<text :style="{color:request.tagColor}">暂无</text>
			</view>
		</view>
		<view v-if="type == '3'">
			<view v-if="processData.length > 0">
				<view class="scoll-item-title" v-for="(processItem, processIndex) in processData" :key="processItem.id" :style="{backgroundColor:request.backgroundColor,background:'url('+request.background+')'}">
					<view class="scoll-item-container" @click="goProcessDetail(processItem.id)">
						<view class="scoll-item-tag">
							<view>
								<image class="scoll-item-image" :src="processItem.url" mode="scaleToFill"></image>
							</view>
						</view>
						<view class="process-scoll-item-title-info">
							<view class="scoll-item-title-list" :style="{backgroundColor:request.backgroundColor}">
								<view class="scoll-item-title-row b-b">
									<text class="scoll-item-title-tit" :style="{color:request.tagColor}">名称：</text>
									<text class="top-title-text" :style="{color:request.contentColor}">{{processItem.name}}</text>
								</view>
								<view class="scoll-item-title-row b-b">
									<text class="scoll-item-title-tit" :style="{color:request.tagColor}">区域：</text>
									<text class="top-title-text" :style="{color:request.contentColor}">{{processItem.areaText}}</text>
								</view>
								<view class="scoll-item-title-row b-b">
									<text class="scoll-item-title-tit" :style="{color:request.tagColor}">类型：</text>
									<text class="top-title-text" :style="{color:request.contentColor}">{{processItem.typeText}}</text>
								</view>
								<view class="scoll-item-title-row b-b">
									<text class="scoll-item-title-tit" :style="{color:request.tagColor}">创建者：</text>
									<text class="top-title-text" :style="{color:request.contentColor}">{{processItem.createUserName}}</text>
								</view>
								<view class="scoll-item-title-row b-b">
									<text class="scoll-item-title-tit" :style="{color:request.tagColor}">创建时间：</text>
									<text class="top-title-text" :style="{color:request.contentColor}">{{formatDate(processItem.createTime)}}</text>
								</view>
							</view>
						</view>	
					</view>
				</view>
			</view>
			<view v-if="processData.length == 0" :style="{backgroundColor:request.backgroundColor,background:'url('+request.background+')'}">
				<text :style="{color:request.tagColor}">暂无</text>
			</view>
		</view>
		
		<view class="page-bottom">
			<view class="action-btn-group" v-if="type != '2'">
				<button type="primary" class=" action-btn no-border" @click="goBack">更多</button>
			</view>
			<view class="action-btn-group" v-if="showResponse && type != '2'">
				<button type="primary" class=" action-btn no-border" @click="goResponse(request.id)">回复</button>
			</view>
			<view class="action-btn-group" v-if="type != '2'">
				<button type="primary" class=" action-btn no-border" @click="goLogin" v-if="!haslogin">点我分享</button>
				<button id="share-btn" class=" action-btn no-border" open-type="share" v-if="haslogin">点我分享</button>
			</view>
			<view class="mp-search-box" v-if="type == '2'">
				<input type="text" class="ser-input" v-model="sendMessage"/>
				<button class="ser-button" type="primary" size="mini" @click="sendMsg">发送</button>
			</view>
		</view>	
	</view>
</template>

<script>
	import config from '@/config'
	const uniUrl = config.uniUrl
	const requestShareMiniPaht = config.requestShareMiniPath
	const requestShareWebPath = config.requestShareWebPath
	const miniProgramId = config.miniProgramId
	
	import {getResponseInfo} from "@/api/public/request/requestInfoDetail.js"
	import {getMessage,sendMessage} from "@/api/public/im/im.js"
	import {showConfirm} from '@/utils/common'
	import {getProcessByRequestId} from "@/api/public/process/processList.js"
	
	import {sign,getUserInfo,getRequestBySign,shareSign,hasResponse} from "@/api/sign/sign.js"
	export default{
		onShow() {
			if(this.channel == '1'){
				this.validateStatus();
			}
		},
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
				type: 0,
				tabs: [{
						id: 0,
						name: '详情'
					},{
						id: 1,
						name: '回复'
					},
					{
						id: 2,
						name: '评论'
					},
					{
						id: 3,
						name: '所属流程'
					}
				],
				color: {r: 255,g: 0,b: 0,a: 0.6},
				haslogin: false,
				formData: [],
				showResponse: true,
				showShare: true,
				message:[],
				sendMessage:"",
				responseData: [],
				ticket: '',
				processData: [],
				channel: "0"
			};
		},
		onShareAppMessage(res) {
			return new Promise((resolve, reject) => {
				let path = requestShareMiniPaht + '?ticket=';
				shareSign(this.ticket).then(response => {
					path = path + response.data.ticket;
					const shareParams = {
						title: this.request.name,
						path: path,
						imageUrl: this.request.tagUrl,
						url: uniUrl + requestShareWebPath,
						desc: this.request.description,
						mpId: miniProgramId,
						type: 1,
						mpPath: path
					};
					resolve(shareParams);
					}).catch(error => {
						showConfirm('您的登录状态已过期，或者你还未登录。请先登录！').then(res => {
						if (res.confirm) {
							let url = "/pages/public/login";
							uni.navigateTo({ url: url})
						}
					})
				});
			})
		},
		onShareTimeline(res) {
			return new Promise((resolve, reject) => {
				let path = requestShareMiniPaht + '?ticket=';
				shareSign(this.ticket).then(response => {
					path = path + response.data.ticket;
					const shareParams = {
						title: this.request.name,
						path: path,
						imageUrl: this.request.tagUrl,
						url: uniUrl + requestShareWebPath,
						desc: this.request.description,
						mpId: miniProgramId,
						type: 1,
						mpPath: path
					};
					resolve(shareParams);
					}).catch(error => {
						showConfirm('您的登录状态已过期，或者你还未登录。请先登录！').then(res => {
						if (res.confirm) {
							let url = "/pages/public/login";
							uni.navigateTo({ url: url})
						}
					})
				});
			})
		},
		onLoad(options){
			this.ticket = options.ticket
			getRequestBySign(options.ticket).then(response => {
				this.request = response.data.request;
				this.formData = response.data.formData;
				
				let formData = response.data.formData;
				if(formData != null){
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
				}
				this.validateStatus();
			});
		},
		methods:{
			validateStatus(){
				let currentDate= this.getDate('true',0);
				let timeCorrect = false;
				if(this.request.startTime <= currentDate && this.request.endTime >= currentDate){
					timeCorrect = true;
				}
				
				if(timeCorrect){
					getUserInfo().then(response => {
						if(response.data != null){
							this.haslogin = true;
						}else{
							this.haslogin = false;
						}	
						
						if(this.haslogin){
							hasResponse(this.request.id).then(response => {
								if(response.data.data != "0"){
									this.showResponse = false;
								}else{
									this.showResponse = true;
								}	
							});
						}else{
							this.showResponse = true;
							this.showShare = true;
						}
					});
				}else{
					this.showResponse = false;
					this.showShare = false;
				}
			},
			goResponseDetail(id){
				uni.navigateTo({
					url: `/pages/public/share/response/responseViewDetail?id=${id}`
				}) 
			},
			formatDate(dateString) {
				const date = new Date(dateString);
				const year = date.getFullYear();
				const month = String(date.getMonth() + 1).padStart(2, '0');
				const day = String(date.getDate()).padStart(2, '0');
				return `${year}-${month}-${day}`;
			},
			goProcessDetail(id) {
				uni.navigateTo({
					url: `/pages/public/share/process/processView?id=${id}`
				})
			},
			sendMsg(){
				if(this.sendMessage.length == 0){
					this.$modal.msgError("说点你想说的吧。。");
					return;
				}
				let msgData = {'content':this.sendMessage,'objectType':'1','objectId':this.request.id};
				sendMessage(msgData).then(response => {
					this.message.push(response.data);
					});;
				this.sendMessage = '';
			},
			scrollToBottom() {
				const container = this.$refs.scollWrapper;
				container.scrollTop = container.scrollHeight; 
			},
			swichMenu(id) {
				this.type = id;
				if(this.type == '1'){
					console.log(this.type)
					this.responseData = [];
					getResponseInfo(this.request.id).then(response => {
						for(let item of response.data){
							this.responseData.push(item);
						}
					});
				}else if(this.type == '2'){
					getMessage(this.request.id).then(response => {
						this.message = [];
						for(let item of response.data){
							this.message.push(item);
						}
					});
				}else if(this.type == '3'){
					getProcessByRequestId(this.request.id).then(response => {
							this.processData = [];
							for(let item of response.data){
								this.processData.push(item);
							}
					});
				}
			},
			goLogin(){
				showConfirm('为了您的权益，回复此需求前，请先登录！').then(res => {
					if (res.confirm) {
						let that = this;
						uni.navigateTo({
							url: '/pages/public/login',
							success() {
								that.channel = "1";
							}
						})
					}
				})
			},
			goResponse(id){
				if(!this.haslogin){
					this.goLogin();
				}else{
					uni.navigateTo({
						url: `/pages/public/share/response/addResponse?id=${id}`
					})
				}
			},
			goBack(){
				uni.switchTab({
					url:'/pages/public/request/index'
				})
			},
			getDate(type,num) {
				const date = new Date();
				let year = date.getFullYear();
				let month = date.getMonth() + 1;
				let day = date.getDate();
			
				if(type === 'year'){
					year = year + num;
				}else if(type === 'month'){
					month = month + num;
				}else if(type === 'day'){
					day = day + num;
				}
				
				month = month > 9 ? month : '0' + month;
				day = day > 9 ? day : '0' + day;
				
				return `${year}-${month}-${day}`;
			}
		},
	}
</script>

<style lang='scss'>
	page{
	}
	.container{
		padding-bottom: 134upx;
		/* 空白页 */
		.empty{
			position:fixed;
			left: 0;
			top:0;
			width: 100%;
			height: 100%;
			padding-bottom:100upx;
			display:flex;
			justify-content: center;
			flex-direction: column;
			align-items:center;
			image{
				width: 240upx;
				height: 160upx;
				margin-bottom:30upx;
			}
			.empty-tips{
				display:flex;
				font-size: $font-sm+2upx;
				color: $font-color-disabled;
				.navigator{
					color: $uni-color-primary;
					margin-left: 16upx;
				}
			}
		}
	}
	.c-list{
		font-size: $font-sm + 2upx;
		color: $font-color-base;
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
		}
	}
	
	/* 底部操作菜单 */
	.page-bottom{
		position:fixed;
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
			line-height: 1.5;
			
			.top-title-list{
				font-size: $font-sm + 2upx;
				margin-right: 30upx;
				background: #fff;
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
	.scroll-menu-view {
		display:flex;
		white-space: nowrap;
		background-color: $uni-bg-color-main;
		line-height: 50rpx;
		margin-top: 5px;
		align-items: center;
		position: relative;
		top: -2.5px;
		padding: 6upx 30upx 8upx;
		background: $uni-bg-color-main;
	
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
	
	.scoll-item-title{
		margin-top: 3px;
		margin-bottom: 3upx;
		.scoll-item-container{
			display:flex;
			margin-top: 3px;
			align-items: flex-start;
		}
		.scoll-item-tag{
			margin-left: 30upx;
			margin-top: 30upx;
			margin-bottom: 30upx;
			font-size: $font-sm+2upx;
			line-height: 1.8;
			background: $scroll-item-background;
			.scoll-item-image{
				display: flex;
				width: 100px;
				height: 100px;
				border-radius: 6upx;
			}
		}
		.scoll-item-title-info{
			width: 100%;
			margin-left: 30upx;
			margin-top: 30upx;
			margin-bottom: 30upx;
			line-height: 2.3;
			
			.scoll-item-title-list{
				font-size: $font-sm + 2upx;
				margin-right: 30upx;
				background: $card-info-background;
				.scoll-item-title-row{
					align-items:center;
					position:relative;
				}
				.scoll-item-title-tit{
					width: 100upx;
					margin-left: 10upx;
					margin-right: 30upx;
				}
				.scoll-item-title-con{
					flex: 1;
					color: $font-color-dark;
					margin-right: 30upx;
				}
			}
		}
		.process-scoll-item-title-info{
			width: 100%;
			margin-left: 30upx;
			margin-top: 30upx;
			margin-bottom: 30upx;
			line-height: 1.45;
			
			.scoll-item-title-list{
				font-size: $font-sm + 2upx;
				margin-right: 30upx;
				background: $card-info-background;
				.scoll-item-title-row{
					align-items:center;
					position:relative;
				}
				.scoll-item-title-tit{
					width: 100upx;
					margin-left: 10upx;
					margin-right: 30upx;
				}
				.scoll-item-title-con{
					flex: 1;
					color: $font-color-dark;
					margin-right: 30upx;
				}
			}
		}
	}
	.cart-item{
		display:flex;
		position:relative;
		padding:10upx 10upx;
		margin-left: 10%;
		.image-wrapper{
			width: 130upx;
			height: 130upx;
			flex-shrink: 0;
			position:relative;
			image{
				border-radius:8upx;
			}
		}
		.item-right{
			display:flex;
			flex-direction: column;
			flex: 1;
			overflow: hidden;
			position:relative;
			padding-left: 30upx;
			.title,.price{
				font-size:$font-base + 2upx;
				color: $font-color-dark;
				height: 40upx;
				line-height: 40upx;
			}
		}
		.del-btn{
			padding:4upx 5upx;
			font-size:34upx; 
			height: 50upx;
			color: $uni-color-primary;
		}
		.c-list{
			font-size: $font-sm + 2upx;
			color: $font-color-base;
			background: #fff;
			.c-row{
				display:flex;
				align-items:center;
				padding: 10upx 10upx;
				position:relative;
				height: 50upx;
			}
			.tit{
				width: 200upx;
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
	}
	.flex-container {
	  display: flex;
	  justify-content: space-evenly;
	  align-items: center;
	  width: 100%;
	}
	 .chat-room {
	      max-width: 800px;
	      margin: 0 auto;
	      padding: 20px;
	    }
	
	    /* 消息列表容器 */
	    .message-list {
	      list-style-type: none;
	      margin: 0;
	      padding: 5px;
	    }
	
	    /* 单条消息样式 */
	    .message-item {
	      margin-bottom: 10px;
	      padding: 5px;
	      background-color: #f2f2f2;
	    }
	
	    /* 发送者姓名样式 */
	    .sender-name {
	      font-weight: bold;
	    }
	
	    /* 输入框和发送按钮容器 */
	    .input-container {
	      display: flex;
	      margin-top: 20px;
	    }
	
	    /* 输入框样式 */
	    .message-input {
	      flex-grow: 1;
	      padding: 5px;
		  background: #4CAF50;
	    }
	
	    /* 发送按钮样式 */
	    .send-button {
	      padding: 5px 10px;
	      background-color: #4CAF50;
	      color: white;
	      border: none;
	    }
	.owner-info-box{
		height: 80upx;
		display:flex;
		align-items:center;
		position:relative;
		z-index: 1;
		background: $chat-owner-info-background;
		.portrait{
			width: 50upx;
			height: 50upx;
			border:5upx solid #fff;
			border-radius: 50%;
		}
		.username{
			font-size: $font-lg + 6upx;
			color: $font-color-dark;
			margin-left: 20upx;
		}
	}
	.user-info-box{
		height: 80upx;
		display:flex;
		align-items:center;
		position:relative;
		z-index: 1;
		background: $chat-user-info-background;
		.portrait{
			width: 50upx;
			height: 50upx;
			border:5upx solid #fff;
			border-radius: 50%;
		}
		.username{
			font-size: $font-lg + 6upx;
			color: $font-color-dark;
			margin-left: 20upx;
		}
	}
	.portrait-box{
		padding: 3px;
	}
	.mp-search-box{
		position:fixed;
		bottom:0upx;
		display:flex;
		align-items: center;
		position: relative;
		padding: 6upx 30upx 8upx;
		bottom: 0px;
		
		.ser-input{
			flex:1;
			text-align: center;
			border-radius: 100px;
			background: rgba(255, 255, 255, 0.6);
			width: 80%; 
			margin-left: 10%;
			height: 30px;
			border: 1px solid #a6e7f1
		}
		.ser-button{
			border-radius: 100px;
			height: 30px;
			background:$buttom-background;
			color: $buttom-background-color;
		}
	}
</style>
