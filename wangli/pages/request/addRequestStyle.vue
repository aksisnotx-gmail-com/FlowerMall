<template>
	<view class="container">
		<view class="top-title">
			<view class="top-title-tool">
				<view>
					<text>颜色设置：</text>
					<uni-icons type="color" size="25" color="#ff155f" @click="chooseTitleColorType('0')"></uni-icons>
				</view>
				<view class="c-list top-title-tool" >
					<view class="c-row">
						<view class="tit">
							<text>背景设置：</text>
						</view>
						<view class="tit">
							<view>
							<uni-file-picker  limit="1" :del-icon="false" disable-preview :imageStyles="imageStyles"
								file-mediatype="image" @select="setTitleBackground">
									<uni-icons type="image-filled" size="25" color="#ff155f" ></uni-icons>
								</uni-file-picker>
							</view>
						</view>
					</view>
				</view>
				<view>
					<text>清除：</text>
					<uni-icons type="trash" size="25" color="#ff155f" @click="clearTitle"></uni-icons>
				</view>
			</view>
			
			<view class="top-title-container" :style="{backgroundColor:backgroundColor,background:'url('+background+')'}">
				<view class="top-tag">
					<view >
						<image class="image" :src="request.tagUrl" mode="scaleToFill"></image>
					</view>
				</view>
				
				<view class="top-title-info">
					<view class="top-title-list" :style="{backgroundColor:backgroundColor}">
						<view class="top-title-row b-b">
							<text class="top-title-tit" :style="{color:tagColor}">名称：</text>
							<text class="top-title-text" :style="{color:contentColor}">{{request.name}}</text>
						</view>
						<view class="top-title-row b-b">
							<text class="top-title-tit" :style="{color:tagColor}">区域：</text>
							<text class="top-title-text" :style="{color:contentColor}">{{request.areaTxt}}</text>
						</view>
						<view class="top-title-row b-b">
							<text class="top-title-tit" :style="{color:tagColor}">类型：</text>
							<text class="top-title-text" :style="{color:contentColor}">{{request.typeTxt}}</text>
						</view>
						<view class="top-title-row b-b">
							<text class="top-title-tit" :style="{color:tagColor}">时间：</text>
							<text class="top-title-text" :style="{color:contentColor}" v-if="request.startTime !='' && request.endTime != ''">{{request.startTime}}----{{request.endTime}}</text>
						</view>
					</view>
				</view>
			</view>
		</view>
		
		<view class="c-list" :style="{backgroundColor:backgroundColor,background:'url('+background+')'}">
			<view class="c-row b-b">
				<text class="tit" :style="{color:tagColor}">描述</text>
				<view class="con" :style="{backgroundColor:backgroundColor}">
					<text type="textarea" :style="{color:contentColor}">{{request.description}}</text>
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit" :style="{color:tagColor}">要求</text>
				<view class="con" :style="{backgroundColor:backgroundColor}">
					<text type="textarea" :style="{color:contentColor}">{{request.requirement}}</text>
				</view>
			</view>
			<view class="introduce-section">
				<text>其他</text>
			</view>
			<view class="content">
				<view class="c-list" v-if="formData.length > 0" :style="{backgroundColor:backgroundColor,background:'url('+background+')'}">
					<view v-for="(item,index) in formData">
						<view v-if="item.type === '1'">
							<view class="c-row" >
								<text class="tit" :style="{color:tagColor}">{{item.tag}}</text>
								<view class="con" :style="{backgroundColor:backgroundColor}">
									<text type="textarea" :style="{color:contentColor}">{{item.content}}</text>
								</view>
							</view>
						</view>
						<view v-if="item.type === '2'">
							<view class="c-row" v-if="item.tag != ''">
								<text class="tit" :style="{color:tagColor}">{{item.tag}}</text>
								<view class="con" v-if="item.file.length > 0">
									<swiper class="carousel" circular indicator-dots="true">
										<swiper-item v-for="(item, index) in item.file" :key="index" class="carousel-item">
											<image class="swiper-img" :src="item.url" />
										</swiper-item>
									</swiper>
								</view>
							</view>
							<view class="c-row" v-if="item.tag == ''">
								<view class="con" v-if="item.file.length > 0">
									<view class="con" v-if="item.file.length > 0">
										<swiper class="carousel" circular indicator-dots="true">
											<swiper-item v-for="(item, index) in item.file" :key="index" class="carousel-item">
												<image class="swiper-img" :src="item.url" />
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
		
		<view>
			<t-color-picker ref="colorPicker" :color="color" @confirm="colorConfirm"></t-color-picker>
		</view>
		<view>
			<uni-popup ref="message" type="message">
				<uni-popup-message :type="msgType" :message="messageText" :duration="2000"></uni-popup-message>
			</uni-popup>
		</view>
		<view class="page-bottom">
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="goBack">返回</button>
			</view>
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="submitRequest">保存</button>
			</view>
		</view>
	</view>
