<template>
    <ContentField class="col-8" style="margin-top:15px">
        <div class="row justify-content-md-center">
            <div style="width:68.3%">
                <div class="text-center">
                    <label id="title" style="font-size:150%;">登记采购财务信息</label>
                </div>
                <div class="text-center">
                    <label>采购产品名称</label>
                </div>
                <div class="mb-3">
                    <input v-model="info.stuff_name" type="text" class="form-control">
                </div>
                <div class="text-center">
                    <label>采购数量</label>
                </div>
                <div class="mb-3">
                    <input v-model="info.quantity" type="text" class="form-control" placeholder="如：2只">
                </div>
                <div class="text-center">
                    <label>采购费用</label>
                </div>
                <div class="mb-3">
                    <input v-model="info.price" type="text" class="form-control" placeholder="如：100元">
                </div>
                <div class="text-center">
                    <label>采购日期</label>
                </div>
                <div class="mb-3">
                    <input v-model="info.date" type="date" class="form-control text-center">
                </div>
                <div class="text-center">
                    <label>采购地点</label>
                </div>
                <div class="mb-3">
                    <input v-model="info.position" type="text" class="form-control">
                </div>
                <div class="text-center">
                    <label>负责采购的人员姓名</label>
                </div>
                <div class="mb-3">
                    <input v-model="info.staff_name" type="text" class="form-control">
                </div>
                <div class="text-center">
                    <label>批准采购的人员姓名</label>
                </div>
                <div class="mb-3">
                    <input v-model="info.confirmed_staff_name" type="text" class="form-control">
                </div>
                <div class="error_message  text-center">
                    {{ info.error_message }}
                </div>
                <div style="display: flex; justify-content: center; margin-top:10px;">
                    <button data-bs-toggle="modal" data-bs-target="#register-stuff-modal" type="button"
                        class="btn btn-primary my_button_3">登记</button>
                    <!-- Modal -->
                    <div class="modal fade" id="register-stuff-modal" tabindex="-1">
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
                                    确认登记？
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-primary" @click="register_stuff">确认</button>
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">放弃</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </ContentField>
</template>

<script>
import ContentField from "@/components/ContentField.vue";
import $ from 'jquery';
import { reactive } from 'vue';
import { Modal } from 'bootstrap/dist/js/bootstrap';

export default {
    components: {
        ContentField
    },
    setup () {
        //定义info全局变量
        const info = reactive({
            stuff_name: "",
            quantity: "",
            price: "",
            position: "",
            date: "",
            staff_name: "",
            confirmed_staff_name: "",
            error_message: "",
        });

        //查询房间的函数
        const register_stuff = () => {
            $.ajax({
                url: "http://localhost:3000/purchase_staff/register_stuff/",
                type: "post",
                data: {
                    stuff_name: info.stuff_name,
                    quantity: info.quantity,
                    price: info.price,
                    position: info.position,
                    date: info.date,
                    staff_name: info.staff_name,
                    confirmed_staff_name: info.confirmed_staff_name,

                },
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("jwt_token"),
                },
                success (resp) {
                    if (resp.error_message === "success") {
                        //登记成功
                        info.error_message = "登记成功！";
                        //清空信息
                        info.stuff_name = "";
                        info.quantity = "";
                        info.price = "";
                        info.position = "";
                        info.date = "";
                        info.staff_name = "";
                        info.confirmed_staff_name = "";
                        //将模态框关闭
                        Modal.getInstance("#register-stuff-modal").hide();
                        setTimeout(() => {
                            info.error_message = ""
                        }, 1000);
                    } else {
                        //否则将错误信息显示出来
                        info.error_message = resp.error_message;
                        //将模态框关闭
                        Modal.getInstance("#register-stuff-modal").hide();
                    }
                }
            })
        }

        return {
            info,
            register_stuff,
        }
    }
}
</script>

<style scoped>
.error_message {
    color: red;
}

::placeholder {
    text-align: center;
    font-size: 14px;
}

#title {
    margin: 10px auto;
    font-size: 20px;
    padding-bottom: 10px;
}

.my_button_3 {
    width: 30%;
}
</style>