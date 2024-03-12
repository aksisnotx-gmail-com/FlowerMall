<template>
	<view class="container">
		<view class="top-title">
			
			<view class="top-title-container" :style="{backgroundColor:merchant.backgroundColor,background:'url('+merchant.background+')'}">
				<view class="top-tag">
					<view >
						<image class="image" :src="merchant.tagUrl" mode="scaleToFill"></image>
					</view>
				</view>
				
				<view class="top-title-info">
					<view class="top-title-list" :style="{backgroundColor:merchant.backgroundColor}">
						<view class="top-title-row b-b">
							<text class="top-title-tit" :style="{color:merchant.tagColor}">名称：</text>
							<text class="top-title-text" :style="{color:merchant.contentColor}">{{merchant.name}}</text>
						</view>
						<view class="top-title-row b-b">
							<text class="top-title-tit" :style="{color:merchant.tagColor}">区域：</text>
							<text class="top-title-text" :style="{color:merchant.contentColor}">{{merchant.areaText}}</text>
						</view>
						<view class="top-title-row b-b">
							<text class="top-title-tit" :style="{color:merchant.tagColor}">联系人：</text>
							<text class="top-title-text" :style="{color:merchant.contentColor}">{{merchant.userName}}</text>
						</view>
						<view class="top-title-row b-b">
							<text class="top-title-tit" :style="{color:merchant.tagColor}">联系电话：</text>
							<text class="top-title-text" :style="{color:merchant.contentColor}">{{merchant.phone}}</text>
						</view>
					</view>
				</view>
			</view>
		</view>
		
		<view class="c-list" :style="{backgroundColor:merchant.backgroundColor,background:'url('+merchant.background+')'}">
			<view class="c-row b-b">
				<text class="tit" :style="{color:merchant.tagColor}">简介</text>
				<view class="con" :style="{backgroundColor:merchant.backgroundColor}">
					<text type="textarea" :style="{color:merchant.contentColor}">{{merchant.description}}</text>
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit" :style="{color:merchant.tagColor}">详细地址</text>
				<view class="con" :style="{backgroundColor:merchant.backgroundColor}">
					<text class="title text-center" :style="{color:merchant.contentColor}">{{merchant.address}}</text>
				</view>
			</view>
			<view class="c-row b-b">
				<text :style="{color:merchant.tagColor}">材料</text>
			</view>
			<view class="c-row b-b">
				<view class="con">
					<uni-file-picker :value="file" readonly></uni-file-picker>
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit" :style="{color:merchant.tagColor}">标签</text>
				<view class="con">
					<uni-data-checkbox v-model="selectTag" multiple :localdata="this.tag" mode="tag" selected-color="#a6e7f1" selected-text-color="#ff00ff" max="3"/>
				</view>
			</view>
			<view class="c-row introduce-section">
				<text class="tit">我的商品</text>
				<view class="con">
					<uni-icons type="gift-filled" size="30" color="#ff00ff" @click="linkProduct(merchant.id)"></uni-icons>
				</view>
			</view>
			<view class="seckill-section m-t">
				<scroll-view class="floor-list" scroll-x>
					<view class="scoll-wrapper">
						<view 
							v-for="(item, index) in product" :key="index"
							class="floor-item"
						>
							<image :src="item.tagUrl" mode="aspectFill" @click="viewProduct(item.id)"></image>
							<text class="title clamp">{{item.name}}</text>
							<uni-icons type="trash" size="30" color="#f12a07" @click="deleteProduct(index,item.id)"></uni-icons>
						</view>
					</view>
				</scroll-view>
			</view>
			<view class="introduce-section">
				<text>其他</text>
			</view>
			<view class="content">
				<view class="c-list" v-if="formData.length > 0" :style="{backgroundColor:merchant.backgroundColor,background:'url('+merchant.background+')'}">
					<view v-for="(item,index) in formData">
						<view v-if="item.type === '1'">
							<view class="c-row" v-if="item.tag != ''">
								<text class="tit" :style="{color:merchant.tagColor}">{{item.tag}}</text>
								<view class="con" :style="{backgroundColor:merchant.backgroundColor}">
									<text type="textarea" :style="{color:merchant.contentColor}">{{item.content}}</text>
								</view>
							</view>
							<view class="c-row" v-if="item.tag == ''">
								<text class="tit1" :style="{color:merchant.tagColor}">{{item.tag}}</text>
								<view class="con" :style="{backgroundColor:merchant.backgroundColor}">
									<text type="textarea" :style="{color:merchant.contentColor}">{{item.content}}</text>
								</view>
							</view>
						</view>
						<view v-if="item.type === '2'">
							<view class="c-row" v-if="item.tag != ''">
								<text class="tit" :style="{color:merchant.tagColor}">{{item.tag}}</text>
								<view class="con" v-if="item.file.length > 0">
									<swiper class="carousel" circular indicator-dots="true">
										<swiper-item v-for="(imgItem, imgIndex) in item.file" :key="imgIndex" class="carousel-item">
											<image class="swiper-img" :src="imgItem.url" />
										</swiper-item>
									</swiper>
								</view>
							</view>
							<view class="c-row" v-if="item.tag == ''">
								<text class="tit1" :style="{color:merchant.tagColor}"></text>
								<view class="con" v-if="item.file.length > 0">
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
			</view>
		</view>
		
		<!-- 底部操作菜单 -->
		<view class="page-bottom">
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="goBack">返回</button>
			</view>
		</view>
	</view>
