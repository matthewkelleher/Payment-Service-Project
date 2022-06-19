import axios from 'axios';

export default {

//   login(user) {
//     return axios.post('/login', user)
//   },

//   register(user) {
//     return axios.post('/register', user)
//   }

    balance(user) {
    return axios.get('/account/balance', user)
    },

    transfer(transfer, user) {
        return axios.put('/transfer', transfer, user)
    },

    list(user) {
        return axios.get('/transfer', user)
    },

    request(transfer) {
        return axios.post('/request', transfer)
    },

    pending(user) {
        return axios.get('/pending', user)
    },

    approve(transfer) {
        return axios.put('/approve', transfer)
    },

    reject(transfer) {
        return axios.put('/reject', transfer)
    }
  

}