<template>
  <div>${{user.balance}}</div>
</template>

<script>
import TransferService from '../services/TransferService'

export default {
name: "menu-panel",
data() {
  return {
   user: {
    userId: null,
    accountId: null,
    balance: null,   
   },
  }
},
created() {
  TransferService.balance().then((response) => {
    this.user.userId = response.data.userId;
    this.user.accountId = response.data.accountId;
    this.user.balance = response.data.balance;
  })
}
}
</script>

<style>

</style>