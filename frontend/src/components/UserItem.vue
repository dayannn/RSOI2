<template>
    <div class = "user-item shadow" style="margin-bottom: 10px">
        <b-card >
            <p class="card-text">
                <strong>Name:</strong> {{user.name}} {{user.lastName}}<br/>
                <strong>Login:</strong> {{user.login}}<br/>
                <strong>Rating:</strong> {{user.rating}}<br/>
                <strong>Reviews:</strong> {{user.reviewsNum}}<br/>
            </p>
            <b-button class="btn btn-info btn-sm shadowed-button">Изменить</b-button>
            <b-button class="btn btn-danger btn-sm shadowed-button" @click="deleteUser">Удалить</b-button>

            <!--<b-modal id="modalConfirmUserDelete"-->
                     <!--ref="modal"-->
                     <!--title="Вы действительно ходите удалить поьзователя?"-->
                     <!--@ok="handleOk">-->
            <!--</b-modal>-->
        </b-card>

    </div>
</template>

<script>
    export default{
        name: "UserItem",
        props: ["user"],
        methods:{
            deleteUser(){
                this.$emit('del-user', this.user.id)
            }
        }
    }
</script>

<style scoped>
    .user-item{
        background: #f4f4f4;
        padding: 5px;
        border-bottom: 1px #ccc solid;
        text-align: left;
        width: 50%;
        margin: auto;
    }

    .shadowed-button {
        box-shadow: 0 4px 5px 0 rgba(0, 0, 0, .14), 0 1px 10px 0 rgba(0, 0, 0, .12), 0 2px 4px -1px rgba(0, 0, 0, .2)
    }
    .shadowed-button:hover{
        box-shadow: 0 2px 2px 0 rgba(0, 0, 0, .14), 0 3px 1px -2px rgba(0, 0, 0, .2), 0 1px 5px 0 rgba(0, 0, 0, .12)
    }

</style>
