<template>
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="card" style="margin-top:20px;">
                    <div class="card-header">
                        <!-- 给字体添加样式要借助span标签！ -->
                        <span style="font-size:140%;">
                            客房信息
                        </span>
                        <!-- float-end类是让组件向右对齐的意思！！！ -->
                        <button type="button" class="btn btn-primary float-end" data-bs-toggle="modal"
                            data-bs-target="#add-room-modal" @click="refresh_roomadd">
                            新建客房
                        </button>
                        <!-- Modal -->
                        <div class="modal fade" id="add-room-modal" tabindex="-1">
                            <!-- modal-xl是让模态框变大 -->
                            <div class="modal-dialog modal-l">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5">
                                            新建客房
                                        </h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="mb-3">
                                            <label for="add-room-number" class="form-label">客房编号</label>
                                            <input v-model="roomadd.number" type="text" class="form-control"
                                                id="add-room-number">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-room-area" class="form-label">客房面积</label>
                                            <input v-model="roomadd.area" type="text" class="form-control"
                                                id="add-room-area">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-room-price" class="form-label">单日价格</label>
                                            <input v-model="roomadd.price" type="text" class="form-control"
                                                id="add-room-price">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-room-type" class="form-label">客房类型</label>
                                            <br>
                                            <select name="type" id="add_type">
                                                <option value="双床房">双床房</option>
                                                <option value="大床房">大床房</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="modal-footer text-center">
                                        <div class="error_message">{{ roomadd.error_message }}</div>
                                        <button type="button" class="btn btn-primary" @click="add_room">新建</button>
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">放弃</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>客房编号</th>
                                    <th>客房类型</th>
                                    <th>客房面积</th>
                                    <th>单日价格</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="room in rooms" :key="room.number">
                                    <td>{{ room.number }}</td>
                                    <td>{{ room.type }}</td>
                                    <td>{{ room.area }} (㎡)</td>
                                    <td>{{ room.price }} 元</td>
                                    <td>
                                        <button type="button" class="btn btn-warning" data-bs-toggle="modal"
                                            :data-bs-target="'#update-room-modal' + room.number"
                                            @click="load_roomupdate(room)">修改</button>
                                        <!-- Modal -->
                                        <div class="modal fade" :id="'update-room-modal' + room.number" tabindex="-1">
                                            <!-- modal-xl是让模态框变大 -->
                                            <div class="modal-dialog modal-l">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title fs-5">
                                                            修改客房信息
                                                        </h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="mb-3">
                                                            <label for="update-room-number" class="form-label">客房编号</label>
                                                            <input v-model="roomupdate.number" type="text"
                                                                class="form-control" id="update-room-number">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="update-room-area" class="form-label">客房面积</label>
                                                            <input v-model="roomupdate.area" type="text"
                                                                class="form-control" id="update-room-area">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="update-room-price" class="form-label">单日价格</label>
                                                            <input v-model="roomupdate.price" type="text"
                                                                class="form-control" id="update-room-price">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="update-room-type" class="form-label">客房类型</label>
                                                            <br>
                                                            <select name="type" :id="'update_type' + room.number">
                                                                <option value="双床房">双床房</option>
                                                                <option value="大床房">大床房</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer text-center">
                                                        <div class="error_message">{{ roomupdate.error_message }}</div>
                                                        <button type="button" class="btn btn-warning"
                                                            @click="update_room(room)">修改</button>
                                                        <button type="button" class="btn btn-secondary"
                                                            data-bs-dismiss="modal">放弃</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <button type="button" class="btn btn-danger" data-bs-toggle="modal"
                                            :data-bs-target="'#remove-room-modal' + room.number" style="margin-left:25px;"
                                            @click="refresh_roomremove">删除</button>
                                        <!-- Modal -->
                                        <div class="modal fade" :id="'remove-room-modal' + room.number" tabindex="-1">
                                            <!-- modal-sm是让模态框变小 -->
                                            <div class="modal-dialog modal-sm modal-dialog-centered">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title fs-5">
                                                            提示
                                                        </h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        是否删除该房间？
                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="error_message">{{ roomremove.error_message
                                                        }}</div>
                                                        <button type="button" class="btn btn-danger"
                                                            @click="remove_room(room)">删除</button>
                                                        <button type="button" class="btn btn-secondary"
                                                            data-bs-dismiss="modal">放弃</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { ref, reactive } from 'vue';
