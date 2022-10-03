<template>
  <div id="app"
       v-on:keyup.enter="calculate"
  >
    <div class="row_container">
      <div class="row"></div>
      <div class="col">A</div>
      <div class="col">B</div>
      <div class="col">C</div>
      <div class="col">D</div>
    </div>

    <div class="row_container">
      <div class="row">1</div>
      <Cell
          v-bind:address = "'A1'"
          ref="A1"
      />
      <Cell
          v-bind:address = "'B1'"
          ref="B1"
      />
      <Cell
          v-bind:address = "'C1'"
          ref="C1"
      />
      <Cell
          v-bind:address = "'D1'"
          ref="D1"
      />
    </div>

    <div class="row_container">
      <div class="row">2</div>
      <Cell
          v-bind:address = "'A2'"
          ref="A2"
      />
      <Cell
          v-bind:address = "'B2'"
          ref="B2"
      />
      <Cell
          v-bind:address = "'C2'"
          ref="C2"
      />
      <Cell
          v-bind:address = "'D2'"
          ref="D2"
      />
    </div>

    <div class="row_container">
      <div class="row">3</div>
      <Cell
          v-bind:address = "'A3'"
          ref="A3"
      />
      <Cell
          v-bind:address = "'B3'"
          ref="B3"
      />
      <Cell
          v-bind:address = "'C3'"
          ref="C3"
      />
      <Cell
          v-bind:address = "'D3'"
          ref="D3"
      />
    </div>

    <div class="row_container">
      <div class="row">4</div>
      <Cell
          v-bind:address = "'A4'"
          ref="A4"
      />
      <Cell
          v-bind:address = "'B4'"
          ref="B4"
      />
      <Cell
          v-bind:address = "'C4'"
          ref="C4"
      />
      <Cell
          v-bind:address = "'D4'"
          ref="D4"
      />
    </div>

    <br>
    <div> {{error_message}}</div>
  </div>
</template>

<script>
import Cell from "@/components/Cell";
import RestService from "@/services/RestService";

export default {
  name: 'App',
  components: {Cell},
  data() {
    return {
      request: [],
      result: null,
      cells: new Map(),
      error_message: null
    }
  },
  methods: {
    calculate() {
      this.request = []
      this.cells.forEach((value, key) => this.request.push({address: key, value: value}))
      console.log(process.env.API_ENDPOINT)
      RestService.calculateCells(this.request)
          .then((response) => {
                this.error_message = null
                this.result = response.data
                this.result.data.forEach((cell) => {
                  let address = cell.address
                  this.$refs[address].calculated_value = cell.value
                  this.$refs[address].value = cell.value
                })
              },
              (error) => {
                console.log(error.response.data)
                let address = error.response.data.cellAddress
                this.$refs[address].value = "ОШИБКА"
                this.error_message = error.response.data.message
              }
          )
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
.row_container{
 display: flex;
  flex-direction: row;
  width: 650px;
  margin-left: auto;
  margin-right: auto;
}
.col{
  width: 150px;
  height: 30px;
  text-align: center;
}
.row{
  width: 50px;
  height: 30px;
  text-align: center;
  vertical-align: middle;
  display: table-cell;
}
</style>
