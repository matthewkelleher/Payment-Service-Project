import axios from 'axios';

export default {


    balance(user) {
    return axios.get('/account/balance', user)
    },

    send(transfer, user) {
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
    },

    getUserId(username) {
        console.log("fired")
        return axios.get(`/id/${username}`)
    },
    getUsernames(username) {
        return axios.get(`/usernames/${username}`)
    }
  

}