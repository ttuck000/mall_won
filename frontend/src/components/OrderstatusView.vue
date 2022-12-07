<template>

    <v-data-table
        :headers="headers"
        :items="orderstatus"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'OrderstatusView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
                { text: "status", value: "status" },
            ],
            orderstatus : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/orderstatuses'))

            temp.data._embedded.orderstatuses.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.orderstatus = temp.data._embedded.orderstatuses;
        },
        methods: {
        }
    }
</script>

