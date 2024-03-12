<template>
  <view class="container">
	  <view class="example">
	    <uni-forms ref="form" :model="user" labelWidth="80px">
	      <uni-forms-item label="用户姓名" name="userName">
	        <uni-easyinput v-model="user.nickName" placeholder="请输入姓名" />
	      </uni-forms-item>
	      <uni-forms-item label="手机号码" name="phonenumber">
	        <uni-easyinput v-model="user.phonenumber" placeholder="请输入手机号码" />
	      </uni-forms-item>
	    </uni-forms>
      <button type="primary" @click="updateUserInfo">提交</button>
    </view>
  </view>
</template>

<script>
	import {updateUser} from "@/api/system/user"
	import {
	    mapState 
	} from 'vuex';  
  export default {
    data() {
		return {
			user: {
				userId:undefined,
				nickName: "",
				phonenumber: ""
			},
			registing: false,
			rules: {
			  nickName: {
			    rules: [{
			      required: true,
			      errorMessage: '用户姓名不能为空'
			    }]
			  },
			  phonenumber: {
			    rules: [{
			      required: true,
			      errorMessage: '手机号码不能为空'
			    }, {
			      pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
			      errorMessage: '请输入正确的手机号码'
			    }]
			  }
			}
		}
    },
	computed: {
		...mapState(['hasLogin','userInfo'])
	},
	onLoad(){
		this.$store.dispatch('GetInfo').then(res => {
			this.user = res.user;
			console.log("用户信息",this.user)
		})
		
	},
    methods: {
		updateUserInfo() {
			let updateData = {'userId':this.user.userId,'nickName':this.user.nickName,'phonenumber':this.user.phonenumber}
			updateUser(updateData).then(response => {
				this.$modal.msgSuccess("修改成功")
				this.$store.dispatch('GetInfo').then(res => {
					setTimeout(function() {
						 uni.navigateBack();
					}, 1000);
				})
			}).catch(() => {
			})
		},
    }
  }
</script>

<style lang="scss">
  page {
    background-color: #ffffff;
  }

  .example {
    padding: 15px;
    background-color: #fff;
  }

  .segmented-control {
    margin-bottom: 15px;
  }

  .button-group {
    margin-top: 15px;
    display: flex;
    justify-content: space-around;
  }

  .form-item {
    display: flex;
    align-items: center;
    flex: 1;
  }

  .button {
    display: flex;
    align-items: center;
    height: 35px;
    line-height: 35px;
    margin-left: 10px;
  }
</style>