</template>

<script>
	import tColorPicker from '@/components/t-color-picker/t-color-picker.vue'
	import {uploadTag,uploadFile,saveRequestInfo} from "@/api/request/addRequest"
	export default{
		components: {
			tColorPicker
		},
		data() {
			return {
				request: {
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
					background: ""
				},
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
				chooseColorType:"",
				chooseColorTypeIndex: "",
				backgroundColor: "#ffffff",
				tagColor:"",
				contentColor:"",
				background: "",
				backgroundImg: undefined,
				color: {r: 255,g: 0,b: 0,a: 0.6},
				publishing: false,
				areaTxt: '请选择',
				typeTxt: '请选择',
				messageText:"",
				msgType:"success",
				formData: [],
				tagImg: undefined
			};
		},
		async onLoad(options){
			let pages = getCurrentPages();
			let prePage = pages[pages.length - 2];
			this.request = prePage.$vm.request;
			this.formData = prePage.$vm.formData;
			this.tagImg = prePage.$vm.tagImg;
			if(this.tagImg == undefined){
				this.request.tagUrl = "";
			}else{
				this.request.tagUrl = this.tagImg.url;
			}
			
			this.areaTxt = prePage.$vm.areaTxt;
			if(this.request.firstLevel == ""){
				this.typeTxt = "未选择";
			}else{
				this.typeTxt = prePage.$vm.typeTxt;
			}
		},
		methods:{
			async submitRequest(){
				let tagText = '头像上传中'
				uni.showLoading({
					title: tagText,
					mask: true
				})
				if(this.tagImg != "" && this.tagImg != undefined){
					let tag = {name: "file", filePath: this.tagImg.url,extname:this.tagImg.extname};
					await uploadTag(tag).then(response => {
						this.request.tagUrl = response.imgUrl
					});
				}
				
				if(this.backgroundImg != "" && this.backgroundImg != undefined){
					let background = {name: "file", filePath: this.backgroundImg.url,extname:this.backgroundImg.extname};
					await uploadTag(background).then(response => {
						this.request.background = response.imgUrl
					});
				}
				
				let formDataList = this.formData;
				if(this.formData.length > 0){
					let tagText  = '附件上传中'
					for(let formData of formDataList){
						if(formData.type == '2'){
							if(formData.tag != ''){
								tagText = "正在上传：" + formData.tag
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
				tagText = '需求创建中'
				uni.showLoading({
					title: tagText,
					mask: true
				})
				
				this.request.backgroundColor = this.backgroundColor;
				this.request.tagColor = this.tagColor;
				this.request.contentColor = this.contentColor;
				
				let addData = {'request':this.request,'formData':this.formData};
				saveRequestInfo(addData).then(response => {
					this.$modal.msgSuccess("需求创建成功")
					setTimeout(function() {
						uni.switchTab({
							url: '/pages/work/index'
						})
					}, 1000);
				}).catch(() => {
				});
			},
			clearTitle(){
				this.chooseColorType = '';
				this.chooseColorTypeIndex = '';
				this.backgroundColor = '';
				this.tagColor = '';
				this.background = '';
				this.contentColor = '';
			},
			setTitleBackground(res){
				this.background = res.tempFilePaths;
				this.backgroundImg = res.tempFiles[0];
			},
			chooseTitleColorType(type) {
				var that = this;
				uni.showActionSheet({
					itemList: ['设置背景颜色', '设置标签颜色','设置内容颜色'],
					success (res) {
						that.openChooseColorTool(type,res.tapIndex);
					},
					fail (res) {
					}
				})
			},
			openChooseColorTool(type,index){
				this.chooseColorType = type;
				this.chooseColorTypeIndex = index;
				this.$refs.colorPicker.open();
			},
			colorConfirm(e) {
				if(this.chooseColorType == 0){//需求头
					if(this.chooseColorTypeIndex ==0){//背景色
					console.log("设置需求头--背景色")
						this.backgroundColor = e.hex;
					}else if(this.chooseColorTypeIndex == 1){//字体色
					console.log("设置需求头--标签")
						this.tagColor = e.hex;
					}else{
						console.log("设置需求头--内容")
						this.contentColor = e.hex;
					}
				}
			},
			goBack(){
				uni.navigateBack();
			},
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
	.swiper-img{
		max-width: 100%;
		max-height: 100%;
		display:inline-block;
		position:relative;
	}
</style>
