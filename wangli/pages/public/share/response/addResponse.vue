<template>
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
		
		<view class="introduce-section">
			<text class="con">回复内容</text>
		</view>
		<view class="c-list" >
			<view class="c-row b-b">
				<text class="tit">头像</text>
				<view class="con">
					<uni-file-picker limit="1" ref="tagFile" file-extname="png,jpg,jpeg" :auto-upload="false"></uni-file-picker>
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">回复主题</text>
				<view class="con custom-input">
					<uni-easyinput v-model="response.name" placeholder="请输入回复主题" :maxlength="10" :clearable="false"/>
					 <span class="character-count">{{ response.name.length }}/10</span>
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">回复</text>
				<view class="con custom-input">
					<uni-easyinput type="textarea" v-model="response.responseDesc" placeholder="请输入回复内容"  :maxlength="100" auto-height />
					<span class="character-count">{{ response.responseDesc.length }}/100</span>
				</view>
			</view>
			<view class="introduce-section">
				<text class="con">其他</text>
			</view>
			<uni-section>
				<view class="content">
					<view class="c-list" v-if="this.formData.length > 0">
						<view v-for="(item,index) in formData" :key="index">
							<view class="c-row b-b" v-if="item.type === '1'">
								<view class="tit custom-input">
									<uni-easyinput style="margin-left: -5px" v-model="item.tag" placeholder="标签" :clearable="false" :maxlength="4"/>
									 <span class="character-count">{{ item.tag.length }}/4</span>
								</view>
								<view class="con custom-input">
									<uni-easyinput v-model="item.content" type="textarea" placeholder="请输入内容" :maxlength="100"/>
									<span class="character-count">{{ item.content.length }}/100</span>
								</view>
							</view>
							<view class="c-row b-b" v-if="item.type === '2'">
								<view class="tit custom-input">
									<uni-easyinput style="width:80%;margin-left: -5px;" v-model="item.tag" placeholder="标签" :clearable="false" :maxlength="4"/>
									<span class="character-count">{{ item.tag.length }}/4</span>
								</view>
								<view class="con" @click="beforeSelectFile(index)">
									<uni-file-picker v-model="item.file" limit="6" file-extname="png,jpg,jpeg" :auto-upload="false" @select="selectFileSuccess" @delete="deleteFile"></uni-file-picker>
								</view>
							</view>
						</view>
					</view>
				</view>
			</uni-section>
			<!-- <view class="introduce-section">
				<text class="con">商品</text>
			</view>
			<view class="c-row">
				<view v-for="(productItem,productIndex) in product" :key="productIndex">
					<view class="con" style="width: 230upx;height: 150upx;">
						<image :src="productItem.tagUrl" mode="aspectFill" style="width: 95%;height: 150upx;" @click="viewProduct(productItem.id)"></image>
					</view>
					<view class="con" style="width: 230upx;height: 80upx;">
						<text>{{productItem.name}}</text>
					</view>
					<view class="con" style="width: 100%;height: 40upx;">
						<uni-icons type="trash" size="30" color="#f12a07" @click="deleteProduct(productIndex)"></uni-icons>
					</view>
				</view>
			</view> -->
			<view class="c-row b-b">
				<view class="con">
					<text>文本</text>
					<uni-icons type="compose" size="30" color="#a6e7f1" @click="addItem('1')"></uni-icons>
				</view>
				<view class="con">
					<text>附件</text>
					<uni-icons type="paperclip" size="30" color="#a6e7f1" @click="addItem('2')"></uni-icons>
				</view>
				<!-- <view class="con">
					<text>商品</text>
					<uni-icons type="gift-filled" size="30" color="#a6e7f1" @click="linkProduct"></uni-icons>
				</view> -->
			</view>
		</view>
		
		<!-- 底部操作菜单 -->
		<view class="page-bottom">
			<view class="action-btn-group">
				<button type="primary" class="action-btn no-border add-cart-btn" @click="goBack(request.id)">返回</button>
			</view>
			<view class="action-btn-group">
				<button type="primary" class="action-btn no-border buy-now-btn" @click="saveResponse" :disabled="publishing">确定</button>
			</view>
			<view class="action-btn-group">
				<button type="primary" class="action-btn no-border add-cart-btn" @click="goPreview">预览</button>
			</view>
		</view>
	</view>