</template>

<script>
	import {getMerchantInfo,getMerchantTag,getProductInfo,deleteProductFromMerchant} from "@/api/merchant/merchantView.js"
	export default{
		data() {
			return {
				merchant: {
					id: "",
					name: "",
					description: "",
					phone: "",
					tagUrl: "",
					userName: "",
					province: "",
					city: "",
					county: "",
					address: "",
					areaText: "",
					url: "",
					tag: "",
					backgroundColor: "#ffffff",
					tagColor:"",
					contentColor:"",
					background: ""
				},
				product: [],
				file: [],
				formData: [],
				selectTag: [],
				tag: [],
				txt: '请选择',
				formDataIndex: 0
			};
		},
		onLoad(option){
			getMerchantInfo(option.id).then(response => {
				this.merchant = response.data.merchant
				let formData = response.data.formData;
				for(let detail of formData){
					if(detail.type == '2'){
						if(detail.url == ''){
							continue;
						}
						let urlArr = detail.url.split(',');
						let file = [];
						for(let url of urlArr){
							let fileData = {url: url};
							file.push(fileData);
						}
						detail.file = file;
					}
					this.formData.push(detail);
				}
				
				let selectTags = this.merchant.tag.split(',');
				for(let tag of selectTags){
					this.selectTag.push(tag);
				}
				
				getMerchantTag().then(response => {
					for(let tags of response.data){
						if(this.selectTag.includes(tags.dictValue) ){
							let tag = {'text': tags.dictLabel,'value': tags.dictValue,'disable':true};
							this.tag.push(tag);
						}
					}
				});
				
				if(this.merchant.url == ''){
					return;
				}
				let fileUrl = this.merchant.url.split(',');
				for(let url of fileUrl){
					let fileData = {url: url};
					this.file.push(fileData);
				}
				
			});
			
			getProductInfo(option.id).then(response => {
				for(let product of response.data){
					this.product.push(product);
				}
			});
		},
		methods:{
			deleteProduct(index,id){
				this.product.splice(index,1);
				let deleteData = {'merchantId':this.merchant.id,'productId':id}
				deleteProductFromMerchant(deleteData);
			},
			linkProduct(id){
				uni.navigateTo({
					url: `/pages/merchant/productList?id=${id}`
				})
			},
			viewProduct(id){
				uni.navigateTo({
					url: `/pages/product/productViewDetail?id=${id}`
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
	/* 分类推荐楼层 */
	.hot-floor{
		width: 100%;
		overflow: hidden;
		margin-bottom: 20upx;
		.floor-img-box{
			width: 100%;
			height:160upx;
			position:relative;
			&:after{
				content: '';
				position:absolute;
				left: 0;
				top: 0;
				width: 100%;
				height: 100%;
				background: linear-gradient(rgba(255,255,255,.06) 30%, #f8f8f8);
			}
		}
		.floor-img{
			width: 100%;
			height: 100%;
		}
		.floor-list{
			white-space: nowrap;
			padding: 20upx;
			padding-right: 50upx;
			border-radius: 6upx;
			margin-top:-140upx;
			margin-left: 30upx;
			background: #fff;
			box-shadow: 1px 1px 5px rgba(0,0,0,.2);
			position: relative;
			z-index: 1;
		}
		.floor-type{
			white-space: nowrap;
			padding: 20upx;
			padding-right: 50upx;
			border-radius: 6upx;
			margin-top:80upx;
			margin-left: 0upx;
			background: #fff;
			box-shadow: 1px 1px 5px rgba(0,0,0,.2);
			position: relative;
			z-index: 1;
		}
		.scoll-wrapper{
			display:flex;
			align-items: flex-start;
		}
		.floor-item{
			width: 180upx;
			margin-right: 20upx;
			font-size: $font-sm+2upx;
			color: $font-color-dark;
			line-height: 1.8;
			image{
				width: 180upx;
				height: 180upx;
				border-radius: 6upx;
			}
			.price{
				color: $uni-color-primary;
			}
		}
		.more{
			display:flex;
			align-items: center;
			justify-content: center;
			flex-direction: column;
			flex-shrink: 0;
			width: 180upx;
			height: 180upx;
			border-radius: 6upx;
			background: #f3f3f3;
			font-size: $font-base;
			color: $font-color-light;
			text:first-child{
				margin-bottom: 4upx;
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
	.top-title{
		margin-top: 5px;
		background: $uni-bg-color-main;
		.top-title-container{
			display:flex;
			padding: 20upx 30upx;
			align-items: flex-start;
			background: $scroll-background-color;
		}
		.top-tag{
			width: 180upx;
			margin: 10upx;
			font-size: $font-sm+2upx;
			line-height: 0.5;
			background: $scroll-item-background;
			.image{
				width: 180upx;
				height: 180upx;
				border-radius: 6upx;
			}
		}
		.top-title-info{
			width: 100%;
			margin: 10upx;
			line-height: 1.8;
			
			.top-title-list{
				font-size: $font-sm + 2upx;
				background: $card-info-background;
				.top-title-row{
					align-items:center;
					position:relative;
				}
				.top-title-tit{
					width: 100upx;
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
