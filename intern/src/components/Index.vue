<template>
	<div class="full">
		<FundSystemHeader></FundSystemHeader>
	  <el-main style="padding:60px 0px 0px 0px;height:100%;">
	 		<FundSystemMain></FundSystemMain>
	  </el-main>
  </div>
</template>

<script>
import FundSystemHeader from '@/components/FundSystemHeader'
import FundSystemMain from '@/components/FundSystemMain'

export default {
  name: 'Index',
  components: {
    FundSystemHeader,
    FundSystemMain
  },
  data () {
  	return{
  	
  	}
  }
}
</script>

<style>

.full{
  height:100%;
  position:absolute;
  width:100%;
},

.el-container.is-vertical {
  height:-webkit-fill-available
}
</style>
