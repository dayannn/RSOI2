<template>
    <div class="users" >
        <h1>Пользователи</h1>
        <b-button
                v-b-toggle.collapse1 class="btn btn-info btn-sm shadowed-button" style="margin: 10px">Добавить пользователя</b-button>
        <b-collapse id="collapse1" v-model="showCollapse" class="mt-2">
            <AddUserForm style="width: 50%; margin: auto"
                         v-on:hide-add-user-form="hideAddUserForm"
                         v-on:add-user="addUser"/>
        </b-collapse>
        <div v-bind:key="user.id" v-for="user in users">
            <UserItem v-bind:user="user" v-on:del-user="deleteUser"/>
        </div>
    </div>
</template>

<script>
    import UserItem from "../components/UserItem";
    import AddUserForm from '../components/AddUserForm'
    import axios from 'axios';
    export default {
        name: "users",
        components: {UserItem, AddUserForm},
        data(){
            return{
                users:[],
                showCollapse: false
            }
        },
        methods: {
            deleteUser(id){
                axios.delete(`/api/users/${id}`)
                    .then()
                    .catch(err => console.log(err));
                setTimeout(() => {this.updateData()}, 500);
            },
            addUser(user){
                axios.post('api/users', user)
                    .then(this.hideAddUserForm)
                    .catch(err => console.log(err));
                setTimeout(() => {this.updateData()}, 500);
            },
            updateData(){
                axios.get('/api/users')
                    .then(res => this.users = res.data)
                    .catch(err => console.log(err));
            },
            hideAddUserForm(){
                this.showCollapse = false;
            }
        },
        created() {
            this.updateData();
        }
    }
</script>

<style scoped>
    .shadowed-button {
        box-shadow: 0 4px 5px 0 rgba(0, 0, 0, .14), 0 1px 10px 0 rgba(0, 0, 0, .12), 0 2px 4px -1px rgba(0, 0, 0, .2)
    }
    .shadowed-button:hover{
        box-shadow: 0 2px 2px 0 rgba(0, 0, 0, .14), 0 3px 1px -2px rgba(0, 0, 0, .2), 0 1px 5px 0 rgba(0, 0, 0, .12)
    }
</style>