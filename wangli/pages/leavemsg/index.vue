<script>
	import { getLeaveListApi, addLeaveListApi, delLeaveListApi } from '@/api/leave/index.js'
	export default {
		data() {
			return {
				content: '',
				msgList: [],
				userInfo: {}
			}
		},
		methods: {
			async onRelease() {
				const val = this.content.trim()
				const res = await addLeaveListApi(val)
				if(res.code === 200) {
					uni.showToast({
						title: '留言成功~',
						icon: 'success',
						duration: 3000
					})
					this.getLeaveList()
					this.content = ''
				}
			},
			getUserInfo () {
				const userInfo = uni.getStorageSync('user')
				console.log(userInfo, 'u');
				if(!userInfo.nickName) {
					uni.showToast({
						title: '请您登录~',
						icon: 'error',
						duration: 3000
					})
					return
				}
				this.userInfo = userInfo
			},
			convertDateTimeFormat(dateTimeStr) {
			  // 解析ISO格式的时间字符串
			  const date = new Date(dateTimeStr);
			  // 使用Intl.DateTimeFormat来格式化日期
			  const options = { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit', hour12: false };
			  const formatter = new Intl.DateTimeFormat('en-GB', options);
			
			  // 格式化日期，然后替换日期中的"/"为"-"，以匹配所需的格式
			  const formattedDate = formatter.format(date).replace(/\//g, '-');
			  
			  // 将格式化的日期分割并重组，以满足"YYYY-MM-DD hh:mm:ss"的格式要求
			  const [dd, mm, yyyy, hh, ii, ss] = formattedDate.match(/\d+/g);
			  return `${yyyy}-${mm}-${dd} ${hh}:${ii}:${ss}`;
			},
			async delMessage (item) {
				const { id } = item
				const res = await delLeaveListApi(id)
				if(res.code === 200) {
					uni.showToast({
						title: '删除成功~',
						icon: 'success',
						duration: 3000
					})
					this.getLeaveList()
				}
			},
			async getLeaveList () {
				const res = await getLeaveListApi()
				const { code, data } = res
				if(code === 200) {
					this.msgList = [ ...data.records ]
				}
			}
		},
		mounted () {
			this.getUserInfo()
			this.getLeaveList()
		}
	}
</script>

<template>
	<view>
		<!-- 发布留言的主体 -->
		<view class="wrap">
			<view class="wrap-head">
				<view class="head-logo">
					<image src="image/xx.png" />
				</view>
				<view class="head-txt">
					<a class="title-txt" href="javascript:void(0)">置办年货省省省！红包在手年货无忧！点击领取年货红包&nbsp;&nbsp;&nbsp;热门微博</a>
				</view>
			</view>
			<view class="main-txt">
				<textarea v-model="content" placeholder="请输入留言内容" class="main-area"></textarea>
			</view>
			<view class="warp-footer" @click="onRelease">
				<view class="warp-footer-btns">
					<view class="release-btn">
						<text>发布</text>
					</view>
				</view>
			</view>
		</view>
		<!-- 显示留言的主体 -->
		<view class="show">
			<view class="show-content" v-for="item of msgList" :key="item.id">
				<view class="show-name">{{item.createUser}}</view>
				<view class="show-txt">
					<p class="">{{item.message}}</p>
				</view>
				<view class="show-time">{{convertDateTimeFormat(item.createTime)}}</view>
				<view class="show-close" v-if="item.userId == userInfo.userId" @click="delMessage(item)">x</view>
			</view>
		</view>
	</view>
</template>

<style scoped>
	body, ul {
		margin: 0;
		padding: 0;
	}
	ul {
		list-style: none;
	}
	 
	/*最外层*/
	.wrap {
		width: 600px;
		height: 165px;
		margin: 20px auto;
		border-radius: 4px;
		border: 1px solid #ddd;
		padding: 0 10px;
	}
	 
	.wrap-head {
		width: 100%;
		height: 24px;
		padding-top: 4px;
		overflow: hidden;
	}
	 
	.head-logo {
		width: 40%;
		float: left;
	}
	 
	.head-logo image {
		width: 30px;
		height: 30px;
	}
	 
	.head-txt {
		padding: 4px 0;
		width: 60%;
		float: right;
	}
	 
	.head-txt a {
		font-size: 12px;
		color: #eb7350;
		text-decoration: none;
	}
	.title-txt.title {
		text-align: right;
		color: black;
		display: block;
		width: 100%;
	}
	 
	 
	/*内层设计，输入框*/
	.main-txt {
		border: 1px solid #ccc;
		width: 98%;
		height: 68px;
		margin: 4px 0 0;
		padding: 5px;
		box-shadow: 0px 0px 3px 0px rgba(0, 0, 0, 0.15) inset;
	}
	 
	.main-txt textarea {
		border: none;
		width: 100%;
		height: 66px;
		outline: none;
		resize: none;
		color: #333;
	}
	.main-txt.outline {
		outline: 2px orange solid;
	}
	 
	/*下层设计*/
	.warp-footer {
		width: 100%;
		height: 40px;
		margin: 6px 0;
		overflow: hidden;
	}
	 
	.warp-icon-cont {
		width: 65%;
		float: left;
		margin-top: 10px;
	}
	 
	.warp-icon-cont ul li {
		display: inline-block;
		margin-right: 15px;
		cursor: pointer;
	}
	 
	.warp-icon-cont a {
		font-size: 12px;
		color: #333;
		text-decoration: none;
		height: 20px;
		margin-left: 5px;
		display: block;
		width: 25px;
		float: right;
		line-height: 20px;
	}
	 
	.warp-icon-cont a:hover {
		color: #eb7350;
	}
	 
	.warp-icon-cont image {
		width: 20px;
		height: 20px;
	}
	 
	.warp-footer-btns {
		width: 35%;
		float: right;
		overflow: hidden;
		margin-top: 3px;
	}
	 
	.release-btn {
		width: 80px;
		height: 28px;
		float: right;
		background-color: #ffc09f;
		border: 4px solid #fbbd9e;
		cursor: pointer;
		border-radius: 2px;
	}
	 
	.release-btn text {
		display: block;
		color: #fff;
		width: 80px;
		height: 28px;
		line-height: 28px;
		text-align: center;
		text-decoration: none;
		font-size: 15px;
	}
	 
	/*后期添加的留言框*/
	.show {
		width: 600px;
		margin: 20px auto;
	}
	 
	.show-content {
		width: 578px;
		border: 1px solid #ccc;
		border-radius: 4px;
		margin-bottom: 10px;
		padding: 10px;
		position: relative;
		overflow: hidden;
	}
	 
	.show-name {
		width: 100%;
		text-align: left;
		font-size: 14px;
		color: #333;
		font-weight: bold;
	}
	 
	.show-txt {
		width: 100%;
		color: #444;
		font-size: 14px;
		margin-top: 10px;
	}
	 
	.show-txt p {
		width: 100%;
		word-wrap: break-word;
	}
	 
	.show-time {
		font-size: 12px;
		color: #808080;
		margin-top: 10px;
	}
	 
	.show-close {
		position: absolute;
		top: 10px;
		right: 10px;
		font-size: 24px;
		color: #ccc;
		cursor: pointer;
		transition: .5s;
	}
	 
	.show-close:hover {
		color: red;
	}
	
</style>