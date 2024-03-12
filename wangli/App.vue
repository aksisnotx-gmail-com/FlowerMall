<script>
  import config from './config'
  import store from '@/store'
  import { getToken } from '@/utils/auth'
  import {mapMutations} from 'vuex';

  export default {
    onLaunch: function() {
      this.initApp()
    },
    methods: {
		...mapMutations(['login']),
		onLaunch: function(options) {
			// let path = options.path;
			// let ticket = options.query.ticket;
			// if(undefined != path && undefined != ticket){
			// 	uni.navigateTo({
			// 		url: "/"+path+'?ticket='+ticket
			// 	})
			// }else{
			// 	let userInfo = uni.getStorageSync('userInfo') || '';
			// 	uni.switchTab({
			// 		url: `/pages/public/request/index`
			// 	})
			// }
			uni.navigateTo({
				url: "/pages/index/index"
			})
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		},
      // 初始化应用
      initApp() {
        // 初始化应用配置
        this.initConfig()
        // 检查用户登录状态
        //#ifdef H5
        // this.checkLogin()
        //#endif
      },
      initConfig() {
        this.globalData.config = config
      },
      checkLogin() {
        // if (!getToken()) {
        //   this.$tab.reLaunch('/pages/public/login') 
        // }
      }
    }
  }
</script>

<style lang="scss">
  @import '@/static/scss/index.scss'
</style>