</template>

<script>
	import config from '@/config'
	import {getRequestInfo,uploadTag,uploadFile,saveResponseInfo} from "@/api/public/response/addResponse.js"
	
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
					url: "",
					tagUrl: "",
					startTime: "",
					endTime: "",
				},
				response: {
					name: "",
					responseDesc: "",
					requestId: "",
					responseProductId: "",
					url: "",
					tagUrl: ""
				},
				formData: [],
				product: [],
				publishing: false,
				responseDesc: "",
				openToRequestFlat: false,
				openToPublicFlag: false,
				responseFIles: [],
				requestImgList: [],
				fileCount: 0
			};
		},
		async onLoad(options){
			this.response.requestId = options.id;
			await getRequestInfo(options.id).then(response => {
					this.request = response.data;
			})
		},
		methods:{
			deleteFile(){
				this.fileCount = this.fileCount - 1;
			},
			selectFileSuccess(e){
				for(let tempFile of e.tempFiles){
					this.formData[this.formDataIndex].file.push(tempFile);
					this.fileCount = this.fileCount + 1;
				}
			},
			beforeSelectFile(index,item){
				this.formDataIndex = index;
			},
			addItem(type) {
				this.formData.push({
					type: type,
					tag: '',
					content:'',
					url:'',
					file: []
				});
			},
			deleteProduct(index){
				this.product.splice(index,1);
			},
			viewProduct(id){
				uni.navigateTo({
					url: `/pages/public/product/productViewDetail?id=${id}`
				})
			},
			linkProduct(){
				uni.navigateTo({
					url: `/pages/public/product/productList`
				})
			},
			openRequestToggle(){
				this.$refs.popup.open('bottom');
			},
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
				await this.$store.dispatch('GetInfo').then(res => {
					this.user = res.user;
				}).catch(error => {
					return;
				})
				if(this.fileCount > 9){
					this.$modal.msgError("最多9个附件");
					return;
				}
				this.publishing = true;
				let productList = new Array();
				for(let product of this.product){
					productList.push(product.id);
				}
				this.response.responseProductId = productList.join();
				
				uni.showLoading({
					title: "头像上传中",
					mask: true
				});
				
				let tagImgs = this.$refs.tagFile.getFiles();
				let tag = undefined;
				if(tagImgs.length == 1){
					tag = {name: "file", filePath: tagImgs[0].url,extname:tagImgs[0].extname};
					await uploadTag(tag).then(response => {
						this.response.tagUrl = response.imgUrl
					});
				}
				
				let formDataList = this.formData;
				if(this.formData.length > 0){
					for(let formData of formDataList){
						if(formData.type == '2'){
							let tagText  = formData.tag
							if(tagText == ''){
								tagText = '附件上传中'
							}else{
								tagText = "正在上传：" + tagText
							}
							uni.showLoading({
								title: tagText,
								mask: true
							})
							let url = new Array();
							for(let file of formData.file){
								let fileInfo = {name: "file", filePath: file.url,extname:file.extname};
								await uploadFile(fileInfo).then(response => {
									url.push(response.imgUrl);
								});
							}
							formData.url = url.join();
						}
					}
				}
				uni.showLoading({
					title: "回复创建中",
					mask: true
				})
				
				let data = {'response':this.response,'item': this.formData};
				saveResponseInfo(data).then(response => {
					this.$modal.msgSuccess("回复成功,请在回复列表中查看")
					setTimeout(()=>{
						uni.navigateBack()
					}, 1000)
				}).catch(() => {
					this.publishing = false;
				});
				this.publishing = false;
			},
			goBack(id){
				uni.navigateBack();
			},
			goPreview(){
				uni.navigateTo({
					url: '/pages/public/share/response/previewResponse'
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
	.custom-input {
	  position: relative;
	}
	
	.character-count {
	  position: absolute;
	  right: 5px;
	  bottom: 5px;
	  color: gray;
	}
	
	.custom-input input::after {
	  position: absolute;
	  right: 5px;
	  bottom: 5px;
	  color: gray;
	}
</style>
