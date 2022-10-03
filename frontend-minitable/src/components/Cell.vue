<template>
    <input
        v-model = "value"
        v-on:focus="showUserValue"
        v-on:focusout="setValues"
        v-on:keyup.enter="removeFocus"
        class="cell"
    />
</template>

<script>
export default {
  props: ['address'],
  data() {
    return {
      user_value: '',
      calculated_value: '',
      value: '',
      isActive: false
    }
  },
  methods: {
    removeFocus() {
      this.$el.blur()
    },
    showUserValue() {
      this.value = this.user_value
      this.isActive = true
    },
    setValues() {
      this.user_value = this.value.toUpperCase()
      if(this.calculated_value === '') {
        this.value = this.user_value
      } else {
        this.value = this.calculated_value
      }
      this.isActive = false
      if(this.user_value !=='') {
        this.$parent.cells.set(this.address, this.user_value)
      }
    },
    getUpdatedValue(data) {
      this.calculated_value = data
      this.value = data
    }
  }
}
</script>

<style>
.cell {
  width: 150px;
  height: 30px;
}
</style>