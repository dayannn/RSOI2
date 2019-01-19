<template>
    <div class="users" style="background: #eeeeee">
        <h1>Пользователи</h1>
        <b-button
                v-b-toggle.collapse1 class="btn btn-info btn-sm" style="margin: 10px">Добавить пользователя</b-button>
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
                this.updateData();
            },
            addUser(user){
                axios.post('api/users', user)
                    .then(this.updateData())
                    .catch(err => console.log(err));

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

</style>