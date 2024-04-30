<template>
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="card" style="margin-top:20px;">
                    <div class="card-header">
                        <!-- 给字体添加样式要借助span标签！ -->
                        <span style="font-size:140%;">
                            点餐
                        </span>
                        <span style="font-size: 130%; margin-left:400px;">总价格：{{ order.total_price }} 元
                        </span>
                        <button type="button" class="btn btn-success float-end" style="font-size: 110%; margin-left:40px"
                            data-bs-toggle="modal" data-bs-target="#submit-modal"
                            @click="refresh_order_error_message">提交订单</button>
                        <!-- Modal -->
                        <div class="modal fade" id="submit-modal" tabindex="-1">
                            <!-- modal-sm是让模态框变小 -->
                            <div class="modal-dialog modal-sl modal-dialog-centered" style="height: 500px;">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5">
                                            提示
                                        </h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        确定提交订单？
                                    </div>
                                    <div class="modal-footer">
                                        <span class="text-start" style="font-size: 100%;color:red;">{{
                                            order.error_message }}</span>
                                        <button type="button" class="btn btn-success" @click="submit">确定</button>
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
                                    <th>数量</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="dish in dishes" :key="dish.number">
                                    <td>{{ dish.number }}</td>
                                    <td>{{ dish.name }}</td>
                                    <td>{{ dish.price }} 元</td>
                                    <td>
                                        <!-- 计数器 -->
                                        <div class="counter">
                                            <span style="margin-right:20px">{{ dish.quantity }}</span>
                                            <button @click="incrementQuantity(dish)" id="incrementButton">+</button>
                                            <button @click="decrementQuantity(dish)" id="decrementButton">-</button>
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
import $ from 'jquery';
import { ref, reactive } from 'vue';
import { Modal } from 'bootstrap/dist/js/bootstrap';

export default {
    setup () {
        //要展示的dishes列表
        let dishes = ref([]);

        // 创建一个空的 Map 对象
        const dishMap = ref(new Map());

        // 将菜品及数量添加到 Map 中
        const addDishToMap = (dish) => {
            if (dish.quantity > 0)
                dishMap.value.set(dish.number, dish.quantity);
        };
        //利用reactive定义order全局变量
        const order = reactive({
            total_price: 0,
            error_message: "",
        });

        const compute_total_price = () => {
            let totalPrice = 0;
            for (const dish of dishes.value) {
                totalPrice += dish.quantity * dish.price;
            }
            order.total_price = totalPrice;
        }
        //增加数量的函数
        const incrementQuantity = (dish) => {
            dish.quantity++;
            compute_total_price();
        }

        //减少数量的函数
        const decrementQuantity = (dish) => {
            if (dish.quantity > 0) {
                dish.quantity--;
                compute_total_price();
            }
        }

        //获取menu的函数
        const get_menu = () => {
            $.ajax({
                url: "http://localhost:3000/restaurant_staff/get_menu/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    //查询menu成功
                    dishes.value = resp.map(dish => {
                        return { ...dish, quantity: 0 };
                    });
                }
            })
        }
        get_menu();

        //提交餐饮订单的函数
        const submit = () => {
            if (order.total_price === 0) {
                order.error_message = "不能提交空的订单！";
            } else {
                //将dish存入dishMap中
                for (const dish of dishes.value) {
                    addDishToMap(dish);
                }
                // 将 dishMap 转换为 JSON 字符串并传递给后端
                const dishMapJson = JSON.stringify(Array.from(dishMap.value.entries()));

                $.ajax({
                    url: "http://localhost:3000/restaurant_staff/submit_order/",
                    type: "post",
                    data: {
                        dishMap: dishMapJson,
                    },
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                    },
                    success (resp) {
                        if (resp.error_message === "success") {
                            //提交订单成功
                            order.error_message = "提交订单成功！";
                            // 清空dishMap
                            dishMap.value.clear();
                            //清空缓存
                            for (const dish of dishes.value) {
                                dish.quantity = 0;
                            }
                            order.total_price = 0;
                            setTimeout(() => {
                                Modal.getInstance("#submit-modal").hide();
                            }, 1000);
                        }
                    }
                })
            }
        }

        const refresh_order_error_message = () => {
            order.error_message = "";
        }

        return {
            incrementQuantity,
            decrementQuantity,
            dishes,
            order,
            get_menu,
            submit,
            compute_total_price,
            refresh_order_error_message,

        }
    }
}
</script>

<style scoped>
.input-wrapper {
    display: inline-block;
    margin-left: 10px;
    margin-right: 30px;
    /* 调整输入框之间的间距 */
}

#decrementButton {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    width: 30px;
    height: 30px;
    border-radius: 50%;
    background-color: blue;
    color: black;
    font-size: 20px;
    cursor: pointer;
    margin: 0 5px;
    background-color: transparent
}

#incrementButton {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    width: 30px;
    height: 30px;
    border-radius: 50%;
    color: black;
    font-size: 20px;
    cursor: pointer;
    margin: 0 5px;
    background-color: transparent
}
</style>