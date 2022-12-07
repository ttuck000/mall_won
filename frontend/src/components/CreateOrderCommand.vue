<template>

    <v-card outlined>
        <v-card-title>
            CreateOrder
        </v-card-title>

        <v-card-text>
            <String label="Adress" v-model="value.adress" :editMode="editMode"/>
            <Number label="OrderQty" v-model="value.orderQty" :editMode="editMode"/>
            <Number label="OrderId" v-model="value.orderId" :editMode="editMode"/>
            <String label="OrderSubject" v-model="value.orderSubject" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="createOrder"
            >
                CreateOrder
            </v-btn>
            
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="close"
            >
                Close
            </v-btn>
        </v-card-actions>
    </v-card>

</template>

<script>
   
    export default {
        name: 'CreateOrderCommand',
        components:{},
        props: {},
        data: () => ({
            editMode: true,
            value: {},
        }),
        created() {
            this.value.adress = '';
            this.value.orderQty = 0;
            this.value.orderId = 0;
            this.value.orderSubject = '';
        },
        watch: {
        },
        methods: {
            createOrder() {
                this.$emit('createOrder', this.value);
            },
            close() {
                this.$emit('closeDialog');
            },
            change() {
                this.$emit('input', this.value);
            },
        }
    }
</script>

