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
		</view>
		<view class="c-list">
			<view class="introduce-section" style="background-color: #a6e7f1;" />
		</view>
		<view class="c-list">
			<view class="c-row b-b">
				<text class="tit">头像</text>
				<view class="con">
					<uni-file-picker limit="1" ref="tagFile" file-extname="png,jpg,jpeg" :auto-upload="false"></uni-file-picker>
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">回复名称</text>
				<view class="con">
					<uni-easyinput v-model="response.name" placeholder="请输入回复内容" />
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">回复</text>
				<view class="con">
					<uni-easyinput type="textarea" v-model="response.responseDesc" placeholder="请输入回复内容" />
				</view>
			</view>
			
			<uni-section title="">
				<view class="content">
					<view class="c-list" v-if="this.formData.length > 0">
						<view v-for="(item,index) in this.formData">
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
									<uni-file-picker v-model="item.file" limit="6" file-extname="png,jpg,jpeg" :auto-upload="false" @select="selectFileSuccess" @delete="delFile"></uni-file-picker>
								</view>
							</view>
						</view>
					</view>
				</view>
			</uni-section>
			<view class="c-row">
				<view v-for="(item,index) in this.product" :key="index">
					<view class="con" style="width: 230upx;height: 150upx;">
						<image :src="item.tagUrl" mode="aspectFill" style="width: 95%;height: 150upx;" @click="viewProduct(item.id)"></image>
					</view>
					<view class="con" style="width: 230upx;height: 80upx;">
						<text>{{item.name}}</text>
					</view>
					<view class="con" style="width: 100%;height: 40upx;">
						<uni-icons type="trash" size="30" color="#f12a07" @click="deleteProduct(index)"></uni-icons>
					</view>
				</view>
			</view>
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
					<text>商品</text>
					<uni-icons type="gift-filled" size="30" color="#a6e7f1" @click="openRequestToggle"></uni-icons>
				</view>
			</view>
		</view>
		
		<view>
			<uni-popup ref="popup" background-color="#fff">
					<view class="c-list">
						<view class="c-row b-b">
							<view class="con">
								<text>添加商品</text>
								<uni-icons type="link" size="30" color="#a6e7f1" @click="linkProduct"></uni-icons>
							</view>
						</view>
					</view>
			</uni-popup>
		</view>
		
		<!-- 底部操作菜单 -->
		<view class="page-bottom">
			<view class="action-btn-group">
				<button type="primary" class="action-btn no-border add-cart-btn" @click="goBack(request.id)">返回</button>
			</view>
			<view class="action-btn-group">
				<button type="primary" class="action-btn no-border buy-now-btn" @click="saveResponse" :disabled="publishing">确定</button>
			</view>
		</view>
	</view>
</template>

<script>
	import config from '@/config'
	import {getRequestInfo,uploadTag,uploadFile,saveResponseInfo} from "@/api/response/addResponse.js"
	
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
			};
		},
		async onLoad(options){
			this.response.requestId = options.id;
			await getRequestInfo(options.id).then(response => {
					this.request = response.data;
			})
		},
		methods:{
			selectFileSuccess(e){
				for(let tempFile of e.tempFiles){
					this.formData[this.formDataIndex].file.push(tempFile);
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
					url: `/pages/product/productViewDetail?id=${id}`
				})
			},
			linkProduct(){
				uni.navigateTo({
					url: `/pages/response/productList`
				})
				this.$refs.popup.close();
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
				}
				
				await uploadTag(tag).then(response => {
					this.response.tagUrl = response.imgUrl
				});
				
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
					this.$modal.msgSuccess("回复成功")
					this.publishing = false;
					this.$router.push("/pages/work/index")
					uni.navigateTo({
						url: '/pages/response/responseList'
					})
				}).catch(() => {
					this.publishing = false;
				});
				this.publishing = false;
			},
			goBack(id){
				uni.redirectTo({
					url: `/pages/response/requestInfoDetail?id=${id}`
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
