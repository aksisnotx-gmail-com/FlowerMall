<template>
	<view class="container">
		<view class="c-list">
			<view class="c-row b-b">
				<view class="tit">名称</view>
				<view class="con">
					<uni-easyinput v-model="product.name" placeholder="请输入名称" />
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">单位</text>
				<view class="con">
					<uni-easyinput v-model="product.unit" placeholder="请输入单位" />
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">价格</text>
				<view class="con">
					<uni-easyinput v-model="product.price" placeholder="请输入价格" />
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">描述</text>
				<view class="con">
					<uni-easyinput v-model="product.description" type="textarea" placeholder="请输入描述" />
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">讲价：</text>
				<view class="con">
					<switch :checked="product.bargainFlag" @change="switchBargainChange"></switch>
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">自动上架：</text>
				<view class="con">
					<switch :checked="product.publish" @change="switchPublishChange"></switch>
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">商品头像</text>
				<view class="con">
					<uni-file-picker limit="1" ref="tagFile" v-model="productTag" file-extname="png,jpg,jpeg" :auto-upload="false"></uni-file-picker>
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">附件(最多选择6张图片)</text>
				<view class="con">
					<uni-file-picker limit="6" ref="file" v-model="productImgList" file-extname="png,jpg,jpeg" :auto-upload="false" ></uni-file-picker>
				</view>
			</view>
		</view>
		<!-- 底部操作菜单 -->
		<view class="page-bottom">
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="updateProductInfo" :disabled="publishing">修改</button>
			</view>
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border add-cart-btn" @click="goBack">取消</button>
			</view>
		</view>
	</view>
</template>

<script>
	import config from '@/config'
	import {getProductInfo,uploadTag,uploadFile,updateProduct} from "@/api/product/productUpdateDetail.js"
	
	const baseUrl = config.baseUrl
	export default{
		data() {
			return {
				product: {
					id: "",
					name: "",
					description: "",
					unit: "",
					price: "",
					bargainFlag: false,
					tagUrl: "",
					url: "",
					publish: false
				},
				productTag: [],
				productTagTmp: [],
				productImgList: [],
				productImgListTmp: [],
				publishing: false
			};
		},
		async onLoad(options){
			await getProductInfo(options.id).then(response => {
					this.product = response.data;
					
					let productImgListTmp = this.product.url.split(',');
					
					for(let img of productImgListTmp){
						let data = {url: img};
						this.productImgList.push(data);
					}
					this.productImgListTmp = productImgListTmp;
					
					let tagData = {url: this.product.tagUrl};
					this.productTag = tagData;
					this.productTagTmp = this.product.tagUrl
					
			})
		},
		methods:{
			switchBargainChange(e){
				if(e.detail.value){
					this.product.bargainFlag = true;
				}else{
					this.product.bargainFlag = false;
				}
				console.log("-----bargainFlag-----------",this.product.bargainFlag)
			},
			switchPublishChange(e){
				if(e.detail.value){
					this.product.publish = true;
				}else{
					this.product.publish = false;
				}
				console.log("-----publish-----------",this.product.publish)
			},
			async updateProductInfo(){
				this.publishing = true;
				let tagImgs = this.$refs.tagFile.getFiles();
				if(!this.productTagTmp.includes(tagImgs[0].url)){
					let tag = {name: "file", filePath: tagImgs[0].url,extname:tagImgs[0].extname};
					await uploadTag(tag).then(response => {
						this.product.tagUrl = response.imgUrl
					});
				}
				let imgs = this.$refs.file.getFiles();
				let productFiles = await this.uploadFiles(imgs);
				
				this.product.url = productFiles.join();
				console.log(this.product)
				updateProduct(this.product).then(response => {
					this.$modal.msgSuccess("修改成功")
					this.publishing = false;
					uni.redirectTo({
						url: '/pages/product/productList'
					})
				}).catch(() => {
					this.publishing = false;
				});
				this.publishing = false;
			},
			goBack(){
				uni.redirectTo({
					url: '/pages/product/productList'
				})
			},
			handelTagSelect(res){
			},
			handelSelect(res){
			},
			async uploadFiles(imgs){
				let tempFileList = [];
				for(let img of imgs){
					if(this.productImgListTmp.includes(img.url)){
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
