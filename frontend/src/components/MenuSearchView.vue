<template>

    <v-data-table
        :headers="headers"
        :items="menuSearch"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'MenuSearchView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
            ],
            menuSearch : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/menuSearches'))

            temp.data._embedded.menuSearches.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.menuSearch = temp.data._embedded.menuSearches;
        },
        methods: {
        }
    }
</script>

