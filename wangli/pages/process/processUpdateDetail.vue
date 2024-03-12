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
					<uni-icons type="minus-filled" size="30" color="#a6e7f1" style="margin-left: 30%;" @click="delProcess"></uni-icons>
				</view>
				<view class="con">
					<uni-icons type="plus" size="30" color="#a6e7f1" style="margin-left: 30%;" @click="addProcess"></uni-icons>
				</view>
				<view class="tit">
					<uni-icons type="right" size="30" color="#a6e7f1" style="margin-left: 30%;" @click="nextStep"></uni-icons>
				</view>
			</view>
		</view>
		
		<view>
			<uni-popup ref="inputDialog" type="dialog">
				<uni-popup-dialog ref="inputClose"  value="" mode="input" title="新增流程" placeholder="请输入流程名" @confirm="dialogInputConfirm" />
			</uni-popup>
		</view>
		
		<uni-section title="">
			<view class="content" v-if="this.detail.formData.length > 0">
				<view class="c-list">
					<view v-for="(item,index) in this.detail.formData[this.active].item">
						<view class="c-row b-b" v-if="item.type === '1'">
							<view class="tit">
								<uni-easyinput style="width:80%" v-model="item.tag" placeholder="标签" :clearable="false"/>
							</view>
							<view class="con">
								<uni-easyinput v-model="item.content" type="textarea" placeholder="请输入内容" />
							</view>
						</view>
						<view class="c-row b-b" v-if="item.type === '2'">
							<view class="tit">
								<uni-easyinput style="width:80%" v-model="item.tag" placeholder="标签" :clearable="false"/>
							</view>
							<view class="con" @click="beforeSelectFile(index)">
								<uni-file-picker v-model="item.file" limit="6" ref="tagFile" file-extname="png,jpg,jpeg" :auto-upload="false" @select="selectFileSuccess" @delete="delFile"></uni-file-picker>
							</view>
						</view>
					</view>
				</view>
				<view class="c-list" v-if="this.detail.formData[this.active].requestList != undefined && this.detail.formData[this.active].requestList.length != 0">
					<view class="c-row b-b">
						<text class="con-list">任务列表</text>
					</view>
					
					<view class="c-row">
						<view v-for="(item,index) in this.detail.formData[this.active].requestList" :key="index">
							<view class="con" style="width: 250upx;height: 150upx;">
								<image :src="item.tagUrl" mode="aspectFill" style="width: 95%;height: 150upx;" @click="viewRequest(item.id)"></image>
							</view>
							<view class="con" style="width: 250upx;height: 80upx;">
								<text>{{item.name}}</text>
							</view>
							<view class="con" style="width: 100%;height: 40upx;">
								<uni-icons type="trash" size="30" color="#f12a07" @click="deleteRequest(index)"></uni-icons>
							</view>
						</view>
					</view>
				</view>
			</view>
			
			<view class="c-list">
				<view class="c-row b-b">
					<view class="con">
						<text>文本</text>
						<uni-icons type="compose" size="30" color="#a6e7f1" @click="addItem('1')"></uni-icons>
					</view>
					<view class="con">
						<text>附件</text>
						<uni-icons type="paperclip" size="30" color="#a6e7f1" @click="addItem('2')"></uni-icons>
					</view>
					<view class="con">
						<text>任务</text>
						<uni-icons type="gift-filled" size="30" color="#a6e7f1" @click="openRequestToggle"></uni-icons>
					</view>
				</view>
			</view>
		</uni-section>
		
		<view>
			<uni-popup ref="message" type="message">
				<uni-popup-message :type="msgType" :message="messageText" :duration="2000"></uni-popup-message>
			</uni-popup>
		</view>
		
		<view>
			<uni-popup ref="popup" background-color="#fff">
					<view class="c-list">
						<view class="c-row b-b">
							<view class="con">
								<text>关联已有任务</text>
								<uni-icons type="link" size="30" color="#a6e7f1" @click="linkRequest"></uni-icons>
							</view>
						</view>
					</view>
			</uni-popup>
		</view>

		
		<view class="page-bottom">
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="goBack">返回</button>
			</view>
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="addProcessPreview">预览</button>
			</view>
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="saveItem">保存</button>
			</view>
		</view>
	</view>
</template>