import $ from 'jquery';
import { Modal } from 'bootstrap/dist/js/bootstrap';

export default {
    setup () {
        //要展示的room列表
        let rooms = ref([]);

        //刷新room列表的函数
        const refresh_rooms = () => {
            $.ajax({
                url: "http://localhost:3000/front_desk_staff/get_all_room_list/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    rooms.value = resp;
                }
            })
        }

        //在加载页面时就执行的函数
        refresh_rooms();

        //利用reactive定义roomadd全局变量
        const roomadd = reactive({
            number: "",
            area: "",
            price: "",
            type: "",
            error_message: "",
        });

        //清空roomadd全局变量的函数
        const refresh_roomadd = () => {
            roomadd.number = "";
            roomadd.area = "";
            roomadd.price = "";
            roomadd.type = "";
            roomadd.error_message = "";
        }

        //新建room函数
        const add_room = () => {
            //清空报错信息
            roomadd.error_message = "";
            $.ajax({
                url: "http://localhost:3000/front_desk_staff/add_room/",
                type: "post",
                data: {
                    number: roomadd.number,
                    area: roomadd.area,
                    price: roomadd.price,
                    type: document.getElementById("add_type").value,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        roomadd.error_message = "新建客房成功！";
                        setTimeout(() => {
                            //新建room成功，清空变量的值，并将模态框关闭
                            refresh_roomadd;
                            Modal.getInstance("#add-room-modal").hide();
                            //刷新一下room列表
                            refresh_rooms();
                        }, 1000);
                    } else {
                        //否则将错误信息显示出来
                        roomadd.error_message = resp.error_message;
                    }
                }
            })
        }

        //利用reactive定义roomremove全局变量
        const roomremove = reactive({
            error_message: "",
        });

        const refresh_roomremove = () => {
            roomremove.error_message = "";
        }

        //删除room函数
        const remove_room = (room) => {
            $.ajax({
                url: "http://localhost:3000/front_desk_staff/remove_room/",
                type: "post",
                data: {
                    number: room.number,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        //删除room成功，将模态框关闭
                        Modal.getInstance("#remove-room-modal" + room.number).hide();
                        //刷新一下room列表
                        refresh_rooms();
                    } else {
                        roomremove.error_message = resp.error_message;
                    }
                },
            })
        }


        //利用reactive定义roomupdate全局变量
        const roomupdate = reactive({
            number: "",
            area: "",
            price: "",
            type: "",
            error_message: "",
        });

        //用于将当前选中要更新的room信息加载到模态框里
        const load_roomupdate = (room) => {
            roomupdate.error_message = "";
            roomupdate.number = room.number;
            roomupdate.area = room.area;
            roomupdate.price = room.price;
            roomupdate.type = room.type;
        }

        //更新room函数
        const update_room = (room) => {
            $.ajax({
                url: "http://localhost:3000/front_desk_staff/update_room/",
                type: "post",
                data: {
                    number_origin: room.number,
                    number: roomupdate.number,
                    area: roomupdate.area,
                    price: roomupdate.price,
                    type: document.getElementById("update_type" + room.number).value,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        roomupdate.error_message = "修改客房成功！";
                        setTimeout(() => {
                            //更新room成功，将模态框关闭
                            Modal.getInstance("#update-room-modal" + room.number).hide();
                            //刷新一下room列表
                            refresh_rooms();
                        }, 1000);
                    } else {
                        //否则将错误信息显示出来
                        roomupdate.error_message = resp.error_message;
                    }
                }
            })
        }

        return {
            rooms,
            roomadd,
            roomupdate,
            roomremove,
            add_room,
            remove_room,
            update_room,
            refresh_roomadd,
            load_roomupdate,
            refresh_roomremove,
        }
    }
}

</script>

<style scoped>
.error_message {
    color: red;
}
</style>