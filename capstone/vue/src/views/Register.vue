<template>
  <div id="register" class="text-center">
    <div>
      <b-container fluid>
    <b-form @submit="register">
      <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
      <!-- <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>       -->
     
      <b-form-group id="username-group"
       label="Username:" label-for="username">
      <b-form-input
      
        id="username"
        placeholder="Username"
        v-model="user.username"
        required
        
      ></b-form-input>
      </b-form-group>
      <b-form-group
       label="Password" label-for="password">
      <b-form-input
       
        id="password"
        
        placeholder="Password"
        v-model="user.password"
        required
        
      ></b-form-input>
      </b-form-group>
      <b-form-group
       label="Confirm Password" label-for="confirm-password">
      <b-form-input
       
        id="confirm-password"
        
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
        
      ></b-form-input>
      </b-form-group>
     
      <b-button pill type="submit" variant="primary">
        Create Account
      </b-button>
    </b-form>
      </b-container>
    </div>
    <router-link :to="{ name: 'login' }">Have an account?</router-link>
   
  </div>
 
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'register',
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style></style>
