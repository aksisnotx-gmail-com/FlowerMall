<template>
	<view class="container">
		<uni-section title="" >
			<uni-steps :options="list" :active="active" active-color="#1aad19"/>
		</uni-section>
		
		<view>
			<uni-popup ref="inputDialog" type="dialog">
				<uni-popup-dialog ref="inputClose"  value="" mode="input" title="新增流程" placeholder="请输入流程名" @confirm="dialogInputConfirm" />
			</uni-popup>
		</view>
		
		<uni-section title="">
					<view class="uni-padding-wrap uni-common-mt">
						<uni-segmented-control :current="active" :values="items" :style-type="styleType"
							:active-color="activeColor" @clickItem="onClickItem" />
					</view>
					<view class="action-btn-group">
					</view>
					<view class="content">
						<view v-if="active === 0">
							<text class="content-text">简介</text>
							<uni-easyinput type="textarea" placeholder="请输入该需求的描述" />
						</view>
						<view v-if="active === 1"><text class="content-text">选项卡2的内容</text></view>
						<view v-if="active === 2">
							<text class="content-text">结束</text>
							<uni-easyinput type="textarea" placeholder="请输入该需求的描述" />
						</view>
						
						<view v-for="(item,index) in formData">
							<uni-easyinput v-model="formData[index].tag" placeholder="请输入标签" />
							<uni-easyinput v-model="formData[index].content" placeholder="请输入内容" />
							<button class="button" size="mini" type="default" @click="del(item.id)">删除</button>
						</view>
					</view>
					<button type="primary" size="mini" @click="addItem">新增</button>
					<button type="primary" size="mini" @click="saveItem">确定</button>
		</uni-section>
		<view>
			<uni-popup ref="message" type="message">
				<uni-popup-message :type="msgType" :message="messageText" :duration="2000"></uni-popup-message>
			</uni-popup>
		</view>
		
		<view class="page-bottom">
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="preStep">上一流程</button>
			</view>
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="addProcess">新增流程</button>
			</view>
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="delProcess">删除流程</button>
			</view>
			<view class="action-btn-group">
				<button type="primary" class=" action-btn no-border buy-now-btn" @click="nextStep">下一流程</button>
			</view>
		</view>
	</view>
</template>

<script>
	import config from '@/config'
	import {uploadTag,uploadFile,saveProduct} from "@/api/product/addProduct"
	
	const baseUrl = config.baseUrl
	export default{
		data() {
			return {
				active: 0,
				messageText:"",
				msgType:"success",
				list: [{
							"title": '开始'
						}, {
							"title": '结束'
						}],
				items: [],
				formData: [],
				styles: [{
						value: 'button',
						text: '按钮',
						checked: true
					}],
				colors: ['#007aff', '#4cd964', '#dd524d'],
				colorIndex: 0,
				activeColor: '#007aff',
				styleType: 'button'
			};
		},
		onLoad(){
			for(let listTmp of this.list){
				this.items.push(listTmp.title);
			}
		},
		methods:{
			saveItem(){
				console.log(this.formData)
			},
			addItem() {
				let id = Date.now();
				this.formData.push({
					id: id,
					tag: '',
					content:''
				});
			},
			addProcess(){
				if(this.active == this.list.length -1){
					this.msgType = "error"
					this.messageText = `请在结束流程之前添加新流程`
					this.$refs.message.open()
					return;
				}
				this.$refs.inputDialog.open()
			},
			delProcess(){
				if(this.active == 0){
					this.msgType = "error"
					this.messageText = `开始流程不能删除`
					this.$refs.message.open()
					return;
				}
				if(this.active == this.list.length -1){
					this.msgType = "error"
					this.messageText = `结束流程不能删除`
					this.$refs.message.open()
					return;
				}
				this.list.splice(this.active,1)
				this.items.splice(this.active,1)
			},
			dialogInputConfirm(val) {
				let data = {"title":val};
				this.list.splice(this.active+1,0,data)
				this.items.splice(this.active+1,0,val);
				
				this.active += 1;
			},
			nextStep() {
					if (this.active < this.list.length - 1) {
						this.active += 1
					} else {
						this.active = 0
					}
				},
			preStep(){
				if (this.active > 0) {
					this.active -= 1
				} else {
					this.active = this.list.length - 1
				}
			},
			onClickItem(e) {
				if (this.active !== e.currentIndex) {
					this.active = e.currentIndex
				}
				this.formData.length = 0;
			},
			styleChange(e) {
				if (this.styleType !== e.detail.value) {
					this.styleType = e.detail.value
				}
			},
			colorChange(e) {
				if (this.styleType !== e.detail.value) {
					console.log(e.detail.value);
					this.activeColor = e.detail.value
				}
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