<script>
	import config from '@/config'
	import storage from '@/utils/storage'
	import constant from '@/utils/constant'
	import {getProcessDetail,uploadTag,uploadFile,saveProcessDetail} from "@/api/process/processUpdateDetail.js"
	
	const baseUrl = config.baseUrl
	export default{
		data() {
			return {
				detail: {
					formData: []
				},
				active: 0,
				messageText:"",
				msgType:"success",
				list: [{
							"title": '开始'
						}, {
							"title": '结束'
						}],
				items: [],
				styles: [{
						value: 'button',
						text: '按钮',
						checked: true
					}],
				colors: ['#007aff', '#4cd964', '#dd524d'],
				colorIndex: 0,
				activeColor: '#007aff',
				styleType: 'button',
				fileIndex: 0,
				
			};
		},
		onLoad(e){
			// this.detail.parentId = e.id;
			
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
			async saveItem(){
				let pages = getCurrentPages();
				let prePage = pages[pages.length - 2];
				let tagImgs = prePage.$vm.$refs.tagFile.getFiles();
				let tag = undefined;
				if(tagImgs.length == 1){
					tag = {name: "file", filePath: tagImgs[0].url,extname:tagImgs[0].extname};
					await uploadTag(tag).then(response => {
						prePage.$vm.process.url = response.imgUrl
					});
				}
				this.detail.process = prePage.$vm.process;
				console.log("-----------process--------------",prePage.$vm.process);
				console.log("-----------before--------------",this.detail)
				let formDataList = this.detail.formData;
				for(let formData of formDataList){
					let itemList = formData.item;
					let requestList = formData.requestList;
					
					for(let item of itemList){
						if(item.type == '2' ){
							let url = new Array();
							for(let file of item.file){
								let fileInfo = {name: "file", filePath: file.url,extname:file.extname};
								await uploadFile(fileInfo).then(response => {
									url.push(response.imgUrl);
								});
							}
							item.url = url.join();
						}
					}
					
					let requests = new Array(); 
					for(let requestItem of requestList){
						requests.push(requestItem.id);
					}
					formData.requestId = requests.join();
				}
				console.log("-------------after---------------",this.detail)
				saveProcessDetail(this.detail).then(response => {
					console.log(response.data);
					this.$modal.msgSuccess("流程创建成功")
					// uni.redirectTo({
					// 	url: '/pages/process/addProcessDetail?id='+response.data.id
					// })
				}).catch(() => {
				});
			},
			deleteRequest(index){
				this.detail.formData[this.active].requestList.splice(index,1);
			},
			viewRequest(id){
				uni.navigateTo({
					url: `/pages/request/requestViewDetail?id=${id}`
				})
			},
			linkRequest(){
				uni.navigateTo({
					url: `/pages/process/requestList`
				});
				this.$refs.popup.close();
			},
			openRequestToggle(){
				this.$refs.popup.open('bottom');
			},
			addProcessPreview(){
				uni.navigateTo({
					url: `/pages/process/addProcessPreview?detail=${JSON.stringify(this.detail)}&active=${this.active}`
				})
			},
			delFile(e){
				let files = this.detail.formData[this.active].item[this.fileIndex].file;
				let newFiles = [];
				for(let file of files){
					if(file.url != e.tempFile.url){
						newFiles.push(file);
					}
				}
				this.detail.formData[this.active].item[this.fileIndex].file = newFiles;
			},
			beforeSelectFile(index,item){
				this.fileIndex = index;
			},
			selectFileSuccess(e){
				for(let tempFile of e.tempFiles){
					this.detail.formData[this.active].item[this.fileIndex].file.push(tempFile);
				}
			},
			addItem(type) {
				this.detail.formData[this.active].item.push({
					type: type,
					tag: '',
					content:'',
					url:'',
					file: []
				});
			},
			addProcess(){
				if(this.active == this.detail.formData.length -1){
					this.msgType = "error"
					this.messageText = `请在结束流程之前添加新流程`
					this.$refs.message.open()
					return;
				}
				this.$refs.inputDialog.open()
			},
			dialogInputConfirm(val) {
				let formData = {'title':val ,'item':new Array(),'requestList': new Array()};
				this.detail.formData.splice(this.active+1,0,formData);
				this.active += 1;
				
			},
			delProcess(){
				if(this.active == 0){
					this.msgType = "error"
					this.messageText = `开始流程不能删除`
					this.$refs.message.open()
					return;
				}
				if(this.active == this.detail.formData.length -1){
					this.msgType = "error"
					this.messageText = `结束流程不能删除`
					this.$refs.message.open()
					return;
				}
				this.detail.formData.splice(this.active,1)
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
</style>
