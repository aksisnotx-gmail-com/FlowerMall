<template>
	<view class="container">
		<view class="c-list">
			<view class="c-row b-b">
				<text class="tit">头像</text>
				<view class="con">
					<uni-file-picker limit="1" ref="tagFile" file-extname="png,jpg,jpeg" :auto-upload="false" @select="selectTagSuccess"></uni-file-picker>
				</view>
			</view>
			<view class="c-row b-b">
				<view class="tit">名称</view>
				<view class="con">
					<uni-easyinput v-model="merchant.name" placeholder="请输入名称" />
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">简介</text>
				<view class="con">
					<uni-easyinput v-model="merchant.description" type="textarea" placeholder="请输入简介" />
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">联系电话</text>
				<view class="con">
					<uni-easyinput v-model="merchant.phone" placeholder="请输入联系电话" />
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">联系人</text>
				<view class="con">
					<uni-easyinput v-model="merchant.userName" placeholder="请输入联系人" />
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">所属区域</text>
				<view class="con">
					<pickerAddress @change="change">
						<text class="tit1">{{txt}}</text>
					</pickerAddress>
				</view>
			</view>
			<view class="c-row b-b">
				<text class="tit">详细地址</text>
				<view class="con">
					<uni-easyinput v-model="merchant.address" placeholder="请输入详细地址" />
				</view>
			</view>
			<view class="c-row b-b">
				<text>材料(证明你是你，能让用户相信你的材料)</text>
			</view>
			<view class="c-row b-b">
				<view class="con">
					<uni-file-picker limit="6" ref="refFile" file-extname="png,jpg,jpeg" :auto-upload="false"></uni-file-picker>
				</view>
			</view>
			<uni-forms-item label="标签" required>
				<uni-data-checkbox v-model="selectTag" multiple :localdata="this.tag" mode="tag" selected-color="#a6e7f1" selected-text-color="#ff00ff" max="3"/>
			</uni-forms-item>
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
									<uni-file-picker v-model="item.file" limit="6" file-extname="png,jpg,jpeg" :auto-upload="false" @select="selectFileSuccess"></uni-file-picker>
								</view>
							</view>
						</view>
					</view>
				</view>
			</uni-section>
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
				</view>
			</view>
		</view>
		<!-- 底部操作菜单 -->
		<view class="page-bottom">
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="goBack">返回</button>
			</view>
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="goNext">下一步</button>
			</view>
		</view>
	</view>
</template>

<script>
	import {getMerchantTag} from "@/api/merchant/addMerchant.js"
	
	export default{
		data() {
			return {
				merchant: {
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
					tag: ""
				},
				formData: [],
				selectTag: [],
				tag: [],
				txt: '请选择',
				formDataIndex: 0,
				tagImg: undefined
			};
		},
		onLoad(){
			getMerchantTag().then(response => {
				for(let data of response.data){
					let tag = {'text': data.dictLabel,'value': data.dictValue}
					this.tag.push(tag);
				}
			});
		},
		methods:{
			selectTagSuccess(e){
				if(e.tempFiles.length > 0){
					this.tagImg = e.tempFiles[0];
				}
			},
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
			switchChange(e){
				if(e.detail.value){
					this.product.bargainFlag = true;
				}else{
					this.product.bargainFlag = false;
				}
			},
			switchPublishChange(e){
				if(e.detail.value){
					this.product.publish = true;
				}else{
					this.product.publish = false;
				}
			},
			
			async uploadFiles(imgs){
				let tempFileList = [];
				for(let img of imgs){
					let data = {name: "file", filePath: img.url};
					await uploadFile(data).then(response => {
						tempFileList.push(response.imgUrl)
					});
				}
				return tempFileList;
			},
			goNext(){
				uni.navigateTo({
					url: `/pages/merchant/addMerchantStyle`
				})
			},
			change(data) {
				let dataTmp = [];
				if(data.index[0] !=0){
					dataTmp.push(data.data[0]);
					this.merchant.province = data.index[0];
				}
				if(data.index[1] !=0){
					dataTmp.push(data.data[1]);
					this.merchant.city = data.index[1];
				}
				if(data.index[2] !=0){
					dataTmp.push(data.data[2]);
					this.merchant.county = data.index[2];
				}
				if(dataTmp.length == 0){
					dataTmp.push("全国");
				}
				this.txt = dataTmp.join('---');
				this.merchant.areaText = this.txt;
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
</style>
