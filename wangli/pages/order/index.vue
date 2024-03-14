
<script>
	import { getOrderListApi } from '@/api/order/index.js'
	
	export default {
		data() {
			return {
				orderList: []
			}
		},
		created() {
			this.getOrderList()
		},
		methods: {
			async getOrderList () {
				const res = await getOrderListApi()
				const { code, data } = res
				if(code === 200) {
					this.orderList = [ ...data.records ]
				}
			},
			mapState (state) {
				const obj = {
					WAITING: '代付款',
					PAID: '已付款',
					CANCELLED: '已取消'
				}
				
				if(!obj[state]) return ''
				return obj[state]
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
		},
	}
	
</script>

<template>
	<view>
		<block v-for="item of orderList" :key="item.id">
			<block v-for="product of item.products" :key="product.id">
				<view class="order_main">
					<image :src="product.url.replace(';', '')" mode="aspectFit"></image>
					<view class="order_conetent">
						<view class="order_info">
							<view>
								<text class="title">商品名: </text>
								<text style="margin-left: 24px;">{{product.material}}</text>
							</view>
							<view>
								<text class="title">下单时间: </text>
								<text style="margin-left: 24px;">{{convertDateTimeFormat(item.createTime)}}</text>
							</view>
							<view>
								<text class="title">总价: </text>
								<text class="order_price">¥ {{product.price * product.productNum}}</text>
							</view>
						</view>
						<text style="display: flex; width: 200px;">{{item.deliveryAddress}}</text>
						<text style="margin-left: 240; font-size: 24px; color: #ccc;">{{mapState(item.orderState)}}</text>
					</view>
				</view>
			</block>
		</block>
	</view>
</template>

<style scoped>
	
	.order_main {
		display: flex;
		margin-bottom: 10px;
	}
	
	.order_conetent {
		display: flex;
		align-items: center;
		justify-content: space-between;
		margin-left: 40px;
		width: 900px;
	}
	
	.order_info {
		display: flex; 
		flex-direction: column; 
		gap: 30px; 
		width: 500px;
	}
	
	.order_price {
		font-weight: 700;
		font-size: 20px;
		color: red;
		margin-left: 20px;
	}
	
	.title {
		font-weight: 700;
		font-size: 17px;
	}
</style>