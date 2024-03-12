<template>
	<view class="container">
		<view class="c-list">
			<view class="c-row b-b">
				<view class="tit">名称</view>
				<view class="con">
					<uni-easyinput v-model="request.name" placeholder="请输入名称" />
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">描述</text>
				<view class="con">
					<uni-easyinput v-model="request.description" type="textarea" placeholder="请输入该需求的描述" />
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">要求</text>
				<view class="con">
					<uni-easyinput v-model="request.requirement" type="textarea" placeholder="请输入您的要求" />
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">奖励</text>
				<view class="con">
					<uni-easyinput v-model="request.reward" type="textarea" placeholder="请输入您提供的奖励" />
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit" @click="openCalendar">开始时间</text>
				<view class="con">
					<uni-calendar :insert="false":lunar="true" ref="startCalendar" v-model="request.startTime" :start-date="startDate" :end-date="endDate" @confirm="startDateChange"/>
					<text class="tit" @click="openStartCalendar">{{request.startTime}}</text>
				</view>
				<text class="tit" @click="openCalendar">结束时间</text>
				<view class="con">
					<uni-calendar :insert="false":lunar="true" ref="endCalendar" v-model="request.endTime" :start-date="startDate" :end-date="endDate" @confirm="endDateChange"/>
					<text class="tit" @click="openEndCalendar">{{request.endTime}}</text>
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">需求头像</text>
				<view class="con">
					<uni-file-picker limit="1" ref="tagFile" v-model="requestTag" file-extname="png,jpg,jpeg" :auto-upload="false"></uni-file-picker>
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">自动发布：</text>
				<view class="con">
					<switch :checked="request.publish" @change="switchChange"></switch>
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">附件(最多选择6张图片)</text>
				<view class="con">
					<uni-file-picker limit="6" ref="file" v-model="requestImgList" file-extname="png,jpg,jpeg" :auto-upload="false"></uni-file-picker>
				</view>
			</view>
		</view>
		<!-- 底部操作菜单 -->
		<view class="page-bottom">
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="updateRequest" :disabled="publishing">修改</button>
			</view>
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border add-cart-btn" @click="goBack">取消</button>
			</view>
		</view>
	</view>
</template>

<script>
	import config from '@/config'
	import {getRequestInfo,uploadTag,uploadFile,updateRequestInfo} from "@/api/request/requestUpdateDetail.js"
	
	const baseUrl = config.baseUrl
	export default{
		data() {
			const startDate= this.getDate('true',0);
			const endDate= this.getDate('month',2);
			return {
				request: {
					id:"",
					name: "",
					description: "",
					requirement: "",
					reward: "",
					url: "",
					publish: false,
					tagUrl: "",
					startTime: startDate,
					endTime: endDate,
				},
				startDate: startDate,
				endDate: endDate,
				requestTag: [],
				requestTagTmp: [],
				requestImgList: [],
				requestImgListTmp: [],
				publishing: false
			};
		},
		async onLoad(options){
			await getRequestInfo(options.id).then(response => {
					this.request = response.data;
					
					let requestImgListTmp = this.request.url.split(',');
					
					for(let img of requestImgListTmp){
						let data = {url: img};
						this.requestImgList.push(data);
					}
					this.requestImgListTmp = requestImgListTmp;
					
					let tagData = {url: this.request.tagUrl};
					this.requestTag = tagData;
					this.requestTagTmp = this.request.tagUrl
					
			})
		},
		methods:{
			switchChange(e){
				if(e.detail.value){
					this.request.publish = true;
				}else{
					this.request.publish = false;
				}
			},
			openStartCalendar(){
				this.$refs.startCalendar.open();
			},
			openEndCalendar(){
				this.$refs.endCalendar.open();
			},
			startDateChange(e){
				this.request.startTime = e.fulldate;
			},		
			endDateChange(e){
				this.request.endTime = e.fulldate;
			},
			async updateRequest(){
				this.publishing = true;
				let tagImgs = this.$refs.tagFile.getFiles();
				if(!this.requestTagTmp.includes(tagImgs[0].url)){
					let tag = {name: "file", filePath: tagImgs[0].url,extname:tagImgs[0].extname};
					await uploadTag(tag).then(response => {
						this.request.tagUrl = response.imgUrl
					});
				}
				let imgs = this.$refs.file.getFiles();
				let requestFiles = await this.uploadFiles(imgs);
				
				this.request.url = requestFiles.join();
				updateRequestInfo(this.request).then(response => {
					this.$modal.msgSuccess("创建需求成功")
					this.publishing = false;
					uni.redirectTo({
						url: '/pages/business/requestList'
					})
				}).catch(() => {
					this.publishing = false;
				});
				this.publishing = false;
			},
			goBack(){
				uni.redirectTo({
					url: '/pages/request/requestList'
				})
			},
			handelTagSelect(res){
			},
			handelSelect(res){
			},
			async uploadFiles(imgs){
				let tempFileList = [];
				for(let img of imgs){
					if(this.requestImgListTmp.includes(img.url)){
						tempFileList.push(img.url);
					}else{
						let data = {name: "file", filePath: img.url};
						await uploadFile(data).then(response => {
							tempFileList.push(response.imgUrl)
						});
					}
				}
				return tempFileList;
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
				
				console.log("日期返回",`${year}-${month}-${day}`);
				return `${year}-${month}-${day}`;
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
</style>
