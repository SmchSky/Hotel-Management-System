<template>
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="card" style="margin-top:20px;">
                    <div class="card-header">
                        <!-- 给字体添加样式要借助span标签！ -->
                        <span style="font-size:140%;">
                            菜品信息
                        </span>
                        <!-- float-end类是让组件向右对齐的意思！！！ -->
                        <button type="button" class="btn btn-primary float-end" data-bs-toggle="modal"
                            data-bs-target="#add-dish-modal" @click="refresh_dishadd">
                            新建菜品
                        </button>
                        <!-- Modal -->
                        <div class="modal fade" id="add-dish-modal" tabindex="-1">
                            <!-- modal-xl是让模态框变大 -->
                            <div class="modal-dialog modal-l">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5">
                                            新建菜品
                                        </h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="mb-3">
                                            <label for="add-dish-name" class="form-label">菜品名称</label>
                                            <input v-model="dishadd.name" type="text" class="form-control"
                                                id="add-dish-name">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-dish-price" class="form-label">菜品单价</label>
                                            <input v-model="dishadd.price" type="text" class="form-control"
                                                id="add-dish-price">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-dish-status" class="form-label">菜品状态</label>
                                            <br>
                                            <select name="status" id="add_status">
                                                <option value="有库存">有库存</option>
                                                <option value="无库存">无库存</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="modal-footer text-center">
                                        <div class="error_message">{{ dishadd.error_message }}</div>
                                        <button type="button" class="btn btn-primary" @click="add_dish">新建</button>
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
                                    <th>菜品编号</th>
                                    <th>菜品名称</th>
                                    <th>菜品单价</th>
                                    <th>菜品状态</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="dish in dishes" :key="dish.number">
                                    <td>{{ dish.number }}</td>
                                    <td>{{ dish.name }}</td>
                                    <td>{{ dish.price }} 元</td>
                                    <td>{{ dish.status }} </td>
                                    <td>
                                        <button type="button" class="btn btn-warning" data-bs-toggle="modal"
                                            :data-bs-target="'#update-dish-modal' + dish.number"
                                            @click="load_dishupdate(dish)">修改</button>
                                        <!-- Modal -->
                                        <div class="modal fade" :id="'update-dish-modal' + dish.number" tabindex="-1">
                                            <!-- modal-xl是让模态框变大 -->
                                            <div class="modal-dialog modal-l">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title fs-5">
                                                            修改菜品信息
                                                        </h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="mb-3">
                                                            <label for="update-dish-name" class="form-label">菜品名称</label>
                                                            <input v-model="dishupdate.name" type="text"
                                                                class="form-control" id="update-dish-name">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="update-dish-price" class="form-label">菜品单价</label>
                                                            <input v-model="dishupdate.price" type="text"
                                                                class="form-control" id="update-dish-price">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="update-dish-status" class="form-label">菜品状态</label>
                                                            <br>
                                                            <select name="status" :id="'update_status' + dish.number">
                                                                <option value="有库存">有库存</option>
                                                                <option value="无库存">无库存</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer text-center">
                                                        <div class="error_message">{{ dishupdate.error_message }}</div>
                                                        <button type="button" class="btn btn-warning"
                                                            @click="update_dish(dish)">修改</button>
                                                        <button type="button" class="btn btn-secondary"
                                                            data-bs-dismiss="modal">放弃</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <button type="button" class="btn btn-danger" data-bs-toggle="modal"
                                            :data-bs-target="'#remove-dish-modal' + dish.number"
                                            style="margin-left:15px;">删除</button>
                                        <!-- Modal -->
                                        <div class="modal fade" :id="'remove-dish-modal' + dish.number" tabindex="-1">
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
                                                        确认删除该菜品？
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-danger"
                                                            @click="remove_dish(dish)">删除</button>
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
        //要展示的dish列表
        let dishes = ref([]);

        //刷新dish列表的函数
        const refresh_dishes = () => {
            $.ajax({
                url: "http://localhost:3000/restaurant_staff/get_all_dish_list/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    dishes.value = resp;
                }
            })
        }

        //在加载页面时就执行的函数
        refresh_dishes();

        //利用reactive定义dishadd全局变量
        const dishadd = reactive({
            number: "",
            name: "",
            price: "",
            status: "",
            error_message: "",
        });

        //清空dishadd全局变量的函数
        const refresh_dishadd = () => {
            dishadd.number = "";
            dishadd.name = "";
            dishadd.price = "";
            dishadd.status = "";
            dishadd.error_message = "";
        }

        //新建dish函数
        const add_dish = () => {
            //清空报错信息
            dishadd.error_message = "";
            $.ajax({
                url: "http://localhost:3000/restaurant_staff/add_dish/",
                type: "post",
                data: {
                    name: dishadd.name,
                    price: dishadd.price,
                    status: document.getElementById("add_status").value,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        dishadd.error_message = "新建菜品成功！";
                        setTimeout(() => {
                            //新建dish成功，清空变量的值，并将模态框关闭
                            refresh_dishadd;
                            Modal.getInstance("#add-dish-modal").hide();
                            //刷新一下dish列表
                            refresh_dishes();
                        }, 1000);
                    } else {
                        //否则将错误信息显示出来
                        dishadd.error_message = resp.error_message;
                    }
                }
            })
        }

        //删除dish函数
        const remove_dish = (dish) => {
            $.ajax({
                url: "http://localhost:3000/restaurant_staff/remove_dish/",
                type: "post",
                data: {
                    number: dish.number,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        //删除room成功，将模态框关闭
                        Modal.getInstance("#remove-dish-modal" + dish.number).hide();
                        //刷新一下dishes列表
                        refresh_dishes();
                    }
                },
            })
        }


        //利用reactive定义dishupdate全局变量
        const dishupdate = reactive({
            name: "",
            status: "",
            price: "",
            error_message: "",
        });

        //用于将当前选中要更新的dish信息加载到模态框里
        const load_dishupdate = (dish) => {
            dishupdate.error_message = "";
            dishupdate.number = dish.number;
            dishupdate.name = dish.name;
            dishupdate.price = dish.price;
            dishupdate.status = dish.type;
        }

        //更新dish函数
        const update_dish = (dish) => {
            $.ajax({
                url: "http://localhost:3000/restaurant_staff/update_dish/",
                type: "post",
                data: {
                    number: dish.number,
                    name: dishupdate.name,
                    price: dishupdate.price,
                    status: document.getElementById("update_status" + dish.number).value,
                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        dishupdate.error_message = "修改菜品成功！";
                        setTimeout(() => {
                            //更新room成功，将模态框关闭
                            Modal.getInstance("#update-dish-modal" + dish.number).hide();
                            //刷新一下room列表
                            refresh_dishes();
                        }, 1000);
                    } else {
                        //否则将错误信息显示出来
                        dishupdate.error_message = resp.error_message;
                    }
                }
            })
        }

        return {
            dishes,
            dishadd,
            dishupdate,
            add_dish,
            update_dish,
            refresh_dishadd,
            load_dishupdate,
            remove_dish,
        }
    }
}

</script>

<style scoped>
.error_message {
    color: red;
}
</style>